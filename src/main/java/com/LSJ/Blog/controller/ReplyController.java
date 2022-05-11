package com.LSJ.Blog.controller;

import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.dto.reply.ReplySaveForm;
import com.LSJ.Blog.service.ArticleService;
import com.LSJ.Blog.service.MemberService;
import com.LSJ.Blog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.PrintStream;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final MemberService memberService;
    private final ArticleService articleService;

    @PostMapping("/articles/{article_id}/reply")
    public String writeReply(@PathVariable(name = "article_id") Long articleId, ReplySaveForm replySaveForm, Principal principal){

        Member findMember = memberService.findByLoginId(principal.getName());
        Article findArticle = articleService.findById(articleId);

        replyService.writeReply(replySaveForm, findMember, findArticle);

        return "redirect:/articles/" + articleId;
    }
}
