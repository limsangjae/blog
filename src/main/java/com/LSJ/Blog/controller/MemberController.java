package com.LSJ.Blog.controller;


import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.dto.member.MemberModifyForm;
import com.LSJ.Blog.dto.member.MemberSaveForm;
import com.LSJ.Blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

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
    
    @GetMapping("/members/modify/{loginId}")
    public String showModify(@PathVariable(name = "loginId") String loginId, Principal principal, Model model){

        Member findMember = memberService.findByLoginId(loginId);

        if(!findMember.getLoginId().equals(principal.getName())){
            return "redirect:/";
        }
        model.addAttribute("memberModifyForm", new MemberModifyForm(findMember));
        model.addAttribute("loginId",loginId);

        return "usr/member/modify";

    }
    @PostMapping("/members/modify/{loginId}")
    public String doModify(@PathVariable(name = "loginId")String loginID,MemberModifyForm memberModifyForm ){

        memberService.modifyMember(memberModifyForm,loginID);

        return "redirect:/";

    }

}
