package com.example.library.controller;
import com.example.library.dto.CheckoutRequest;
import com.example.library.model.Loan;
import com.example.library.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/loans")
public class LoanController {
    private final LibraryService service;
    public LoanController(LibraryService service) { this.service = service; }
    @GetMapping public List<Loan> list(@RequestParam(defaultValue = "false") boolean active) { return active ? service.activeLoans() : service.loans(); }
    @GetMapping("/member/{memberId}") public List<Loan> memberLoans(@PathVariable Long memberId) { return service.memberLoans(memberId); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Loan checkout(@Valid @RequestBody CheckoutRequest request) { return service.checkout(request); }
    @PostMapping("/{id}/return") public Loan returnBook(@PathVariable Long id) { return service.returnLoan(id); }
}
