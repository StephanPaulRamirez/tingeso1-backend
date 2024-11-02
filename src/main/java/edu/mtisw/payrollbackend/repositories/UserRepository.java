package edu.mtisw.payrollbackend.repositories;

import edu.mtisw.payrollbackend.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByRut(String rut);
}