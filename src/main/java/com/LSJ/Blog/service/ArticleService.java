package com.LSJ.Blog.service;

import com.LSJ.Blog.dao.ArticleRepository;
import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.dto.article.ArticleDTO;
import com.LSJ.Blog.dto.article.ArticleListDTO;
import com.LSJ.Blog.dto.article.ArticleModifyForm;
import com.LSJ.Blog.dto.article.ArticleSaveForm;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public void save(ArticleSaveForm articleSaveForm, Member member) {
        Article article = Article.createArticle(
            articleSaveForm.getTitle(),
            articleSaveForm.getBody()
        );

        article.setMember(member);

        articleRepository.save(article);
    }
    public Article findById(Long id){
        Optional<Article> articleOptional = articleRepository.findById(id);
        articleOptional.orElseThrow(
                ()-> new IllegalStateException("존재하지 않는 게시글 입니다.")
        );
        return articleOptional.get();
    }

    @Transactional
    public void modifyArticle(ArticleModifyForm articleModifyForm, Long id) {

        Article findArticle = findById(id);

        findArticle.modifyArticle(
                articleModifyForm.getTitle(),
                articleModifyForm.getBody()
        );
    }

    public List<ArticleListDTO> getArticleList() {

        List<Article> articleList = articleRepository.findAll();

        List<ArticleListDTO> articleDTOList = new ArrayList<>();

        for(Article article : articleList){

            ArticleListDTO articleDTO = new ArticleListDTO(article);
            articleDTOList.add(articleDTO);

        }
        return articleDTOList;




    }
}
