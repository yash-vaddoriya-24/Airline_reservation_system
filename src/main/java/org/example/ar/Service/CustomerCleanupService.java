//package org.example.ar.Service;
//
//import org.example.ar.Models.CustomerDetail;
//import org.example.ar.Repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Timestamp;
//import java.util.List;
//
//@Service
//@Transactional
//public class CustomerCleanupService {
//
//    @Autowired
//    CustomerRepository customerRepository;
//
//    @Scheduled(fixedRate = 60000) // Run every minute
//    public void cleanupUnverifiedUsers() {
//        Long fiveMinutesAgo = System.currentTimeMillis() - (5 * 60 * 1000);
//        List<CustomerDetail> unverifiedUsers = customerRepository.findByConfirmedFalseAndCreatedDateBefore(fiveMinutesAgo);
//
//        for (CustomerDetail customer : unverifiedUsers) {
//            customerRepository.delete(customer);
//            // Optionally, we could send an email to the user notifying them of deletion.
//        }
//    }
//}
