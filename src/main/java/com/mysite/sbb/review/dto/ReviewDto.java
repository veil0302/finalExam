package com.mysite.sbb.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {

    @NotBlank(message = "리뷰 제목을 입력하세요.")
    private String reviewTitle;


    @NotBlank(message = "책 제목을 입력하세요.")
    private String bookTitle;

    private String bookAuthor;
    private String bookImage;  // 이미지 URL


    @NotBlank(message = "리뷰 내용을 입력하세요.")
    private String reviewContent;
}
