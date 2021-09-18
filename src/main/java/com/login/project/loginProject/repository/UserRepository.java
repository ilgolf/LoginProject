package com.login.project.loginProject.repository;

import com.login.project.loginProject.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String name);
}
