package observer;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import observer.SpreadServiceOuterClass.NewsUpdate;
import observer.SpreadServiceOuterClass.ReceiverRequest;
import observer.NewsServiceGrpc;


 // A gRPC client that registers to receive news updates from the server.
 
public class NewsReceiverClient {
    public static void main(String[] args) {
        // Create a gRPC channel to connect to the server
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext() 
                .build();

        // Create a stub for the NewsService
        NewsServiceGrpc.NewsServiceStub newsStub = NewsServiceGrpc.newStub(channel);

        // Register this client to receive news updates
        newsStub.registerReceiver(ReceiverRequest.newBuilder()
                .setClientId("JavaClient") // Unique ID for this client
                .build(), new StreamObserver<NewsUpdate>() {
            @Override
            public void onNext(NewsUpdate value) {
                // Handle received news update
                System.out.println("Received News: " + value.getNews() +
                        " from " + value.getSource() +
                        " at " + value.getTimestamp());
            }

            @Override
            public void onError(Throwable t) {
                // Handle error during communication
                System.err.println("Error: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                // Called when the server completes the stream
                System.out.println("Stream completed.");
            }
        });

        // Keep the client running to receive updates
        try {
            Thread.sleep(60000); // Run for 1 minute
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Shutdown when done
            channel.shutdown();
        }
    }
}
