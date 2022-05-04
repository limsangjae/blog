package com.LSJ.Blog.dto.category;

import com.LSJ.Blog.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModifyForm {
    private Long id;
    private String name;

    public CategoryModifyForm(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }
}
