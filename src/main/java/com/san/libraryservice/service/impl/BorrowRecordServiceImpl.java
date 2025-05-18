package com.san.libraryservice.service.impl;

import com.san.libraryservice.model.BorrowRecord;
import com.san.libraryservice.repository.BorrowRecordRepository;
import com.san.libraryservice.service.BorrowRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowRecordServiceImpl implements BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;

    /**
     * Validates that the book with the specified ID is available for borrowing.
     * Checks if there is an active borrow record for the book.
     * If the book is already borrowed, an exception is thrown.
     *
     * @param bookId the ID of the book to check
     * @throws IllegalStateException if the book is currently borrowed and unavailable
     * @author Supunsan
     */
    @Override
    public void validateBookAvailability(Long bookId) {
        boolean isAlreadyBorrowed = borrowRecordRepository.existsByBookIdAndReturnedAtIsNull(bookId);
        if (isAlreadyBorrowed) {
            throw new IllegalStateException("Book with ID " + bookId + " is already borrowed.");
        }
    }

    /**
     * Saves the given borrow record to the repository.
     *
     * @param borrowRecord the BorrowRecord entity to be saved
     * @author Supunsan
     */
    @Override
    public void saveBorrowRecord(BorrowRecord borrowRecord) {
        borrowRecordRepository.save(borrowRecord);
    }
}
