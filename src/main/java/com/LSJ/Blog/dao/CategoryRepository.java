package com.LSJ.Blog.dao;

import com.LSJ.Blog.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category , Long> {

}
