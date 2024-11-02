package edu.mtisw.payrollbackend.services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoanSimulationServiceTest {

    private final LoanSimulationService loanSimulationService = new LoanSimulationService();

    @Test
    void testSimulate() {
        Float principal = 100000000f;
        Float years = 20f;
        Float interestRate = 4.5f;

        Long expectedMonthlyInstallment = 632649L;

        Long monthlyInstallment = loanSimulationService.simulate(principal, years, interestRate);

        assertEquals(expectedMonthlyInstallment, monthlyInstallment);
    }
}
