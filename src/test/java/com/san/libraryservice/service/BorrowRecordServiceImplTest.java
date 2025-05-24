package com.san.libraryservice.service;

import com.san.libraryservice.model.BorrowRecord;
import com.san.libraryservice.repository.BorrowRecordRepository;
import com.san.libraryservice.service.impl.BorrowRecordServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.san.libraryservice.constant.MessageConstants.BOOK_ALREADY_BORROWED;
import static com.san.libraryservice.constant.MessageConstants.BORROW_RECORD_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowRecordServiceImplTest {

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @InjectMocks
    private BorrowRecordServiceImpl borrowRecordService;

    @Test
    void validateBookAvailability_shouldNotThrow_whenBookIsAvailable() {
        // Book is not currently borrowed
        Long bookId = 1L;
        when(borrowRecordRepository.existsByBookIdAndReturnedAtIsNull(bookId)).thenReturn(false);

        // Method should execute without throwing an exception
        assertDoesNotThrow(() -> borrowRecordService.validateBookAvailability(bookId));
        verify(borrowRecordRepository).existsByBookIdAndReturnedAtIsNull(bookId);
    }

    @Test
    void validateBookAvailability_shouldThrowException_whenBookIsAlreadyBorrowed() {
        // Book is already borrowed
        Long bookId = 1L;
        when(borrowRecordRepository.existsByBookIdAndReturnedAtIsNull(bookId)).thenReturn(true);

        // Expect exception with proper message
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> borrowRecordService.validateBookAvailability(bookId)
        );
        assertEquals(String.format(BOOK_ALREADY_BORROWED, bookId), exception.getMessage());
        verify(borrowRecordRepository).existsByBookIdAndReturnedAtIsNull(bookId);
    }

    @Test
    void saveBorrowRecord_shouldCallRepositorySave_once() {
        // Arrange: Prepare a mock BorrowRecord object
        BorrowRecord record = new BorrowRecord();

        // Save the record
        borrowRecordService.saveBorrowRecord(record);

        // Repository save() should be called once with the correct object
        verify(borrowRecordRepository).save(record);
    }

    @Test
    void getActiveBorrowRecord_shouldReturnRecord_whenExists() {
        // Setup existing active borrow record
        Long borrowerId = 1L;
        Long bookId = 2L;
        BorrowRecord mockRecord = new BorrowRecord();

        when(borrowRecordRepository.findByBorrowerIdAndBookIdAndReturnedAtIsNull(borrowerId, bookId))
                .thenReturn(Optional.of(mockRecord));

        // Retrieve the active borrow record
        BorrowRecord result = borrowRecordService.getActiveBorrowRecord(borrowerId, bookId);

        // Should return the expected object
        assertNotNull(result);
        assertEquals(mockRecord, result);
        verify(borrowRecordRepository).findByBorrowerIdAndBookIdAndReturnedAtIsNull(borrowerId, bookId);
    }

    @Test
    void getActiveBorrowRecord_shouldThrowException_whenNoActiveRecordFound() {
        // No active borrow record found in repository
        Long borrowerId = 1L;
        Long bookId = 2L;

        when(borrowRecordRepository.findByBorrowerIdAndBookIdAndReturnedAtIsNull(borrowerId, bookId))
                .thenReturn(Optional.empty());

        // Expect exception with correct message
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> borrowRecordService.getActiveBorrowRecord(borrowerId, bookId)
        );
        assertEquals(String.format(BORROW_RECORD_NOT_FOUND, borrowerId, bookId), exception.getMessage());
        verify(borrowRecordRepository).findByBorrowerIdAndBookIdAndReturnedAtIsNull(borrowerId, bookId);
    }

    @Test
    void updateBorrowRecord_shouldCallSaveMethod() {
        // Prepare a mock BorrowRecord object
        BorrowRecord record = new BorrowRecord();

        // Invoke update method
        borrowRecordService.updateBorrowRecord(record);

        // Verify save was called
        verify(borrowRecordRepository).save(record);
    }
}
