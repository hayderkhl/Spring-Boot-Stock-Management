package com.haidar.gestiondestock.controller.api;

import com.haidar.gestiondestock.dto.auth.AuthenticationRequest;
import com.haidar.gestiondestock.dto.auth.AuthenticationRespense;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.haidar.gestiondestock.utils.Constants.AUTHENTICATION_ENDPOINT;

@Api("authentication")
//@RequestMapping("/api/v1/authentication/")
public interface AuthenticationApi {

    @PostMapping(AUTHENTICATION_ENDPOINT + "/authenticate")
    public ResponseEntity<AuthenticationRespense> authenticate(@RequestBody AuthenticationRequest request);

}
