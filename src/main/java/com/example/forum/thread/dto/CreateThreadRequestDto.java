package com.example.forum.thread.dto;

import java.util.List;

public record CreateThreadRequestDto(String title,String userId, String body, List<String> tags, boolean pinned, boolean locked) {
}
