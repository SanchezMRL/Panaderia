package com.panaderia.config;

import com.panaderia.entity.Cliente;
import com.panaderia.repository.ClienteRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        System.out.println("ROLES AUTENTICADOS:");
        authentication.getAuthorities().forEach(a -> System.out.println(a.getAuthority()));

        String redirectURL = "/";
        String email = authentication.getName(); // email del usuario

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            String role = auth.getAuthority().replace("ROLE_", "");

            if (role.equals("ADMIN")) {
                redirectURL = "/index";
                break;
            }

            if (role.equals("CLIENTE")) {

                // guarda el id de cliente
                Cliente cliente = clienteRepository.findByEmail(email);
                if (cliente != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("idCliente", cliente.getIdCliente());
                    System.out.println("idCliente guardado en sesión: " + cliente.getIdCliente());
                }

                redirectURL = "/clienteMenu";
                break;
            }
        }

        response.sendRedirect(redirectURL);
    }
}
