package com.panaderia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.panaderia.entity.Empleado;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Para login
    Empleado findByEmailAndPassword(String email, String password);

    // Para verificar si un correo ya existe antes de registrar
    Optional<Empleado> findByEmail(String email);
}
