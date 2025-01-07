package com.example.modernecommerce.modernecommerce.repository;

import com.example.modernecommerce.modernecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    public User findByEmail(String email);

}
