package com.benvcode.banco.intermediario.domain.transacao.event;

import com.benvcode.shared.module.config.kafka.KafkaTopicConfig;
import com.benvcode.shared.module.doamin.transacao.TransacaoApiContractDto;
import com.benvcode.shared.module.doamin.transacao.exceptions.CodigoAgenciaBancariaDesconhecidoException;
import com.benvcode.shared.module.doamin.transacao.exceptions.TipoDeRegistroInvalidoException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TransacaoConsumerEvent {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    //@Value("${spring.kafka.consumer.group-id}")
    //private String consumerGroupId;

    private final WebClient bancoAWebClient;
    private final WebClient bancoBWebClient;

    @KafkaListener(topics = {KafkaTopicConfig.TOPIC_TRANSACAO_INTERBANCARIA}, containerFactory = "kafkaListenerJsonFactory",
            groupId = KafkaTopicConfig.CONSUMER_TRANSACAO_INTERBANCARIA_GROUP_ID)
    public void getTransacao(List<ConsumerRecord<String, Object>> recordList) {
        logger.info("#### TransacaoConsumerEvent.getTransacao() -> {} ", recordList);
        for (ConsumerRecord<String, Object> record : recordList) {
            Object recordObj = record.value();
            if (recordObj instanceof TransacaoApiContractDto) {
                String codigoAgenciaBancaria = record.key();
                // Verifica se o registro pertence à partição do Banco A
                if (record.partition() == KafkaTopicConfig.PARTITION_BANCO_A) {
                    switch (codigoAgenciaBancaria) {
                        case "BB":
                            realizarTransacaoBancoB(recordObj);
                            break;
                        // Outros bancos...
                        default:
                            throw new CodigoAgenciaBancariaDesconhecidoException("Código de agência bancária desconhecido:"
                                    ,codigoAgenciaBancaria);
                    }
                }

                // Verifica se o registro pertence à partição do Banco B
                if (record.partition() == KafkaTopicConfig.PARTITION_BANCO_B) {
                    switch (codigoAgenciaBancaria) {
                        case "BA":
                            realizarTransacaoBancoA(recordObj);
                            break;
                        // Outros bancos...
                        default:
                            throw new CodigoAgenciaBancariaDesconhecidoException("Código de agência bancária desconhecido:"
                                    ,codigoAgenciaBancaria);
                    }
                }
            }
            else {
                throw new TipoDeRegistroInvalidoException("O objeto do registro não é uma instância de TransacaoApiContractDto"
                ,recordObj);
            }
        }
    }

    private <T> void realizarTransacaoBancoA(T recordObj) {
        bancoBWebClient.post()
                .uri("/transacao/interbancaria")
                .bodyValue(recordObj)
                .retrieve()
                .bodyToMono(recordObj.getClass())
                .subscribe(response -> {
                    logger.info("Requisição concluída com sucesso: {}", response);
                }, error -> {
                    logger.error("Ocorreu um erro ao processar a requisição", error);
                });
    }

    private <T> void realizarTransacaoBancoB(T recordObj) {
        bancoAWebClient.post()
                .uri("/transacao/interbancaria")
                .bodyValue(recordObj)
                .retrieve()
                .bodyToMono(recordObj.getClass())
                .subscribe(response -> {
                    logger.info("Requisição concluída com sucesso: {}", response);
                }, error -> {
                    logger.error("Ocorreu um erro ao processar a requisição", error);
                });
    }
}
