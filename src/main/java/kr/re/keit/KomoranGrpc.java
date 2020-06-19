package kr.re.keit;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.29.1-SNAPSHOT)",
    comments = "Source: kr/re/keit/Komoran.proto")
public final class KomoranGrpc {

  private KomoranGrpc() {}

  public static final String SERVICE_NAME = "kr.re.keit.Komoran";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<kr.re.keit.KomoranOuterClass.TokenizeRequest,
      kr.re.keit.KomoranOuterClass.TokenizeResponse> getTokenizeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "tokenize",
      requestType = kr.re.keit.KomoranOuterClass.TokenizeRequest.class,
      responseType = kr.re.keit.KomoranOuterClass.TokenizeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<kr.re.keit.KomoranOuterClass.TokenizeRequest,
      kr.re.keit.KomoranOuterClass.TokenizeResponse> getTokenizeMethod() {
    io.grpc.MethodDescriptor<kr.re.keit.KomoranOuterClass.TokenizeRequest, kr.re.keit.KomoranOuterClass.TokenizeResponse> getTokenizeMethod;
    if ((getTokenizeMethod = KomoranGrpc.getTokenizeMethod) == null) {
      synchronized (KomoranGrpc.class) {
        if ((getTokenizeMethod = KomoranGrpc.getTokenizeMethod) == null) {
          KomoranGrpc.getTokenizeMethod = getTokenizeMethod =
              io.grpc.MethodDescriptor.<kr.re.keit.KomoranOuterClass.TokenizeRequest, kr.re.keit.KomoranOuterClass.TokenizeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "tokenize"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  kr.re.keit.KomoranOuterClass.TokenizeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  kr.re.keit.KomoranOuterClass.TokenizeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new KomoranMethodDescriptorSupplier("tokenize"))
              .build();
        }
      }
    }
    return getTokenizeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static KomoranStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<KomoranStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<KomoranStub>() {
        @java.lang.Override
        public KomoranStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new KomoranStub(channel, callOptions);
        }
      };
    return KomoranStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static KomoranBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<KomoranBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<KomoranBlockingStub>() {
        @java.lang.Override
        public KomoranBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new KomoranBlockingStub(channel, callOptions);
        }
      };
    return KomoranBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static KomoranFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<KomoranFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<KomoranFutureStub>() {
        @java.lang.Override
        public KomoranFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new KomoranFutureStub(channel, callOptions);
        }
      };
    return KomoranFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class KomoranImplBase implements io.grpc.BindableService {

    /**
     */
    public void tokenize(kr.re.keit.KomoranOuterClass.TokenizeRequest request,
        io.grpc.stub.StreamObserver<kr.re.keit.KomoranOuterClass.TokenizeResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getTokenizeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getTokenizeMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                kr.re.keit.KomoranOuterClass.TokenizeRequest,
                kr.re.keit.KomoranOuterClass.TokenizeResponse>(
                  this, METHODID_TOKENIZE)))
          .build();
    }
  }

  /**
   */
  public static final class KomoranStub extends io.grpc.stub.AbstractAsyncStub<KomoranStub> {
    private KomoranStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected KomoranStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new KomoranStub(channel, callOptions);
    }

    /**
     */
    public void tokenize(kr.re.keit.KomoranOuterClass.TokenizeRequest request,
        io.grpc.stub.StreamObserver<kr.re.keit.KomoranOuterClass.TokenizeResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getTokenizeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class KomoranBlockingStub extends io.grpc.stub.AbstractBlockingStub<KomoranBlockingStub> {
    private KomoranBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected KomoranBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new KomoranBlockingStub(channel, callOptions);
    }

    /**
     */
    public kr.re.keit.KomoranOuterClass.TokenizeResponse tokenize(kr.re.keit.KomoranOuterClass.TokenizeRequest request) {
      return blockingUnaryCall(
          getChannel(), getTokenizeMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class KomoranFutureStub extends io.grpc.stub.AbstractFutureStub<KomoranFutureStub> {
    private KomoranFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected KomoranFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new KomoranFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<kr.re.keit.KomoranOuterClass.TokenizeResponse> tokenize(
        kr.re.keit.KomoranOuterClass.TokenizeRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getTokenizeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_TOKENIZE = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final KomoranImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(KomoranImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_TOKENIZE:
          serviceImpl.tokenize((kr.re.keit.KomoranOuterClass.TokenizeRequest) request,
              (io.grpc.stub.StreamObserver<kr.re.keit.KomoranOuterClass.TokenizeResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class KomoranBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    KomoranBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return kr.re.keit.KomoranOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Komoran");
    }
  }

  private static final class KomoranFileDescriptorSupplier
      extends KomoranBaseDescriptorSupplier {
    KomoranFileDescriptorSupplier() {}
  }

  private static final class KomoranMethodDescriptorSupplier
      extends KomoranBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    KomoranMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (KomoranGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new KomoranFileDescriptorSupplier())
              .addMethod(getTokenizeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
