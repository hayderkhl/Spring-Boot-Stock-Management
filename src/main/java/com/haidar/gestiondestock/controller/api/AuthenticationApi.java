package com.haidar.gestiondestock.controller.api;

import com.haidar.gestiondestock.dto.auth.AuthenticationRequest;
import com.haidar.gestiondestock.dto.auth.AuthenticationRespense;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.haidar.gestiondestock.utils.Constants.APP_ROOT;
import static com.haidar.gestiondestock.utils.Constants.AUTHENTICATION_ENDPOINT;

@Api(APP_ROOT + "/authenticate")
//@RequestMapping("/api/v1/authentication/")
public interface AuthenticationApi {

    @PostMapping(APP_ROOT + "/authenticate/auth")
    public ResponseEntity<AuthenticationRespense> authenticate(@RequestBody AuthenticationRequest request);

}
