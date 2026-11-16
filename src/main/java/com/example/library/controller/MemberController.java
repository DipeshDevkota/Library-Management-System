package com.example.library.controller;
import com.example.library.dto.MemberRequest;
import com.example.library.model.Member;
import com.example.library.service.LibraryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/members")
public class MemberController {
    private final LibraryService service;
    public MemberController(LibraryService service) { this.service = service; }
    @GetMapping public List<Member> list() { return service.members(); }
    @GetMapping("/{id}") public Member get(@PathVariable Long id) { return service.member(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Member create(@Valid @RequestBody MemberRequest request) { return service.createMember(request); }
    @PutMapping("/{id}") public Member update(@PathVariable Long id, @Valid @RequestBody MemberRequest request) { return service.updateMember(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) { service.deleteMember(id); }
}
