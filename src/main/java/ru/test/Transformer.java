package ru.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Objects;

@Slf4j
public class Transformer implements ValueTransformer<Integer, String> {

    private KeyValueStore<String, Integer> stateStore;
    private final String storeName;
    private ProcessorContext context;

    public Transformer(String storeName) {
        Objects.requireNonNull(storeName, "Store Name can't be null");
        this.storeName = storeName;
    }

    @Override
    public void init(ProcessorContext context) {
        this.context = context;
        stateStore = this.context.getStateStore(storeName);
    }

    @Override
    public String transform(Integer value) {
        String key = "testKey";
        var storeValue = stateStore.get(key);

        var newValue = storeValue + value;

        log.info("Map");

        stateStore.put(key, newValue);
        return "NewValue =" + newValue;
    }

    @Override
    public void close() {

    }
}
