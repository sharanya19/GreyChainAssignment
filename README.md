# Loan Store Application

This is a Java program implementing a Loan Store application using Spring Boot.

## Assignment Description

Write a Java Program with all the JUNIT test cases. TDD approach will be preferred.
Framework: Spring Boot, Build Tool: Maven or Gradle.

REST APIs need to be exposed as follows:
- Get All Loans (`/loans`)
- Add loan (`/loans/add`)
- Get loan by loan id (`/loans/{loanId}`)
- Get loan by customer id (`/loans/customer/{customerId}`)
- Get loans by lenderId (`/loans/lender/{lenderId}`)
- Get aggregate loans by Lender (aggregate remaining amount, Interest, and Penalty) (`/loans/aggregate/lender/{lenderId}`)
- Get aggregate loans by customer id (aggregate remaining amount, Interest, and Penalty) (`/loans/aggregate/customer/{customerId}`)
- Get the aggregate loans by interest (aggregate remaining amount, Interest, and Penalty) (`/loans/aggregate/interest/{interestPerDay}`)

## cURL Commands

Here are some example cURL commands to interact with the APIs:

- Get All Loans:
  ```bash
  curl http://localhost:8080/loans
- Add Loan:
 ```bash
  curl -X POST -H "Content-Type: application/json" -d '{"customerId":"C1", "lenderId":"LEN1", "amount":20000, "remainingAmount":20000, "paymentDate":"05/06/2023", "interestPerDay":1, "dueDate":"05/07/2023", "penaltyPerDay":0.01}' http://localhost:8080/loans/add


