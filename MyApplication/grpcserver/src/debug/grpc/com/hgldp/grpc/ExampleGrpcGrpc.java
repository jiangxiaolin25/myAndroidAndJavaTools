package com.hgldp.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.0.0)",
    comments = "Source: grpc.proto")
public class ExampleGrpcGrpc {

  private ExampleGrpcGrpc() {}

  public static final String SERVICE_NAME = "ExampleGrpc";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.hgldp.grpc.HelloRequest,
      com.hgldp.grpc.HelloResponse> METHOD_SEND_SIMPLE_MESSAGE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.UNARY,
          generateFullMethodName(
              "ExampleGrpc", "sendSimpleMessage"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.hgldp.grpc.HelloRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.hgldp.grpc.HelloResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.hgldp.grpc.HelloRequest,
      com.hgldp.grpc.StudentResponse> METHOD_GET_LIST_MESSAGE =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING,
          generateFullMethodName(
              "ExampleGrpc", "getListMessage"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.hgldp.grpc.HelloRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.hgldp.grpc.StudentResponse.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.hgldp.grpc.HelloRequest,
      com.hgldp.grpc.StudentResponseList> METHOD_GET_STUDENT_LIST =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.CLIENT_STREAMING,
          generateFullMethodName(
              "ExampleGrpc", "getStudentList"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.hgldp.grpc.HelloRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.hgldp.grpc.StudentResponseList.getDefaultInstance()));
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.hgldp.grpc.StreamRequest,
      com.hgldp.grpc.StreamResponse> METHOD_BI_TALK =
      io.grpc.MethodDescriptor.create(
          io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING,
          generateFullMethodName(
              "ExampleGrpc", "BiTalk"),
          io.grpc.protobuf.ProtoUtils.marshaller(com.hgldp.grpc.StreamRequest.getDefaultInstance()),
          io.grpc.protobuf.ProtoUtils.marshaller(com.hgldp.grpc.StreamResponse.getDefaultInstance()));

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ExampleGrpcStub newStub(io.grpc.Channel channel) {
    return new ExampleGrpcStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ExampleGrpcBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ExampleGrpcBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary and streaming output calls on the service
   */
  public static ExampleGrpcFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ExampleGrpcFutureStub(channel);
  }

  /**
   */
  public static abstract class ExampleGrpcImplBase implements io.grpc.BindableService {

    /**
     */
    public void sendSimpleMessage(com.hgldp.grpc.HelloRequest request,
        io.grpc.stub.StreamObserver<com.hgldp.grpc.HelloResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SEND_SIMPLE_MESSAGE, responseObserver);
    }

    /**
     */
    public void getListMessage(com.hgldp.grpc.HelloRequest request,
        io.grpc.stub.StreamObserver<com.hgldp.grpc.StudentResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_LIST_MESSAGE, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.hgldp.grpc.HelloRequest> getStudentList(
        io.grpc.stub.StreamObserver<com.hgldp.grpc.StudentResponseList> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_GET_STUDENT_LIST, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.hgldp.grpc.StreamRequest> biTalk(
        io.grpc.stub.StreamObserver<com.hgldp.grpc.StreamResponse> responseObserver) {
      return asyncUnimplementedStreamingCall(METHOD_BI_TALK, responseObserver);
    }

    @java.lang.Override public io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_SEND_SIMPLE_MESSAGE,
            asyncUnaryCall(
              new MethodHandlers<
                com.hgldp.grpc.HelloRequest,
                com.hgldp.grpc.HelloResponse>(
                  this, METHODID_SEND_SIMPLE_MESSAGE)))
          .addMethod(
            METHOD_GET_LIST_MESSAGE,
            asyncServerStreamingCall(
              new MethodHandlers<
                com.hgldp.grpc.HelloRequest,
                com.hgldp.grpc.StudentResponse>(
                  this, METHODID_GET_LIST_MESSAGE)))
          .addMethod(
            METHOD_GET_STUDENT_LIST,
            asyncClientStreamingCall(
              new MethodHandlers<
                com.hgldp.grpc.HelloRequest,
                com.hgldp.grpc.StudentResponseList>(
                  this, METHODID_GET_STUDENT_LIST)))
          .addMethod(
            METHOD_BI_TALK,
            asyncBidiStreamingCall(
              new MethodHandlers<
                com.hgldp.grpc.StreamRequest,
                com.hgldp.grpc.StreamResponse>(
                  this, METHODID_BI_TALK)))
          .build();
    }
  }

