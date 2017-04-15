package com.alex.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alex.models.*;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.dao.DataAccessException;

@Transactional
@Service
public class UserManagement {
  
	private final static Logger logger = LoggerFactory.getLogger(UserManagement.class);
  
	@Autowired
	private UserRepository repository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
  
	/**
	 * Make sure the username doesn't exist.
	 * Create the hashed password which is the result of concatenation of the password + salt.
	 * @param username - the username
	 * @param password - the password (clear text)
	 * @return the saved user.
	 */
	public User register(String username, String password) throws DataAccessException {
		repository.findByUsername(username).ifPresent(user -> {
			throw new IllegalArgumentException("User with that login already exists!");
		});
		logger.info("Register: login: " + username);
		logger.info("Register:   pwd: " + password);
		
		// This will hash the password and add a salt.
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		logger.info("Register:  epwd: " + encodedPassword);

		return repository.save(new User(username, encodedPassword));
	}

	/**
	 * Find the User object with the username.
	 * @param username - the username
	 * @return a Optional User.
	 */
	public Optional<User> findByUsername(String username) throws DataAccessException {
		if (username == null) {
			throw new IllegalArgumentException("Username is null.");
		}
		return repository.findByUsername(username);
	}
	
}
