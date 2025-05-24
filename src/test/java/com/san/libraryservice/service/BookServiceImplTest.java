package com.san.libraryservice.service;

import com.san.libraryservice.dto.BookRequest;
import com.san.libraryservice.dto.BookResponse;
import com.san.libraryservice.exception.RecordNotFoundException;
import com.san.libraryservice.model.Book;
import com.san.libraryservice.repository.BookRepository;
import com.san.libraryservice.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.san.libraryservice.constant.MessageConstants.ISBN_CONFLICT_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void addBook_shouldSaveAndReturnBookResponse() {
        // Given a valid book request and no existing book with the same ISBN
        BookRequest request = new BookRequest("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        Book savedBook = Book.builder()
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .author(request.getAuthor())
                .available(true)
                .build();
        savedBook.setId(1L);

        // Mock repository behavior: no conflict found, so save returns savedBook
        when(bookRepository.findFirstByIsbn(request.getIsbn())).thenReturn(Optional.empty());
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // When the book is added
        BookResponse response = bookService.addBook(request);

        // Then it should return the saved book response
        assertNotNull(response);
        assertEquals(savedBook.getId(), response.getId());
        assertEquals(savedBook.getIsbn(), response.getIsbn());
        assertEquals(savedBook.getTitle(), response.getTitle());
        assertEquals(savedBook.getAuthor(), response.getAuthor());
        assertTrue(response.isBorrowed()); // borrowed == available

        verify(bookRepository).findFirstByIsbn(request.getIsbn());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void addBook_shouldThrowExceptionIfIsbnConflict() {
        // Given a conflicting book with same ISBN but different title/author
        BookRequest request = new BookRequest("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        Book conflictingBook = Book.builder()
                .isbn(request.getIsbn())
                .title("Clean Code")
                .author("Robert Martin")
                .available(true)
                .build();
        conflictingBook.setId(2L);

        // When trying to add the book, then an exception should be thrown
        when(bookRepository.findFirstByIsbn(request.getIsbn())).thenReturn(Optional.of(conflictingBook));

        // Act & Assert
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> bookService.addBook(request));
        assertEquals(ISBN_CONFLICT_MESSAGE, ex.getMessage());

        verify(bookRepository).findFirstByIsbn(request.getIsbn());
        verify(bookRepository, never()).save(any());
    }


    @Test
    void getAllBooks_shouldReturnListOfBookResponses() {
        // Given one book in the repository
        Book book = Book.builder()
                .isbn("978-0-13-468599-1")
                .title("Effective Java")
                .author("Joshua Bloch")
                .available(true)
                .build();
        book.setId(1L);

        when(bookRepository.findAll()).thenReturn(List.of(book));

        // When fetching all books
        List<BookResponse> responses = bookService.getAllBooks();

        // Then it should return a list with one response
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(book.getId(), responses.get(0).getId());

        verify(bookRepository).findAll();
    }

    @Test
    void getAllBooks_shouldThrowRecordNotFoundExceptionWhenEmpty() {
        // Given no books in the repository
        when(bookRepository.findAll()).thenReturn(Collections.emptyList());

        // Then it should throw a RecordNotFoundException
        assertThrows(RecordNotFoundException.class, () -> bookService.getAllBooks());

        verify(bookRepository).findAll();
    }

    @Test
    void getBookById_shouldReturnBook() {
        // Given a book exists with ID
        Book book = Book.builder()
                .isbn("978-3-16-148410-0")
                .title("Clean Code")
                .author("Robert Martin")
                .available(true)
                .build();
        book.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // When fetching the book by ID
        Book foundBook = bookService.getBookById(1L);

        // Then it should return the book
        assertNotNull(foundBook);
        assertEquals(1L, foundBook.getId());

        verify(bookRepository).findById(1L);
    }

    @Test
    void getBookById_shouldThrowRecordNotFoundExceptionWhenNotFound() {
        // Given no book exists with ID
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        // Then it should throw a RecordNotFoundException
        assertThrows(RecordNotFoundException.class, () -> bookService.getBookById(1L));

        verify(bookRepository).findById(1L);
    }

    @Test
    void updateBook_shouldSaveBook() {
        // Given a book to update
        Book book = Book.builder()
                .isbn("978-3-16-148410-0")
                .title("Clean Code")
                .author("Robert Martin")
                .available(true)
                .build();
        book.setId(1L);

        // When the book is updated
        bookService.updateBook(book);

        // Then it should be saved via the repository
        verify(bookRepository).save(book);
    }
}

