package com.mysite.sbb.library.controller;

import com.mysite.sbb.member.dto.MemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("library")
public class LibraryController {

    @GetMapping("/my")
    public String my(Model model) {
        model.addAttribute("memberDto", new MemberDto());

        return "/library/mylibrary";
    }

}
