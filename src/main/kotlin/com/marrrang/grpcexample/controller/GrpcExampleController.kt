package com.marrrang.grpcexample.controller

import com.marrrang.grpcexample.service.GrpcClientService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
class GrpcExampleController(private val grpcClientService: GrpcClientService) {
    @GetMapping("/sayHelloAsync")
    fun sayHelloAsync(@RequestParam name: String): CompletableFuture<String> {
        val responseFuture = grpcClientService.sayHello(name)

        return responseFuture
    }
}