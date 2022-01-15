package ru.test;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Conf {

    @Bean
    public StoreBuilder myStore() {
        return Stores.keyValueStoreBuilder(
                Stores.persistentKeyValueStore("my-store"), Serdes.String(), Serdes.Integer()
        );
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic("my-topic-in", 1, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic("my-topic-out", 1, (short) 1);
    }
}
