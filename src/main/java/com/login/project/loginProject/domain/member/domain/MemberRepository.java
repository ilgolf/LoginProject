package com.login.project.loginProject.domain.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m as email from Member m")
    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByNickName(String email);

//    @Query("select m as id from Member m")
//    Member findOne(Long id);
}
