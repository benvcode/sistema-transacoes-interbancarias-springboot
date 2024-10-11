package com.benvcode.banco.a.domain.transacao;

import com.benvcode.banco.a.domain.conta.Conta;
import com.benvcode.shared.module.doamin.transacao.TransacaoApiContractDto;
import com.benvcode.banco.a.domain.conta.ContaService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/banco-a/api/v1/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;
    private final ContaService contaService;
    private final TransacaoMapperImpl transacaoMapperImpl;

    // Endpoint(URL) para transações interbáncarias
    @PostMapping("/interbancaria")
    public ResponseEntity<TransacaoDto> create(
            @RequestBody TransacaoApiContractDto transacaoApiContractDto) {

        // Mapear "TransacaoApiContractDto" para "TransacaoDto"
        TransacaoDto transacaoDto = transacaoMapperImpl.toTransacaoDto(transacaoApiContractDto);

        // Mapear "TransacaoDto" para "Transacao"
        Transacao transacao = transacaoMapperImpl.dtoToEntity(transacaoDto);

        // Buscar a conta associada
        Conta conta = contaService.getByIban(transacaoApiContractDto.getIbanDestino());

        // Associar a conta encontrada à transação
        transacao.setConta(conta);

        // Salvar a transacao
        Transacao transacaoCriada = transacaoService.save(transacao);

        // Retorna uma resposta com status 201 (CREATED) e corpo contendo os dados da transacao.
        return ResponseEntity.status(HttpStatus.CREATED).body(
                    transacaoMapperImpl.entityToDto(transacaoCriada));
    }

    @PostMapping("/kafka/send-transacao")
    public ResponseEntity<TransacaoDto> kafkaSendTransacao(
            @RequestBody TransacaoApiContractDto transacaoApiContractDto){

            this.transacaoService.kafkaSendTransacao(transacaoApiContractDto);

            // Retorna uma resposta com status 204 (No Content) indicando que a tarefa foi excluída com sucesso.
            return ResponseEntity.noContent().build();
    }
}
