package com.haidar.gestiondestock.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@Jacksonized //missing
@Builder
@Data

public class AuthenticationRespense {

    private String accessToken;
}
