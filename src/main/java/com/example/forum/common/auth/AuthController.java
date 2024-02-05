package com.example.forum.common.auth;

import com.example.forum.thread.Thread;
import com.example.forum.thread.ThreadRepository;
import com.example.forum.thread.dto.GetAllThreadResponseDto;
import com.example.forum.user.User;
import com.example.forum.user.UserRepository;
import com.example.forum.user.dto.UserCreateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserRepository userRepository;
    private final ThreadRepository threadRepository;
    @GetMapping
    public String index(Model model, @AuthenticationPrincipal User user) {
        GetAllThreadResponseDto allThreads = threadRepository.getAllThreads();
        model.addAttribute("threads", allThreads.threads());
        if (user != null) {
            model.addAttribute("user", user);
            model.addAttribute("thread", new Thread());
        }
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }
    @PostMapping("/register")
    public String registerUser(User user, RedirectAttributes redirectAttributes){
        UserCreateRequestDto userCreateRequestDto = new UserCreateRequestDto(user.getUsername(), user.getPassword(), user.getEmail(), user.getDisplayName(), List.of("User"));
        User saved = userRepository.registerUser(userCreateRequestDto);
        if (saved!=null){
            log.info("User registered successfully with username: {}", user.getUsername());
            redirectAttributes.addFlashAttribute("message", "User registered successfully");
        }else {
            redirectAttributes.addFlashAttribute("message", "User registration failed");
            log.info("User registration failed with username: {}", user.getUsername());
        }

        return "redirect:/login";
    }
}
