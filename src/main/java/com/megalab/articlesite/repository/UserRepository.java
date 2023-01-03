package com.megalab.articlesite.repository;

import com.megalab.articlesite.model.Picture;
import com.megalab.articlesite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
    Boolean existsByUsername(String username);

    Optional<User>findUserById(long id);

}
