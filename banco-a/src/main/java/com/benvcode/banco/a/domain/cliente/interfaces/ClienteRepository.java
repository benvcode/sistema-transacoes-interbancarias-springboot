package com.benvcode.banco.a.domain.cliente.interfaces;

import com.benvcode.banco.a.domain.cliente.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Cliente findByNome(String nomeCliente);
}