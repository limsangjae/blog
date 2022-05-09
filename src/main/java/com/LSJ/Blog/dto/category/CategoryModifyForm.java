package com.LSJ.Blog.dto.category;

import com.LSJ.Blog.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryModifyForm {
    private Long id;
    @NotBlank(message = "수정할 이름을 입력해 주세요.")
    private String name;

    public CategoryModifyForm(Category category){
        this.id = category.getId();
        this.name = category.getName();
    }
}
