package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "books", indexes = @Index(name = "idx_book_isbn", columnList = "isbn", unique = true))
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String title;
    @NotBlank
    @Column(nullable = false)
    private String author;
    @NotBlank
    @Column(nullable = false, unique = true)
    private String isbn;
    @Min(0)
    private int publicationYear;
    @Min(0)
    @Column(nullable = false)
    private int totalCopies;
    @Min(0)
    @Column(nullable = false)
    private int availableCopies;

    protected Book() {
    }

    public Book(String title, String author, String isbn, int publicationYear, int totalCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setTotalCopies(int totalCopies) {
        int borrowed = this.totalCopies - this.availableCopies;
        if (totalCopies < borrowed)
            throw new IllegalArgumentException("Total copies cannot be less than copies currently on loan");
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies - borrowed;
    }

    public void borrowCopy() {
        if (availableCopies < 1)
            throw new IllegalStateException("No copies of this book are available");
        availableCopies--;
    }

    public void returnCopy() {
        if (availableCopies >= totalCopies)
            throw new IllegalStateException("All copies of this book are already available");
        availableCopies++;
    }
}
