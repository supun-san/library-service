package com.san.libraryservice.controller;

import com.san.libraryservice.dto.BorrowerRequest;
import com.san.libraryservice.dto.BorrowerResponse;
import com.san.libraryservice.service.BorrowerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/borrowers")
@RequiredArgsConstructor
public class BorrowerController {

    private final BorrowerService borrowerService;

    /**
     * Registers a new borrower in the library.
     *
     * @param borrowerRequest the request body containing borrower details
     * @return {@link ResponseEntity} with the created borrower's information
     * @author Supunsan
     */
    @Operation(
            summary = "Register a new borrower",
            description = "Creates and saves a new borrower using the provided name and email."
    )
    @PostMapping
    public ResponseEntity<BorrowerResponse> registerBorrower(@Valid @RequestBody BorrowerRequest borrowerRequest) {
        return ResponseEntity.ok(borrowerService.register(borrowerRequest));
    }
}
