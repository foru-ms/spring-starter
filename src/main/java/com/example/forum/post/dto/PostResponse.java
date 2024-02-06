package com.example.forum.post.dto;

import com.example.forum.post.Post;

import java.util.List;

public record PostResponse(List<Post> posts, int count) {}
