package com.relatablecode.ecommerce;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ECommerceApplication {

    public static void main(String[] args) {
//        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
//                .usePlaintext()
//                .build();
//
//        GreetServiceGrpc.GreetServiceBlockingStub stub = GreetServiceGrpc.newBlockingStub(channel);
//
//        GreetRequest request = GreetRequest.newBuilder()
//                .setName("John Doe")
//                .build();
//
//        GreetResponse response = stub.greet(request);
//        System.out.println(response.getGreeting());
//
//        channel.shutdown();
        SpringApplication.run(ECommerceApplication.class, args);
    }

}
