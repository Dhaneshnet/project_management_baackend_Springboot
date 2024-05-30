package com.danesh.repository;

import com.danesh.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User  findByEmail(String email);


}


