package com.benvcode.banco.a.transacao.service;

import com.benvcode.banco.a.config.TestConfig;
import com.benvcode.banco.a.domain.conta.Conta;

import com.benvcode.banco.a.domain.transacao.Transacao;
import com.benvcode.banco.a.domain.transacao.TransacaoService;
import com.benvcode.banco.a.transacao.util.TestTrasancaoDataHelper;
import com.benvcode.shared.module.doamin.transacao.TransacaoApiContractDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// TransacaoDevTestIT.java: teste de integracao em ambiente de desenvolvimento.
// "@SpringBootTest" carrega o contexto completo da aplicação, incluindo beans, congiguracoes, etc
// "@ActiveProfiles("dev")" usa as configurações do "application-dev.properties" para desenvolvimento local.
// "@DataJpaTest" carrega apenas os componentes relevantes teste JPA, como entidades, interfaces de
// repositórios e configurações necessárias do JPA. Por padrão "@DataJpaTest" faz rollback(usa "@Transactional")
// para metodo de teste.

//@SpringBootTest
@ActiveProfiles("dev")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Import(TestConfig.class)
public class TransacaoServiceIT {
    @Autowired
    TransacaoService transacaoService;

    @Autowired
    private TestTrasancaoDataHelper testTrasancaoDataHelper;

    private Conta conta;

    @BeforeEach
    public void setUp() {
        this.conta = testTrasancaoDataHelper.setupContaParaTransacao();
        assertNotNull(conta);
    }

    @Test
    public void testSalvarTransacao() {
        // Transacao
        //      Conta
        //          AgenciaBancaria
        //          Cliente

        Transacao transacao = Transacao.builder()
                .ibanDestino("001000016763")
                .montante(new BigDecimal("50.000"))
                .conta(this.conta)
                .build();

        Transacao transacaoSalva = transacaoService.save(transacao);

        // Verificando se a transação foi salva
        assertNotNull(transacaoSalva);

        // Verificando se o saldo da conta foi atualizado corretamente
        assertEquals(new BigDecimal("50.000"), transacaoSalva.getConta().getSaldo());
    }

    @Test
    public void  testKafkaSendTransacao(){
        TransacaoApiContractDto transacaoApiContractDto = TransacaoApiContractDto.builder()
                .montante(new BigDecimal("50.000"))
                .ibanDestino("1000016763")
                .codigoBancoDestino("BA")
                .build();

        transacaoService.kafkaSendTransacao(transacaoApiContractDto);

        // Após enviar a transação, podemos verificarr se a mensagem foi enviada acessando a inteface
        // do Kafka Drop em http://localhost:19000/

    }
}