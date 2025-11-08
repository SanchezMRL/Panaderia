package com.panaderia.controller;

import com.panaderia.entity.Empleado;
import com.panaderia.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empleado")
@CrossOrigin(origins = "*") 
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepo;

    // ✅ Registrar nuevo empleado
    @PostMapping("/registro")
    public ResponseEntity<?> registrarEmpleado(@RequestBody Empleado empleado) {
        try {
            // Verificar si el email ya existe
            if (empleadoRepo.findByEmail(empleado.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("El correo ya está registrado");
            }

            Empleado guardado = empleadoRepo.save(empleado);
            return ResponseEntity.ok(guardado);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al registrar empleado: " + e.getMessage());
        }
    }

    // ✅ Listar todos los empleados
    @GetMapping
    public ResponseEntity<?> listarEmpleados() {
        return ResponseEntity.ok(empleadoRepo.findAll());
    }

    // ✅ Obtener un empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEmpleado(@PathVariable Long id) {
        return empleadoRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().body("Empleado no encontrado"));
    }

    // ✅ Actualizar empleado
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEmpleado(
            @PathVariable Long id,
            @RequestBody Empleado datos) {

        return empleadoRepo.findById(id).map(empleado -> {

            // ✅ Solo actualizamos lo que existe en tu ENTIDAD REAL
            empleado.setNombre(datos.getNombre());
            empleado.setEmail(datos.getEmail());
            empleado.setCargo(datos.getCargo());
            empleado.setPassword(datos.getPassword());

            empleadoRepo.save(empleado);
            return ResponseEntity.ok("Empleado actualizado correctamente");

        }).orElse(ResponseEntity.badRequest().body("Empleado no encontrado"));
    }

    // ✅ Eliminar empleado
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Long id) {
        if (!empleadoRepo.existsById(id)) {
            return ResponseEntity.badRequest().body("Empleado no encontrado");
        }

        empleadoRepo.deleteById(id);
        return ResponseEntity.ok("Empleado eliminado correctamente");
    }
}
