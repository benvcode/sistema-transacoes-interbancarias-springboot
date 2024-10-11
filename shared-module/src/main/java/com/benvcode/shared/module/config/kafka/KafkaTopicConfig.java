package com.benvcode.shared.module.config.kafka;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaTopicConfig {
    public final static String TOPIC_TRANSACAO_INTERBANCARIA = "transacao-interbancaria";
    public final static String CONSUMER_TRANSACAO_INTERBANCARIA_GROUP_ID = "transacao-interbancaria-group-id";
    public final static int PARTITION_BANCO_A = 0;
    public final static int PARTITION_BANCO_B = 1;

    @Bean
    public NewTopic  topicTransacaoInterbancaria(){
        return TopicBuilder.name(TOPIC_TRANSACAO_INTERBANCARIA)
                .partitions(2)
                .replicas(1) // '1', pois há apenas um broker disponível(cada partição terá uma cópia)
                .build();
    }
}
