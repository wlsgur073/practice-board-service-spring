package com.board.boardpractice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
//@EntityListeners(AuditingEntityListener.class) // -> MappedSuperclass 클래스에 배치가 가능
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // mysql은 IDENTITY 방식으로 auto increase 됨
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;
    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    @Setter
    private String hashtag;

    // 양방향 설정
    @ToString.Exclude // 양방향 바인딩으로 인한 순환 참조(무한루프) 제외 처리
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // 댓글은 게시글에 속해야 되니까 cascade
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    // metadata EnableJpaAuditing 설정
//    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;
//    @CreatedBy @Column(nullable = false, length = 100) private String createdBy;
//    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;
//    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy;

    // 중복되는 column을 추출하는 방법은 2가지가 있다.
//    @MappedSuperclass : AuditingFields를 extends해서 사용
//    @Embedded : 공통 컬럼들을 모은 클래스를 하나의 필드로 설정하는 방법
//    당연하지만 주의사항으로 컬럼을 공통으로 사용하면 장단점이 있을 것이다.

    protected Article() {
    } // 외부에서 생성자 생성 못하게 설정

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // factory method 패턴
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    // 동일성 동등성 검사 - 모든 컬럼을 비교할 필요 없이 ID만 보면 되니, custom
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false; // pattern matching
        return Objects.equals(id, article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
