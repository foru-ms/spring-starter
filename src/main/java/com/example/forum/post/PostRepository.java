package com.example.forum.post;

import com.example.forum.post.dto.CreatePostRequestDto;
import com.example.forum.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostRepository {
    private final WebClient webClient;
    public Post createPost(CreatePostRequestDto createPostRequestDto) {
        return webClient.post()
                .uri("post")
                .body(Mono.just(createPostRequestDto), CreatePostRequestDto.class)
                .retrieve()
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(Post.class)
                .block();
    }
    public PostResponse getAllPosts(){
        return webClient.get()
                .uri("posts")
                .retrieve()
                .onStatus(HttpStatus.INTERNAL_SERVER_ERROR::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new))
                .bodyToMono(PostResponse.class)
                .block();
    }

    public List<Post> getPostsByThreadId(String threadId){
        PostResponse allPosts = getAllPosts();
        return allPosts.posts().stream().filter(post -> post.getThreadId().equals(threadId)).toList();
    }
}
