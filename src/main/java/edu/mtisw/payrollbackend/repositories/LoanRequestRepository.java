package edu.mtisw.payrollbackend.repositories;

import edu.mtisw.payrollbackend.entities.LoanRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface LoanRequestRepository extends JpaRepository<LoanRequestEntity, Long> {
    ArrayList<LoanRequestEntity> findByUserRut(String userRut);
}

