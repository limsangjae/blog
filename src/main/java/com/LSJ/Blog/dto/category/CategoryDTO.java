package com.LSJ.Blog.dto.category;

import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String title;


    public void setCategoryDTO(Category category , Article article) {
        this.id = category.getId();
        this.name = category.getName();
        this.title = article.getTitle();
    }
}
