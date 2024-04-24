package org.example.ar.Service;

import org.example.ar.Models.ForgotPasswordDetails;
import org.example.ar.Repository.ForgotPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    public ForgotPasswordDetails saveDetails(ForgotPasswordDetails forgotPasswordDetails) {
        try {
            return forgotPasswordRepository.save(forgotPasswordDetails);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ForgotPasswordDetails findForgotPasswordDetailsBytoken(String token) {
        try {
            return forgotPasswordRepository.findByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
