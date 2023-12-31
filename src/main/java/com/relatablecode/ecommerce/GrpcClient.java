package com.relatablecode.ecommerce;

import com.relatablecode.ecommerce.GreetServiceGrpc;
import com.relatablecode.ecommerce.GreetRequest;
import com.relatablecode.ecommerce.GreetResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

//public class GrpcClient {
//    public static void main(String[] args) {
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
//    }
//}
