package org.prueba.alianza.converter;

import org.prueba.alianza.dto.ClienteDTO;
import org.prueba.alianza.entity.Cliente;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ClienteConverter {

    public Cliente modelToEntity(ClienteDTO clienteDTO) {

        Cliente cliente = new Cliente();
        cliente.setSharedKey(clienteDTO.getSharedKey());
        cliente.setBusinessId(clienteDTO.getBusinessId());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setPhone(clienteDTO.getPhone());
        cliente.setDateAdded(clienteDTO.getDateAdded());
        cliente.setStartDate(clienteDTO.getStartDate());
        cliente.setEndDate(clienteDTO.getEndDate());
        return cliente;
    }

    public ClienteDTO entityToModel(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setSharedKey(cliente.getSharedKey());
        clienteDTO.setBusinessId(cliente.getBusinessId());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setPhone(cliente.getPhone());
        clienteDTO.setDateAdded(cliente.getDateAdded());
        clienteDTO.setStartDate(cliente.getStartDate());
        clienteDTO.setEndDate(cliente.getEndDate());
        return clienteDTO;
    }
}
