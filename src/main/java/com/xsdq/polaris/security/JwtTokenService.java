package com.xsdq.polaris.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.InvalidKeyException;
import java.util.List;

public interface JwtTokenService {

    String createToken(UserDetails userDetails) throws InvalidKeyException;

    <T extends UserDetails> T getUserDetails(String identity);

    <T extends UserDetails> T getUserDetails(HttpServletRequest request);

    <T extends UserDetails> List<T> listUserDetails();

    void removeUserDetails(String identity);
}
