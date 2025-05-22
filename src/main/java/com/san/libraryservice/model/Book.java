package com.san.libraryservice.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends BaseEntity {

    private String isbn;
    private String title;
    private String author;
    private boolean available;

}
