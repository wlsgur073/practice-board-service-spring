package com.board.boardpractice.repository;

import com.board.boardpractice.domain.Article;
import com.board.boardpractice.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>, // 엔티티에 몯느 필드에 대한 기본 검색 기능 추가 // 전체 검색밖에 안됨
        QuerydslBinderCustomizer<QArticle> // 부분 검색 등 부가 기능을 만들기 위해
{
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        /*
        * java 8 이후로 interface Override method도 body를 수정할 수 있다.
        * repository layer에서 직접 구현체를 만들지 않고 JPA의 인터페이스만으로 기능을 사용하게끔 접근하고 있기에
        * default method를 사용하는 것이 적절할 것 같다.
        * */
        bindings.excludeUnlistedProperties(true); // 선택적으로 검색이 가능하게 exclude
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy); // 검색 가능 필터링 필드
//        기본 설정은 exact matching으로 title의 문자열이 처음부터 끝까지 equals 해야지만 검색 가능한 상태.
//        bindings.bind(root.title).first((path, value) -> path.eq(value));
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // sql query : like '${v}'
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // sql query : like '%${v}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    };
}
