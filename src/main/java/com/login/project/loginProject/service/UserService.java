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
        return userRepository.findById(member.getId()).orElseGet(Member::new); // 지렸다. 이게 인텔리제이인가 가슴이 웅장해진다.
    }
}
