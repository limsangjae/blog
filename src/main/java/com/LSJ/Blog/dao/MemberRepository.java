package com.LSJ.Blog.dao;

import com.LSJ.Blog.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MemberRepository extends JpaRepository<Member,Long> {
}
