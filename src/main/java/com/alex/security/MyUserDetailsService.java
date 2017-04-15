package com.alex.security;

import com.alex.models.User;
import com.alex.tools.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Make sure there is only 1 subclass of UserDetailsService in the project.
 */ 
@Service
public class MyUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Get the user associated to the username.
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = null;
        
        if (userOptional.isPresent())
            user = userOptional.get();
        else
            throw new UsernameNotFoundException("Username not found: " + username);
        
        // Empty because we have no role.
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getHashedPassword(), grantedAuthorities);
    }
}
