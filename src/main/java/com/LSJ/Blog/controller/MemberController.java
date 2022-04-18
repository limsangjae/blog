package com.LSJ.Blog.controller;


import com.LSJ.Blog.dto.member.MemberSaveForm;
import com.LSJ.Blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/join")
    public  String showJoin(){
        return  "usr/member/join";
    }

    @PostMapping("/members/join")
    public String doJoin(MemberSaveForm memberSaveForm){

        memberService.save(memberSaveForm);

        return "redirect:/";
    }

    @GetMapping("/members/login")
    public String showLogin(){
        return "usr/member/login";
    }

}
