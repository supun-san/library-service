package com.san.libraryservice.service.impl;

import com.san.libraryservice.dto.BookRequest;
import com.san.libraryservice.dto.BookResponse;
import com.san.libraryservice.exception.RecordNotFoundException;
import com.san.libraryservice.model.Book;
import com.san.libraryservice.repository.BookRepository;
import com.san.libraryservice.service.BookService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.san.libraryservice.constant.MessageConstants.BOOK_NOT_FOUND_MESSAGE;
import static com.san.libraryservice.constant.MessageConstants.ISBN_CONFLICT_MESSAGE;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    /**
     * Adds a new book to the repository and returns its response DTO.
     *
     * @param bookRequest the DTO containing details of the book to add
     * @return {@link BookResponse} the BookResponse of the saved book
     * @author Supunsan
     */
    @Override
    @Transactional
    public BookResponse addBook(BookRequest bookRequest) {

        bookRepository.findFirstByIsbn(bookRequest.getIsbn())
                .filter(book -> !(book.getTitle().equals(bookRequest.getTitle())
                        && book.getAuthor().equals(bookRequest.getAuthor())))
                .ifPresent(book -> {
                    throw new IllegalArgumentException(ISBN_CONFLICT_MESSAGE);
                });

        return mapToBookResponse(bookRepository.save(mapToBook(bookRequest)));
    }

    /**
     * Retrieves all books from the repository.
     * Throws {@link RecordNotFoundException} if no books are found.
     *
     * @return List of {@link BookResponse} containing all books
     * @throws RecordNotFoundException if no books exist in the repository
     * @author Supunsan
     */
    @Override
    public List<BookResponse> getAllBooks() {

        List<Book> books = Optional.of(bookRepository.findAll())
                .filter(book -> !book.isEmpty())
                .orElseThrow(() -> new RecordNotFoundException(BOOK_NOT_FOUND_MESSAGE));

        return books.stream()
                .map(this::mapToBookResponse)
                .toList();
    }

    /**
     * Retrieves a book by its unique identifier.
     *
     * @param bookId the ID of the book to retrieve
     * @return the {@link Book} object with the specified ID
     * @throws RecordNotFoundException if no book is found with the given ID
     * @author Supunsan
     */
    @Override
    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RecordNotFoundException("Book not found with ID: " + bookId));
    }

    /**
     * Updates the details of an existing book.
     *
     * @param book the {@link Book} object containing updated information
     */
    @Override
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    /**
     * Maps a {@link BookRequest} DTO to a {@link Book} entity.
     *
     * @param request the book request DTO
     * @return a new {@link Book} entity populated from the request DTO
     * @author Supunsan
     */
    private Book mapToBook(BookRequest request) {
        return Book.builder()
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .author(request.getAuthor())
                .available(true)
                .build();
    }

    /**
     * Maps a {@link Book} entity to a {@link BookResponse} DTO.
     *
     * @param book the book entity
     * @return a new {@link BookResponse} DTO populated from the book entity
     * @author Supunsan
     */
    private BookResponse mapToBookResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .borrowed(book.isAvailable())
                .build();
    }
}
