package org.prueba.alianza.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.prueba.alianza.dto.ClienteDTO;
import org.prueba.alianza.entity.Cliente;
import org.prueba.alianza.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Servicios REST de los clientes")
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);

    @Autowired
    private ClienteService clientService;

    @PostMapping(path = "/crear", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Registra un cliente")
    public ResponseEntity<ClienteDTO> crearClient(@RequestBody ClienteDTO clienteDTO) {
        return ResponseEntity.ok(clientService.crearCliente(clienteDTO));
    }

    @GetMapping(path = "/listar",consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista los clientes", description = "Devuelve una lista de todos los clientes registrados.")
    public ResponseEntity<List<Cliente>> getAllClients() {
        return ResponseEntity.ok(clientService.listarClientes());
    }

    @GetMapping(path = "/search")
    @Operation(summary = "Busca el cliente", description = "Busca el shared key del cliente registrado")
    public ResponseEntity<List<Cliente>> buscarSharedKey(@RequestParam @Valid String sharedKey) {
        return ResponseEntity.ok(clientService.buscarSharedKey(sharedKey));
    }
}
