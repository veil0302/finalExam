package com.mysite.sbb.book.dto;

import lombok.Data;

@Data
public class BookDto {
    private String title;
    private String image;
    private String author;
    private String publisher; // 필요하면 사용
    private String description; // 책 소개 네이버 젝ㅇ
    private String link;
    private String category;
    private String categoryName;

}
