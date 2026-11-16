package com.example.library.service;

import com.example.library.dto.*;
import com.example.library.model.*;
import com.example.library.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class LibraryService {
    private final BookRepository books;
    private final MemberRepository members;
    private final LoanRepository loans;

    public LibraryService(BookRepository books, MemberRepository members, LoanRepository loans) {
        this.books = books;
        this.members = members;
        this.loans = loans;
    }

    public List<Book> books(String search) {
        return search == null || search.isBlank() ? books.findAll()
                : books.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(search, search);
    }

    public Book book(Long id) {
        return books.findById(id).orElseThrow(() -> new NotFoundException("Book", id));
    }

    public Book createBook(BookRequest request) {
        if (books.existsByIsbn(request.isbn()))
            throw new ConflictException("A book with this ISBN already exists");
        return books.save(new Book(request.title(), request.author(), request.isbn(), request.publicationYear(),
                request.totalCopies()));
    }

    public Book updateBook(Long id, BookRequest request) {
        Book book = book(id);
        if (!book.getIsbn().equals(request.isbn()) && books.existsByIsbn(request.isbn()))
            throw new ConflictException("A book with this ISBN already exists");
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setIsbn(request.isbn());
        book.setPublicationYear(request.publicationYear());
        book.setTotalCopies(request.totalCopies());
        return books.save(book);
    }

    public void deleteBook(Long id) {
        Book book = book(id);
        if (loans.existsByBookId(id))
            throw new ConflictException("Book has loan history and cannot be deleted");
        books.delete(book);
    }

    public List<Member> members() {
        return members.findAll();
    }

    public Member member(Long id) {
        return members.findById(id).orElseThrow(() -> new NotFoundException("Member", id));
    }

    public Member createMember(MemberRequest request) {
        if (members.existsByEmail(request.email()))
            throw new ConflictException("A member with this email already exists");
        return members.save(new Member(request.name(), request.email(), request.phone()));
    }

    public Member updateMember(Long id, MemberRequest request) {
        Member member = member(id);
        if (!member.getEmail().equals(request.email()) && members.existsByEmail(request.email()))
            throw new ConflictException("A member with this email already exists");
        member.setName(request.name());
        member.setEmail(request.email());
        member.setPhone(request.phone());
        return members.save(member);
    }

    public void deleteMember(Long id) {
        Member member = member(id);
        if (!loans.findByMemberIdAndReturnDateIsNull(id).isEmpty())
            throw new ConflictException("Member has active loans");
        if (loans.existsByMemberId(id))
            throw new ConflictException("Member has loan history and cannot be deleted");
        members.delete(member);
    }

    public List<Loan> loans() {
        return loans.findAll();
    }

    public List<Loan> activeLoans() {
        return loans.findByReturnDateIsNull();
    }

    public List<Loan> memberLoans(Long memberId) {
        member(memberId);
        return loans.findByMemberId(memberId);
    }

    @Transactional
    public Loan checkout(CheckoutRequest request) {
        Book book = books.findById(request.bookId()).orElseThrow(() -> new NotFoundException("Book", request.bookId()));
        Member member = members.findById(request.memberId())
                .orElseThrow(() -> new NotFoundException("Member", request.memberId()));
        book.borrowCopy();
        books.save(book);
        LocalDate today = LocalDate.now();
        return loans.save(new Loan(book, member, today, today.plusDays(14)));
    }

    @Transactional
    public Loan returnLoan(Long loanId) {
        Loan loan = loans.findById(loanId).orElseThrow(() -> new NotFoundException("Loan", loanId));
        if (loan.isReturned())
            throw new ConflictException("This loan has already been returned");
        loan.markReturned(LocalDate.now());
        loan.getBook().returnCopy();
        books.save(loan.getBook());
        return loans.save(loan);
    }
}
