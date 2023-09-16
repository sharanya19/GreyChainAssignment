package com.greychain.loanstore.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AggregateData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aggregateDataId;

    private double aggregateRemainingAmount;
    private double aggregateInterest;
    private double aggregatePenalty;

    public AggregateData(double aggregateRemainingAmount, double aggregateInterest, double aggregatePenalty) {
        this.aggregateRemainingAmount = aggregateRemainingAmount;
        this.aggregateInterest = aggregateInterest;
        this.aggregatePenalty = aggregatePenalty;

    }

    public double getAggregateRemainingAmount() {
        return aggregateRemainingAmount;
    }

    public double getAggregateInterest() {
        return aggregateInterest;
    }

    public void setAggregateInterest(double aggregateInterest) {
        this.aggregateInterest = aggregateInterest;
    }

    public double getAggregatePenalty() {
        return aggregatePenalty;
    }

    public void setAggregatePenalty(double aggregatePenalty) {
        this.aggregatePenalty = aggregatePenalty;
    }

    public Long getAggregateDataId() {
        return aggregateDataId;
    }

    public void setAggregateDataId(Long aggregateDataId) {
        this.aggregateDataId = aggregateDataId;
    }
}
