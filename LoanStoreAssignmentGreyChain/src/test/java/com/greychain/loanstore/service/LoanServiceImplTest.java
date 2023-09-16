package com.greychain.loanstore.service;

import com.greychain.loanstore.dao.LoanRepository;
import com.greychain.loanstore.entity.Loan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

public class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    private LoanServiceImpl loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loanService = new LoanServiceImpl(loanRepository);
    }

    @Test
    void getAllLoans() {
        List<Loan> mockLoans = new ArrayList<>();
        mockLoans.add(new Loan());
        when(loanRepository.findAll()).thenReturn(mockLoans);

        List<Loan> loans = loanService.getAllLoans();

        assertEquals(mockLoans.size(), loans.size());
    }

    @Test
    void addLoan() {
        Loan mockLoan = new Loan();
        when(loanRepository.save(mockLoan)).thenReturn(mockLoan);

        Loan addedLoan = loanService.addLoan(mockLoan);

        assertSame(mockLoan, addedLoan);
    }

    @Test
    void getLoanById() {
        String loanId = "123";
        Loan mockLoan = new Loan();
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(mockLoan));

        Loan loan = loanService.getLoanById(loanId);

        assertSame(mockLoan, loan);
    }

    @Test
    void getLoansByCustomerId() {
        String customerId = "C1";
        List<Loan> mockLoans = new ArrayList<>();
        when(loanRepository.findAllByCustomerId(customerId)).thenReturn(mockLoans);

        List<Loan> loans = loanService.getLoansByCustomerId(customerId);

        assertEquals(mockLoans.size(), loans.size());
    }

    @Test
    void getLoansByLenderId() {
        String lenderId = "LEN1";
        List<Loan> mockLoans = new ArrayList<>();
        when(loanRepository.findAllByLenderId(lenderId)).thenReturn(mockLoans);

        List<Loan> loans = loanService.getLoansByLenderId(lenderId);

        assertEquals(mockLoans.size(), loans.size());
    }
}
