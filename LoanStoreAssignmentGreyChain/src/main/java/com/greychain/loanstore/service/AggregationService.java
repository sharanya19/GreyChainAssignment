package com.greychain.loanstore.service;

public interface AggregationService {

    double getAggregateRemainingAmountByLender(String lenderId);
    double getAggregateInterestByLender(String lenderId);
    double getAggregatePenaltyByLender(String lenderId);
    double getAggregateRemainingAmountByInterest(int interestPerDay);
    double getAggregateInterestByInterest(int interestPerDay);
    double getAggregatePenaltyByInterest(int interestPerDay);
    double getAggregateRemainingAmountByCustomer(String customerId);
    double getAggregateInterestByCustomer(String customerId);
    double getAggregatePenaltyByCustomer(String customerId);
}
