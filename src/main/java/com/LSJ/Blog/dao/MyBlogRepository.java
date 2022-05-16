package com.LSJ.Blog.dao;

import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.domain.MyBlog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyBlogRepository extends JpaRepository<MyBlog, Long> {

    boolean existsByMember(Member member);

}
