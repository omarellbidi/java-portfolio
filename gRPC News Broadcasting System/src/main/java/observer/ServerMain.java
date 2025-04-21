package observer;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;

/**
 * Entry point for the gRPC server, hosting SpreadService and NewsService.
 */
public class ServerMain {
    public static void main(String[] args) throws Exception {
        NewsServiceImpl newsService = new NewsServiceImpl();
        MainSpreader mainSpreader = new MainSpreader();

        // Start the gRPC server on port 8080
        Server server = ServerBuilder.forPort(8080)
                .addService(new SpreadServiceImpl(mainSpreader)) // Add SpreadService
                .addService(newsService) // Add NewsService
                .addService(ProtoReflectionService.newInstance()) // For easier client interaction
                .build();

        System.out.println("Server started on port 8080");
        server.start();
        server.awaitTermination();
    }
}
