package com.LSJ.Blog.dao;

import com.LSJ.Blog.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category , Long> {

    Optional<Category> findByName(String name);
}
