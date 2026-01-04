package com.demo.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.function.Supplier;

@Configuration
public class KafkaProducerStreams {

    @Bean
    public Supplier<RiderLocation> sendRiderLocation(){
        Random random = new Random();
        return ()->{
            String riderId = String.valueOf(random.nextInt(10));
            RiderLocation riderLocation = new RiderLocation(riderId, random.nextDouble(100), random.nextDouble(100));
            System.out.println("Sending: "+riderLocation.getRiderId()+" to location: "+riderLocation.getLatitude()+" "+riderLocation.getLongitude()+" at "+ LocalDateTime.now());
            return riderLocation;
        };
    }

    @Bean
    public Supplier<Message<String>> sendRiderStatus(){
        Random random = new Random();
        return ()->{
            String riderId = String.valueOf(random.nextInt(10));
            String status = random.nextBoolean()?"ride started":"ride completed";
            System.out.println("Sending: "+status);
            return MessageBuilder.withPayload(riderId+":"+status)
                    .setHeader(KafkaHeaders.KEY,riderId.getBytes())
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.TEXT_PLAIN)
                    .build();

        };
    }

}
