package com.benvcode.banco.a.domain.agencia.bancaria.interfaces;

import com.benvcode.banco.a.domain.agencia.bancaria.AgenciaBancaria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgenciaBancariaRepository extends JpaRepository<AgenciaBancaria, Long> {
    AgenciaBancaria findByNome(String nomeAgencia);
}
