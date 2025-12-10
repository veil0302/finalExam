package com.mysite.sbb.reviewcomment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCommentDto {

    @NotBlank(message = "댓글 내용을 입력하세요.")
    private String content;

}
