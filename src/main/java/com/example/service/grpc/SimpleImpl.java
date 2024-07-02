package com.example.service.grpc;

import com.example.service.proto.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDateTime;

@GrpcService
public class SimpleImpl extends SimpleGrpc.SimpleImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse helloResponse = HelloResponse.newBuilder().setMessage("Hello " + request.getName()).build();
        responseObserver.onNext(helloResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void generateData(Empty request, StreamObserver<HelloResponse> responseObserver) {
        for (int i = 1; i <= 5; i++) {
            HelloResponse helloResponse = HelloResponse.newBuilder().setMessage(String.format("Message Number: %d", i)).build();
            responseObserver.onNext(helloResponse);
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Action> createAction(StreamObserver<HelloResponse> responseObserver) {
        return new StreamObserver<Action>() {
            @Override
            public void onNext(Action value) {
                if (value == null) return;
                if (value.getType() == ActionType.START) {
                    responseObserver.onNext(HelloResponse.newBuilder().setMessage(String.format("Msg Received! %d", LocalDateTime.now().getNano())).build());
                }
                if (value.getType() == ActionType.STOP) {
                    responseObserver.onCompleted();
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
