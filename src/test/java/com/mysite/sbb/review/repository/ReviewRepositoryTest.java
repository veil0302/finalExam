package com.mysite.sbb.review.repository;

import com.mysite.sbb.review.entity.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void testSave(){
        Review q1 = new Review();
        q1.setSubject("sbb가 뭔가요?");
        q1.setContent("sbb는 질의 응답 게시판 인가요?");
        reviewRepository.save(q1);

        Review q2 = Review.builder()
                .content("스프링부트는 어려울까요?")
                .subject("스프링 부트 난이도?")
                .build();
        Review saved = reviewRepository.save(q2);
        assertEquals(2, saved.getId());
    }

}