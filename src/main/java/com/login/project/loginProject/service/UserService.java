package com.login.project.loginProject.service;

import com.login.project.loginProject.model.Member;
import com.login.project.loginProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Member login(Member member) {
        return userRepository.findById(member.getId()).orElseGet(Member::new);
    }

    public void join(Member member) {
        validateMember(member);
        userRepository.save(member);
    }

    private void validateMember(Member member) {
        userRepository.findByUsername(member.getUsername())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    public void modify(Member member) {
        Member persistance = userRepository.findById(member.getId()).orElseThrow(IllegalArgumentException::new);

        persistance.setUsername(member.getUsername());
        persistance.setPassword(member.getPassword());
        persistance.setEmail(member.getEmail());
    }
}