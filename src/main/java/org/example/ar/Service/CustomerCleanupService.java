package org.example.ar.Service;

import org.example.ar.Models.CustomerDetail;
import org.example.ar.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
@EnableScheduling
public class CustomerCleanupService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCleanupService.class);

    @Autowired
    CustomerRepository customerRepository;

    @Scheduled(fixedRate = 2 * 60 * 1000) // Run every 2 minutes
    public void cleanupUnverifiedUsers() {
        Timestamp fiveMinutesAgo = (new Timestamp(System.currentTimeMillis() - (6 * 60 * 1000)));
        logger.info("Starting cleanup task at: {}", fiveMinutesAgo);
        System.out.println("Starting cleanup task at: " + fiveMinutesAgo);

        List<CustomerDetail> unverifiedUsers = customerRepository.findByCreatedBeforeAndConfirmedFalse(fiveMinutesAgo);
        logger.info("Found {} unverified users for deletion.", unverifiedUsers.size());


        for (CustomerDetail customer : unverifiedUsers) {
            customerRepository.delete(customer);
            logger.info("Deleted customer: {}", customer.getUsername());
            // Optionally, we could send an email to the user notifying them of deletion.
        }
    }
}
