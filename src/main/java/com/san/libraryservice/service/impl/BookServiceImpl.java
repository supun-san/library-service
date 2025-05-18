package com.san.libraryservice.service.impl;

import com.san.libraryservice.dto.BookRequest;
import com.san.libraryservice.dto.BookResponse;
import com.san.libraryservice.exception.RecordNotFoundException;
import com.san.libraryservice.model.Book;
import com.san.libraryservice.repository.BookRepository;
import com.san.libraryservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    /**
     * Adds a new book to the repository and returns its response DTO.
     *
     * @param bookRequest the DTO containing details of the book to add
     * @return {@link BookResponse} he BookResponse of the saved book
     * @author Supunsan
     */
    @Override
    public BookResponse addBook(BookRequest bookRequest) {
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
                .orElseThrow(() -> new RecordNotFoundException("Not found any books"));

        return books.stream()
                .map(this::mapToBookResponse)
                .toList();
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
                .borrowed(book.isBorrowed())
                .build();
    }
}
