package com.example.forum.common.security;

import com.example.forum.user.User;
import com.example.forum.user.UserRepository;
import com.example.forum.user.dto.LoginRequestDto;
import com.example.forum.user.dto.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration
@RequiredArgsConstructor
public class ExternalApiAuthenticationProvider implements AuthenticationProvider {
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try {
            String username = authentication.getName();
            String password = authentication.getCredentials().toString();

            TokenResponse tokenResponse = userRepository.loginUser(new LoginRequestDto(username, password));

            if (tokenResponse.token().isPresent()) {
                User userDetails = extractUserDetailsFromTokenResponse(tokenResponse);
                userDetails.setBearerToken(tokenResponse.token().get());
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities() != null ? userDetails.getAuthorities() : null);
            } else {
                throw new BadCredentialsException("Authentication failed");
            }
        } catch (Exception e) {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    private User extractUserDetailsFromTokenResponse(TokenResponse tokenResponse) {
        return userRepository.findByToken(tokenResponse.token());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
