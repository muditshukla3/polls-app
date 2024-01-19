package com.ms.polls.service;

import com.ms.polls.dto.UserRecord;
import com.ms.polls.entity.Role;
import com.ms.polls.entity.RoleName;
import com.ms.polls.entity.User;
import com.ms.polls.exception.AppException;
import com.ms.polls.exception.ResourceNotFoundException;
import com.ms.polls.mapper.ModelMapper;
import com.ms.polls.repository.PollRepository;
import com.ms.polls.repository.RoleRepository;
import com.ms.polls.repository.UserRepository;
import com.ms.polls.repository.VoteRepository;
import com.ms.polls.request.SignupRequest;
import com.ms.polls.response.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VoteRepository voteRepository;
    private final PollRepository pollRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public boolean checkUsername(String username){
        return userRepository.existsByUsername(username);
    }

    public boolean checkEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public UserRecord createUser(SignupRequest request){
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(()->new AppException("User Role not set"));
        user.setRoles(Collections.singleton(userRole));
        User savedUser = userRepository.save(user);

        return mapper.convertUserEntityToUserRecord(savedUser);
    }

    public UserProfile userProfile(String username){
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("user ", username));

        long pollCount = pollRepository.countByCreatedBy(user.getId());
        long voteCount = voteRepository.countByUserId(user.getId());

        return UserProfile.builder()
                .name(user.getName())
                .pollCount(pollCount)
                .voteCount(voteCount)
                .joinedAt(user.getCreatedAt())
                .build();
    }

}
