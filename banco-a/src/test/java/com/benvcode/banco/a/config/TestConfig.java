package com.benvcode.banco.a.config;

import com.benvcode.banco.a.transacao.util.TestTrasancaoDataHelper;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages =
        {"com.benvcode.banco.a.domain",
        "com.benvcode.banco.a.config"})
public class TestConfig {

    @Bean
    TestTrasancaoDataHelper TestDataHelper(){
        return  new TestTrasancaoDataHelper();
    }
}
