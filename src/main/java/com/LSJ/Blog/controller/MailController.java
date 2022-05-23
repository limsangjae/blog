package com.LSJ.Blog.controller;

import com.LSJ.Blog.dto.member.FindPasswordForm;
import com.LSJ.Blog.service.MailService;
import com.LSJ.Blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    private final MemberService memberService;

    public boolean getForgetPassword(FindPasswordForm findPasswordForm){

        if(!memberService.isDupleMember(findPasswordForm.getLoginId())){
            System.out.println("없는 아이디 입니다.");
            return false;
        }

        return true;

    }
}
