package com.example.forum.thread;

import com.example.forum.thread.dto.CreateThreadRequestDto;
import com.example.forum.thread.dto.GetAllThreadResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ThreadRepository {
    private final WebClient webClient;

    public GetAllThreadResponseDto getAllThreads() {
        return webClient.get()
                .uri("threads")
                .retrieve()
                .bodyToMono(GetAllThreadResponseDto.class)
                .block();
    }

    public Thread save(CreateThreadRequestDto thread) {
        return webClient.post()
                .uri("thread")
                .body(Mono.just(thread), CreateThreadRequestDto.class)
                .retrieve()
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(Thread.class)
                .block();
    }

    public Thread getThreadById(String id) {
        return webClient.get()
                .uri("thread/" + id)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(Thread.class)
                .block();
    }
}
