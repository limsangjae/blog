package com.LSJ.Blog.controller;


import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.dto.member.CheckStatus;
import com.LSJ.Blog.dto.member.MemberModifyForm;
import com.LSJ.Blog.dto.member.MemberSaveForm;
import com.LSJ.Blog.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members/join")
    public  String showJoin(Model model, MemberSaveForm memberSaveForm) {

        model.addAttribute("memberSaveForm", memberSaveForm);
        return  "usr/member/join";

    }

    @PostMapping("/members/join")
    public String doJoin(@Validated MemberSaveForm memberSaveForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "usr/member/join";
        }

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
    public String doModify(@PathVariable(name = "loginId")String loginID,@Validated MemberModifyForm memberModifyForm ,BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "usr/member/modify";
        }

        memberService.modifyMember(memberModifyForm,loginID);

        return "redirect:/";

    }
    @GetMapping("/members/find/pw")
    public String showFindPw(){
        return "usr/member/findPw";
    }

    @DeleteMapping("/members")
    @ResponseBody
    public boolean doDelete(@RequestBody String loginId, Principal principal){

        if(!loginId.equals(principal.getName())){
            return false;
        }

        SecurityContextHolder.clearContext();

        memberService.deleteMember(loginId);

        return true;

    }
    @RequestMapping("/members/check/id")
    @ResponseBody
    public CheckStatus checkDuple(@RequestParam String loginId){

        boolean isExists = memberService.isDupleMember(loginId);

        CheckStatus checkStatus = new CheckStatus(isExists);

        return checkStatus;

    }
    @RequestMapping("/members/check/nickname")
    @ResponseBody
    public CheckStatus checkNickname(@RequestParam String nickname){

        boolean isExists = memberService.idDupleNickname(nickname);

        CheckStatus checkStatus = new CheckStatus(isExists);

        return checkStatus;
    }

    @RequestMapping("/members/check/email")
    @ResponseBody
    public CheckStatus checkEmail(@RequestParam String email){
        boolean isExists = memberService.isDupleEmail(email);

        CheckStatus checkStatus =  new CheckStatus(isExists);

        return checkStatus;
    }
}
