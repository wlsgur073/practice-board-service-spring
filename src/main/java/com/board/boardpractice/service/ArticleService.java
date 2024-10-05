package com.board.boardpractice.service;

import com.board.boardpractice.domain.Article;
import com.board.boardpractice.domain.constant.SearchType;
import com.board.boardpractice.dto.ArticleDto;
import com.board.boardpractice.dto.ArticleWithCommentsDto;
import com.board.boardpractice.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        // service는 도메인 코드와 dto만 알고 있다.
        // 이렇게 함으로써 컨트롤러에서는 실제로 dto밖에 알 수 없다.
        if (searchKeyword == null || searchKeyword.isBlank()) { // searchKeyword가 없을 경우
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        // java 13부터 switch lambda 표현식 가능
        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword, pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword, pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword, pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword, pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword, pageable).map(ArticleDto::from); // #을 자동으로할지 리팩토링 요소로 남겨둠
        };
    }

    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    public void updateArticle(ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(dto.id());

            // record는 getter, setter을 포함하며 getField()가 아닌 object.field 방식으로 get한다.
            if (dto.title() != null) { article.setTitle(dto.title()); }
            if (dto.content() != null) { article.setTitle(dto.content()); }
            article.setTitle(dto.hashtag());

            // class level Transactional에 의해 method 단위로 Transaction이 묶여 있어서
            // Transaction 끝날 때 영속성 컨텍스트는 해당 entity의 변경사항을 감지하고 update query를 실행 시킨다.
            // 따라서 save할 필요없음
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다. - dto: {}", dto);
        }
    }

    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);
    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if (hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }
        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);
    }

    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }

    public long getArticleCount() {
        return articleRepository.count();
    }
}
