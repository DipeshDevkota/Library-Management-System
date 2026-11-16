package com.example.library.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Book book;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Member member;
    @Column(nullable = false)
    private LocalDate checkoutDate;
    @Column(nullable = false)
    private LocalDate dueDate;
    private LocalDate returnDate;

    protected Loan() {
    }

    public Loan(Book book, Member member, LocalDate checkoutDate, LocalDate dueDate) {
        this.book = book;
        this.member = member;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getCheckoutDate() {
        return checkoutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public void markReturned(LocalDate date) {
        this.returnDate = date;
    }
}
