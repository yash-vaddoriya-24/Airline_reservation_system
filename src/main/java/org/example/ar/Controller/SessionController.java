package org.example.ar.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    @GetMapping("/getSessionData")
    public ResponseEntity<?> getSessionData(HttpSession session) {
        try {
            if (session != null && session.getAttribute("token") != null) {
                String token = (String) session.getAttribute("token");
                return new ResponseEntity<>(token, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Invalid session", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred", HttpStatus.UNAUTHORIZED);
        }

    }


}

