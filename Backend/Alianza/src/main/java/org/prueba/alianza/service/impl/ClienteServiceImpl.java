package org.prueba.alianza.service.impl;

import org.prueba.alianza.converter.ClienteConverter;
import org.prueba.alianza.dto.ClienteDTO;
import org.prueba.alianza.entity.Cliente;
import org.prueba.alianza.exception.ResourceNotFoundException;
import org.prueba.alianza.repository.ClienteRepository;
import org.prueba.alianza.service.ClienteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteConverter clienteConverter;

    @Override
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        logger.info("Creando nuevo cliente: {}", clienteDTO);
        String[] names = clienteDTO.getBusinessId().split(" ");
        String sharedKey = (names[0].charAt(0) + names[names.length - 1]).toLowerCase();
        clienteDTO.setSharedKey(sharedKey);
        clienteDTO.setDateAdded(new Date());
        return clienteConverter.entityToModel(clienteRepository.save(clienteConverter.modelToEntity(clienteDTO)));
    }

    @Override
    public List<Cliente> listarClientes() {
        logger.info("Búsqueda de todos los clientes.");
        return clienteRepository.findAll();
    }

    @Override
    public List<Cliente> buscarSharedKey(String sharedKey) {
        logger.info("Búsqueda de clientes con clave compartida: {}", sharedKey);
        List<Cliente> clientes = clienteRepository.findBySharedKeyContainingIgnoreCase(sharedKey);

        if (clientes.isEmpty()) {
            throw new ResourceNotFoundException("Cliente no encontrado con sharedKey: " + sharedKey);
        }

        return clientes;
    }

}
