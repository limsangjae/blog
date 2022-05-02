package com.LSJ.Blog.dto.article;


import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleModifyForm {
    private String title;
    private String body;
    private Long categoryId;

    public ArticleModifyForm(Article findArticle){
        this.title = findArticle.getTitle();
        this.body = findArticle.getBody();
        this.categoryId = findArticle.getCategory().getId();

    }
}
