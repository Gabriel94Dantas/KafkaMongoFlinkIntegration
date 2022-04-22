package org.example.jobs;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.contexts.KafkaContext;
import org.example.contexts.KafkaDispatcher;
import org.example.converters.EventConverter;
import org.example.models.Event;
import org.example.services.EventService;

/***
 * Class responsible to consume and execute the Events transformation
 * */
public class EventJob {

    private KafkaContext kafkaContext;

    public EventJob(){
        this.kafkaContext = new KafkaContext();
    }

    public void run() throws Exception {
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        KafkaSource<String> kafkaSource = this.kafkaContext.createKafkaPlataformConnection();
        DataStream<String> events = environment.fromSource(kafkaSource,
                WatermarkStrategy.noWatermarks(),
                "KafkaSource");

        SingleOutputStreamOperator<String> map = events.map(new MapFunction<String, String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public String map(String value) throws Exception {
                Event event = EventConverter.jsonToEvent(value);

                EventService eventService = new EventService();
                if(eventService.returnEventId(event) == null){
                    KafkaDispatcher kafkaDispatcher = new KafkaDispatcher();
                    kafkaDispatcher.send(event);
                    eventService.insertEvent(event);
                }
                return EventConverter.eventToJson(event);
            }

        });

        environment.execute();
    }

}
