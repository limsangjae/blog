package com.LSJ.Blog.controller;

import com.LSJ.Blog.domain.MyBlog;
import com.LSJ.Blog.dto.article.ArticleDTO;
import com.LSJ.Blog.dto.myblog.MyBlogMainDTO;
import com.LSJ.Blog.dto.myblog.MyBlogSaveForm;
import com.LSJ.Blog.service.MyBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/b")
@RequiredArgsConstructor
public class MyBlogController {

    private final MyBlogService myBlogService;

    @GetMapping("/{loginId}/blog")
    private String showMyBlog(@PathVariable(name = "loginId")String loginId, Principal principal){

        if(!principal.getName().equals(loginId)){
            return "redirect:/";
        }

        // myblog가 없을 때
        if(!myBlogService.ownBlog(loginId)){
            return "usr/blog/createBlog";
        }else if(myBlogService.ownBlog(loginId)){
            return "redirect:/b/" + loginId;
        }

        return "usr/member/myblog";
    }

    @PostMapping("/{loginId}/blog")
    private String createBlog(
            @PathVariable(name = "loginId")String loginId,
            @Validated MyBlogSaveForm myBlogSaveForm,
            BindingResult bindingResult,
            Principal principal){

        if(!principal.getName().equals(loginId)){
            return "redirect:/";
        }
        if(myBlogService.ownBlog(loginId)){
            return "redirect:/b/" + loginId;
        }

        myBlogService.createBlog(myBlogSaveForm, loginId);

        return "redirect:/";
    }
    @GetMapping("/{loginId}")
    public String showMyBlog(@PathVariable(name = "loginId")String loginId,
                             @RequestParam(value = "category", required = false, defaultValue = "all") String categoryName,
                             Model model){

        MyBlogMainDTO myBlogMainDto = myBlogService.getMyBlogMainDto(loginId);

        List<ArticleDTO> articleByLoginId = myBlogService.getArticleByLoginId(loginId);

        if(categoryName.equals("all")){
            model.addAttribute("articleList", articleByLoginId);
        }else if(categoryName.equals("none")){
            model.addAttribute("articleList", myBlogService.getNoCategoryArticleList(loginId));
        }else{
            model.addAttribute("articleList", myBlogService.getArticleByCategoryName(categoryName));
        }

        model.addAttribute("loginId", loginId);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("info", myBlogMainDto);

        return "usr/blog/blogMain";

    }

    @GetMapping("/{loginId}/articles/{id}")
    public String showDetail(@PathVariable(name = "loginId")String loginId,
                             @PathVariable(name = "id") int id,
                             Model model
                             ){

        List<ArticleDTO> findArticle = myBlogService.getArticleByLoginId(loginId);

        model.addAttribute("articleId", id);
        model.addAttribute("article", findArticle);

        return "usr/article/detail";
    }


}
