package com.example.service.impl;

import com.example.entity.User;
import com.example.entity.common.UserPrincipal;
import com.example.repository.UserRepository;
import com.example.service.SecurityService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserRepository userRepository;

    public SecurityServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //we need to get our own user from database
        User user = userRepository.findByUsername(username);
        //return some exception if user doesnt exist
        if (user==null){
            throw new UsernameNotFoundException("This user does not exist");
        }
        //return user information in as a UserDetails
        return new UserPrincipal(user);
    }
}
