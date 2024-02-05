package com.example.forum.user;

import com.example.forum.user.dto.LoginRequestDto;
import com.example.forum.user.dto.TokenResponse;
import com.example.forum.user.dto.UserCreateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepository {
    private final WebClient webClient;

    public TokenResponse loginUser(LoginRequestDto loginRequestDto) {
        return webClient.post()
                .uri("auth/login")
                .body(Mono.just(loginRequestDto), LoginRequestDto.class)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(TokenResponse.class)
                .block();
    }

    public User findByToken(Optional<String> token) {
        return token.map(s -> webClient.get()
                .uri("auth/me")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(s))
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(User.class)
                .block()).orElse(null);
    }

    public User registerUser(UserCreateRequestDto user) {
        try {
            return webClient.post()
                    .uri("user")
                    .body(Mono.just(user), UserCreateRequestDto.class)
                    .retrieve()
                    .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                    .bodyToMono(User.class)
                    .block();
        } catch (Exception e) {
            log.error("Error registering user: {}", e.getMessage());
            return null;
        }

    }
    public List<User> getAllUsers() {
        return webClient.get()
                .uri("users")
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToFlux(User.class)
                .collectList()
                .block();
    }

    public User getUserById(String id) {
        return webClient.get()
                .uri("user/" + id)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(User.class)
                .block();
    }
}
