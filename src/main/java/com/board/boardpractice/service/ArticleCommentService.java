package com.board.boardpractice.service;

import com.board.boardpractice.dto.ArticleCommentDto;
import com.board.boardpractice.repository.ArticleCommentRepository;
import com.board.boardpractice.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleCommentService {

    private final ArticleRepository articleRepository;

    private final ArticleCommentRepository articleCommentRepository;

    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComment(long articleId) {
        return List.of();
    }

    public void saveArticleComment(ArticleCommentDto dto) {
    }
}
