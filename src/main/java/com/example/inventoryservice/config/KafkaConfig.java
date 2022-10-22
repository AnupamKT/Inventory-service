package com.example.inventoryservice.config;

import com.example.inventoryservice.model.Inventory;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${kafka.bootstrap.server}")
    private String KAFKA_BOOTSTRAP_SERVER;
    @Value("${kafka.consumer.group.id}")
    private String KAFKA_CONSUMER_GROUP_ID;

    @Bean
    public Map<String, Object> getConfigProps() {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVER);
        configMap.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_CONSUMER_GROUP_ID);
        configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return configMap;
    }

    @Bean
    public ConsumerFactory<String, Inventory> getInventoryConsumer() {
        return new DefaultKafkaConsumerFactory<>(
                getConfigProps(),
                new StringDeserializer(),
                new JsonDeserializer<>(Inventory.class, false));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Inventory> getKafkaConsumerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Inventory> listenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        listenerContainerFactory.setConsumerFactory(getInventoryConsumer());
        return listenerContainerFactory;
    }
}
