package com.greychain.loanstore.dao;

import com.greychain.loanstore.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String> {

    @Query("SELECT l FROM Loan l WHERE l.lenderId = :lenderId")
    List<Loan> findAllByLenderId(@Param("lenderId") String lenderId);

    @Query("SELECT l FROM Loan l WHERE l.interestPerDay = :interestPerDay")
    List<Loan> findAllByInterestPerDay(@Param("interestPerDay") int interestPerDay);

    @Query("SELECT l FROM Loan l WHERE l.customerId = :customerId")
    List<Loan> findAllByCustomerId(@Param("customerId") String customerId);

}
