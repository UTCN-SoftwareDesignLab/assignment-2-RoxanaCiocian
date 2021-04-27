package com.example.demo.book.model.dto;

import lombok.*;

import javax.persistence.Column;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    private String genre;
    private Float price;
    private Integer quantity;
}