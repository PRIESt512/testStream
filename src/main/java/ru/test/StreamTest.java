package ru.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Slf4j
@Service
public class StreamTest {

    @Bean
    public Function<KStream<Object, Integer>, KStream<Object, String>> testStreamController() {
//        return input -> input.transformValues(() -> new Transformer("my-store"), "my-store");
        return input -> input.map((KeyValueMapper<Object, Integer, KeyValue<Object, String>>) (key, value) -> {
            log.info("map");
            return new KeyValue<>(key, value.toString());
        });
    }

    @KafkaListener(topics = {"my-topic-out"}, groupId = "test")
    public void consumerApp(String body) {
        log.info(body);
    }
}
