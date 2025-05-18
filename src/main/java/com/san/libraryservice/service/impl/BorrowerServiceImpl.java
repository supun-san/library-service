package com.san.libraryservice.service.impl;

import com.san.libraryservice.dto.BorrowerRequest;
import com.san.libraryservice.dto.BorrowerResponse;
import com.san.libraryservice.exception.RecordNotFoundException;
import com.san.libraryservice.model.Book;
import com.san.libraryservice.model.BorrowRecord;
import com.san.libraryservice.model.Borrower;
import com.san.libraryservice.repository.BorrowerRepository;
import com.san.libraryservice.service.BookService;
import com.san.libraryservice.service.BorrowRecordService;
import com.san.libraryservice.service.BorrowerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BorrowerServiceImpl implements BorrowerService {

    private final BorrowerRepository borrowerRepository;
    private final BookService bookService;
    private final BorrowRecordService borrowRecordService;

    /**
     * Registers a new borrower by saving the provided request data to the repository.
     *
     * @param borrowerRequest The borrower request containing name and email.
     * @return {@link BorrowerResponse} containing the saved borrower's details.
     * @author Supunsan
     */
    @Override
    public BorrowerResponse register(BorrowerRequest borrowerRequest) {
        return mapToBorrowerResponse(borrowerRepository.save(mapToBorrower(borrowerRequest)));
    }

    /**
     * Processes the borrowing of a book by a borrower.
     * <p>
     * Steps performed by this method:
     * <br>1. Fetches the borrower and book by their IDs.
     * <br>2. Validates that the book is available for borrowing.
     * <br>3. Creates a borrow record with the current timestamp.
     * <br>4. Saves the borrow record and updates the book's availability status.
     * </p>
     * <p>
     * This method is transactional to ensure atomicity of operations.
     * </p>
     *
     * @param borrowerId the ID of the borrower
     * @param bookId     the ID of the book to be borrowed
     * @throws RecordNotFoundException if the borrower or book is not found
     * @throws IllegalStateException   if the book is not available for borrowing
     * @author Supunsan
     */
    @Override
    @Transactional
    public void borrowBook(Long borrowerId, Long bookId) {

        Borrower borrower = getBorrowerById(borrowerId);
        Book book = bookService.getBookById(bookId);

        borrowRecordService.validateBookAvailability(bookId);

        BorrowRecord borrowRecord = BorrowRecord.builder()
                .book(book)
                .borrower(borrower)
                .borrowedAt(LocalDateTime.now())
                .build();

        borrowRecordService.saveBorrowRecord(borrowRecord);

        book.setAvailable(false);
        bookService.updateBook(book);
    }

    /**
     * Retrieves a borrower by their unique ID.
     *
     * @param borrowerId the ID of the borrower to retrieve
     * @return the Borrower object with the specified ID
     * @throws RecordNotFoundException if no borrower is found with the given ID
     * @author Supunsan
     */
    @Override
    public Borrower getBorrowerById(Long borrowerId) {
        return borrowerRepository.findById(borrowerId)
                .orElseThrow(() -> new RecordNotFoundException("Borrower not found with ID: " + borrowerId));
    }


    /**
     * Maps a {@link BorrowerRequest} DTO to a {@link Borrower} entity.
     *
     * @param borrowerRequest The DTO containing borrower input data.
     * @return {@link Borrower} entity ready to be saved in the database.
     * @author Supunsan
     */
    private Borrower mapToBorrower(BorrowerRequest borrowerRequest) {
        return Borrower.builder()
                .name(borrowerRequest.getName())
                .email(borrowerRequest.getEmail())
                .build();
    }

    /**
     * Maps a {@link Borrower} entity to a {@link BorrowerResponse} DTO.
     *
     * @param borrower The borrower entity fetched from the database.
     * @return {@link BorrowerResponse} containing ID, name, and email.
     * @author Supunsan
     */
    private BorrowerResponse mapToBorrowerResponse(Borrower borrower) {
        return BorrowerResponse.builder()
                .id(borrower.getId())
                .name(borrower.getName())
                .email(borrower.getEmail())
                .build();
    }
}
