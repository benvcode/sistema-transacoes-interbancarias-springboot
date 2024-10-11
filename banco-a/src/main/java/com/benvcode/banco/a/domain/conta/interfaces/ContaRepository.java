package com.benvcode.banco.a.domain.conta.interfaces;

import com.benvcode.banco.a.domain.conta.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    @Query("SELECT C FROM Conta C WHERE C.iban like :ibanConta")
    Conta findByIban(String ibanConta);
}