package edu.mtisw.payrollbackend.services;

import edu.mtisw.payrollbackend.entities.LoanRequestEntity;
import edu.mtisw.payrollbackend.repositories.LoanRequestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanRequestServiceTest {

    @Mock
    private LoanRequestRepository loanRequestRepository;

    @Mock
    private LoanSimulationService loanSimulationService;

    @InjectMocks
    private LoanRequestService loanRequestService;

    private LoanRequestEntity loanRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        loanRequest = new LoanRequestEntity();
        loanRequest.setId(1L);
        loanRequest.setRequestedAmount(100000.0f);
        loanRequest.setPeriod(10.0f);
        loanRequest.setInterestRate(5.0f);
        loanRequest.setClientMonthlyIncome(10000);
    }

    @Test
    void testSaveLoanRequest() {
        when(loanSimulationService.simulate(anyFloat(), anyFloat(), anyFloat())).thenReturn(5000L);
        when(loanRequestRepository.save(any(LoanRequestEntity.class))).thenReturn(loanRequest);

        LoanRequestEntity savedLoanRequest = loanRequestService.saveLoanRequest(loanRequest);

        assertNotNull(savedLoanRequest);
        assertEquals(5000L, savedLoanRequest.getMonthlyInstallment());
        verify(loanRequestRepository, times(1)).save(loanRequest);
    }

    @Test
    void testGetLoanRequestById_Success() {
        when(loanRequestRepository.findById(1L)).thenReturn(Optional.of(loanRequest));

        LoanRequestEntity found = loanRequestService.getLoanRequestById(1L);

        assertNotNull(found);
        verify(loanRequestRepository, times(1)).findById(1L);
    }

    @Test
    void testGetLoanRequestById_NotFound() {
        when(loanRequestRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> loanRequestService.getLoanRequestById(1L));
    }

    @Test
    void testGetLoanRequestByUserRut() {
        ArrayList<LoanRequestEntity> loanRequests = new ArrayList<>();
        loanRequests.add(loanRequest);
        when(loanRequestRepository.findByUserRut("12345678-9")).thenReturn(loanRequests);

        List<LoanRequestEntity> result = loanRequestService.getLoanRequestByUserRut("12345678-9");

        assertEquals(1, result.size());
        verify(loanRequestRepository, times(1)).findByUserRut("12345678-9");
    }

    @Test
    void testUpdateLoanRequest() {
        when(loanRequestRepository.save(any(LoanRequestEntity.class))).thenReturn(loanRequest);

        LoanRequestEntity updated = loanRequestService.updateLoanRequest(loanRequest);

        assertNotNull(updated);
        verify(loanRequestRepository, times(1)).save(loanRequest);
    }

    @Test
    void testSimulateService() {
        when(loanSimulationService.simulate(anyFloat(), anyFloat(), anyFloat())).thenReturn(5000L);

        Long simulation = loanRequestService.simulateService(100000f, 10f, 5f);

        assertEquals(5000L, simulation);
        verify(loanSimulationService, times(1)).simulate(100000f, 10f, 5f);
    }

    @Test
    void testGetLoanRequestCosts() {
        loanRequest.setPeriod(20.0f);
        loanRequest.setMonthlyInstallment(632649L);
        loanRequest.setRequestedAmount(100000000.0f);

        Long calculatedCosts = loanRequestService.getLoanRequestCosts(loanRequest);

        double desgravamenInsurance = 100000000.0 * 0.0003;
        long fireInsurance = 20000L;
        double administrationFee = 100000000.0 * 0.01;
        double monthlyCost = 632649L + desgravamenInsurance + fireInsurance;
        double expectedTotalCost = (monthlyCost * (20.0f * 12)) + administrationFee;

        assertEquals(Math.round(expectedTotalCost), calculatedCosts);
    }

}
