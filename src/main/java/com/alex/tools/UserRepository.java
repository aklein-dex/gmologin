package com.alex.tools;


import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.alex.models.User;

/**
 * Extend CrudRepository so we can use basic Crud functions. 
 */
public interface UserRepository extends CrudRepository<User, Long>{
	
    /**
     * @username - username
     * @return a User if the username exists.
     */
	Optional<User> findByUsername(String username);
}
