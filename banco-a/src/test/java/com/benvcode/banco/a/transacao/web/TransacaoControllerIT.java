package com.benvcode.banco.a.transacao.web;


import com.benvcode.banco.a.domain.conta.Conta;
import com.benvcode.banco.a.domain.conta.ContaService;
import com.benvcode.banco.a.domain.transacao.TransacaoDto;
import com.benvcode.banco.a.transacao.util.TestTrasancaoDataHelper;
import com.benvcode.shared.module.doamin.transacao.TransacaoApiContractDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("dev")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransacaoControllerIT {

    @Autowired
    WebTestClient webTestClient;

    @Autowired
    private ContaService contaService;

    @Autowired
    private TestTrasancaoDataHelper testTrasancaoDataHelper;
    private Conta conta;

    @BeforeEach
    public void setUp() {
        this.conta = testTrasancaoDataHelper.setupContaParaTransacao();
        assertNotNull(conta);
    }

    @Test
    public void testCriarTransacaoController() {
        // Dados do DTO da requisição
        TransacaoApiContractDto requestDto = TransacaoApiContractDto.builder()
                .montante(new BigDecimal("100.000"))
                .ibanDestino(conta.getIban())
                .codigoBancoDestino("BA")
                .build();

        // Executar a requisição POST para /banco-a/api/v1/transacao/interbancaria
        TransacaoDto responseBody = webTestClient
                .post()
                .uri("/banco-a/api/v1/transacao/interbancaria")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransacaoDto.class)
                .returnResult().getResponseBody();

        assertNotNull(responseBody);

        // Verificando se o saldo da conta foi atualizado corretamente
        Conta contaAtualizada = contaService.getByIban(conta.getIban());
        assertEquals(0, responseBody.getMontante().compareTo(contaAtualizada.getSaldo()));
    }

    @Test
    public void testKafkaSendTransacao(){
        // Dados do DTO da requisição
        TransacaoApiContractDto requestDto = TransacaoApiContractDto.builder()
                .montante(new BigDecimal("100.000"))
                .ibanDestino(conta.getIban())
                .codigoBancoDestino("BA")
                .build();

        // Executar a requisição POST para /banco-a/api/v1/transacao/interbancaria
        TransacaoDto responseBody = webTestClient
                .post()
                .uri("/banco-a/api/v1/transacao/kafka-send-transacao")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TransacaoDto.class)
                .returnResult().getResponseBody();

        assertNotNull(responseBody);
    }
}
