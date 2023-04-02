package com.project.ddbb.domain.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);  // 회원 가입시 중복된 회원이 있는지 검사하기 위해 이메일로 회원을 검사하는 메소드

}