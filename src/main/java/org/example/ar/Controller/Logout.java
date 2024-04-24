package org.example.ar.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Logout {

    @PostMapping("logout")
    public String logout(Model model, HttpServletRequest request) {
        try{
            HttpSession session = request.getSession(false);
            if(session != null) {
                session.invalidate();
                return "redirect:/index";
            }
            else{
                model.addAttribute("errorMessage", "You already logged out");
                return "redirect:/login";
            }
        }catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }
}
