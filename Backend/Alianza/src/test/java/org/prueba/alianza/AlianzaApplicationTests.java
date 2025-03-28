package org.prueba.alianza;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prueba.alianza.converter.ClienteConverter;
import org.prueba.alianza.dto.ClienteDTO;
import org.prueba.alianza.entity.Cliente;
import org.prueba.alianza.repository.ClienteRepository;
import org.prueba.alianza.service.ClienteService;
import org.prueba.alianza.service.impl.ClienteServiceImpl;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class AlianzaApplicationTests {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @Mock
    private ClienteConverter clienteConverter;

    @Test
    public void testGetAllClients() {
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(
                new Cliente("eleal", "Edyson Leal", "edysonleal3@gmail.com", "3508927334", new Date(), new Date(), new Date()),
                new Cliente("fleal", "Fabian Leal", "edysonleal10@gmail.com", "3508927334", new Date(), new Date(), new Date())
        ));

        List<Cliente> clients = clienteService.listarClientes();
        assertEquals(2, clients.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    public void testCreateClient() {
        // Arrange (Preparación)
        Date startDate = new Date();
        Date endDate = new Date();

        // Crear DTO de entrada
        ClienteDTO inputDTO = new ClienteDTO();
        inputDTO.setBusinessId("Chayane Emilio");
        inputDTO.setEmail("test@gmail.com");
        inputDTO.setPhone("1234567890");
        inputDTO.setStartDate(startDate);
        inputDTO.setEndDate(endDate);

        // Crear entidad que simula lo que se guardaría
        Cliente savedEntity = new Cliente();
        savedEntity.setSharedKey("cemilio");
        savedEntity.setBusinessId("Chayane Emilio");
        savedEntity.setEmail("test@gmail.com");
        savedEntity.setPhone("1234567890");
        savedEntity.setStartDate(startDate);
        savedEntity.setEndDate(endDate);
        savedEntity.setDateAdded(new Date());

        // Crear DTO esperado como resultado
        ClienteDTO expectedDTO = new ClienteDTO();
        expectedDTO.setSharedKey("calejandro");
        expectedDTO.setBusinessId("Covid Alejandro");
        expectedDTO.setEmail("test@gmail.com");
        expectedDTO.setPhone("1234567890");
        expectedDTO.setStartDate(startDate);
        expectedDTO.setEndDate(endDate);
        expectedDTO.setDateAdded(savedEntity.getDateAdded());

        // Configurar mocks
        when(clienteConverter.modelToEntity(inputDTO)).thenReturn(savedEntity);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(savedEntity);
        when(clienteConverter.entityToModel(savedEntity)).thenReturn(expectedDTO);

        // Act (Ejecución)
        ClienteDTO resultDTO = clienteService.crearCliente(inputDTO);

        // Assert (Verificación)
        assertNotNull(resultDTO, "El DTO resultante no debería ser nulo");
        assertEquals("calejandro", resultDTO.getSharedKey());
        assertEquals("Covid Alejandro", resultDTO.getBusinessId());
        assertEquals("test@gmail.com", resultDTO.getEmail());
        assertEquals("1234567890", resultDTO.getPhone());
        assertEquals(startDate, resultDTO.getStartDate());
        assertEquals(endDate, resultDTO.getEndDate());
        assertNotNull(resultDTO.getDateAdded());

        // Verificar interacciones
        verify(clienteConverter).modelToEntity(inputDTO);
        verify(clienteRepository).save(savedEntity);
        verify(clienteConverter).entityToModel(savedEntity);
        verifyNoMoreInteractions(clienteRepository, clienteConverter);
    }
}
