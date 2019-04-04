// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: WebsocketSystem.proto

package kcwiki.msgtransfer.protobuf.proto;

/**
 * Protobuf type {@code kcwiki.msgtransfer.protobuf.WebsocketSystem}
 */
public  final class WebsocketSystem extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:kcwiki.msgtransfer.protobuf.WebsocketSystem)
    WebsocketSystemOrBuilder {
private static final long serialVersionUID = 0L;
  // Use WebsocketSystem.newBuilder() to construct.
  private WebsocketSystem(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private WebsocketSystem() {
    msgType_ = 0;
    data_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private WebsocketSystem(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 8: {
            int rawValue = input.readEnum();

            msgType_ = rawValue;
            break;
          }
          case 18: {
            java.lang.String s = input.readStringRequireUtf8();

            data_ = s;
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return kcwiki.msgtransfer.protobuf.proto.WebsocketSystemProto.internal_static_kcwiki_msgtransfer_protobuf_WebsocketSystem_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return kcwiki.msgtransfer.protobuf.proto.WebsocketSystemProto.internal_static_kcwiki_msgtransfer_protobuf_WebsocketSystem_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.class, kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.Builder.class);
  }

  /**
   * Protobuf enum {@code kcwiki.msgtransfer.protobuf.WebsocketSystem.SystemMessageType}
   */
  public enum SystemMessageType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>SYSTEM_INFO = 0;</code>
     */
    SYSTEM_INFO(0),
    /**
     * <code>DEBUG_MSG = 1;</code>
     */
    DEBUG_MSG(1),
    /**
     * <code>PAYLOAD_ERROR = 2;</code>
     */
    PAYLOAD_ERROR(2),
    /**
     * <code>CLIENT_REBOOT = 3;</code>
     */
    CLIENT_REBOOT(3),
    /**
     * <code>CLIENT_SHUTDOWN = 4;</code>
     */
    CLIENT_SHUTDOWN(4),
    /**
     * <code>SERVER_REBOOT = 5;</code>
     */
    SERVER_REBOOT(5),
    /**
     * <code>SERVER_SHUTDOWN = 6;</code>
     */
    SERVER_SHUTDOWN(6),
    /**
     * <code>AUTHORIZATION_REQUIRED = 7;</code>
     */
    AUTHORIZATION_REQUIRED(7),
    /**
     * <code>AUTHORIZATION_FAIL = 8;</code>
     */
    AUTHORIZATION_FAIL(8),
    /**
     * <code>AUTHORIZATION_SUCCESS = 9;</code>
     */
    AUTHORIZATION_SUCCESS(9),
    /**
     * <code>PING = 10;</code>
     */
    PING(10),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>SYSTEM_INFO = 0;</code>
     */
    public static final int SYSTEM_INFO_VALUE = 0;
    /**
     * <code>DEBUG_MSG = 1;</code>
     */
    public static final int DEBUG_MSG_VALUE = 1;
    /**
     * <code>PAYLOAD_ERROR = 2;</code>
     */
    public static final int PAYLOAD_ERROR_VALUE = 2;
    /**
     * <code>CLIENT_REBOOT = 3;</code>
     */
    public static final int CLIENT_REBOOT_VALUE = 3;
    /**
     * <code>CLIENT_SHUTDOWN = 4;</code>
     */
    public static final int CLIENT_SHUTDOWN_VALUE = 4;
    /**
     * <code>SERVER_REBOOT = 5;</code>
     */
    public static final int SERVER_REBOOT_VALUE = 5;
    /**
     * <code>SERVER_SHUTDOWN = 6;</code>
     */
    public static final int SERVER_SHUTDOWN_VALUE = 6;
    /**
     * <code>AUTHORIZATION_REQUIRED = 7;</code>
     */
    public static final int AUTHORIZATION_REQUIRED_VALUE = 7;
    /**
     * <code>AUTHORIZATION_FAIL = 8;</code>
     */
    public static final int AUTHORIZATION_FAIL_VALUE = 8;
    /**
     * <code>AUTHORIZATION_SUCCESS = 9;</code>
     */
    public static final int AUTHORIZATION_SUCCESS_VALUE = 9;
    /**
     * <code>PING = 10;</code>
     */
    public static final int PING_VALUE = 10;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static SystemMessageType valueOf(int value) {
      return forNumber(value);
    }

    public static SystemMessageType forNumber(int value) {
      switch (value) {
        case 0: return SYSTEM_INFO;
        case 1: return DEBUG_MSG;
        case 2: return PAYLOAD_ERROR;
        case 3: return CLIENT_REBOOT;
        case 4: return CLIENT_SHUTDOWN;
        case 5: return SERVER_REBOOT;
        case 6: return SERVER_SHUTDOWN;
        case 7: return AUTHORIZATION_REQUIRED;
        case 8: return AUTHORIZATION_FAIL;
        case 9: return AUTHORIZATION_SUCCESS;
        case 10: return PING;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<SystemMessageType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        SystemMessageType> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<SystemMessageType>() {
            public SystemMessageType findValueByNumber(int number) {
              return SystemMessageType.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.getDescriptor().getEnumTypes().get(0);
    }

    private static final SystemMessageType[] VALUES = values();

    public static SystemMessageType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private SystemMessageType(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:kcwiki.msgtransfer.protobuf.WebsocketSystem.SystemMessageType)
  }

  public static final int MSG_TYPE_FIELD_NUMBER = 1;
  private int msgType_;
  /**
   * <code>.kcwiki.msgtransfer.protobuf.WebsocketSystem.SystemMessageType msg_type = 1;</code>
   */
  public int getMsgTypeValue() {
    return msgType_;
  }
  /**
   * <code>.kcwiki.msgtransfer.protobuf.WebsocketSystem.SystemMessageType msg_type = 1;</code>
   */
  public kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.SystemMessageType getMsgType() {
    @SuppressWarnings("deprecation")
    kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.SystemMessageType result = kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.SystemMessageType.valueOf(msgType_);
    return result == null ? kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.SystemMessageType.UNRECOGNIZED : result;
  }

  public static final int DATA_FIELD_NUMBER = 2;
  private volatile java.lang.Object data_;
  /**
   * <code>string data = 2;</code>
   */
  public java.lang.String getData() {
    java.lang.Object ref = data_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      data_ = s;
      return s;
    }
  }
  /**
   * <code>string data = 2;</code>
   */
  public com.google.protobuf.ByteString
      getDataBytes() {
    java.lang.Object ref = data_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      data_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (msgType_ != kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.SystemMessageType.SYSTEM_INFO.getNumber()) {
      output.writeEnum(1, msgType_);
    }
    if (!getDataBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, data_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (msgType_ != kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.SystemMessageType.SYSTEM_INFO.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, msgType_);
    }
    if (!getDataBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, data_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof kcwiki.msgtransfer.protobuf.proto.WebsocketSystem)) {
      return super.equals(obj);
    }
    kcwiki.msgtransfer.protobuf.proto.WebsocketSystem other = (kcwiki.msgtransfer.protobuf.proto.WebsocketSystem) obj;

    if (msgType_ != other.msgType_) return false;
    if (!getData()
        .equals(other.getData())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + MSG_TYPE_FIELD_NUMBER;
    hash = (53 * hash) + msgType_;
    hash = (37 * hash) + DATA_FIELD_NUMBER;
    hash = (53 * hash) + getData().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(kcwiki.msgtransfer.protobuf.proto.WebsocketSystem prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code kcwiki.msgtransfer.protobuf.WebsocketSystem}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:kcwiki.msgtransfer.protobuf.WebsocketSystem)
      kcwiki.msgtransfer.protobuf.proto.WebsocketSystemOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return kcwiki.msgtransfer.protobuf.proto.WebsocketSystemProto.internal_static_kcwiki_msgtransfer_protobuf_WebsocketSystem_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return kcwiki.msgtransfer.protobuf.proto.WebsocketSystemProto.internal_static_kcwiki_msgtransfer_protobuf_WebsocketSystem_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.class, kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.Builder.class);
    }

    // Construct using kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      msgType_ = 0;

      data_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return kcwiki.msgtransfer.protobuf.proto.WebsocketSystemProto.internal_static_kcwiki_msgtransfer_protobuf_WebsocketSystem_descriptor;
    }

    @java.lang.Override
    public kcwiki.msgtransfer.protobuf.proto.WebsocketSystem getDefaultInstanceForType() {
      return kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.getDefaultInstance();
    }

    @java.lang.Override
    public kcwiki.msgtransfer.protobuf.proto.WebsocketSystem build() {
      kcwiki.msgtransfer.protobuf.proto.WebsocketSystem result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public kcwiki.msgtransfer.protobuf.proto.WebsocketSystem buildPartial() {
      kcwiki.msgtransfer.protobuf.proto.WebsocketSystem result = new kcwiki.msgtransfer.protobuf.proto.WebsocketSystem(this);
      result.msgType_ = msgType_;
      result.data_ = data_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof kcwiki.msgtransfer.protobuf.proto.WebsocketSystem) {
        return mergeFrom((kcwiki.msgtransfer.protobuf.proto.WebsocketSystem)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(kcwiki.msgtransfer.protobuf.proto.WebsocketSystem other) {
      if (other == kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.getDefaultInstance()) return this;
      if (other.msgType_ != 0) {
        setMsgTypeValue(other.getMsgTypeValue());
      }
      if (!other.getData().isEmpty()) {
        data_ = other.data_;
        onChanged();
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      kcwiki.msgtransfer.protobuf.proto.WebsocketSystem parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (kcwiki.msgtransfer.protobuf.proto.WebsocketSystem) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int msgType_ = 0;
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketSystem.SystemMessageType msg_type = 1;</code>
     */
    public int getMsgTypeValue() {
      return msgType_;
    }
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketSystem.SystemMessageType msg_type = 1;</code>
     */
    public Builder setMsgTypeValue(int value) {
      msgType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketSystem.SystemMessageType msg_type = 1;</code>
     */
    public kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.SystemMessageType getMsgType() {
      @SuppressWarnings("deprecation")
      kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.SystemMessageType result = kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.SystemMessageType.valueOf(msgType_);
      return result == null ? kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.SystemMessageType.UNRECOGNIZED : result;
    }
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketSystem.SystemMessageType msg_type = 1;</code>
     */
    public Builder setMsgType(kcwiki.msgtransfer.protobuf.proto.WebsocketSystem.SystemMessageType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      msgType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketSystem.SystemMessageType msg_type = 1;</code>
     */
    public Builder clearMsgType() {
      
      msgType_ = 0;
      onChanged();
      return this;
    }

    private java.lang.Object data_ = "";
    /**
     * <code>string data = 2;</code>
     */
    public java.lang.String getData() {
      java.lang.Object ref = data_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        data_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string data = 2;</code>
     */
    public com.google.protobuf.ByteString
        getDataBytes() {
      java.lang.Object ref = data_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        data_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string data = 2;</code>
     */
    public Builder setData(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      data_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string data = 2;</code>
     */
    public Builder clearData() {
      
      data_ = getDefaultInstance().getData();
      onChanged();
      return this;
    }
    /**
     * <code>string data = 2;</code>
     */
    public Builder setDataBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      data_ = value;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:kcwiki.msgtransfer.protobuf.WebsocketSystem)
  }

  // @@protoc_insertion_point(class_scope:kcwiki.msgtransfer.protobuf.WebsocketSystem)
  private static final kcwiki.msgtransfer.protobuf.proto.WebsocketSystem DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new kcwiki.msgtransfer.protobuf.proto.WebsocketSystem();
  }

  public static kcwiki.msgtransfer.protobuf.proto.WebsocketSystem getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<WebsocketSystem>
      PARSER = new com.google.protobuf.AbstractParser<WebsocketSystem>() {
    @java.lang.Override
    public WebsocketSystem parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new WebsocketSystem(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<WebsocketSystem> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<WebsocketSystem> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public kcwiki.msgtransfer.protobuf.proto.WebsocketSystem getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

