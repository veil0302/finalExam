package com.mysite.sbb.book.controller;


import com.mysite.sbb.book.api.BookSearchService;
import com.mysite.sbb.book.dto.BookDto;
import com.mysite.sbb.book.entity.Book;
import com.mysite.sbb.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/books")
public class BookController {

    private final BookSearchService bookSearchService;
    private final BookRepository bookRepository;


    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {

        List<BookDto> books = bookSearchService.searchBooksAsDto(query);

        model.addAttribute("books", books);
        model.addAttribute("query", query);

        // DB book 테이블에 저장된 책 정보 가져와서 recommend로 날리기
        List<Book> recommendBooks = bookRepository.findRandomBooks();
        model.addAttribute("recommendBooks", recommendBooks);

        return "/index";
    }

}
