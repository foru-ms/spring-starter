package com.example.forum.post;

import com.example.forum.post.dto.CreatePostRequestDto;
import com.example.forum.thread.ThreadController;
import com.example.forum.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postService;
    @PostMapping("/post")
    private String createPost(@ModelAttribute Post post, @AuthenticationPrincipal User user, RedirectAttributes model) {
        CreatePostRequestDto createPostRequestDto = new CreatePostRequestDto(post.getBody(), post.getThreadId(), user.getId(), post.getParentId());
        Post result = postService.createPost(createPostRequestDto);
        if (result != null) {
            model.addFlashAttribute("message", "Post created successfully");
        } else {
            model.addFlashAttribute("message", "Error creating post");
        }
        return "redirect:/thread/" + post.getThreadId();
    }
}
