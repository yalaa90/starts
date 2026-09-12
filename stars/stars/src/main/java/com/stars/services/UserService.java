package com.stars.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.stars.domain.entities.UserEntity;
import com.stars.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional 
public class UserService {

    private final UserRepository userRepository;
    private final List<UserEntity> buffer = new ArrayList<>();

    public void saveUsers(UserEntity user) {

        userRepository.save(user);
        // buffer.add(user);

        // if (buffer.size() >= 10) {
        //     flush();
        // }
    }
    public void saveUsers(List<UserEntity> users) {
        userRepository.saveAll(users.stream().filter(Objects::nonNull).toList());
    }

    private void flush() {
        if (buffer.isEmpty()) {
            return;
        }

        userRepository.saveAll(buffer);
        userRepository.flush();

        buffer.clear();
    }

}
