package com.LSJ.Blog.dto.article;

import com.LSJ.Blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleListDTO {

    private Long id;
    private String title;
    private String nickname;
    private LocalDateTime regDate;

    public ArticleListDTO(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.nickname = article.getMember().getNickname();
        this.regDate = article.getRegDate();
    }

}
