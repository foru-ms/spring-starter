package com.example.forum.post.dto;
public record CreatePostRequestDto(String body, String threadId, String userId, String parentId) {
}
