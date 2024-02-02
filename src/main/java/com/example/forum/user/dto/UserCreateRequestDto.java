package com.example.forum.user.dto;

import java.util.List;

public record UserCreateRequestDto(String username, String password, String email, String displayName, List<String> roles ) {
}
