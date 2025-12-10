package com.mysite.sbb.book.controller;


import com.mysite.sbb.book.api.BookSearchService;
import com.mysite.sbb.book.dto.BookDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookSearchService bookSearchService;


    public BookController(BookSearchService bookSearchService) {
        this.bookSearchService = bookSearchService;
    }

    @GetMapping("/search")
    public String search(@RequestParam String query, Model model) {

        List<BookDto> books = bookSearchService.searchBooksAsDto(query);

        model.addAttribute("books", books);
        model.addAttribute("query", query);

        return "/index";
    }

}
