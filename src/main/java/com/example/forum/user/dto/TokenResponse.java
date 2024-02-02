package com.example.forum.user.dto;

import java.util.Optional;

public record TokenResponse(Optional<String> token, Optional<String> message, Optional<String> error) {
}
