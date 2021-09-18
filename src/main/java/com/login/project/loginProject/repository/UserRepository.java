package com.login.project.loginProject.repository;

import com.login.project.loginProject.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Member, Long> {

}
