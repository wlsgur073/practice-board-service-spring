package com.board.boardpractice.domain;

import java.time.LocalDateTime;

public class ArticleComment {

    private Long id;
    private Article article;
    private String content;


    // common
    private LocalDateTime createAt;
    private String createBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;


}
