package com.san.libraryservice.service;

import com.san.libraryservice.dto.BorrowerRequest;
import com.san.libraryservice.dto.BorrowerResponse;
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

}
