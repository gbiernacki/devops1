package com.amdocs;

import java.util.Properties;
import java.util.concurrent.Future;

//import simple producer packages
import org.apache.kafka.clients.producer.Producer;

//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class SimpleProducer {

	public static void main(String[] args) throws Exception {

		String topicName = args[0];
		String bootstrapServers = args[1];
		System.out.println(topicName +"/" + bootstrapServers);
		//String topicName = "kafka-topic-11";

		// create instance for properties to access producer configs
		Properties props = new Properties();

		// Assign localhost id
		//props.put("bootstrap.servers", "10.236.81.58:9092");
		props.put("bootstrap.servers", bootstrapServers);
		
		// Set acknowledgements for producer requests.
		props.put("acks", "all");

		// If the request fails, the producer can automatically retry,
		props.put("retries", 0);

		// Specify buffer size in config
		props.put("batch.size", 16384);

		// Reduce the no of requests less than 0
		props.put("linger.ms", 1);

		// The buffer.memory controls the total amount of memory available to the
		// producer for buffering.
		props.put("buffer.memory", 33554432);

		props.put("key.serializer", Class.forName("org.apache.kafka.common.serialization.StringSerializer"));

		props.put("value.serializer", Class.forName("org.apache.kafka.common.serialization.StringSerializer"));

		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		try {
			for (int i = 0; i < 3; i++) {
				System.out.println("here");
				Future<RecordMetadata> x = producer.send(new ProducerRecord<String, String>(topicName, Integer.toString(i),
						"Hi Loukas-".concat(Integer.toString(i))));
				System.out.println("Message sent successfully " + x.toString());
			}
			producer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}
}