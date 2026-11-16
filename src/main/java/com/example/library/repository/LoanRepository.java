package com.example.library.repository;

import com.example.library.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    boolean existsByBookId(Long bookId);

    boolean existsByMemberId(Long memberId);

    List<Loan> findByMemberId(Long memberId);

    List<Loan> findByMemberIdAndReturnDateIsNull(Long memberId);

    List<Loan> findByReturnDateIsNull();
}
