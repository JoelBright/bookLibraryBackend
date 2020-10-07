package mts.demo.booksSpringBoot.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mts.demo.booksSpringBoot.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByBookId(Long bookId, Pageable pageable);
    List<Book> findByBookIdIn(List<Long> bookIds);

    Optional<Book> findByBookName(String bookName);
    Optional<Book> findByBookNameAndPrice(String bookName, Float price);
    Optional<Book> findByBookNameAndCreatedDate(String bookName, Date createdDate);
}
