package com.LSJ.Blog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
=======
>>>>>>> 634bd51c0a5fc012ab2fa6d1f987dd353396c7cd

@Entity
@Getter
@NoArgsConstructor
public class Article {
    @Id
    @Column(name = "article_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "article_id") DB에 들어가는 필드 이름바꾸는법
    private Long id;

    private String title;

    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

<<<<<<< HEAD
    @OneToMany(mappedBy = "article")
    private List<Reply> replies = new ArrayList<>();

=======
>>>>>>> 634bd51c0a5fc012ab2fa6d1f987dd353396c7cd

    private LocalDateTime regDate = LocalDateTime.now();

    private LocalDateTime updateDate = LocalDateTime.now();

    public static  Article createArticle(String title, String body){
        Article article = new Article();
        article.title = title;
        article.body = body;

        return article;
    }

    public void setMember(Member member){
        this.member = member;
        member.getArticles().add(this);
    }

    public void setCategory(Category category){
        this.category = category;
        category.getArticles().add(this);
    }

    public void modifyArticle(String title, String body) {
        this.title = title;
        this.body = body;
    }
}
