package org.example.ar.Service;


import org.example.ar.Models.CustomerDetail;
import org.example.ar.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public boolean saveAllDetails(CustomerDetail customerDetail) {
        try {
            customerRepository.save(customerDetail);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CustomerDetail> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public CustomerDetail validateLogin(String username, String password) {
        return customerRepository.findByUsernameAndAndPassword(username, password);
    }

    public CustomerDetail findByToken(String token) {
        try {
            return customerRepository.findByToken(token);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer updateCustomerDetail(String email, String password) {
        try {
            return customerRepository.updatePasswordByEmail(email, password);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

