package com.greychain.loanstore.service;


import com.greychain.loanstore.dao.LoanRepository;
import com.greychain.loanstore.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService{

    private final LoanRepository loanRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public Loan addLoan(Loan loan) {
        return loanRepository.save(loan);
    }

    @Override
    public Loan getLoanById(String loanId) {
        return loanRepository.findById(loanId).orElse(null);
    }

    @Override
    public List<Loan> getLoansByCustomerId(String customerId) {
        return loanRepository.findAllByCustomerId(customerId);
    }

    @Override
    public List<Loan> getLoansByLenderId(String lenderId) {
        return loanRepository.findAllByLenderId(lenderId);
    }
}
