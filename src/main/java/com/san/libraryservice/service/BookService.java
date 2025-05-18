package com.san.libraryservice.service;

import com.san.libraryservice.dto.BookRequest;
import com.san.libraryservice.dto.BookResponse;
import com.san.libraryservice.exception.RecordNotFoundException;
import jakarta.validation.Valid;

import java.util.List;

public interface BookService {

    /**
     * Adds a new book to the repository and returns its response DTO.
     *
     * @param bookRequest the DTO containing details of the book to add
     * @return {@link BookResponse} he BookResponse of the saved book
     * @author Supunsan
     */
    BookResponse addBook(@Valid BookRequest bookRequest);

    /**
     * Retrieves all books from the repository.
     * Throws {@link RecordNotFoundException} if no books are found.
     *
     * @return List of {@link BookResponse} containing all books
     * @throws RecordNotFoundException if no books exist in the repository
     * @author Supunsan
     */
    List<BookResponse> getAllBooks();
}
