package com.greychain.loanstore.service;


import com.greychain.loanstore.dao.LoanRepository;
import com.greychain.loanstore.entity.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AggregationServiceImpl implements AggregationService {

    private final LoanRepository loanRepository;

    @Autowired
    public AggregationServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public double getAggregateRemainingAmountByLender(String lenderId) {
        try {
            List<Loan> loans = loanRepository.findAllByLenderId(lenderId);
            return loans.stream().mapToDouble(Loan::getRemainingAmount).sum();
        } catch (Exception e) {
            // Handle specific exceptions and rethrow a custom exception
            if (e instanceof NoSuchElementException) {
                throw new NotFoundException("No loans found for the given lender", e);
            } else {
                throw new RuntimeException("An error occurred while aggregating remaining amount by lender", e);
            }
        }
    }

    @Override
    public double getAggregateInterestByLender(String lenderId) {
        try {
            List<Loan> loans = loanRepository.findAllByLenderId(lenderId);
            return loans.stream().mapToDouble(Loan::getInterestPerDay).sum();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while aggregating interest by lender", e);
        }
    }

    @Override
    public double getAggregatePenaltyByLender(String lenderId) {
        try {
            List<Loan> loans = loanRepository.findAllByLenderId(lenderId);
            return loans.stream().mapToDouble(Loan::getPenaltyPerDay).sum();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while aggregating penalty by lender", e);
        }
    }

    @Override
    public double getAggregateRemainingAmountByInterest(int interestPerDay) {
        if (interestPerDay <= 0) {
            throw new IllegalArgumentException("Interest per day should be a positive value.");
        }

        List<Loan> loans = loanRepository.findAllByInterestPerDay(interestPerDay);
        return loans.stream().mapToDouble(Loan::getRemainingAmount).sum();
    }


    @Override
    public double getAggregateRemainingAmountByCustomer(String customerId) {
        if (customerId == null || customerId.isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty.");
        }

        List<Loan> loans = loanRepository.findAllByCustomerId(customerId);
        return loans.stream().mapToDouble(Loan::getRemainingAmount).sum();
    }

    public double getAggregateInterestByInterest(int interestPerDay) {
        try {
            List<Loan> loans = loanRepository.findAllByInterestPerDay(interestPerDay);
            return loans.stream().mapToDouble(Loan::getInterestPerDay).sum();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while aggregating interest by interest per day", e);
        }
    }


    public double getAggregatePenaltyByInterest(int interestPerDay) {
        try {
            List<Loan> loans = loanRepository.findAllByInterestPerDay(interestPerDay);
            return loans.stream().mapToDouble(Loan::getPenaltyPerDay).sum();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while aggregating penalty by interest per day", e);
        }
    }

    public double getAggregateInterestByCustomer(String customerId) {
        try {
            List<Loan> loans = loanRepository.findAllByCustomerId(customerId);
            return loans.stream().mapToDouble(Loan::getInterestPerDay).sum();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while aggregating interest by customer", e);
        }
    }

    public double getAggregatePenaltyByCustomer(String customerId) {
        try {
            List<Loan> loans = loanRepository.findAllByCustomerId(customerId);
            return loans.stream().mapToDouble(Loan::getPenaltyPerDay).sum();
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while aggregating penalty by customer", e);
        }
    }

    static class NotFoundException extends RuntimeException {
        public NotFoundException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
