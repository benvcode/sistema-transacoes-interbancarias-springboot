package com.benvcode.banco.intermediario.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    // Configuração do consumidor Kafka para lidar com mensagens em formato JSON
    //
    @Bean
    public <T> ConsumerFactory<String, T> jsonConsumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        // 'latest': consumidor inicia leitura apartir do último offset disponível, ignorando mensagens anteriores
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");

        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

        configProps.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
        configProps.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

        // Desserialização restrita para mitigar riscos de ataques, em vez de permitir todos os pacotes
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES,
                "com.benvcode.banco.a.domain.transacao, com.benvcode.banco.b.domain.transacao");

        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public <T> ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerJsonFactory() {
        ConcurrentKafkaListenerContainerFactory<String, T> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(jsonConsumerFactory());
        // Nas últimas versões do 'Spring Kafka' a desserialização de 'Json String'
        // para 'Objectos Java' é automatica.
//      factory.setMessageConverter(new StringJsonMessageConverter());
        factory.setBatchListener(true); // 'setBatchListener(true)': Para lidar com mensagens em lotes
        return factory;
    }
}