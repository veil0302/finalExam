package com.mysite.sbb.review.controller;

import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.service.MemberService;
import com.mysite.sbb.review.dto.ReviewDto;
import com.mysite.sbb.review.entity.Review;
import com.mysite.sbb.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;
    private final MemberService memberService;

    /* 리뷰 삭제 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Principal principal){
        Review review = reviewService.getReview(id);
        if(!review.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        reviewService.delete(review);
        return "redirect:/review/list";
    }

    /* 리뷰 수정 */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modify(@PathVariable Long id,
                         @Valid ReviewDto reviewDto,
                         BindingResult bindingResult,
                         Principal principal) {

        if(bindingResult.hasErrors()) {
            return "review/inputForm";
        }

        Review review = reviewService.getReview(id);

        if(!review.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        reviewService.modify(review, reviewDto);
        return "redirect:/review/detail/" + id;
    }

    /* 수정 화면 */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyForm(@PathVariable Long id, Model model, Principal principal){

        Review review = reviewService.getReview(id);

        if(!review.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        ReviewDto dto = new ReviewDto();
        dto.setBookTitle(review.getBookTitle());
        dto.setReviewTitle(review.getReviewTitle());
        dto.setReviewContent(review.getReviewContent());

        model.addAttribute("reviewDto", dto);
        return "review/inputForm";
    }

    /* 리스트 페이지 */
    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value="page", defaultValue = "0") int page){

        Page<Review> paging = reviewService.getReviewList(page);
        model.addAttribute("paging", paging);
        return "review/list";
    }

    /* 상세 페이지 */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model){
        Review review = reviewService.getReview(id);
        model.addAttribute("review", review);
        return "review/detail";
    }

    /* 작성 폼 */
    @GetMapping("/create")
    public String createForm(ReviewDto reviewDto, Model model){
        model.addAttribute("reviewDto", reviewDto);
        return "review/inputForm";
    }

    /* 리뷰 등록 */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@Valid ReviewDto reviewDto,
                         BindingResult bindingResult,
                         Principal principal,
                         Model model){

        if(bindingResult.hasErrors()){
            return "review/inputForm";
        }

        Member member = memberService.getMember(principal.getName());
        reviewService.create(reviewDto, member);

        return "redirect:/review/list";
    }

}
