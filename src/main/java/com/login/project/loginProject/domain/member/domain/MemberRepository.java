package com.login.project.loginProject.domain.member.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(final String email);
    boolean existsByEmail(final String email);
    boolean existsByNickname(final String email);
    void deleteByEmail(final String email);
    Page<Member> findAll(final Pageable pageable);
    List<Member> findByNameContaining(final String name, final Pageable pageable);
}
