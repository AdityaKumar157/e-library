package com.makeprojects.library.elib.entity;

import com.makeprojects.library.elib.enums.Category;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String author;

    @Column(unique = true)
    private String isbn;

    private Double price;

    private String description;

    private Category category;

}
