package com.mysite.sbb.memberbook.entity;

import com.mysite.sbb.book.entity.Book;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.memberbook.constant.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MemberBook {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Book book;

    @Enumerated(EnumType.STRING)
    private Status status;      // TO_READ, READING, COMPLETED

    private LocalDate startedDate;
    private LocalDate completedDate;

    private LocalDateTime createdAt = LocalDateTime.now();
}
