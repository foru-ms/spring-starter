package com.example.forum.thread;

import com.example.forum.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class Thread {
    private String id;
    private String title;
    private String slug;
    private String body;
    private boolean locked;
    private boolean pinned;
    private User user;
    private List<Tag> tags;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private String createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private String updatedAt;
}
