package com.board.boardpractice.controller;

import com.board.boardpractice.config.SecurityConfig;
import com.board.boardpractice.dto.ArticleCommentDto;
import com.board.boardpractice.dto.UserAccountDto;
import com.board.boardpractice.dto.request.ArticleCommentRequest;
import com.board.boardpractice.service.ArticleCommentService;
import com.board.boardpractice.util.FormDataEncoder;
import org.hibernate.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View Controller - ArticleComment")
@Import({SecurityConfig.class, FormDataEncoder.class})
@WebMvcTest(ArticleCommentController.class) // 해당 컨트롤러만 읽음 -> 가벼워짐
class ArticleCommentControllerTest {

    private final MockMvc mvc;
    private final FormDataEncoder formDataEncoder;

    @MockBean private ArticleCommentService articleCommentService;

    public ArticleCommentControllerTest(@Autowired MockMvc mvc, @Autowired FormDataEncoder formDataEncoder) {
        this.mvc = mvc;
        this.formDataEncoder = formDataEncoder;
    }

    @DisplayName("[view][POST] 댓글 등록 - 정상 호출")
    @Test
    void givenArticleCommentInfo_whenRequesting_thenSavesNewArticleComment() throws Exception {
        //given
        long articleId = 1L;
        ArticleCommentRequest request = ArticleCommentRequest.of(articleId, "new article comment");
        willDoNothing().given(articleCommentService).saveArticleComment(request.toDto(any(UserAccountDto.class)));

        //when & then
        mvc.perform(
                        post("/comments/new")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(formDataEncoder.encode(request))
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId))
                .andExpect(redirectedUrl("/articles/" + request.articleId()));
        then(articleCommentService).should().saveArticleComment(any(ArticleCommentDto.class));
    }

    @DisplayName("[view][POST] 댓글 삭제 - 정상 호출")
    @Test
    void givenArticleCommentInfoToDelete_whenRequesting_thenDeletesArticleComment() throws Exception {
        //given
        long articleId = 1L;
        long articleCommentId = 1L;
        willDoNothing().given(articleCommentService).deleteArticleComment(articleCommentId);

        //when & then
        mvc.perform(
                        post("/comments/" + articleCommentId + "/delete")
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .content(formDataEncoder.encode(Map.of("articleId", articleId)))
                                .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/articles/" + articleId))
                .andExpect(redirectedUrl("/articles/" + articleId));
        then(articleCommentService).should().deleteArticleComment(articleCommentId);
    }
}