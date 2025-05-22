package com.san.libraryservice.dto;

import com.san.libraryservice.validation.ValidName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowerRequest {

    @Schema(
            description = "Allowed characters are letters, spaces, hyphens and apostrophes.",
            example = "Supun-San",
            minLength = 2,
            maxLength = 50
    )
    @NotBlank(message = "Name is required")
    @ValidName
    private String name;

    @Schema(
            description = "Must be a valid email format.",
            example = "supun.san@example.com",
            minLength = 5,
            maxLength = 254
    )
    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

}
