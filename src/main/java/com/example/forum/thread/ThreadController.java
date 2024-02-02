package com.example.forum.thread;

import com.example.forum.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThreadController {
    @GetMapping("/new-thread")
    public String newThread(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "new-thread";
    }
}
