package com.board.boardpractice.dto;

import java.io.Serializable;

/**
 * DTO for {@link com.board.boardpractice.domain.Article}
 */
public record ArticleUpdateDto(
                               String title,
                               String content,
                               String hashtag
) {
    public static ArticleUpdateDto of(String title, String content, String hashtag) {
        return new ArticleUpdateDto(title, content, hashtag);
    }
}