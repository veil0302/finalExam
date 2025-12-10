package com.mysite.sbb.review.service;

import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.review.dto.ReviewDto;
import com.mysite.sbb.review.entity.Review;
import com.mysite.sbb.review.repository.ReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    /* 리뷰 리스트 */
    public Page<Review> getReviewList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("created"));   // BaseEntity 에 created 존재한다고 가정
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return reviewRepository.findAll(pageable);
    }

    /* 리뷰 단건 조회 */
    public Review getReview(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 리뷰를 찾을 수 없습니다."));
    }

    /* 리뷰 생성 */
    public void create(ReviewDto reviewDto, Member member) {
        Review review = Review.builder()
                .title(reviewDto.getTitle())               // 책 제목
                .reviewTitle(reviewDto.getReviewTitle())   // 리뷰 제목
                .reviewContent(reviewDto.getReviewContent()) // 리뷰 내용
                .author(member)
                .build();

        reviewRepository.save(review);
    }

    /* 리뷰 수정 */
    public void modify(Review review, @Valid ReviewDto reviewDto) {
        review.setTitle(reviewDto.getTitle());
        review.setReviewTitle(reviewDto.getReviewTitle());
        review.setReviewContent(reviewDto.getReviewContent());
        reviewRepository.save(review);
    }

    /* 리뷰 삭제 */
    public void delete(Review review) {
        reviewRepository.delete(review);
    }
}
