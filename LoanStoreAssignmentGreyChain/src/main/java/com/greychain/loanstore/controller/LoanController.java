package com.greychain.loanstore.controller;


import com.greychain.loanstore.entity.AggregateData;
import com.greychain.loanstore.entity.Loan;
import com.greychain.loanstore.exception.CustomerNotFoundException;
import com.greychain.loanstore.exception.LoanNotFoundException;
import com.greychain.loanstore.service.AggregationService;
import com.greychain.loanstore.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final AggregationService aggregationService;

    private final LoanService loanService;

    @Autowired
    public LoanController(AggregationService aggregationService, LoanService loanService) {
        this.aggregationService = aggregationService;
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        try {
            List<Loan> loans = loanService.getAllLoans();
            return ResponseEntity.ok(loans);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Loan> addLoan(@RequestBody Loan loan) {
        try {
            Loan addedLoan = loanService.addLoan(loan);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedLoan);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable String loanId) {
        Loan loan = loanService.getLoanById(loanId);
        if (loan == null) {
            throw new LoanNotFoundException("Loan with ID " + loanId + " not found.");
        }
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Loan>> getLoansByCustomerId(@PathVariable String customerId) {
        List<Loan> loans = loanService.getLoansByCustomerId(customerId);
        if (loans.isEmpty()) {
            throw new CustomerNotFoundException("Loans not found for customer with ID " + customerId);
        }
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/lender/{lenderId}")
    public ResponseEntity<List<Loan>> getLoansByLenderId(@PathVariable String lenderId) {
        List<Loan> loans = loanService.getLoansByLenderId(lenderId);
        return ResponseEntity.ok(loans);
    }

    @GetMapping("/aggregate/lender/{lenderId}")
    public ResponseEntity<AggregateData> getAggregateLoansByLender(@PathVariable String lenderId) {
        double aggregateRemainingAmount = aggregationService.getAggregateRemainingAmountByLender(lenderId);
        double aggregateInterest = aggregationService.getAggregateInterestByLender(lenderId);
        double aggregatePenalty = aggregationService.getAggregatePenaltyByLender(lenderId);

        AggregateData aggregateData = new AggregateData(aggregateRemainingAmount, aggregateInterest, aggregatePenalty);
        return ResponseEntity.ok(aggregateData);
    }

    @GetMapping("/aggregate/customer/{customerId}")
    public ResponseEntity<AggregateData> getAggregateLoansByCustomer(@PathVariable String customerId) {
        double aggregateRemainingAmount = aggregationService.getAggregateRemainingAmountByCustomer(customerId);
        double aggregateInterest = aggregationService.getAggregateInterestByCustomer(customerId);
        double aggregatePenalty = aggregationService.getAggregatePenaltyByCustomer(customerId);

        AggregateData aggregateData = new AggregateData(aggregateRemainingAmount, aggregateInterest, aggregatePenalty);
        return ResponseEntity.ok(aggregateData);
    }

    @GetMapping("/aggregate/interest/{interestPerDay}")
    public ResponseEntity<AggregateData> getAggregateLoansByInterest(@PathVariable int interestPerDay) {
        double aggregateRemainingAmount = aggregationService.getAggregateRemainingAmountByInterest(interestPerDay);
        double aggregateInterest = aggregationService.getAggregateInterestByInterest(interestPerDay);
        double aggregatePenalty = aggregationService.getAggregatePenaltyByInterest(interestPerDay);

        AggregateData aggregateData = new AggregateData(aggregateRemainingAmount, aggregateInterest, aggregatePenalty);
        return ResponseEntity.ok(aggregateData);
    }

}
