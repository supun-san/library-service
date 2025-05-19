package com.san.libraryservice.controller;

import com.san.libraryservice.dto.BorrowerRequest;
import com.san.libraryservice.dto.BorrowerResponse;
import com.san.libraryservice.service.BorrowerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.san.libraryservice.constant.LogConstants.*;

@RestController
@RequestMapping("/api/v1/borrowers")
@RequiredArgsConstructor
@Slf4j
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
    @PostMapping("/register")
    public ResponseEntity<BorrowerResponse> registerBorrower(@Valid @RequestBody BorrowerRequest borrowerRequest) {
        log.info(REGISTER_BORROWER_CONTROLLER_START, borrowerRequest.getEmail());
        return ResponseEntity.ok(borrowerService.register(borrowerRequest));
    }

}
