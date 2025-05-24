package com.san.libraryservice.service;

import com.san.libraryservice.exception.RecordNotFoundException;
import com.san.libraryservice.model.Book;
import com.san.libraryservice.model.BorrowRecord;
import com.san.libraryservice.model.Borrower;
import com.san.libraryservice.repository.BorrowerRepository;
import com.san.libraryservice.service.impl.LibraryServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryServiceImplTest {

    @Mock
    private BorrowerRepository borrowerRepository;

    @Mock
    private BookService bookService;

    @Mock
    private BorrowRecordService borrowRecordService;

    @InjectMocks
    private LibraryServiceImpl libraryService;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        // Initialize Mockito mocks and keep the AutoCloseable resource
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Release resources held by Mockito mocks
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void borrowBook_shouldSucceed_whenBookIsAvailable() {
        // Prepare mock borrower and book with availability set to true
        Long borrowerId = 1L;
        Long bookId = 100L;

        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);

        Book book = new Book();
        book.setId(bookId);
        book.setAvailable(true);

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookService.getBookById(bookId)).thenReturn(book);
        doNothing().when(borrowRecordService).validateBookAvailability(bookId);
        doNothing().when(borrowRecordService).saveBorrowRecord(any(BorrowRecord.class));
        doNothing().when(bookService).updateBook(book);

        // No exception should be thrown, and all necessary methods should be called
        assertDoesNotThrow(() -> libraryService.borrowBook(borrowerId, bookId));

        // Verify interactions with mocks
        verify(borrowerRepository).findById(borrowerId);
        verify(bookService).getBookById(bookId);
        verify(borrowRecordService).validateBookAvailability(bookId);
        verify(borrowRecordService).saveBorrowRecord(any(BorrowRecord.class));
        verify(bookService).updateBook(book);
    }

    @Test
    void borrowBook_shouldThrowException_whenBorrowerNotFound() {
        // Simulate missing borrower
        Long borrowerId = 1L;
        Long bookId = 100L;

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.empty());

        // Expect RecordNotFoundException to be thrown
        assertThrows(RecordNotFoundException.class, () -> libraryService.borrowBook(borrowerId, bookId));

        // Verify that the repository was called
        verify(borrowerRepository).findById(borrowerId);
    }

    @Test
    void returnBook_shouldSucceed_whenBorrowRecordExists() {
        // Set up a borrowed book and a borrower with a valid borrow record
        Long borrowerId = 1L;
        Long bookId = 100L;

        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);

        Book book = new Book();
        book.setId(bookId);
        book.setAvailable(false);

        BorrowRecord record = BorrowRecord.builder()
                .borrower(borrower)
                .book(book)
                .borrowedAt(LocalDateTime.now().minusDays(1))
                .build();

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));
        when(bookService.getBookById(bookId)).thenReturn(book);
        when(borrowRecordService.getActiveBorrowRecord(borrowerId, bookId)).thenReturn(record);
        doNothing().when(borrowRecordService).updateBorrowRecord(record);
        doNothing().when(bookService).updateBook(book);

        // No exception expected when returning a valid book
        assertDoesNotThrow(() -> libraryService.returnBook(borrowerId, bookId));

        // Verify all necessary operations occurred
        verify(borrowerRepository).findById(borrowerId);
        verify(bookService).getBookById(bookId);
        verify(borrowRecordService).getActiveBorrowRecord(borrowerId, bookId);
        verify(borrowRecordService).updateBorrowRecord(record);
        verify(bookService).updateBook(book);
    }

    @Test
    void returnBook_shouldThrowException_whenBorrowerNotFound() {
        // Simulate borrower not found
        Long borrowerId = 1L;
        Long bookId = 100L;

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.empty());

        // Expect RecordNotFoundException when borrower does not exist
        assertThrows(RecordNotFoundException.class, () -> libraryService.returnBook(borrowerId, bookId));
    }

    @Test
    void getBorrowerById_shouldReturnBorrower_whenExists() {
        // Set up a borrower that exists in the repository
        Long borrowerId = 1L;
        Borrower borrower = new Borrower();
        borrower.setId(borrowerId);

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.of(borrower));

        // Call the method to get the borrower
        Borrower result = libraryService.getBorrowerById(borrowerId);

        // The returned borrower should match the input ID
        assertEquals(borrowerId, result.getId());
        verify(borrowerRepository).findById(borrowerId);
    }

    @Test
    void getBorrowerById_shouldThrowException_whenNotFound() {
        // Simulate missing borrower in repository
        Long borrowerId = 1L;

        when(borrowerRepository.findById(borrowerId)).thenReturn(Optional.empty());

        // Expect exception when borrower is not found
        assertThrows(RecordNotFoundException.class, () -> libraryService.getBorrowerById(borrowerId));
        verify(borrowerRepository).findById(borrowerId);
    }
}
