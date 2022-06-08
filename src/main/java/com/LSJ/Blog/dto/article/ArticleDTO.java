package com.LSJ.Blog.dto.article;

import com.LSJ.Blog.domain.Article;
import com.LSJ.Blog.domain.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private Long id;

    private String title;

    private String body;

    private String nickname;

    private String categoryName;

    private LocalDateTime regDate;

    private LocalDateTime updateDate;

    private List<Reply> replies;

    public ArticleDTO(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.body = article.getBody();
        this.nickname = article.getMember().getNickname();

        if(article.getCategory() != null){
            this.categoryName = article.getCategory().getName();
        }else{
            this.categoryName = null;
        }


        this.regDate = article.getRegDate();
        this.updateDate = article.getUpdateDate();
        this.replies = article.getReplies();

    }

}
