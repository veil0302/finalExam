package com.mysite.sbb.reviewcomment.service;

import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.review.entity.Review;
import com.mysite.sbb.reviewcomment.dto.ReviewCommentDto;
import com.mysite.sbb.reviewcomment.entity.ReviewComment;
import com.mysite.sbb.reviewcomment.repository.ReviewCommentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewCommentService {

    private final ReviewCommentRepository reviewCommentRepository;

    public ReviewComment getComment(Long id) {
        return reviewCommentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("댓글을 찾을 수 없습니다."));
    }

    public void create(Review review, String content, Member member) {
        ReviewComment comment = ReviewComment.builder()
                .review(review)
                .content(content)
                .author(member)
                .build();
        reviewCommentRepository.save(comment);
    }

    public void modify(ReviewComment comment, ReviewCommentDto dto) {
        comment.setContent(dto.getContent());
        reviewCommentRepository.save(comment);
    }

    public void delete(ReviewComment comment) {
        reviewCommentRepository.delete(comment);
    }
}
