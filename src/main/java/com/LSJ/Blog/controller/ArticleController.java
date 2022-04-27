package com.LSJ.Blog.controller;

import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.dto.article.ArticleDTO;
import com.LSJ.Blog.dto.article.ArticleListDTO;
import com.LSJ.Blog.dto.article.ArticleModifyForm;
import com.LSJ.Blog.dto.article.ArticleSaveForm;
import com.LSJ.Blog.dto.member.MemberModifyForm;
import com.LSJ.Blog.service.ArticleService;
import com.LSJ.Blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    private  final MemberService memberService;

    @GetMapping("/articles/write")
    public String showWrite(){
        return "usr/article/write";
    }

    @PostMapping("/articles/write")
    public String doWrite(ArticleSaveForm articleSaveForm, Principal principal, Model model){
        try {
            Member findMember = memberService.findByLoginId(principal.getName());

            articleService.save(
                    articleSaveForm,
                    findMember
            );
        }catch (IllegalStateException e){
            model.addAttribute("err_msg", e.getMessage());
            return "usr/article/write";
        }

        return "redirect:/";
    }
    @GetMapping("/articles/modify/{id}")
    public String showModify(@PathVariable(name = "id") Long id, Principal principal, Model model){

        Article findArticle = articleService.findById(id);

        if(!findArticle.getMember().getLoginId().equals(principal.getName())){
            return "redirect:/";
        }
        model.addAttribute("articleModifyForm", new ArticleModifyForm(findArticle));
        model.addAttribute("id",id);

        return "usr/article/modify";

    }
    @PostMapping("/articles/modify/{id}")
    public String doModify(@PathVariable(name = "id")Long id, ArticleModifyForm articleModifyForm){

        articleService.modifyArticle(articleModifyForm, id);

        return "redirect:/";

    }
    @GetMapping("/articles")
    public String showList(Model model){
        List<ArticleListDTO> articles = articleService.getArticleList();

        model.addAttribute("articles",articles);

        return "usr/article/list";
    }

    @GetMapping("/articles/{id}")
    public String showDetail(@PathVariable(name = "id") Long id, Model model){

        ArticleDTO findArticle = articleService.getArticle(id);

        model.addAttribute("article",findArticle);

        return "usr/article/detail";
    }


}
