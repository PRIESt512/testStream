package ru.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@SpringBootApplication
public class Start {
    public static void main(String[] args) {
        SpringApplication.run(Start.class, args);
    }

    @Bean
    public CommandLineRunner startKafka(KafkaTemplate kafkaTemplate) {
        return (args) -> {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(1000);
                int finalI = i;
                kafkaTemplate.executeInTransaction((in) -> {
                    in.send("my-topic-in", Integer.valueOf(finalI));
                    log.info("Send");
                    return true;
                });
            }
        };
    }
}
