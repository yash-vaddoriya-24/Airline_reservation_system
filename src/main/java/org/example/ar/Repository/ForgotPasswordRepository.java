package org.example.ar.Repository;


import org.example.ar.Models.ForgotPasswordDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPasswordDetails, Integer> {

    ForgotPasswordDetails save(ForgotPasswordDetails forgotPasswordDetails);
    
    ForgotPasswordDetails findByToken(String token);
}
