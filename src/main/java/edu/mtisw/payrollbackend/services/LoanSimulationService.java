package edu.mtisw.payrollbackend.services;

import org.springframework.stereotype.Service;

@Service
public class LoanSimulationService {
    public Long simulate(Float p, Float n, Float rate){
        double r = rate/12/100;
        double power = Math.pow((1+r), n*12);
        return Math.round(p*((r*power)/(power-1)));
    }
}
