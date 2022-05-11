package com.LSJ.Blog.controller;

import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Category;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.dto.article.ArticleDTO;
import com.LSJ.Blog.dto.article.ArticleListDTO;
import com.LSJ.Blog.dto.article.ArticleModifyForm;
import com.LSJ.Blog.dto.article.ArticleSaveForm;
import com.LSJ.Blog.dto.member.MemberModifyForm;
import com.LSJ.Blog.service.ArticleService;
import com.LSJ.Blog.service.CategoryService;
import com.LSJ.Blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    private  final CategoryService categoryService;

    @GetMapping("/articles/write")
    public String showWrite(Model model, ArticleSaveForm articleSaveForm)
    {
        model.addAttribute("articleSaveForm",articleSaveForm);
        model.addAttribute("categoryList",categoryService.findAll());
        return "usr/article/write";

    }

    @PostMapping("/articles/write")
    public String doWrite(@Validated ArticleSaveForm articleSaveForm, BindingResult bindingResult, Principal principal, Model model){

        if (bindingResult.hasErrors()){

            model.addAttribute("categoryList",categoryService.findAll());
            return "usr/article/write";
        }

        try {
            Category findCategory = categoryService.findById(articleSaveForm.getCategoryId());
            Member findMember = memberService.findByLoginId(principal.getName());

            articleService.save(
                    articleSaveForm,
                    findMember,
                    findCategory
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
        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("selectedCategory",findArticle.getCategory().getId());

        return "usr/article/modify";

    }
    @PostMapping("/articles/modify/{id}")
    public String doModify(@PathVariable(name = "id")Long id, @Validated ArticleModifyForm articleModifyForm, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "usr/article/modify";
        }

        Category findCategory = categoryService.findById(articleModifyForm.getCategoryId());
        articleService.modifyArticle(articleModifyForm, id, findCategory);

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

    @GetMapping("/articles/delete/{id}")
    public String deleteArticle(@PathVariable(name = "id")Long id, Principal principal){

        //id번 게시글의 로그인 아이디 =? principal
        Article findArticle = articleService.findById(id);

        if (!findArticle.getMember().getLoginId().equals(principal.getName())){
            return "redirect:/";
        }

        articleService.delete(id);
        return "redirect:/articles";
    }


}
