package com.benvcode.banco.a.config;

import com.benvcode.shared.module.config.jpa.JpaAuditingConfig;
import com.benvcode.shared.module.config.kafka.KafkaTopicConfig;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({JpaAuditingConfig.class, KafkaTopicConfig.class})
@ComponentScan({"com.benvcode.shared.module"})
public class AppConfig {
}