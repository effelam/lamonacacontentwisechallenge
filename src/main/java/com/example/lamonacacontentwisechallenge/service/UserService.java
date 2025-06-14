package com.example.lamonacacontentwisechallenge.service;

import com.example.lamonacacontentwisechallenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

}
