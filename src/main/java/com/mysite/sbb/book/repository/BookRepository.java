package com.mysite.sbb.book.repository;

import com.mysite.sbb.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleAndAuthor(String title, String author);

    @Query(value = "SELECT * FROM book ORDER BY DBMS_RANDOM.VALUE FETCH FIRST 3 ROWS ONLY",nativeQuery = true)
    List<Book> findRandomBooks();
}
