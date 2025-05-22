package com.san.libraryservice.service.impl;

import com.san.libraryservice.exception.RecordNotFoundException;
import com.san.libraryservice.model.Book;
import com.san.libraryservice.model.BorrowRecord;
import com.san.libraryservice.model.Borrower;
import com.san.libraryservice.repository.BorrowerRepository;
import com.san.libraryservice.service.BookService;
import com.san.libraryservice.service.BorrowRecordService;
import com.san.libraryservice.service.LibraryService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.san.libraryservice.constant.LogConstants.*;
import static com.san.libraryservice.constant.LogConstants.BORROW_BOOK_SUCCESS;
import static com.san.libraryservice.constant.LogConstants.RETURN_BOOK_RECORD_FOUND;
import static com.san.libraryservice.constant.LogConstants.RETURN_BOOK_RECORD_UPDATED;
import static com.san.libraryservice.constant.LogConstants.RETURN_BOOK_SERVICE_START;
import static com.san.libraryservice.constant.LogConstants.RETURN_BOOK_SUCCESS;
import static com.san.libraryservice.constant.MessageConstants.BORROWER_NOT_FOUND_BY_ID;

@Service
@AllArgsConstructor
@Slf4j
public class LibraryServiceImpl implements LibraryService {

    private final BorrowerRepository borrowerRepository;
    private final BookService bookService;
    private final BorrowRecordService borrowRecordService;

    /**
     * Processes the borrowing of a book by a borrower.
     * <p>
     * Steps performed by this method:
     * <br>1. Fetches the borrower and book by their IDs.
     * <br>2. Validates that the book is available for borrowing.
     * <br>3. Creates a borrow record with the current timestamp.
     * <br>4. Saves the borrow record and updates the book's availability status.
     * </p>
     * <p>
     * This method is transactional to ensure atomicity of operations.
     * </p>
     *
     * @param borrowerId the ID of the borrower
     * @param bookId     the ID of the book to be borrowed
     * @throws RecordNotFoundException if the borrower or book is not found
     * @throws IllegalStateException   if the book is not available for borrowing
     * @author Supunsan
     */
    @Override
    @Transactional
    public void borrowBook(Long borrowerId, Long bookId) {

        log.info(BORROW_BOOK_SERVICE_START, borrowerId, bookId);
        Borrower borrower = getBorrowerById(borrowerId);
        Book book = bookService.getBookById(bookId);

        log.info(BORROW_BOOK_VALIDATION, bookId);
        borrowRecordService.validateBookAvailability(bookId);

        BorrowRecord borrowRecord = BorrowRecord.builder()
                .book(book)
                .borrower(borrower)
                .borrowedAt(LocalDateTime.now())
                .build();

        borrowRecordService.saveBorrowRecord(borrowRecord);
        log.info(BORROW_RECORD_SAVED, borrowerId, bookId);

        book.setAvailable(false);
        bookService.updateBook(book);
        log.info(BORROW_BOOK_SUCCESS, borrowerId, bookId);
    }

    /**
     * Retrieves a borrower by their unique ID.
     *
     * @param borrowerId the ID of the borrower to retrieve
     * @return the Borrower object with the specified ID
     * @throws RecordNotFoundException if no borrower is found with the given ID
     * @author Supunsan
     */
    @Override
    public Borrower getBorrowerById(Long borrowerId) {
        return borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new RecordNotFoundException(BORROWER_NOT_FOUND_BY_ID + borrowerId));
    }

    /**
     * Processes the return of a borrowed book by a borrower.
     * <p>
     * Steps performed by this method:
     * <br>1. Validates that the borrower and book exist.
     * <br>2. Retrieves the active borrow record (not yet returned).
     * <br>3. Marks the borrow record as returned by setting the return timestamp.
     * <br>4. Updates the borrow record and marks the book as available again.
     * This method is transactional to ensure atomicity of all related updates.
     *
     * @param borrowerId the ID of the borrower returning the book
     * @param bookId     the ID of the book being returned
     * @throws RecordNotFoundException if borrower or book does not exist
     * @throws IllegalStateException   if there is no active borrow record for the given borrower and book
     * @author Supunsan
     */
    @Override
    @Transactional
    public void returnBook(Long borrowerId, Long bookId) {

        log.info(RETURN_BOOK_SERVICE_START, borrowerId, bookId);

        getBorrowerById(borrowerId);
        Book book = bookService.getBookById(bookId);

        BorrowRecord borrowRecord = borrowRecordService.getActiveBorrowRecord(borrowerId, bookId);
        log.info(RETURN_BOOK_RECORD_FOUND, borrowerId, bookId);
        borrowRecord.setReturnedAt(LocalDateTime.now());

        borrowRecordService.updateBorrowRecord(borrowRecord);
        log.info(RETURN_BOOK_RECORD_UPDATED, borrowerId, bookId);

        book.setAvailable(true);
        bookService.updateBook(book);
        log.info(RETURN_BOOK_SUCCESS, bookId, borrowerId);
    }

}
