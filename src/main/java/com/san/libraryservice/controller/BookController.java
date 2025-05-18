package com.san.libraryservice.controller;

import com.san.libraryservice.dto.BookRequest;
import com.san.libraryservice.dto.BookResponse;
import com.san.libraryservice.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
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
        return ResponseEntity.ok(bookService.getAllBooks());
    }
}
