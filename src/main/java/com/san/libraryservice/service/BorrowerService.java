package com.san.libraryservice.service;

import com.san.libraryservice.dto.BorrowerRequest;
import com.san.libraryservice.dto.BorrowerResponse;
import com.san.libraryservice.exception.RecordNotFoundException;
import com.san.libraryservice.model.Borrower;
import jakarta.validation.Valid;

public interface BorrowerService {

    /**
     * Registers a new borrower by saving the provided request data to the repository.
     *
     * @param borrowerRequest The borrower request containing name and email.
     * @return {@link BorrowerResponse} containing the saved borrower's details.
     * @author Supunsan
     */
    BorrowerResponse register(@Valid BorrowerRequest borrowerRequest);

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
    void borrowBook(Long borrowerId, Long bookId);

    /**
     * Retrieves a borrower by their unique ID.
     *
     * @param borrowerId the ID of the borrower to retrieve
     * @return the Borrower object with the specified ID
     * @throws RecordNotFoundException if no borrower is found with the given ID
     * @author Supunsan
     */
    Borrower getBorrowerById(Long borrowerId);
}
