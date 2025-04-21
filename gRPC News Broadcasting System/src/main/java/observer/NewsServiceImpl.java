package observer;

import io.grpc.stub.StreamObserver;
import observer.SpreadServiceOuterClass.NewsUpdate;
import observer.SpreadServiceOuterClass.ReceiverRequest;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Implements the gRPC NewsService to manage client registrations and broadcast news updates.
 */
public class NewsServiceImpl extends NewsServiceGrpc.NewsServiceImplBase {
    private final CopyOnWriteArrayList<StreamObserver<NewsUpdate>> clients = new CopyOnWriteArrayList<>();

    @Override
    public void registerReceiver(ReceiverRequest request, StreamObserver<NewsUpdate> responseObserver) {
        clients.add(responseObserver); // Register the client
        responseObserver.onNext(NewsUpdate.newBuilder()
                .setNews("Registered for updates")
                .setSource("Server")
                .setTimestamp(LocalDateTime.now().toString())
                .build());
    }

    public void broadcastToClients(String news, String source, LocalDateTime timestamp) {
        // Send news updates to all registered clients
        for (StreamObserver<NewsUpdate> client : clients) {
            client.onNext(NewsUpdate.newBuilder()
                    .setNews(news)
                    .setSource(source)
                    .setTimestamp(timestamp.toString())
                    .build());
        }
    }
}
