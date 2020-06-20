package kr.re.keit;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class Main {
    static final int port = 50051;

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder
            .forPort(port)
            .addService(new KomoranService())
            .build();
        server.start().awaitTermination();
    }
}
