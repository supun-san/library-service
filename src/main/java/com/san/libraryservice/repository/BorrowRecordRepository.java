package com.san.libraryservice.repository;

import com.san.libraryservice.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    /**
     * Checks if there is an active borrow record for the specified book
     * that has not yet been returned.
     *
     * @param bookId the ID of the book to check
     * @return true if the book is currently borrowed
     * @author Supunsan
     */
    boolean existsByBookIdAndReturnedAtIsNull(Long bookId);

    /**
     * Retrieves an active borrow record for the given borrower and book,
     * where the book has not yet been returned.
     *
     * @param borrowerId the ID of the borrower
     * @param bookId     the ID of the book
     * @return an {@link Optional} containing the active {@link BorrowRecord} if found, otherwise empty
     * @author Supunsan
     */
    Optional<BorrowRecord> findByBorrowerIdAndBookIdAndReturnedAtIsNull(Long borrowerId, Long bookId);
}
