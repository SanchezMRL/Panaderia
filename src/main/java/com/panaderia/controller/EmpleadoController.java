package com.panaderia.controller;

import com.panaderia.entity.Empleado;
import com.panaderia.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/empleado")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepo;

    // ✅ REGISTRAR NUEVO EMPLEADO (USADO POR fetch DEL FORMULARIO)
    @PostMapping("/registro")
    public ResponseEntity<?> registrarEmpleado(@RequestBody Empleado empleado) {
        try {
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

    // ✅ LISTAR TODOS LOS EMPLEADOS (PARA LA TABLA DEL HTML)
    @GetMapping("/lista")
    public ResponseEntity<?> listarEmpleados() {
        return ResponseEntity.ok(empleadoRepo.findAll());
    }

    // ✅ OBTENER UN EMPLEADO POR ID (PARA CARGAR EN EL FORMULARIO DE EDICIÓN)
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerEmpleado(@PathVariable Long id) {
        Optional<Empleado> empleado = empleadoRepo.findById(id);
        return empleado.isPresent()
                ? ResponseEntity.ok(empleado.get())
                : ResponseEntity.badRequest().body("Empleado no encontrado");
    }

    // ✅ ACTUALIZAR EMPLEADO (USADO POR EL FORMULARIO EDITAR)
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEmpleado(
            @PathVariable Long id,
            @RequestBody Empleado datos) {

        Optional<Empleado> optional = empleadoRepo.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body("Empleado no encontrado");
        }

        Empleado empleado = optional.get();
        empleado.setNombre(datos.getNombre());
        empleado.setApellido(datos.getApellido());
        empleado.setEmail(datos.getEmail());
        empleado.setTelefono(datos.getTelefono());
        empleado.setCargo(datos.getCargo());

        empleadoRepo.save(empleado);

        return ResponseEntity.ok("Empleado actualizado correctamente");
    }

    // ✅ ELIMINAR EMPLEADO (USADO POR EL BOTÓN ELIMINAR DE LA TABLA)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable Long id) {
        if (!empleadoRepo.existsById(id)) {
            return ResponseEntity.badRequest().body("El empleado no existe");
        }

        empleadoRepo.deleteById(id);
        return ResponseEntity.ok("Empleado eliminado correctamente");
    }
}
