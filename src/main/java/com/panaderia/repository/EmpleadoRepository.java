package com.panaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.panaderia.entity.Empleado;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Buscar empleado por email y contraseña
    Empleado findByEmailAndPassword(String email, String password);
}
