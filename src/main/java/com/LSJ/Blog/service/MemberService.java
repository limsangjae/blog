package com.LSJ.Blog.service;

import com.LSJ.Blog.config.Role;
import com.LSJ.Blog.dao.MemberRepository;
import com.LSJ.Blog.domain.Member;
import com.LSJ.Blog.dto.member.MemberModifyForm;
import com.LSJ.Blog.dto.member.MemberSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    public void save(MemberSaveForm memberSaveForm) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        Member member = Member.createMember(
                memberSaveForm.getLoginId(),
                bCryptPasswordEncoder.encode(memberSaveForm.getLoginPw()),
                memberSaveForm.getName(),
                memberSaveForm.getNickname(),
                memberSaveForm.getEmail(),
                Role.MEMBER
        );
        memberRepository.save(member);
    }

    public Member findByLoginId(String loginId) throws IllegalStateException {

        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        memberOptional.orElseThrow(
                () -> new IllegalStateException("존재하지 않는 회원입니다.")
        );
        return memberOptional.get();
    }

    @Override
    public UserDetails loadUserByUsername(String longinId) throws UsernameNotFoundException {
        return memberRepository.findByLoginId(longinId).orElseThrow(
                () -> new NoSuchElementException("해당 회원은 존재하지 않습니다.")
        );
    }
    @Transactional                          //새로 바뀐 데이터
    public Long modifyMember(MemberModifyForm memberModifyForm, String loginID) {

        Member findMember = findByLoginId(loginID); //바꾸기 전 데이터

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        findMember.modifyMember(
                bCryptPasswordEncoder.encode(memberModifyForm.getLoginPw()),
                memberModifyForm.getNickname(),
                memberModifyForm.getEmail()
        );

        return  findMember.getId();

    }
    @Transactional
    public void changePw(String pw, Member member){

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        member.changPw(bCryptPasswordEncoder.encode(pw));

    }
    public boolean isDupleMember(String loginId){
        return memberRepository.existsByLoginId(loginId);
    }
}
