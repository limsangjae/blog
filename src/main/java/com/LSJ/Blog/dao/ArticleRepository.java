package com.LSJ.Blog.dao;

import com.LSJ.Blog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    @Query(value = "SELECT * FROM `article` WHERE category_id = :categoryId AND `title` LIKE :searchKeyword ORDER BY `reg_date` DESC LIMIT 10 OFFSET :startIndex", nativeQuery = true)
    List<Article> findByKeywordAndPageWithCategory(
            @Param("categoryId") Long categoryId,
            @Param("searchKeyword") String searchKeyword,
            @Param("startIndex") int startIndex

    );
    @Query(value = "SELECT COUNT(*) AS 'totalCount' FROM `article` WHERE `category_Id` :categoryId AND `title` LIKE :searchKeyword ORDER BY `reg_date` DESC",nativeQuery = true)
    Integer countKeywordAndPageWithCategory(@Param("categoryId")Long categoryId, @Param("searchKeyword") String searchKeyword);


    @Query(value = "SELECT * FROM `article` WHERE `title` LIKE :searchKeyword ORDER BY `reg_date` DESC LIMIT 10 OFFSET :startIndex", nativeQuery = true)
    List<Article> findAllByKeywordAndPage(
            @Param("searchKeyword") String searchKeyword,
            @Param("startIndex") int startIndex);

    @Query(value = "SELECT COUNT(*) FROM `article` WHERE `title` LIKE :searchKeyword ORDER BY `reg_date` DESC", nativeQuery = true)
    Integer countByKeywordAndPage( @Param("searchKeyword") String searchKeyword);
}
