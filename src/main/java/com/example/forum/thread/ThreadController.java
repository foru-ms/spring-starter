package com.example.forum.thread;

import com.example.forum.thread.dto.CreateThreadRequestDto;
import com.example.forum.user.User;
import com.example.forum.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ThreadController {
    private final ThreadRepository threadService;
    private final UserRepository userService;
    @PostMapping("/new-thread")
    public String newThread(@ModelAttribute Thread thread, Model model) {
        model.addAttribute("thread", thread);
        return "new-thread";
    }

    @PostMapping("/create-thread")
    public String createThread(@ModelAttribute Thread thread, @AuthenticationPrincipal User user, RedirectAttributes model) {
        List<String> tags = new ArrayList<>();
        tags.add("new post");
        CreateThreadRequestDto createThreadRequestDto = new CreateThreadRequestDto(thread.getTitle(), user.getId(), thread.getBody(), tags, false, true);
        Thread result = threadService.save(createThreadRequestDto);
        if (result != null) {
            model.addFlashAttribute("message", "Thread created successfully");
        } else {
            model.addFlashAttribute("message", "Error creating thread");
        }
//FIXME: change this to thread details page
        return "index";
    }

    @GetMapping("/thread/{id}")
    public String getThread(@PathVariable String id, Model model) {
        Thread thread = threadService.getThreadById(id);
        User user = userService.getUserById(thread.getUserId());
        thread.setUser(user);
        model.addAttribute("thread", thread);
        return "thread";
    }
}
