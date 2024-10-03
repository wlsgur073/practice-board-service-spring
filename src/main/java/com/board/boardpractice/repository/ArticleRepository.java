package com.board.boardpractice.repository;

import com.board.boardpractice.domain.Article;
import com.board.boardpractice.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<Article> findByTitleContaining(String title, Pageable pageable);

    // Containing은 부분 검색을 의미한다.
    Page<Article> findByContentContaining(String content, Pageable pageable);

    // Article 안에 UserAccount가 필요하기에 이와 같이 네이밍함.
    Page<Article> findByUserAccount_UserIdContaining(String userId, Pageable pageable);

    // Containing하면 %% 와일드카드로 인하여 인덱싱을 못하게 될 수 있지만, 나중에 튜닝 요소로 놔두기로 하자.
    Page<Article> findByUserAccount_NicknameContaining(String nickname, Pageable pageable);

    Page<Article> findByHashtag(String hashtag, Pageable pageable); // 해시태그는 정확한 검색으로 설정

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true); // 선택적으로 검색이 가능하게 exclude
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy); // 검색 가능 필터링 필드
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // sql query : like '%${v}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    };
}
