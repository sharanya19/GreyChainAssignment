package com.greychain.loanstore.controller;

import com.greychain.loanstore.entity.AggregateData;
import com.greychain.loanstore.entity.Loan;
import com.greychain.loanstore.exception.LoanNotFoundException;
import com.greychain.loanstore.service.AggregationService;
import com.greychain.loanstore.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class LoanControllerTest {

    @Mock
    private AggregationService aggregationService;

    @Mock
    private LoanService loanService;

    private LoanController loanController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(aggregationService, loanService);
    }

    // Tests for getAllLoans
    @Test
    void getAllLoans_ReturnsListOfLoans() {
        List<Loan> expectedLoans = new ArrayList<>();
        expectedLoans.add(new Loan());
        when(loanService.getAllLoans()).thenReturn(expectedLoans);

        ResponseEntity<List<Loan>> response = loanController.getAllLoans();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLoans, response.getBody());
    }

    @Test
    void getAllLoans_NoLoans_ReturnsEmptyList() {
        when(loanService.getAllLoans()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Loan>> response = loanController.getAllLoans();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }

    @Test
    void addLoan_ValidLoan_ReturnsCreatedLoan() {
        Loan loanToAdd = new Loan();
        when(loanService.addLoan(loanToAdd)).thenReturn(loanToAdd);

        ResponseEntity<Loan> response = loanController.addLoan(loanToAdd);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(loanToAdd, response.getBody());
    }


    @Test
    void getLoanById_ExistingLoanId_ReturnsLoan() {
        String loanId = "123";
        Loan expectedLoan = new Loan();
        when(loanService.getLoanById(loanId)).thenReturn(expectedLoan);

        ResponseEntity<Loan> response = loanController.getLoanById(loanId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLoan, response.getBody());
    }

    @Test
    void getLoansByCustomerId_ExistingCustomerId_ReturnsLoans() {
        String customerId = "C123";
        List<Loan> expectedLoans = new ArrayList<>();
        expectedLoans.add(new Loan());
        when(loanService.getLoansByCustomerId(customerId)).thenReturn(expectedLoans);

        ResponseEntity<List<Loan>> response = loanController.getLoansByCustomerId(customerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLoans, response.getBody());
    }

    @Test
    void getLoansByLenderId_ExistingLenderId_ReturnsLoans() {
        String lenderId = "L456";
        List<Loan> expectedLoans = new ArrayList<>();
        expectedLoans.add(new Loan());
        when(loanService.getLoansByLenderId(lenderId)).thenReturn(expectedLoans);

        ResponseEntity<List<Loan>> response = loanController.getLoansByLenderId(lenderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedLoans, response.getBody());
    }

    @Test
    void getAggregateLoansByCustomer_ReturnsAggregateData() {
        String customerId = "C1";
        double remainingAmount = 30000;
        double interest = 15000;
        double penalty = 0.01;
        when(aggregationService.getAggregateRemainingAmountByCustomer(customerId)).thenReturn(remainingAmount);
        when(aggregationService.getAggregateInterestByCustomer(customerId)).thenReturn(interest);
        when(aggregationService.getAggregatePenaltyByCustomer(customerId)).thenReturn(penalty);

        ResponseEntity<AggregateData> response = loanController.getAggregateLoansByCustomer(customerId);

        assertEquals(30000, response.getBody().getAggregateRemainingAmount(), 0.001);
        assertEquals(15000, response.getBody().getAggregateInterest(), 0.001);
        assertEquals(0.01, response.getBody().getAggregatePenalty(), 0.001);

    }

    @Test
    void getAggregateLoansByInterest_ReturnsAggregateData() {
        int interestPerDay = 1;
        double remainingAmount = 50000;
        double interest = 25000;
        double penalty = 0.02;
        when(aggregationService.getAggregateRemainingAmountByInterest(interestPerDay)).thenReturn(remainingAmount);
        when(aggregationService.getAggregateInterestByInterest(interestPerDay)).thenReturn(interest);
        when(aggregationService.getAggregatePenaltyByInterest(interestPerDay)).thenReturn(penalty);

        ResponseEntity<AggregateData> response = loanController.getAggregateLoansByInterest(interestPerDay);

        assertEquals(50000, response.getBody().getAggregateRemainingAmount());
        assertEquals(25000, response.getBody().getAggregateInterest());
        assertEquals(0.02, response.getBody().getAggregatePenalty());
    }

    @Test
    void getLoanById_InvalidLoanId_ThrowsLoanNotFoundException() {
        // Mock loanService behavior for an invalid loan ID
        when(loanService.getLoanById("100")).thenReturn(null);

        // Assert that a LoanNotFoundException is thrown
        assertThrows(LoanNotFoundException.class, () -> loanController.getLoanById("100"));
    }




}
