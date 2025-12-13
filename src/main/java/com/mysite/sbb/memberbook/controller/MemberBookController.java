package com.mysite.sbb.memberbook.controller;

import com.mysite.sbb.book.dto.BookDto;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.service.MemberService;
import com.mysite.sbb.memberbook.constant.Status;
import com.mysite.sbb.memberbook.service.MemberBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberBookController {

    private final MemberService memberService;
    private final MemberBookService memberBookService;

    @PostMapping("/memberbook/add")
    @ResponseBody
    public String addBook(
            @RequestBody BookDto dto,
            @RequestParam("status") Status status,
            Principal principal
    ) {
        Member member = memberService.getMember(principal.getName());

        memberBookService.addToMemberBook(member, dto, status);

        return "ok";
    }

}
