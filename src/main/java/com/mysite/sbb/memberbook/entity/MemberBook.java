package com.mysite.sbb.memberbook.entity;

import com.mysite.sbb.book.entity.Book;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.memberbook.constant.Status;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberBook {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Enumerated(EnumType.STRING)
    private Status status;     // TO_READ, READING, COMPLETED

    private LocalDate startedDate;     // 읽기 시작 날짜
    private LocalDate completedDate;   // 읽기 완료 날짜

    private LocalDateTime createdAt;
}
