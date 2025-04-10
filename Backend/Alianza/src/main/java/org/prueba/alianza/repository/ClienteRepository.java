package org.prueba.alianza.repository;

import org.prueba.alianza.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, String> {
    List<Cliente> findBySharedKeyContainingIgnoreCase(String sharedKey);
}
