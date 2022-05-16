package com.LSJ.Blog.service;

import com.LSJ.Blog.dao.MyBlogRepository;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.domain.MyBlog;
import com.LSJ.Blog.dto.myblog.MyBlogSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyBlogService {

    private final MemberService memberService;
    private final MyBlogRepository myBlogRepository;

    public boolean ownBlog(String loginId){
        Member findMember = memberService.findByLoginId(loginId);

        boolean isExistMember = myBlogRepository.existsByMember(findMember);
        return isExistMember;
    }

    @Transactional
    public void createBlog(MyBlogSaveForm myBlogSaveForm, String loginId) {

        MyBlog myBlog = MyBlog.createMyBlog(myBlogSaveForm);
        Member findMember = memberService.findByLoginId(loginId);
        myBlog.setMember(findMember);

        myBlogRepository.save(myBlog);

    }
}
