package com.LSJ.Blog.service;

import com.LSJ.Blog.dao.MyBlogRepository;
import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Category;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.domain.MyBlog;
import com.LSJ.Blog.dto.article.ArticleDTO;
import com.LSJ.Blog.dto.article.ArticleListDTO;
import com.LSJ.Blog.dto.category.CategoryDTO;
import com.LSJ.Blog.dto.myblog.MyBlogMainDTO;
import com.LSJ.Blog.dto.myblog.MyBlogSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyBlogService {

    private final MemberService memberService;

    private final CategoryService categoryService;

    private final ArticleService articleService;
    private final MyBlogRepository myBlogRepository;

    public boolean ownBlog(String loginId){
        Member findMember = memberService.findByLoginId(loginId);

        boolean isExistMember = myBlogRepository.existsByMember(findMember);
        return isExistMember;
    }

    @Transactional
    public void createBlog(MyBlogSaveForm myBlogSaveForm, String loginId) {

        MyBlog myBlog = MyBlog.createMyBlog(myBlogSaveForm);
        Member findMember = memberService.findByLoginId(loginId);
        myBlog.setMember(findMember);

        myBlogRepository.save(myBlog);

    }

    public MyBlogMainDTO getMyBlogMainDto(String loginId) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        Member findMember = memberService.findByLoginId(loginId);

        List<Category> categories = findMember.getCategories();

        for (Category category : categories){
            CategoryDTO categoryDTO = categoryService.getCategory(category.getName());
            categoryDTOList.add(categoryDTO);
        }
        MyBlogMainDTO myBlogMainDTO = new MyBlogMainDTO(findMember);
        myBlogMainDTO.setCategoryDTOList(categoryDTOList);

        return myBlogMainDTO;

    }

    public List<ArticleDTO> getArticles() {
        return articleService.getArticleDTOList();
    }

    public List<ArticleDTO> getArticleByCategoryName(String categoryName) {

        List<ArticleDTO> articleList = new ArrayList<>();
        CategoryDTO findCategory = categoryService.getCategory(categoryName);

        for(Article article : findCategory.getArticles()){
            ArticleDTO articleDTO = articleService.getArticle(article.getId());
            articleList.add(articleDTO);
        }
        return articleList;

    }
    public List<ArticleDTO> getArticleByLoginId(String loginId){
        Member findMember = memberService.findByLoginId(loginId);

        List<ArticleDTO> articles = articleService.getArticleDtoList(findMember.getArticles());

        return articles;
    }
}
