package com.megalab.articlesite.security.service;

import com.megalab.articlesite.exception.TokenRefreshException;
import com.megalab.articlesite.model.ERole;
import com.megalab.articlesite.model.RefreshToken;
import com.megalab.articlesite.model.Role;
import com.megalab.articlesite.model.User;
import com.megalab.articlesite.repository.RoleRepository;
import com.megalab.articlesite.repository.UserRepository;
import com.megalab.articlesite.security.jwt.JwtUtils;
import com.megalab.articlesite.security.jwt.request.RequestRefreshToken;
import com.megalab.articlesite.security.jwt.response.ResponseJwt;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import com.megalab.articlesite.security.jwt.response.ResponseRefreshToken;
import com.megalab.articlesite.security.request.RequestLogin;
import com.megalab.articlesite.security.request.RequestSignup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    public ResponseEntity<ResponseJwt> authenticateUser(RequestLogin requestLogin){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestLogin.getUsername(), requestLogin.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        return ResponseEntity.ok().body(new ResponseJwt(jwt, refreshToken.getToken(), userDetails.getId(), userDetails.getUsername(), roles));
    }

    public ResponseEntity<ResponseRefreshToken>refreshToken(RequestRefreshToken requestRefreshToken){
        String refreshToken = requestRefreshToken.getRefreshToken();


        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(new ResponseRefreshToken(token, refreshToken));
                })
                .orElseThrow(() -> new TokenRefreshException(refreshToken,
                        "Refresh token is not in database!"));
    }

    public ResponseEntity<ResponseMessage> registerUser(RequestSignup requestSignup){


        if (userRepository.existsByUsername(requestSignup.getUsername())) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Error: Username is already in use!"));
        }
        if (!requestSignup.getSecondPassword().contentEquals(requestSignup.getPassword())){
            return ResponseEntity.badRequest().body((new ResponseMessage("Error: Passwords should match!")));
        }

        // Create new user's account
        User user = new User(requestSignup.getFirstname(), requestSignup.getLastname(), requestSignup.getUsername(),
                encoder.encode(requestSignup.getPassword()));

        Set<String> strRoles = requestSignup.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                        roles.add(adminRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);

        userRepository.save(user);

        return ResponseEntity.ok().body(new ResponseMessage("User registered successfully!"));
    }


}
