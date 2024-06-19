package org.example.ar.Repository;

import org.example.ar.Models.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerDetail, Long> {
    CustomerDetail save(CustomerDetail customerDetail);

    List<CustomerDetail> findByEmail(String email);

    CustomerDetail findByUsernameAndAndPassword(String username, String Password);

    CustomerDetail findByToken(String token);

    @Modifying
    @Query("UPDATE CustomerDetail c SET c.password = :newPassword where c.email = :email")
    int updatePasswordByEmail(String email, String newPassword);

    void delete(CustomerDetail customerDetail);

    List<CustomerDetail> findByCreatedBeforeAndConfirmedFalse(Timestamp created);
}
