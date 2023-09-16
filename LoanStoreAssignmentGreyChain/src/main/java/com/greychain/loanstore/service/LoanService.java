package com.greychain.loanstore.service;

import com.greychain.loanstore.entity.Loan;

import java.util.List;

public interface LoanService {

    List<Loan> getAllLoans();
    Loan addLoan(Loan loan);
    Loan getLoanById(String loanId);
    List<Loan> getLoansByCustomerId(String customerId);
    List<Loan> getLoansByLenderId(String lenderId);
}
