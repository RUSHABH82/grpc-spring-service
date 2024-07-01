package com.example.service.grpc;

import com.example.service.proto.Empty;
import com.example.service.proto.HelloRequest;
import com.example.service.proto.HelloResponse;
import com.example.service.proto.SimpleGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class SimpleImpl  extends SimpleGrpc.SimpleImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse helloResponse = HelloResponse.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void generateData(Empty request, StreamObserver<HelloResponse> responseObserver) {
        for (int i = 1; i <= 5; i++) {
            HelloResponse helloResponse = HelloResponse.newBuilder()
                    .setMessage(String.format("Message Number: %d", i))
                    .build();
            responseObserver.onNext(helloResponse);
        }
        responseObserver.onCompleted();
    }
}
