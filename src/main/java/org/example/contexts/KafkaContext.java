package org.example.contexts;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.regex.Pattern;

/**
 * Class responsible to connect to the source kafka and sink kafka
 * */
public class KafkaContext {

    private String getKafkaPlataformServer(){
        if (System.getenv("BROKER_HOST") != null
                && !System.getenv("BROKER_HOST").isEmpty()){
            return System.getenv("BROKER_HOST");
        }else{
            return "localhost:9092";
        }
    }

    private String getKafkaClouderaServer(){
        if (System.getenv("BROKER_HOST_CLOUDERA") != null
                && !System.getenv("BROKER_HOST_CLOUDERA").isEmpty()){
            return System.getenv("BROKER_HOST_CLOUDERA");
        }else{
            return "localhost:9092";
        }
    }

    public KafkaSource<String> createKafkaPlataformConnection(){
        KafkaSource<String> kafkaSource = KafkaSource.<String>builder()
                .setBootstrapServers(getKafkaPlataformServer())
                .setTopicPattern(Pattern.compile("br.com.example.*"))
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .setProperty("partition.discovery.interval.ms", "10000")
                .build();

        return kafkaSource;
    }

    public Properties createKafkaClouderaConnection(){
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getKafkaClouderaServer());
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "all");
        return properties;
    }
}
