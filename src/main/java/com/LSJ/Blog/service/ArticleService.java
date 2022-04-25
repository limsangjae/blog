package com.LSJ.Blog.service;

import com.LSJ.Blog.dao.ArticleRepository;
import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.dto.article.ArticleSaveForm;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

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
}
