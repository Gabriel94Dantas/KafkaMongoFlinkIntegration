package org.example.contexts;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.example.converters.EventConverter;
import org.example.models.Event;

import java.io.Closeable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KafkaDispatcher implements Closeable {

    private final KafkaProducer<String, String> producer;
    private KafkaContext kafkaContext;

    public KafkaDispatcher() {
        this.kafkaContext = new KafkaContext();
        this.producer = new KafkaProducer<>(this.kafkaContext.createKafkaClouderaConnection());
    }

    public void send(Event payload) throws ExecutionException, InterruptedException {
        Future<RecordMetadata> future = sendAsync(payload);
        future.get();
    }

    public Future<RecordMetadata> sendAsync(Event payload) {
        ProducerRecord record = new ProducerRecord<>("cloudera." + payload.getType(),
                payload.getId(),
                EventConverter.eventToJson(payload));
        Callback callback = (data, ex) -> {
            if (ex != null) {
                ex.printStackTrace();
                return;
            }
            System.out.println("sucesso enviando " + data.topic() + ":::partition " + data.partition() + "/ offset " + data.offset() + "/ timestamp " + data.timestamp());
        };
        return producer.send(record, callback);
    }

    @Override
    public void close() {
        producer.close();
    }
}
