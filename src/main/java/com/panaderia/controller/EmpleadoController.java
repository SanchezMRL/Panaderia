package com.panaderia.controller;

import com.panaderia.entity.Empleado;
import com.panaderia.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empleado")
@CrossOrigin(origins = "*") // 🔓 Permite peticiones desde cualquier origen (útil para desarrollo)
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepo;

    // 🟢 Registrar nuevo empleado
    @PostMapping("/registro")
    public ResponseEntity<?> registrarEmpleado(@RequestBody Empleado empleado) {
        try {
            // Verificar si ya existe un empleado con ese correo
            if (empleadoRepo.findByEmail(empleado.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("El correo ya está registrado");
            }

            Empleado guardado = empleadoRepo.save(empleado);
            return ResponseEntity.ok(guardado);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al registrar empleado: " + e.getMessage());
        }
    }

    // (opcional) 🟡 Listar todos los empleados
    @GetMapping
    public ResponseEntity<?> listarEmpleados() {
        return ResponseEntity.ok(empleadoRepo.findAll());
    }
}
