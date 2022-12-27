package com.megalab.articlesite.repository;

import com.megalab.articlesite.model.RefreshToken;
import com.megalab.articlesite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findRefreshTokenByToken(String token);
    @Modifying
    int deleteRefreshTokenByUser(User user);
}
