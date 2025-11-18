package com.panaderia.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
                System.out.println("ROLES AUTENTICADOS:");
authentication.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));


        String redirectURL = "/";

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String role = auth.getAuthority();

            // Normaliza cualquier versión
            role = role.replace("ROLE_", ""); // CLIENTE o ADMIN

            if (role.equals("ADMIN")) {
                redirectURL = "/index"; 
                break;
            } else if (role.equals("CLIENTE")) {
                redirectURL = "/clienteMenu";
                break;
            }
        }

        response.sendRedirect(redirectURL);
    }
}
