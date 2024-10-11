package com.benvcode.banco.a.domain.transacao;

import com.benvcode.banco.a.domain.transacao.events.TransacaoProducerEvent;
import com.benvcode.banco.a.domain.transacao.interfaces.TransacaoRepository;
import com.benvcode.shared.module.doamin.transacao.TransacaoApiContractDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.benvcode.banco.a.domain.conta.ContaService;

@Service
@RequiredArgsConstructor
public class TransacaoService {
    private final TransacaoRepository transacaoRepository;
    private final ContaService contaService; // Injetar "contaService"
    @Autowired
    private TransacaoProducerEvent<TransacaoApiContractDto> transacaoProducerEvent;

    @Transactional
    public Transacao save(Transacao transacao) {
        // Cria uma transação e atualiza o saldo da conta correspondente. Se ocorrer algum erro
        // durante a operação, a transação será revertida automaticamente.
        Transacao transacaoCriada = transacaoRepository.save(transacao);

        contaService.updateSaldo(transacao.getConta(), transacaoCriada.getMontante());

        return transacaoCriada;
    }

    // Método para enviar uma transação através do Kafka para o consumidor "microsserviço-
    // banco-intermediario" consumir.
    public void kafkaSendTransacao(TransacaoApiContractDto transacaoApiContractDto){
        transacaoProducerEvent.sendTransacao(transacaoApiContractDto,
                TransacaoApiContractDto.class);
    }



}
