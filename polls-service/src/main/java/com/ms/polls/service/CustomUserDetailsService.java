package com.ms.polls.service;

import com.ms.polls.entity.User;
import com.ms.polls.repository.UserRepository;
import com.ms.polls.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userOrEmail) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsernameOrEmail(userOrEmail, userOrEmail);

        User user = optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("user not found with username "+userOrEmail));

        return UserPrincipal.create(user);
    }
}
