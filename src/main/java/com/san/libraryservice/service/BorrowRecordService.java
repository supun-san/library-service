package com.san.libraryservice.service;

import com.san.libraryservice.model.BorrowRecord;

public interface BorrowRecordService {

    /**
     * Validates that the book with the specified ID is available for borrowing.
     * Checks if there is an active borrow record for the book.
     * If the book is already borrowed, an exception is thrown.
     *
     * @param bookId the ID of the book to check
     * @throws IllegalStateException if the book is currently borrowed and unavailable
     * @author Supunsan
     */
    void validateBookAvailability(Long bookId);

    /**
     * Saves the given borrow record to the repository.
     *
     * @param borrowRecord the BorrowRecord entity to be saved
     * @author Supunsan
     */
    void saveBorrowRecord(BorrowRecord borrowRecord);

    /**
     * Retrieves the active borrow record for a given borrower and book.
     * An active borrow record is one where the book has not been returned yet.
     *
     * @param borrowerId the ID of the borrower
     * @param bookId     the ID of the borrowed book
     * @return the active {@link BorrowRecord}
     * @throws IllegalStateException if no active borrow record is found
     * @author Supunsan
     */
    BorrowRecord getActiveBorrowRecord(Long borrowerId, Long bookId);

    /**
     * Updates an existing borrow record.
     *
     * @param borrowRecord the {@link BorrowRecord} entity to be updated
     * @author Supunsan
     */
    void updateBorrowRecord(BorrowRecord borrowRecord);
}
