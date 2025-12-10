package com.mysite.sbb.reviewcomment.controller;

import com.mysite.sbb.reviewcomment.dto.ReviewCommentDto;
import com.mysite.sbb.reviewcomment.entity.ReviewComment;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.service.MemberService;
import com.mysite.sbb.review.entity.Review;
import com.mysite.sbb.review.service.ReviewService;
import com.mysite.sbb.reviewcomment.service.ReviewCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/review-comment")
@Slf4j
@RequiredArgsConstructor
public class ReviewCommentController {

    private final ReviewService reviewService;
    private final MemberService memberService;
    private final ReviewCommentService reviewCommentService;

    /* 댓글 삭제 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Principal principal) {

        ReviewComment comment = reviewCommentService.getComment(id);
        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        reviewCommentService.delete(comment);

        return "redirect:/review/detail/" + comment.getReview().getId();
    }

    /* 댓글 수정 처리 */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id,
                         @Valid ReviewCommentDto dto,
                         BindingResult bindingResult,
                         Principal principal) {
        if (bindingResult.hasErrors()) {
            return "reviewcomment/inputForm";
        }
        ReviewComment comment = reviewCommentService.getComment(id);

        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        reviewCommentService.modify(comment, dto);

        return "redirect:/review/detail/" + comment.getReview().getId();
    }

    /* 댓글 수정 화면 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modify(@PathVariable("id") Long id, Model model,
                         Principal principal) {
        ReviewComment comment = reviewCommentService.getComment(id);

        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        ReviewCommentDto dto = new ReviewCommentDto();
        dto.setContent(comment.getContent());
        model.addAttribute("reviewCommentDto", dto);

        return "reviewcomment/inputForm";
    }

    /* 댓글 생성 */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{reviewId}")
    public String create(@PathVariable("reviewId") Long reviewId,
                         @RequestParam("content") String content,
                         Principal principal) {

        Review review = reviewService.getReview(reviewId);
        Member member = memberService.getMember(principal.getName());

        reviewCommentService.create(review, content, member);

        return "redirect:/review/detail/" + reviewId;
    }
}
