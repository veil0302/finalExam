package com.mysite.sbb.book.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    private String isbn; // 책 고유 식별자

    private String title;

    private String author;

    private String publisher;

    private String imageUrl;

    private String category;

    private LocalDate publishDate;
}

