package com.mysite.sbb.memberbook.controller;

import com.mysite.sbb.book.dto.BookDto;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.member.service.MemberService;
import com.mysite.sbb.memberbook.constant.Status;
import com.mysite.sbb.memberbook.service.MemberBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberBookController {

    private final MemberService memberService;
    private final MemberBookService memberBookService;

    @PostMapping("/memberbook/add")
    @ResponseBody
    public void addBook(@RequestBody BookDto dto, @RequestParam("status") Status status, Principal principal) {
        System.out.println("memberBook add 호출");
        Member member = memberService.getMember(principal.getName());

        memberBookService.addToMemberBook(member, dto, status);
    }

    @GetMapping("/mypage/mypage")
    public String myPage(Model model, Principal principal) {

        Member member = memberService.getMember(principal.getName());

        // card부분 총 개수량 적을 때 사용
        model.addAttribute("toReadList",
                memberBookService.getBooks(member, Status.TO_READ));
        model.addAttribute("readingList",
                memberBookService.getBooks(member, Status.READING));
        model.addAttribute("completedList",
                memberBookService.getBooks(member, Status.COMPLETED));

        // chart.js - data부분에 값 넣어주는
        int thisYear = LocalDate.now().getYear();
        int lastYear = thisYear - 1;

        model.addAttribute("thisYear", thisYear); // current year
        model.addAttribute("lastYear", lastYear); // last year

        model.addAttribute("thisYearCounts",
                memberBookService.getMonthlyCompletedCounts(member, thisYear));

        model.addAttribute("lastYearCounts",
                memberBookService.getMonthlyCompletedCounts(member, lastYear));

        return "/mypage/mypage";
    }



    @GetMapping("/to-read")
    public String toRead(Model model, Principal principal) {

        Member member = memberService.getMember(principal.getName());
        model.addAttribute("toReadList",
                memberBookService.getBooks(member, Status.TO_READ));

        return "/mypage/to-read";
    }

    @GetMapping("/reading")
    public String reading(Model model, Principal principal) {

        Member member = memberService.getMember(principal.getName());
        model.addAttribute("readingList",
                memberBookService.getBooks(member, Status.READING));

        return "/mypage/reading";
    }

    @GetMapping("/completed")
    public String completed(Model model, Principal principal) {

        Member member = memberService.getMember(principal.getName());
        model.addAttribute("completedList",
                memberBookService.getBooks(member, Status.COMPLETED));

        return "/mypage/completed";
    }


    /* mypage 책 상태 변경 컨트롤러 */
    @PostMapping("/start/{id}")
    public String startReading(@PathVariable Long id,
                               @RequestParam("startedDate") LocalDate startedDate) {

        memberBookService.startReading(id, startedDate);
        return "redirect:/to-read";
    }

    @PostMapping("/complete/{id}")
    public String completeReading(@PathVariable Long id,
                                  @RequestParam("completedDate") LocalDate completedDate) {

        memberBookService.completeReading(id, completedDate);
        return "redirect:/reading";
    }


}
