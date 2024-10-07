package com.board.boardpractice.service;

import com.board.boardpractice.domain.Article;
import com.board.boardpractice.domain.UserAccount;
import com.board.boardpractice.domain.constant.SearchType;
import com.board.boardpractice.dto.ArticleDto;
import com.board.boardpractice.dto.ArticleWithCommentsDto;
import com.board.boardpractice.repository.ArticleRepository;
import com.board.boardpractice.repository.UserAccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final UserAccountRepository userAccountRepository;
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
    public ArticleWithCommentsDto getArticleWithComments(long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    @Transactional(readOnly = true)
    public ArticleDto getArticle(long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: " + articleId));
    }

    public void saveArticle(ArticleDto dto) {
        UserAccount userAccount = userAccountRepository.getReferenceById(dto.userAccountDto().userId()); // DB에서 UserAccount 참조
        articleRepository.save(dto.toEntity(userAccount)); // 참조된 UserAccount로 Article 엔티티 생성 및 저장
    }

    public void updateArticle(Long articleId, ArticleDto dto) {
        try {
            Article article = articleRepository.getReferenceById(articleId);

            if (dto.title() != null) { article.setTitle(dto.title()); }
            if (dto.content() != null) { article.setContent(dto.content()); }
            article.setHashtag(dto.hashtag());
        } catch (EntityNotFoundException e) {
            log.warn("게시글 업데이트 실패. 게시글을 찾을 수 없습니다 - dto: {}", dto);
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

    @Transactional(readOnly = true)
    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }

    @Transactional(readOnly = true)
    public long getArticleCount() {
        return articleRepository.count();
    }
}
