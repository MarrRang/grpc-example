package com.marrrang.grpcexample.service

import com.marrrang.grpc.lib.HelloReply
import com.marrrang.grpc.lib.HelloRequest
import com.marrrang.grpc.lib.MarrrangGrpc
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class GrpcServerService: MarrrangGrpc.MarrrangImplBase() {
    override fun sayHello(req: HelloRequest, responseObserver: StreamObserver<HelloReply?>) {
        val reply: HelloReply = HelloReply.newBuilder()
            .setMessage("Marrrang Hello : " + req.name)
            .build()

        responseObserver.onNext(reply)
        responseObserver.onCompleted()
    }
}