  /**
   */
  public static final class ExampleGrpcStub extends io.grpc.stub.AbstractStub<ExampleGrpcStub> {
    private ExampleGrpcStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExampleGrpcStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExampleGrpcStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExampleGrpcStub(channel, callOptions);
    }

    /**
     */
    public void sendSimpleMessage(com.hgldp.grpc.HelloRequest request,
        io.grpc.stub.StreamObserver<com.hgldp.grpc.HelloResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SEND_SIMPLE_MESSAGE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getListMessage(com.hgldp.grpc.HelloRequest request,
        io.grpc.stub.StreamObserver<com.hgldp.grpc.StudentResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_LIST_MESSAGE, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.hgldp.grpc.HelloRequest> getStudentList(
        io.grpc.stub.StreamObserver<com.hgldp.grpc.StudentResponseList> responseObserver) {
      return asyncClientStreamingCall(
          getChannel().newCall(METHOD_GET_STUDENT_LIST, getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<com.hgldp.grpc.StreamRequest> biTalk(
        io.grpc.stub.StreamObserver<com.hgldp.grpc.StreamResponse> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(METHOD_BI_TALK, getCallOptions()), responseObserver);
    }
  }

  /**
   */
  public static final class ExampleGrpcBlockingStub extends io.grpc.stub.AbstractStub<ExampleGrpcBlockingStub> {
    private ExampleGrpcBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExampleGrpcBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExampleGrpcBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExampleGrpcBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.hgldp.grpc.HelloResponse sendSimpleMessage(com.hgldp.grpc.HelloRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SEND_SIMPLE_MESSAGE, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.hgldp.grpc.StudentResponse> getListMessage(
        com.hgldp.grpc.HelloRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_LIST_MESSAGE, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ExampleGrpcFutureStub extends io.grpc.stub.AbstractStub<ExampleGrpcFutureStub> {
    private ExampleGrpcFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExampleGrpcFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExampleGrpcFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExampleGrpcFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.hgldp.grpc.HelloResponse> sendSimpleMessage(
        com.hgldp.grpc.HelloRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SEND_SIMPLE_MESSAGE, getCallOptions()), request);
    }
  }

  private static final int METHODID_SEND_SIMPLE_MESSAGE = 0;
  private static final int METHODID_GET_LIST_MESSAGE = 1;
  private static final int METHODID_GET_STUDENT_LIST = 2;
  private static final int METHODID_BI_TALK = 3;

  private static class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ExampleGrpcImplBase serviceImpl;
    private final int methodId;

    public MethodHandlers(ExampleGrpcImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SEND_SIMPLE_MESSAGE:
          serviceImpl.sendSimpleMessage((com.hgldp.grpc.HelloRequest) request,
              (io.grpc.stub.StreamObserver<com.hgldp.grpc.HelloResponse>) responseObserver);
          break;
        case METHODID_GET_LIST_MESSAGE:
          serviceImpl.getListMessage((com.hgldp.grpc.HelloRequest) request,
              (io.grpc.stub.StreamObserver<com.hgldp.grpc.StudentResponse>) responseObserver);
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
        case METHODID_GET_STUDENT_LIST:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.getStudentList(
              (io.grpc.stub.StreamObserver<com.hgldp.grpc.StudentResponseList>) responseObserver);
        case METHODID_BI_TALK:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.biTalk(
              (io.grpc.stub.StreamObserver<com.hgldp.grpc.StreamResponse>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    return new io.grpc.ServiceDescriptor(SERVICE_NAME,
        METHOD_SEND_SIMPLE_MESSAGE,
        METHOD_GET_LIST_MESSAGE,
        METHOD_GET_STUDENT_LIST,
        METHOD_BI_TALK);
  }

}
