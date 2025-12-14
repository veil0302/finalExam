package com.mysite.sbb.memberbook.service;

import com.mysite.sbb.book.dto.BookDto;
import com.mysite.sbb.book.entity.Book;
import com.mysite.sbb.book.repository.BookRepository;
import com.mysite.sbb.member.entity.Member;
import com.mysite.sbb.memberbook.constant.Status;
import com.mysite.sbb.memberbook.entity.MemberBook;
import com.mysite.sbb.memberbook.repository.MemberBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberBookService {

    private final BookRepository bookRepository;
    private final MemberBookRepository memberBookRepository;

    public void addToMemberBook(Member member, BookDto bookDto, Status status) {

        // 1) 같은 책이 DB에 있는지 확인
        Book book = bookRepository
                .findByTitleAndAuthor(bookDto.getTitle(), bookDto.getAuthor())
                .orElseGet(() -> {
                    Book newBook = new Book();
                    newBook.setTitle(bookDto.getTitle());
                    newBook.setAuthor(bookDto.getAuthor());
                    newBook.setImageUrl(bookDto.getImage());
                    return bookRepository.save(newBook);
                });

        // 2) MemberBook 생성
        MemberBook mb = new MemberBook();
        mb.setMember(member);
        mb.setBook(book);
        mb.setStatus(status);

        // 읽기 날짜는 상태별로 다르게 적용
        if (status == Status.READING) {
            mb.setStartedDate(LocalDate.now());
        }
        if (status == Status.COMPLETED) {
            mb.setStartedDate(LocalDate.now());
            mb.setCompletedDate(LocalDate.now());
        }

        memberBookRepository.save(mb);
    }

    public List<MemberBook> getBooks(Member member, Status status) {
        return memberBookRepository.findByMemberAndStatus(member, status);
    }

    /* memberbook Status 값 변경 */

    public void startReading(Long memberBookId, LocalDate startedDate) {

        MemberBook mb = memberBookRepository.findById(memberBookId)
                .orElseThrow(() -> new IllegalArgumentException("책 없음"));

        mb.setStatus(Status.READING);
        mb.setStartedDate(startedDate);

        memberBookRepository.save(mb);
    }

    public void completeReading(Long memberBookId, LocalDate completedDate) {

        MemberBook mb = memberBookRepository.findById(memberBookId)
                .orElseThrow(() -> new IllegalArgumentException("책 없음"));

        mb.setStatus(Status.COMPLETED);
        mb.setCompletedDate(completedDate);

        memberBookRepository.save(mb);
    }


}
