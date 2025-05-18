package com.san.libraryservice.service.impl;

import com.san.libraryservice.model.BorrowRecord;
import com.san.libraryservice.repository.BorrowRecordRepository;
import com.san.libraryservice.service.BorrowRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.san.libraryservice.constant.MessageConstants.BORROW_RECORD_NOT_FOUND;

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
    @Override
    public BorrowRecord getActiveBorrowRecord(Long borrowerId, Long bookId) {
        return borrowRecordRepository
                .findByBorrowerIdAndBookIdAndReturnedAtIsNull(borrowerId, bookId)
                .orElseThrow(() -> new IllegalStateException(
                        String.format(BORROW_RECORD_NOT_FOUND, borrowerId, bookId)));
    }

    /**
     * Updates an existing borrow record.
     *
     * @param borrowRecord the {@link BorrowRecord} entity to be updated
     * @author Supunsan
     */
    @Override
    public void updateBorrowRecord(BorrowRecord borrowRecord) {
        borrowRecordRepository.save(borrowRecord);
    }
}
