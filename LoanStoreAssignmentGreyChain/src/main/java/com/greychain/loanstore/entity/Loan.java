package com.greychain.loanstore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;


@Entity
@Table(name = "loans")  // Table name
public class Loan {
    @Id
    @Column(name = "loan_id")
    private String loanId;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "lender_id")
    private String lenderId;

    @Column(name = "amount")
    private double amount;

    @Column(name = "remaining_amount")
    private double remainingAmount;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "interest_per_day")
    private int interestPerDay;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "penalty_per_day")
    private double penaltyPerDay;

    @Column(name = "is_cancelled")
    private boolean isCancelled;

    public Loan() {
        // Default constructor needed for JPA
    }

    public Loan(String loanId, String customerId, String lenderId, double amount,
                double remainingAmount, Date paymentDate, int interestPerDay,
                Date dueDate, double penaltyPerDay, boolean isCancelled) {
        this.loanId = loanId;
        this.customerId = customerId;
        this.lenderId = lenderId;
        this.amount = amount;
        this.remainingAmount = remainingAmount;
        this.paymentDate = paymentDate;
        this.interestPerDay = interestPerDay;
        this.dueDate = dueDate;
        this.penaltyPerDay = penaltyPerDay;
        this.isCancelled = isCancelled;
    }


    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLenderId() {
        return lenderId;
    }

    public void setLenderId(String lenderId) {
        this.lenderId = lenderId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getInterestPerDay() {
        return interestPerDay;
    }

    public void setInterestPerDay(int interestPerDay) {
        this.interestPerDay = interestPerDay;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public double getPenaltyPerDay() {
        return penaltyPerDay;
    }

    public void setPenaltyPerDay(double penaltyPerDay) {
        this.penaltyPerDay = penaltyPerDay;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId='" + loanId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", lenderId='" + lenderId + '\'' +
                ", amount=" + amount +
                ", remainingAmount=" + remainingAmount +
                ", paymentDate=" + paymentDate +
                ", interestPerDay=" + interestPerDay +
                ", dueDate=" + dueDate +
                ", penaltyPerDay=" + penaltyPerDay +
                ", isCancelled=" + isCancelled +
                '}';
    }



}
