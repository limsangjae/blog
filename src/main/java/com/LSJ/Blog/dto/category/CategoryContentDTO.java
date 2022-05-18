package com.LSJ.Blog.dto.category;

import com.LSJ.Blog.dto.article.ArticleDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryContentDTO {
    private String name;
    private int ArticleAmount;
    private List<ArticleDTO> articleList;
}
