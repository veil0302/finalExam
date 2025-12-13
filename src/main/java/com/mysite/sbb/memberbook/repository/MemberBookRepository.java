package com.mysite.sbb.memberbook.repository;

import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.memberbook.constant.Status;
import com.mysite.sbb.memberbook.entity.MemberBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberBookRepository extends JpaRepository<MemberBook, Long> {
    List<MemberBook> findByMemberAndStatus(Member member, Status status);
}
