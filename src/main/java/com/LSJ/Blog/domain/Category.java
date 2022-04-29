package com.LSJ.Blog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime regDate = LocalDateTime.now();

    private LocalDateTime updateDate = LocalDateTime.now();

    public static Category createCategory(Long id , String name){
        Category category = new Category();
        category.id = id;
        category.name = name;

        return category;
    }
    public void setMember(Member member){
        this.member = member;

    }
}
