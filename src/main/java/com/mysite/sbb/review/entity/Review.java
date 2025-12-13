package com.mysite.sbb.review.entity;

import com.mysite.sbb.audit.BaseEntity;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.reviewcomment.entity.ReviewComment;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @Column(length = 50, nullable = false)
    private String bookTitle;  // 책 제목

    @Column(length = 200, nullable = false)
    private String reviewTitle;  // 리뷰 제목

    @Column(nullable = false)
    private String reviewContent; // 리뷰 내용

    private String bookAuthor;
    private String bookImageUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewComment> reviewCommentList; // 리뷰 댓글 리스트

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id", nullable = false)
    private Member author;
}
