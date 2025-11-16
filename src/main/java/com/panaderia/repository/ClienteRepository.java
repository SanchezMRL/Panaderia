package com.panaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.panaderia.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByEmail(String email);
}
