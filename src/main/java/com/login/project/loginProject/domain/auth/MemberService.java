package com.login.project.loginProject.domain.auth;

import com.login.project.loginProject.domain.member.domain.Member;
import com.login.project.loginProject.domain.member.domain.MemberRepository;
import com.login.project.loginProject.domain.member.domain.RoleType;
import com.login.project.loginProject.domain.member.dto.MemberDTO;
import javassist.bytecode.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

   private MemberRepository memberRepository;

   // 회원 정보
   @Transactional(readOnly = true)
   public Member lookup(Long id) {
      log.info("{} : 조회 성공!", id);
      return memberRepository.findById(id).orElseGet(Member::new);
   }

   // 회원 가입
   @Transactional
   public Member signUp(MemberDTO memberDTO) throws DuplicateMemberException {
      if (memberRepository.findByEmail(memberDTO.getEmail()).orElse(null) != null) {
         throw new DuplicateMemberException("이미 가입된 정보입니다.");
      }

      return Member.builder()
              .email(memberDTO.getEmail())
              .password(memberDTO.getPassword())
              .name(memberDTO.getName())
              .age(memberDTO.getAge())
              .roleType(RoleType.USER)
              .build();
   }
}
