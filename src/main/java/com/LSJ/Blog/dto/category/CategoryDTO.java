package com.LSJ.Blog.dto.category;

import com.LSJ.Blog.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;

    private String name;

   public CategoryDTO(Category category){
       this.id = category.getId();
       this.name = category.getName();
   }
}
