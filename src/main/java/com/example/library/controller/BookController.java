package com.example.library.controller;
import com.example.library.dto.BookRequest;
import com.example.library.model.Book;
import com.example.library.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/books")
public class BookController {
    private final LibraryService service;
    public BookController(LibraryService service) { this.service = service; }
    @GetMapping public List<Book> list(@RequestParam(required = false) String search) { return service.books(search); }
    @GetMapping("/{id}") public Book get(@PathVariable Long id) { return service.book(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Book create(@Valid @RequestBody BookRequest request) { return service.createBook(request); }
    @PutMapping("/{id}") public Book update(@PathVariable Long id, @Valid @RequestBody BookRequest request) { return service.updateBook(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.deleteBook(id); }
}
