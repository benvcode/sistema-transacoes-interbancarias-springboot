package com.benvcode.banco.a.domain.transacao.interfaces;

import com.benvcode.banco.a.domain.transacao.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}