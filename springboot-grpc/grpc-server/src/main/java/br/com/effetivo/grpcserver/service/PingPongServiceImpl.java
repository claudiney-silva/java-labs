package br.com.effetivo.grpcserver.service;

import br.com.effetivo.grpcserver.PingPongServiceGrpc;
import br.com.effetivo.grpcserver.PingRequest;
import br.com.effetivo.grpcserver.PongResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PingPongServiceImpl extends PingPongServiceGrpc.PingPongServiceImplBase {
    @Override
    public void ping(
            PingRequest request, StreamObserver<PongResponse> responseObserver) {
        String ping = new StringBuilder()
                .append("pong")
                .toString();
        PongResponse response = PongResponse.newBuilder()
                .setPong(ping)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
