package com.LSJ.Blog.dto.article;


import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleModifyForm {
    @NotBlank(message = "수정할 제목을 입력해 주세요.")
    private String title;
    @NotBlank(message = "수정할 내용을 입력해 주세요.")
    private String body;
    private Long categoryId;

    public ArticleModifyForm(Article findArticle){
        this.title = findArticle.getTitle();
        this.body = findArticle.getBody();

        if(findArticle.getCategory() != null){
            this.categoryId = findArticle.getCategory().getId();
        }else{
            categoryId = null;
        }

    }
}
