package com.board.boardpractice.controller;

import com.board.boardpractice.domain.constant.SearchType;
import com.board.boardpractice.dto.response.ArticleResponse;
import com.board.boardpractice.dto.response.ArticleWithCommentsResponse;
import com.board.boardpractice.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@RequestMapping("/articles")
@Controller // view를 담을 거라서
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    public String articles(
            @RequestParam(name = "searchType", required = false) SearchType searchType,
            @RequestParam(name = "searchValue", required = false) String searchValue,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            ModelMap map
    ) {
        map.addAttribute("articles", articleService.searchArticles(searchType, searchValue, pageable).map(ArticleResponse::from));

        return "articles/index";
    }

    @GetMapping("/{articledId}")
    public String articles(@PathVariable("articledId") Long articledId, ModelMap map) {
        ArticleWithCommentsResponse article = ArticleWithCommentsResponse.from(articleService.getArticle(articledId));
        map.addAttribute("article", article);
        map.addAttribute("articlesComments", article.articleCommentResponse());

        return "articles/detail";
    }
}
