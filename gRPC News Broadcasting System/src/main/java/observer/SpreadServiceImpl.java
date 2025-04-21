package observer;

import io.grpc.stub.StreamObserver;
import observer.SpreadServiceOuterClass.RegisterRequest;
import observer.SpreadServiceOuterClass.RegisterResponse;
import observer.SpreadServiceOuterClass.SpreadRequest;
import observer.SpreadServiceOuterClass.SpreadResponse;

/**
 * Implements the gRPC SpreadService to handle news spreading and source registration.
 */
public class SpreadServiceImpl extends SpreadServiceGrpc.SpreadServiceImplBase {
    private final MainSpreader mainSpreader;

    public SpreadServiceImpl(MainSpreader mainSpreader) {
        this.mainSpreader = mainSpreader;
    }

    @Override
    public void registerTrustedSource(RegisterRequest request, StreamObserver<RegisterResponse> responseObserver) {
        boolean success = mainSpreader.registerTrustedSource(request.getSource(), request.getPwd());
        RegisterResponse response = RegisterResponse.newBuilder().setSuccess(success).build();
        responseObserver.onNext(response); // Send success status
        responseObserver.onCompleted();
    }

    @Override
    public void spreadNews(SpreadRequest request, StreamObserver<SpreadResponse> responseObserver) {
        try {
            String news = mainSpreader.spreadNews(request.getNews(), request.getSource(), request.getPwd());
            SpreadResponse response = SpreadResponse.newBuilder().setNews(news).build();
            responseObserver.onNext(response); // Send the spread news
            responseObserver.onCompleted();
        } catch (NewsSpreaderException e) {
            responseObserver.onError(io.grpc.Status.INVALID_ARGUMENT
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        }
    }
}
