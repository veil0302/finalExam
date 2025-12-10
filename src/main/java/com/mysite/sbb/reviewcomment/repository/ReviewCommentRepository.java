package com.mysite.sbb.reviewcomment.repository;

import com.mysite.sbb.reviewcomment.entity.ReviewComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, Long> {
}
