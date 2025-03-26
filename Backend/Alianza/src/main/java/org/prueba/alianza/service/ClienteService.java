package org.prueba.alianza.service;

import org.prueba.alianza.dto.ClienteDTO;
import org.prueba.alianza.entity.Cliente;

import java.util.List;

public interface ClienteService {

    ClienteDTO crearCliente(ClienteDTO clienteDTO);

    List<Cliente> listarClientes();

    List<Cliente> buscarSharedKey(String sharedKey);
}
