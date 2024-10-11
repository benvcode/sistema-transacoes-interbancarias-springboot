package com.benvcode.banco.intermediario.config;

import com.benvcode.shared.module.config.kafka.KafkaTopicConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({KafkaTopicConfig.class})
@ComponentScan({"com.benvcode.shared.module"})
public class AppConfig {
}