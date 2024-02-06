package com.example.forum.post;

import com.example.forum.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class Post {
    private String id;
    private String body;
    private String threadId;
    private String userId;
    private String parentId;
    private Boolean bestAnswer;
    private User user;
    private List<UpVote> upvotes;
    private List<Like> likes;
    private String instanceId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime updatedAt;
}
