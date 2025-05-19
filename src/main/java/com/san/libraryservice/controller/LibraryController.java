package com.san.libraryservice.controller;

import com.san.libraryservice.service.LibraryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.san.libraryservice.constant.LogConstants.BORROW_BOOK_CONTROLLER_START;
import static com.san.libraryservice.constant.LogConstants.RETURN_BOOK_CONTROLLER_START;
import static com.san.libraryservice.constant.MessageConstants.BOOK_BORROWED_SUCCESS;
import static com.san.libraryservice.constant.MessageConstants.BOOK_RETUNED_SUCCESS;

@RestController
@RequestMapping("/api/v1/library")
@RequiredArgsConstructor
@Slf4j
public class LibraryController {

    private final LibraryService libraryService;

    /**
     * Handles a borrow request where a borrower borrows a book.
     *
     * @param borrowerId the ID of the borrower who wants to borrow the book
     * @param bookId     the ID of the book to be borrowed
     * @return a {@link ResponseEntity} containing a success message upon successful borrowing
     * @author Supunsan
     */
    @Operation(
            summary = "Borrow a book",
            description = "Allows a borrower to borrow a book by specifying borrower ID and book ID."
    )
    @PostMapping("/borrow/{borrowerId}/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        log.info(BORROW_BOOK_CONTROLLER_START, borrowerId, bookId);
        libraryService.borrowBook(borrowerId, bookId);
        return ResponseEntity.ok(BOOK_BORROWED_SUCCESS);
    }

    /**
     * Handles a return request where a borrower returns a previously borrowed book.
     *
     * @param borrowerId the ID of the borrower who is returning the book
     * @param bookId     the ID of the book to be returned
     * @return a {@link ResponseEntity} containing a success message upon successful return
     * @author Supunsan
     */
    @Operation(
            summary = "Return a borrowed book",
            description = "Allows a borrower to return a book by specifying borrower ID and book ID."
    )
    @PostMapping("/return/{borrowerId}/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        log.info(RETURN_BOOK_CONTROLLER_START, borrowerId, bookId);
        libraryService.returnBook(borrowerId, bookId);
        return ResponseEntity.ok(BOOK_RETUNED_SUCCESS);
    }

}
