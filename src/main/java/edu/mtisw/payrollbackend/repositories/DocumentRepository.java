package edu.mtisw.payrollbackend.repositories;

import edu.mtisw.payrollbackend.entities.DocumentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
    DocumentEntity findByLoanRequestIdAndType(Long loanRequestId, String type);
}
