package com.san.libraryservice.repository;

import com.san.libraryservice.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
