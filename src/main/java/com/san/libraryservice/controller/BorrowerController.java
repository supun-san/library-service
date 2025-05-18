package com.san.libraryservice.controller;

import com.san.libraryservice.dto.BorrowerRequest;
import com.san.libraryservice.dto.BorrowerResponse;
import com.san.libraryservice.service.BorrowerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.san.libraryservice.constant.MessageConstants.BOOK_BORROWED_SUCCESS;
import static com.san.libraryservice.constant.MessageConstants.BOOK_RETUNED_SUCCESS;

@RestController
@RequestMapping("/api/v1/borrowers")
@RequiredArgsConstructor
public class BorrowerController {

    private final BorrowerService borrowerService;

    /**
     * Registers a new borrower in the library.
     *
     * @param borrowerRequest the request body containing borrower details
     * @return {@link ResponseEntity} with the created borrower's information
     * @author Supunsan
     */
    @Operation(
            summary = "Register a new borrower",
            description = "Creates and saves a new borrower using the provided name and email."
    )
    @PostMapping("/register")
    public ResponseEntity<BorrowerResponse> registerBorrower(@Valid @RequestBody BorrowerRequest borrowerRequest) {
        return ResponseEntity.ok(borrowerService.register(borrowerRequest));
    }

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
    @PostMapping("/{borrowerId}/borrow/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        borrowerService.borrowBook(borrowerId, bookId);
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
    @PostMapping("/{borrowerId}/return/{bookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
        borrowerService.returnBook(borrowerId, bookId);
        return ResponseEntity.ok(BOOK_RETUNED_SUCCESS);
    }
}
