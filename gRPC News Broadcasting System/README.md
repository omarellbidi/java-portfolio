# gRPC News Broadcasting System

## Project Overview
This project extends a news broadcasting application (based on the Observer pattern) to support communication with external components using gRPC and Protocol Buffers. The system allows trusted sources to publish news that is then distributed to both internal observers and external clients through a standardized network interface.

## Architecture
The system consists of several interconnected components:

1. **Core News Broadcasting Logic**
   - `MainSpreader`: Central implementation of the Observer pattern
   - `TrustedSourceManager`: Handles source authentication with secure password hashing
   - `ContentFilter`: Provides content moderation and word filtering

2. **gRPC Services**
   - `SpreadService`: Enables external components to register as sources and spread news
   - `NewsService`: Allows external components to register as news receivers

3. **Client Implementation**
   - `NewsReceiverClient`: A Java client that demonstrates consuming the NewsService

## Technical Implementation Details

### Protocol Buffers
The system uses Protocol Buffers (protobuf) to define the service interfaces and message formats, enabling language-agnostic communication:

```protobuf
syntax = "proto3";
package observer;
service SpreadService {
    rpc RegisterTrustedSource (RegisterRequest) returns (RegisterResponse);
    rpc SpreadNews (SpreadRequest) returns (SpreadResponse);
}
service NewsService {
    rpc RegisterReceiver (ReceiverRequest) returns (stream NewsUpdate);
}
```

### Key Features

#### Authentication & Security
- Password hashing using SHA-256
- Source verification before news broadcasting
- Proper exception handling and error propagation through gRPC

#### Content Filtering
- Word blocking with configurable redaction policies
- Protection against inappropriate content
- Exception handling for blocked content

#### News Distribution
- Real-time streaming to all connected clients
- Thread-safe client management with CopyOnWriteArrayList
- Support for both synchronous and asynchronous communication patterns

#### Services Integration
- Seamless integration between the core Observer pattern and gRPC services
- Proper error handling and status code mapping
- Support for server reflection to enable tools like grpcurl

## Usage Examples

### Starting the Server
```bash
mvn exec:java -Dexec.mainClass="observer.ServerMain"
```

### Using grpcurl to Interact with the Server
```bash
# Register a trusted source
grpcurl -plaintext -d '{"source": "mysource", "pwd": "mypwd"}' localhost:8080 observer.SpreadService/RegisterTrustedSource

# Spread news
grpcurl -plaintext -d '{"news": "Breaking news!", "source": "mysource", "pwd": "mypwd"}' localhost:8080 observer.SpreadService/SpreadNews
```

### Running the Java Client
```bash
mvn exec:java -Dexec.mainClass="observer.NewsReceiverClient"
```

## Design Highlights
- **Design Patterns**: Observer pattern for internal communication, Adapter pattern for gRPC integration
- **Thread Safety**: Proper handling of concurrent client connections
- **Error Handling**: Comprehensive exception handling both internally and through gRPC

## Technologies Used
- gRPC framework
- Protocol Buffers
- Observer Design Pattern
- Concurrent programming
