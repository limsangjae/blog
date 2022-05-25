package com.LSJ.Blog.controller;

import com.LSJ.Blog.dto.member.FindPasswordForm;
import com.LSJ.Blog.service.MailService;
import com.LSJ.Blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    private final MemberService memberService;

    @ResponseBody
    @PostMapping("/mails/find/pw")
    public boolean getForgetPassword(@RequestBody FindPasswordForm findPasswordForm){

        if(!memberService.isDupleMember(findPasswordForm.getLoginId())){
            System.out.println("없는 아이디 입니다.");
            return false;
        }

        try{
            mailService.sendMail(findPasswordForm);
        }catch (Exception e){
            return false;
        }

        return true;

    }
}
