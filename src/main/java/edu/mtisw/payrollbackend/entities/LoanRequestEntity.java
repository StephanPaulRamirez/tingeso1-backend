package edu.mtisw.payrollbackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "loan_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String userRut;
    private Float requestedAmount;
    private String status;

    private Float period;
    private Long monthlyInstallment;
    private Integer clientMonthlyIncome;

    private boolean installmentToServiceRatio;
    private boolean creditHistory;
    private boolean employmentStability;
    private boolean debtToIncomeRatio;
    private boolean maximumFinancingAmount;
    private boolean applicantAge;

    private String savingsCapacity;

    private boolean minimumRequiredBalance;
    private boolean consistentSavingsHistory;
    private boolean periodicDeposits;
    private boolean balanceToAgeRelationship;
    private boolean recentWithdrawals;

    private Long finalCosts;

    private Long ratio;

    private String loanType;
    private Float interestRate;
    private Integer maxTerm;
    private Double maxFinancing;

    private String documentRequirements;
}
