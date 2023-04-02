package com.haidar.gestiondestock.controller;

import com.haidar.gestiondestock.controller.api.AuthenticationApi;
import com.haidar.gestiondestock.dto.auth.AuthenticationRequest;
import com.haidar.gestiondestock.dto.auth.AuthenticationRespense;
import com.haidar.gestiondestock.model.auth.ExtendedUser;
import com.haidar.gestiondestock.service.auth.ApplicationUserDetailsService;
import com.haidar.gestiondestock.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController implements AuthenticationApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<AuthenticationRespense> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());

        final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);

        return ResponseEntity.ok(AuthenticationRespense.builder().accessToken(jwt).build());
    }

}

