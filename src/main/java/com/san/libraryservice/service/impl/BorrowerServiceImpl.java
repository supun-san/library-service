package com.san.libraryservice.service.impl;

import com.san.libraryservice.dto.BorrowerRequest;
import com.san.libraryservice.dto.BorrowerResponse;
import com.san.libraryservice.model.Borrower;
import com.san.libraryservice.repository.BorrowerRepository;
import com.san.libraryservice.service.BorrowerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.san.libraryservice.constant.LogConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class BorrowerServiceImpl implements BorrowerService {

    private final BorrowerRepository borrowerRepository;

    /**
     * Registers a new borrower by saving the provided request data to the repository.
     *
     * @param borrowerRequest The borrower request containing name and email.
     * @return {@link BorrowerResponse} containing the saved borrower's details.
     * @author Supunsan
     */
    @Override
    public BorrowerResponse register(BorrowerRequest borrowerRequest) {

        log.info(REGISTER_BORROWER_SERVICE_START, borrowerRequest.getEmail());
        Borrower savedBorrower = borrowerRepository.save(mapToBorrower(borrowerRequest));
        log.info(REGISTER_BORROWER_SUCCESS, savedBorrower.getId());

        return mapToBorrowerResponse(savedBorrower);
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
