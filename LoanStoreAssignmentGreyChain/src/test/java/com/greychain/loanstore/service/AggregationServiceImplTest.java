package com.greychain.loanstore.service;

import com.greychain.loanstore.dao.LoanRepository;
import com.greychain.loanstore.entity.Loan;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class AggregationServiceImplTest {

    private final LoanRepository loanRepository = Mockito.mock(LoanRepository.class);
    private final AggregationServiceImpl aggregationService = new AggregationServiceImpl(loanRepository);

    @Test
    void getAggregateRemainingAmountByLender_ShouldReturnCorrectAmount() {
        String lenderId = "LEN1";
        List<Loan> loans = Arrays.asList(
                new Loan("1", "C1", "LEN1", 10000, 10000, null, 1, null, 0.01, false),
                new Loan("2", "C1", "LEN1", 20000, 5000, null, 1, null, 0.01, false)
        );
        when(loanRepository.findAllByLenderId(lenderId)).thenReturn(loans);

        double result = aggregationService.getAggregateRemainingAmountByLender(lenderId);

        assertEquals(15000.0, result, 0.01);
    }

    @Test
    void getAggregateInterestByInterest_ShouldReturnCorrectAmount() {
        int interestPerDay = 1;
        List<Loan> loans = Arrays.asList(
                new Loan("1", "C1", "LEN1", 10000, 10000, null, interestPerDay, null, 0.01, false),
                new Loan("2", "C1", "LEN1", 20000, 5000, null, interestPerDay, null, 0.01, false)
        );
        when(loanRepository.findAllByInterestPerDay(interestPerDay)).thenReturn(loans);

        double result = aggregationService.getAggregateInterestByInterest(interestPerDay);

        assertEquals(2.0, result, 0.01);
    }

    @Test
    void getAggregatePenaltyByInterest_ShouldReturnCorrectAmount() {
        int interestPerDay = 1;
        List<Loan> loans = Arrays.asList(
                new Loan("1", "C1", "LEN1", 10000, 10000, null, interestPerDay, null, 0.01, false),
                new Loan("2", "C1", "LEN1", 20000, 5000, null, interestPerDay, null, 0.01, false)
        );
        when(loanRepository.findAllByInterestPerDay(interestPerDay)).thenReturn(loans);

        double result = aggregationService.getAggregatePenaltyByInterest(interestPerDay);

        assertEquals(0.02, result, 0.01);
    }

    @Test
    void getAggregateInterestByCustomer_ShouldReturnCorrectAmount() {
        String customerId = "C1";
        List<Loan> loans = Arrays.asList(
                new Loan("1", customerId, "LEN1", 10000, 10000, null, 1, null, 0.01, false),
                new Loan("2", customerId, "LEN1", 20000, 5000, null, 1, null, 0.01, false)
        );
        when(loanRepository.findAllByCustomerId(customerId)).thenReturn(loans);

        double result = aggregationService.getAggregateInterestByCustomer(customerId);

        assertEquals(2.0, result, 0.01);
    }

    @Test
    void getAggregatePenaltyByCustomer_ShouldReturnCorrectAmount() {
        String customerId = "C1";
        List<Loan> loans = Arrays.asList(
                new Loan("1", customerId, "LEN1", 10000, 10000, null, 1, null, 0.01, false),
                new Loan("2", customerId, "LEN1", 20000, 5000, null, 1, null, 0.01, false)
        );
        when(loanRepository.findAllByCustomerId(customerId)).thenReturn(loans);

        double result = aggregationService.getAggregatePenaltyByCustomer(customerId);

        assertEquals(0.02, result, 0.01);
    }


    @Test
    void getAggregatePenaltyByLender_ShouldReturnCorrectAmount() {
        String lenderId = "LEN1";
        List<Loan> loans = Arrays.asList(
                new Loan("1", "C1", lenderId, 10000, 10000, null, 1, null, 0.01, false),
                new Loan("2", "C1", lenderId, 20000, 5000, null, 1, null, 0.02, false)
        );
        when(loanRepository.findAllByLenderId(lenderId)).thenReturn(loans);

        double result = aggregationService.getAggregatePenaltyByLender(lenderId);

        assertEquals(0.03, result, 0.01);
    }

    @Test
    void getAggregateRemainingAmountByInterest_WithInvalidInterestPerDay_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            int invalidInterestPerDay = -1;
            aggregationService.getAggregateRemainingAmountByInterest(invalidInterestPerDay);
        });
    }

    @Test
    void getAggregateRemainingAmountByCustomer_WithNullOrEmptyCustomerId_ShouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> {
            aggregationService.getAggregateRemainingAmountByCustomer(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            aggregationService.getAggregateRemainingAmountByCustomer("");
        });
    }

    @Test
    void getAggregatePenaltyByCustomer_WithException_ShouldThrowRuntimeException() {
        String customerId = "C1";
        when(loanRepository.findAllByCustomerId(customerId)).thenThrow(new RuntimeException("DB connection failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> aggregationService.getAggregatePenaltyByCustomer(customerId),
                "Expected getAggregatePenaltyByCustomer to throw RuntimeException, but it didn't");

        assertEquals("An error occurred while aggregating penalty by customer", thrown.getMessage());
        assertNotNull(thrown.getCause());
        assertEquals("DB connection failed", thrown.getCause().getMessage());
    }

    @Test
    void getAggregateInterestByLender_WithException_ShouldThrowRuntimeException() {
        String lenderId = "LEN1";
        when(loanRepository.findAllByLenderId(lenderId)).thenThrow(new RuntimeException("DB connection failed"));

        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> aggregationService.getAggregateInterestByLender(lenderId),
                "Expected getAggregateInterestByLender to throw RuntimeException, but it didn't");

        assertEquals("An error occurred while aggregating interest by lender", thrown.getMessage());
        assertNotNull(thrown.getCause());
        assertEquals("DB connection failed", thrown.getCause().getMessage());
    }

    @Test
    void testConstructor() {
        String message = "Not found";
        Throwable cause = new RuntimeException("Root cause");
        AggregationServiceImpl.NotFoundException exception = new AggregationServiceImpl.NotFoundException(message, cause);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void testMessage() {
        String message = "Not found";
        AggregationServiceImpl.NotFoundException exception = new AggregationServiceImpl.NotFoundException(message, null);

        assertNotNull(exception);
        assertEquals(message, exception.getMessage());
    }

}
