package com.san.libraryservice.service;

import com.san.libraryservice.dto.BorrowerRequest;
import com.san.libraryservice.dto.BorrowerResponse;
import com.san.libraryservice.model.Borrower;
import com.san.libraryservice.repository.BorrowerRepository;
import com.san.libraryservice.service.impl.BorrowerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BorrowerServiceImplTest {

    @Mock
    private BorrowerRepository borrowerRepository;

    @InjectMocks
    private BorrowerServiceImpl borrowerService;

    @Test
    void testRegister_shouldSaveAndReturnResponse() {
        // Prepare a valid BorrowerRequest DTO
        BorrowerRequest request = new BorrowerRequest("Supun San", "supunsan@gmail.com");

        // Simulate saved entity with generated ID
        Borrower savedBorrower = Borrower.builder()
                .name(request.getName())
                .email(request.getEmail())
                .build();
        savedBorrower.setId(1L);

        when(borrowerRepository.save(any(Borrower.class))).thenReturn(savedBorrower);

        // Execute register operation
        BorrowerResponse response = borrowerService.register(request);

        // Assert returned data matches saved entity
        assertNotNull(response);
        assertEquals(request.getName(), response.getName());
        assertEquals(request.getEmail(), response.getEmail());
        assertEquals(savedBorrower.getId(), response.getId());

        verify(borrowerRepository, times(1)).save(any(Borrower.class));
    }
}
