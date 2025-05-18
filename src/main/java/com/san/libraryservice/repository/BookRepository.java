package com.san.libraryservice.repository;

import com.san.libraryservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Retrieves the first book that matches the given ISBN.
     *
     * @param isbn the ISBN of the book to search for
     * @return an Optional containing the first matching Book if found, or an empty Optional if no match exists
     */
    Optional<Book> findFirstByIsbn(String isbn);

}
