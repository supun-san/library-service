package com.san.libraryservice.controller;

import com.san.libraryservice.dto.BookRequest;
import com.san.libraryservice.dto.BookResponse;
import com.san.libraryservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.san.libraryservice.constant.LogConstants.ADD_BOOK_CONTROLLER_START;
import static com.san.libraryservice.constant.LogConstants.GET_ALL_BOOKS_CONTROLLER_START;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    /**
     * Registers a new book in the library.
     *
     * @param bookRequest the incoming request body containing book details, validated with @Valid
     * @return {@link ResponseEntity} containing the saved BookResponse DTO with HTTP 200 OK status
     * @author Supunsan
     */
    @Operation(summary = "Register a new book", description = "Adds a new book to the library")
    @PostMapping("/register")
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookRequest bookRequest) {
        log.info(ADD_BOOK_CONTROLLER_START, bookRequest.getTitle());
        return ResponseEntity.ok(bookService.addBook(bookRequest));
    }

    /**
     * Retrieves all books currently stored in the library.
     *
     * @return {@link ResponseEntity} containing a list of BookResponse DTOs with HTTP 200 OK status
     * @author Supunsan
     */
    @Operation(summary = "Get all books", description = "Retrieve a list of all books in the library")
    @GetMapping("/all")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        log.info(GET_ALL_BOOKS_CONTROLLER_START);
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}
