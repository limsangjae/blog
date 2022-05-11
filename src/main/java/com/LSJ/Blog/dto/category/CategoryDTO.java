package com.LSJ.Blog.dto.category;

import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private List<Article> articles;

    public CategoryDTO(Category category){
        this.id = category.getId();
        this.name = category.getName();
        this.articles = category.getArticles();
    }

}
