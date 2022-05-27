package com.LSJ.Blog.controller;

import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Category;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.dto.article.ArticleDTO;
import com.LSJ.Blog.dto.article.ArticleListDTO;
import com.LSJ.Blog.dto.article.ArticleModifyForm;
import com.LSJ.Blog.dto.article.ArticleSaveForm;
import com.LSJ.Blog.dto.member.MemberModifyForm;
import com.LSJ.Blog.dto.reply.ReplyListDTO;
import com.LSJ.Blog.service.ArticleService;
import com.LSJ.Blog.service.CategoryService;
import com.LSJ.Blog.service.MemberService;
import com.LSJ.Blog.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    private  final MemberService memberService;
    private  final CategoryService categoryService;

    private  final ReplyService replyService;

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

            return "redirect:/b/" + principal.getName() + "?category=" + findCategory.getName();

        }catch (IllegalStateException e){
            model.addAttribute("err_msg", e.getMessage());
            return "usr/article/write";
        }

    }
    @GetMapping("/articles/modify/{id}")
    public String showModify(@PathVariable(name = "id") int id, Principal principal, Model model){

        //Article findArticle = articleService.findById(id);

        Member findMember = memberService.findByLoginId(principal.getName());
        Article findArticle = findMember.getArticles().get(id);

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
    public String doModify(@PathVariable(name = "id")Long id, @Validated ArticleModifyForm articleModifyForm, BindingResult bindingResult,Principal principal){

        if (bindingResult.hasErrors()){
            return "usr/article/modify";
        }

        Member findMember = memberService.findByLoginId(principal.getName());

        Category findCategory = categoryService.findById(articleModifyForm.getCategoryId());

        articleService.modifyArticle(articleModifyForm, id, findCategory);

        return "redirect:/b/" + principal.getName() + "?category=" + findCategory.getName();

    }
    @GetMapping("/articles")
    public String showList(
            @RequestParam(name = "searchKeyword", defaultValue = "")String searchKeyword,
            @RequestParam(name = "page", defaultValue = "1") int currentPage,
            @RequestParam(name = "categoryId",defaultValue = "0")Long categoryId,
            Model model){

        Map<String, Object> articleListInfo = articleService.searchArticle(categoryId, currentPage, searchKeyword);

        int articleListCount = (int) Math.ceil((int)articleListInfo.get("articleListCount") / (double)10);

        model.addAttribute("searchArticle",articleListInfo.get("articleDTOList"));
        model.addAttribute("page", currentPage);
        model.addAttribute("maxSize",articleListCount);

        return "usr/article/list";
    }

    @GetMapping("/articles/{id}")
    public String showDetail(@PathVariable(name = "id") Long id, Model model){

        ArticleDTO findArticle = articleService.getArticle(id);

        model.addAttribute("article", findArticle);
        model.addAttribute("replyList", replyService.getDtoList());

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
