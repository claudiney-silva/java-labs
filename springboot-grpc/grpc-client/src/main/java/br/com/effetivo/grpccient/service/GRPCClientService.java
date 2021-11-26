package br.com.effetivo.grpccient.service;

import br.com.effetivo.grpcserver.PingPongServiceGrpc;
import br.com.effetivo.grpcserver.PingRequest;
import br.com.effetivo.grpcserver.PongResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class GRPCClientService {
    public String ping() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        PingPongServiceGrpc.PingPongServiceBlockingStub stub
                = PingPongServiceGrpc.newBlockingStub(channel);
        PongResponse helloResponse = stub.ping(PingRequest.newBuilder()
                .setPing("")
                .build());
        channel.shutdown();
        return helloResponse.getPong();
    }
}