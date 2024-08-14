package com.marrrang.grpcexample.service

import com.marrrang.grpc.lib.HelloReply
import com.marrrang.grpc.lib.HelloRequest
import com.marrrang.grpc.lib.MarrrangGrpc
import io.grpc.stub.StreamObserver
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture

@Service
class GrpcClientService {
    @GrpcClient("marrrangGrpcClient")
    private lateinit var asyncStub: MarrrangGrpc.MarrrangFutureStub

    fun sayHello(message: String): CompletableFuture<String> {
        val request = HelloRequest.newBuilder().setName("Andrew").build()

        val asyncResponse = asyncStub.sayHello(request)

        val completableFuture = CompletableFuture<String>()

        asyncResponse.addListener(
            {
                try {
                    val response = asyncResponse.get() // 비동기 결과 가져오기
                    completableFuture.complete(response.message)
                } catch (e: Exception) {
                    completableFuture.completeExceptionally(e) // 예외 처리
                }
            },
            { runnable -> runnable.run() } // 직접 실행 (이벤트 루프를 제공할 수도 있음)
        )

        return completableFuture
    }
}