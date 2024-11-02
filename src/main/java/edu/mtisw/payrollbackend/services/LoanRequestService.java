package edu.mtisw.payrollbackend.services;

import edu.mtisw.payrollbackend.entities.LoanRequestEntity;
import edu.mtisw.payrollbackend.repositories.LoanRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LoanRequestService {
    @Autowired
    LoanRequestRepository loanRequestRepository;

    @Autowired
    LoanSimulationService loanSimulationService;

    public LoanRequestEntity saveLoanRequest(LoanRequestEntity loanRequest) {
        Float amount = loanRequest.getRequestedAmount();
        Float years = loanRequest.getPeriod();
        Float rate = loanRequest.getInterestRate();
        loanRequest.setMonthlyInstallment(loanSimulationService.simulate(amount, years, rate));
        loanRequest.setFinalCosts(getLoanRequestCosts(loanRequest));
        Long ratio = Math.round((loanRequest.getMonthlyInstallment() /
                                (float)loanRequest.getClientMonthlyIncome()) * 100D);
        loanRequest.setRatio(ratio);
        return loanRequestRepository.save(loanRequest);
    }

    public LoanRequestEntity getLoanRequestById(Long id){
        return loanRequestRepository.findById(id).get();
    }

    public ArrayList<LoanRequestEntity> getLoanRequestByUserRut(String rut){
        return loanRequestRepository.findByUserRut(rut);
    }

    public LoanRequestEntity updateLoanRequest(LoanRequestEntity loanRequest) {
        return loanRequestRepository.save(loanRequest);
    }

    public Long simulateService(Float p, Float n, Float rate){
        return loanSimulationService.simulate(p, n, rate);
    }


    public Long getLoanRequestCosts(LoanRequestEntity loanRequest) {
        double period = loanRequest.getPeriod();
        long monthlyInstallment = loanRequest.getMonthlyInstallment();
        double requestedAmount = loanRequest.getRequestedAmount();

        double desgravamenInsurance = requestedAmount * 0.0003;
        long fireInsurance = 20000L;
        double administrationFee = requestedAmount * 0.01;
        double monthlyCost = monthlyInstallment + desgravamenInsurance + fireInsurance;

        double totalCost = (monthlyCost * (period * 12)) + administrationFee;

        return Math.round(totalCost);
    }


}
