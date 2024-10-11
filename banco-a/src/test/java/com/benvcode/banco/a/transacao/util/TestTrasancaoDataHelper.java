package com.benvcode.banco.a.transacao.util;

import com.benvcode.banco.a.domain.agencia.bancaria.AgenciaBancaria;
import com.benvcode.banco.a.domain.agencia.bancaria.interfaces.AgenciaBancariaRepository;
import com.benvcode.banco.a.domain.cliente.Cliente;
import com.benvcode.banco.a.domain.cliente.interfaces.ClienteRepository;
import com.benvcode.banco.a.domain.conta.Conta;
import com.benvcode.banco.a.domain.conta.interfaces.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestTrasancaoDataHelper {

    @Autowired
    AgenciaBancariaRepository agenciaBancariaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ContaRepository contaRepository;

    public Conta setupContaParaTransacao() {
        // Inclue:

        // Agencia
        AgenciaBancaria agencia = AgenciaBancaria.builder()
                .nome("Agência Teste")
                .build();

        AgenciaBancaria agenciaBancariaSalva = agenciaBancariaRepository.save(agencia);

        // Cliente
        Cliente cliente = Cliente.builder()
                .nome("Benvindo Xavier")
                .numBi("005284016LA041")
                .build();

        Cliente clienteSalvo = clienteRepository.save(cliente);

        // Conta
        Conta conta = Conta.builder()
                .numConta("1000016763")
                .iban("001000016763")
                .tipoConta(Conta.TipoContaEnum.SINGULAR)
                .agenciaBancaria(agenciaBancariaSalva)
                .cliente(clienteSalvo)
                .build();

        return contaRepository.save(conta);
    }
}
