package com.LSJ.Blog.service;

import com.LSJ.Blog.dao.MemberRepository;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.dto.member.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberSaveForm memberSaveForm) {

        Member member = Member.createMember(
                memberSaveForm.getLoginId(),
                memberSaveForm.getLoginPw(),
                memberSaveForm.getName(),
                memberSaveForm.getNickname(),
                memberSaveForm.getEmail()
        );
        memberRepository.save(member);
    }
}
