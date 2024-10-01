package com.board.boardpractice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/articles")
@Controller // view를 담을 거라서
public class ArticleController {

    @GetMapping
    public String articles(ModelMap map) {
        map.addAttribute("articles", List.of());

        return "articles/index";
    }

    @GetMapping("/{articledId}")
    public String articles(@PathVariable("articledId") Long articledId, ModelMap map) {
        map.addAttribute("article", "null"); // TODO: 구현할 때 실제 데이터 넣기
        map.addAttribute("articlesComments", List.of());

        return "articles/detail";
    }
}
