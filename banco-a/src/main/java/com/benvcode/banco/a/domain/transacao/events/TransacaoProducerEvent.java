package com.benvcode.banco.a.domain.transacao.events;

import static com.benvcode.shared.module.util.GenericsUtils.convertInstanceOfObject;
import com.benvcode.shared.module.config.kafka.KafkaTopicConfig;

import com.benvcode.shared.module.doamin.transacao.TransacaoApiContractDto;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Component
public class TransacaoProducerEvent<T> {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final KafkaTemplate<String, T> jsonKafkaTemplate;

    public void sendTransacao(TransacaoApiContractDto transacaoApiContractDto, Class<T> clazz) {
        logger.info("#### TransacaoProducerEvent.sendTransacao() -> {} ", transacaoApiContractDto);
        //jsonKafkaTemplate.send(topic, (T)transacaoApiContractDto); // Cast inseguro, 'unchecked warning', runtime exception
        T convertedTransacao = convertInstanceOfObject(transacaoApiContractDto, clazz);

        // Criar um ProducerRecord com o 'tópico', 'partição', chave' e 'valor' especificados
        ProducerRecord<String, T> record = new ProducerRecord<>(
                KafkaTopicConfig.TOPIC_TRANSACAO_INTERBANCARIA,
                KafkaTopicConfig.PARTITION_BANCO_A, transacaoApiContractDto.getCodigoBancoDestino()
                ,convertedTransacao);

        // Enviando o ProducerRecord usando o KafkaTemplate
        jsonKafkaTemplate.send(record);

        logger.info("#### TransacaoProducerEvent.sendTransacao() - Mensagem enviada para o tópico '{}'", record.topic());

    }
}