package com.panaderia.controller;

import com.panaderia.entity.Cliente;
import com.panaderia.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepo;

    // REGISTRAR NUEVO CLIENTE
    @PostMapping("/registro")
    public ResponseEntity<?> registrarCliente(@RequestBody Cliente cliente) {
        try {

            // Validación por email 
            if (clienteRepo.findByEmail(cliente.getEmail()) != null) {
                return ResponseEntity.badRequest().body("El correo ya está registrado");
            }

            Cliente guardado = clienteRepo.save(cliente);
            return ResponseEntity.ok(guardado);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al registrar cliente: " + e.getMessage());
        }
    }

    // LISTAR TODOS LOS CLIENTES
    @GetMapping("/lista")
    public ResponseEntity<?> listarClientes() {
        return ResponseEntity.ok(clienteRepo.findAll());
    }

    // OBTENER CLIENTE POR ID
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteRepo.findById(id);
        return cliente.isPresent()
                ? ResponseEntity.ok(cliente.get())
                : ResponseEntity.badRequest().body("Cliente no encontrado");
    }

    // ACTUALIZAR CLIENTE
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCliente(
            @PathVariable Long id,
            @RequestBody Cliente datos) {

        Optional<Cliente> optional = clienteRepo.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body("Cliente no encontrado");
        }

        Cliente cliente = optional.get();

        //  Campos existentes
        cliente.setNombre(datos.getNombre());
        cliente.setEmail(datos.getEmail());
        cliente.setTelefono(datos.getTelefono());

        clienteRepo.save(cliente);

        return ResponseEntity.ok("Cliente actualizado correctamente");
    }

    // ELIMINAR CLIENTE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarCliente(@PathVariable Long id) {
        if (!clienteRepo.existsById(id)) {
            return ResponseEntity.badRequest().body("El cliente no existe");
        }

        clienteRepo.deleteById(id);
        return ResponseEntity.ok("Cliente eliminado correctamente");
    }
}
