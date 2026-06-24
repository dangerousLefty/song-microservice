package com.hamza.songmicroservice.config;

import com.hamza.songmicroservice.grpc.CatalogGrpcService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcServerConfig {

    private static final Logger logger = LoggerFactory.getLogger(GrpcServerConfig.class);

    @Value("${grpc.port:9090}")
    private int grpcPort;

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Server grpcServer(CatalogGrpcService catalogGrpcService) throws IOException {
        Server server = ServerBuilder
                .forPort(grpcPort)
                .addService(catalogGrpcService)
                .build();

        logger.info("gRPC server configured on port {}", grpcPort);
        return server;
    }
}
