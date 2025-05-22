package com.san.libraryservice.dto;

import com.san.libraryservice.validation.Isbn;
import com.san.libraryservice.validation.ValidName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequest {

    @Schema(
            description = "ISBN number of the book",
            example = "978-3-16-148410-0"
    )
    @NotBlank(message = "ISBN is required")
    @Isbn
    private String isbn;

    @Schema(
            description = "Title of the book or resource",
            example = "Clean Code: A Handbook of Java Fundamentals",
            minLength = 2,
            maxLength = 100
    )
    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 100, message = "Title must be between 2 and 100 characters")
    private String title;

    @Schema(
            description = "Allowed characters are letters, spaces, hyphens and apostrophes.",
            example = "Supun-San",
            minLength = 2,
            maxLength = 50
    )
    @NotBlank(message = "Author is required")
    @ValidName
    private String author;

}
