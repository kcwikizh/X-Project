// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: WebsocketWapper.proto

package kcwiki.msgtransfer.protobuf.proto;

/**
 * Protobuf type {@code kcwiki.msgtransfer.protobuf.WebsocketWapper}
 */
public  final class WebsocketWapper extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:kcwiki.msgtransfer.protobuf.WebsocketWapper)
    WebsocketWapperOrBuilder {
private static final long serialVersionUID = 0L;
  // Use WebsocketWapper.newBuilder() to construct.
  private WebsocketWapper(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private WebsocketWapper() {
    protoType_ = 0;
    protoCode_ = 0;
    protoPayload_ = com.google.protobuf.ByteString.EMPTY;
    sign_ = "";
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private WebsocketWapper(
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

            protoType_ = rawValue;
            break;
          }
          case 16: {
            int rawValue = input.readEnum();

            protoCode_ = rawValue;
            break;
          }
          case 24: {

            timestamp_ = input.readInt64();
            break;
          }
          case 34: {
            java.lang.String s = input.readStringRequireUtf8();

            sign_ = s;
            break;
          }
          case 42: {

            protoPayload_ = input.readBytes();
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
    return kcwiki.msgtransfer.protobuf.proto.WebsocketWapperProto.internal_static_kcwiki_msgtransfer_protobuf_WebsocketWapper_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return kcwiki.msgtransfer.protobuf.proto.WebsocketWapperProto.internal_static_kcwiki_msgtransfer_protobuf_WebsocketWapper_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.class, kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.Builder.class);
  }

  /**
   * Protobuf enum {@code kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoType}
   */
  public enum ProtoType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>SYSTEM = 0;</code>
     */
    SYSTEM(0),
    /**
     * <code>NON_SYSTEM = 1;</code>
     */
    NON_SYSTEM(1),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>SYSTEM = 0;</code>
     */
    public static final int SYSTEM_VALUE = 0;
    /**
     * <code>NON_SYSTEM = 1;</code>
     */
    public static final int NON_SYSTEM_VALUE = 1;


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
    public static ProtoType valueOf(int value) {
      return forNumber(value);
    }

    public static ProtoType forNumber(int value) {
      switch (value) {
        case 0: return SYSTEM;
        case 1: return NON_SYSTEM;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<ProtoType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        ProtoType> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<ProtoType>() {
            public ProtoType findValueByNumber(int number) {
              return ProtoType.forNumber(number);
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
      return kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.getDescriptor().getEnumTypes().get(0);
    }

    private static final ProtoType[] VALUES = values();

    public static ProtoType valueOf(
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

    private ProtoType(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoType)
  }

  /**
   * Protobuf enum {@code kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoCode}
   */
  public enum ProtoCode
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>FAIL = 0;</code>
     */
    FAIL(0),
    /**
     * <code>SUCCESS = 1;</code>
     */
    SUCCESS(1),
    /**
     * <code>ERROR = 2;</code>
     */
    ERROR(2),
    UNRECOGNIZED(-1),
    ;

    /**
     * <code>FAIL = 0;</code>
     */
    public static final int FAIL_VALUE = 0;
    /**
     * <code>SUCCESS = 1;</code>
     */
    public static final int SUCCESS_VALUE = 1;
    /**
     * <code>ERROR = 2;</code>
     */
    public static final int ERROR_VALUE = 2;


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
    public static ProtoCode valueOf(int value) {
      return forNumber(value);
    }

    public static ProtoCode forNumber(int value) {
      switch (value) {
        case 0: return FAIL;
        case 1: return SUCCESS;
        case 2: return ERROR;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<ProtoCode>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        ProtoCode> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<ProtoCode>() {
            public ProtoCode findValueByNumber(int number) {
              return ProtoCode.forNumber(number);
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
      return kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.getDescriptor().getEnumTypes().get(1);
    }

    private static final ProtoCode[] VALUES = values();

    public static ProtoCode valueOf(
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

    private ProtoCode(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoCode)
  }

  public static final int PROTO_TYPE_FIELD_NUMBER = 1;
  private int protoType_;
  /**
   * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoType proto_type = 1;</code>
   */
  public int getProtoTypeValue() {
    return protoType_;
  }
  /**
   * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoType proto_type = 1;</code>
   */
  public kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoType getProtoType() {
    @SuppressWarnings("deprecation")
    kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoType result = kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoType.valueOf(protoType_);
    return result == null ? kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoType.UNRECOGNIZED : result;
  }

  public static final int PROTO_CODE_FIELD_NUMBER = 2;
  private int protoCode_;
  /**
   * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoCode proto_code = 2;</code>
   */
  public int getProtoCodeValue() {
    return protoCode_;
  }
  /**
   * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoCode proto_code = 2;</code>
   */
  public kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoCode getProtoCode() {
    @SuppressWarnings("deprecation")
    kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoCode result = kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoCode.valueOf(protoCode_);
    return result == null ? kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoCode.UNRECOGNIZED : result;
  }

  public static final int PROTO_PAYLOAD_FIELD_NUMBER = 5;
  private com.google.protobuf.ByteString protoPayload_;
  /**
   * <code>bytes proto_payload = 5;</code>
   */
  public com.google.protobuf.ByteString getProtoPayload() {
    return protoPayload_;
  }

  public static final int TIMESTAMP_FIELD_NUMBER = 3;
  private long timestamp_;
  /**
   * <code>int64 timestamp = 3;</code>
   */
  public long getTimestamp() {
    return timestamp_;
  }

  public static final int SIGN_FIELD_NUMBER = 4;
  private volatile java.lang.Object sign_;
  /**
   * <code>string sign = 4;</code>
   */
  public java.lang.String getSign() {
    java.lang.Object ref = sign_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      sign_ = s;
      return s;
    }
  }
  /**
   * <code>string sign = 4;</code>
   */
  public com.google.protobuf.ByteString
      getSignBytes() {
    java.lang.Object ref = sign_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      sign_ = b;
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
    if (protoType_ != kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoType.SYSTEM.getNumber()) {
      output.writeEnum(1, protoType_);
    }
    if (protoCode_ != kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoCode.FAIL.getNumber()) {
      output.writeEnum(2, protoCode_);
    }
    if (timestamp_ != 0L) {
      output.writeInt64(3, timestamp_);
    }
    if (!getSignBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 4, sign_);
    }
    if (!protoPayload_.isEmpty()) {
      output.writeBytes(5, protoPayload_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (protoType_ != kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoType.SYSTEM.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(1, protoType_);
    }
    if (protoCode_ != kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoCode.FAIL.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(2, protoCode_);
    }
    if (timestamp_ != 0L) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(3, timestamp_);
    }
    if (!getSignBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, sign_);
    }
    if (!protoPayload_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(5, protoPayload_);
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
    if (!(obj instanceof kcwiki.msgtransfer.protobuf.proto.WebsocketWapper)) {
      return super.equals(obj);
    }
    kcwiki.msgtransfer.protobuf.proto.WebsocketWapper other = (kcwiki.msgtransfer.protobuf.proto.WebsocketWapper) obj;

    if (protoType_ != other.protoType_) return false;
    if (protoCode_ != other.protoCode_) return false;
    if (!getProtoPayload()
        .equals(other.getProtoPayload())) return false;
    if (getTimestamp()
        != other.getTimestamp()) return false;
    if (!getSign()
        .equals(other.getSign())) return false;
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
    hash = (37 * hash) + PROTO_TYPE_FIELD_NUMBER;
    hash = (53 * hash) + protoType_;
    hash = (37 * hash) + PROTO_CODE_FIELD_NUMBER;
    hash = (53 * hash) + protoCode_;
    hash = (37 * hash) + PROTO_PAYLOAD_FIELD_NUMBER;
    hash = (53 * hash) + getProtoPayload().hashCode();
    hash = (37 * hash) + TIMESTAMP_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
        getTimestamp());
    hash = (37 * hash) + SIGN_FIELD_NUMBER;
    hash = (53 * hash) + getSign().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parseFrom(
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
  public static Builder newBuilder(kcwiki.msgtransfer.protobuf.proto.WebsocketWapper prototype) {
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
   * Protobuf type {@code kcwiki.msgtransfer.protobuf.WebsocketWapper}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:kcwiki.msgtransfer.protobuf.WebsocketWapper)
      kcwiki.msgtransfer.protobuf.proto.WebsocketWapperOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return kcwiki.msgtransfer.protobuf.proto.WebsocketWapperProto.internal_static_kcwiki_msgtransfer_protobuf_WebsocketWapper_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return kcwiki.msgtransfer.protobuf.proto.WebsocketWapperProto.internal_static_kcwiki_msgtransfer_protobuf_WebsocketWapper_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.class, kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.Builder.class);
    }

    // Construct using kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.newBuilder()
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
      protoType_ = 0;

      protoCode_ = 0;

      protoPayload_ = com.google.protobuf.ByteString.EMPTY;

      timestamp_ = 0L;

      sign_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return kcwiki.msgtransfer.protobuf.proto.WebsocketWapperProto.internal_static_kcwiki_msgtransfer_protobuf_WebsocketWapper_descriptor;
    }

    @java.lang.Override
    public kcwiki.msgtransfer.protobuf.proto.WebsocketWapper getDefaultInstanceForType() {
      return kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.getDefaultInstance();
    }

    @java.lang.Override
    public kcwiki.msgtransfer.protobuf.proto.WebsocketWapper build() {
      kcwiki.msgtransfer.protobuf.proto.WebsocketWapper result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public kcwiki.msgtransfer.protobuf.proto.WebsocketWapper buildPartial() {
      kcwiki.msgtransfer.protobuf.proto.WebsocketWapper result = new kcwiki.msgtransfer.protobuf.proto.WebsocketWapper(this);
      result.protoType_ = protoType_;
      result.protoCode_ = protoCode_;
      result.protoPayload_ = protoPayload_;
      result.timestamp_ = timestamp_;
      result.sign_ = sign_;
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
      if (other instanceof kcwiki.msgtransfer.protobuf.proto.WebsocketWapper) {
        return mergeFrom((kcwiki.msgtransfer.protobuf.proto.WebsocketWapper)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(kcwiki.msgtransfer.protobuf.proto.WebsocketWapper other) {
      if (other == kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.getDefaultInstance()) return this;
      if (other.protoType_ != 0) {
        setProtoTypeValue(other.getProtoTypeValue());
      }
      if (other.protoCode_ != 0) {
        setProtoCodeValue(other.getProtoCodeValue());
      }
      if (other.getProtoPayload() != com.google.protobuf.ByteString.EMPTY) {
        setProtoPayload(other.getProtoPayload());
      }
      if (other.getTimestamp() != 0L) {
        setTimestamp(other.getTimestamp());
      }
      if (!other.getSign().isEmpty()) {
        sign_ = other.sign_;
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
      kcwiki.msgtransfer.protobuf.proto.WebsocketWapper parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (kcwiki.msgtransfer.protobuf.proto.WebsocketWapper) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private int protoType_ = 0;
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoType proto_type = 1;</code>
     */
    public int getProtoTypeValue() {
      return protoType_;
    }
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoType proto_type = 1;</code>
     */
    public Builder setProtoTypeValue(int value) {
      protoType_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoType proto_type = 1;</code>
     */
    public kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoType getProtoType() {
      @SuppressWarnings("deprecation")
      kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoType result = kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoType.valueOf(protoType_);
      return result == null ? kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoType.UNRECOGNIZED : result;
    }
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoType proto_type = 1;</code>
     */
    public Builder setProtoType(kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoType value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      protoType_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoType proto_type = 1;</code>
     */
    public Builder clearProtoType() {
      
      protoType_ = 0;
      onChanged();
      return this;
    }

    private int protoCode_ = 0;
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoCode proto_code = 2;</code>
     */
    public int getProtoCodeValue() {
      return protoCode_;
    }
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoCode proto_code = 2;</code>
     */
    public Builder setProtoCodeValue(int value) {
      protoCode_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoCode proto_code = 2;</code>
     */
    public kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoCode getProtoCode() {
      @SuppressWarnings("deprecation")
      kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoCode result = kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoCode.valueOf(protoCode_);
      return result == null ? kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoCode.UNRECOGNIZED : result;
    }
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoCode proto_code = 2;</code>
     */
    public Builder setProtoCode(kcwiki.msgtransfer.protobuf.proto.WebsocketWapper.ProtoCode value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      protoCode_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <code>.kcwiki.msgtransfer.protobuf.WebsocketWapper.ProtoCode proto_code = 2;</code>
     */
    public Builder clearProtoCode() {
      
      protoCode_ = 0;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString protoPayload_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes proto_payload = 5;</code>
     */
    public com.google.protobuf.ByteString getProtoPayload() {
      return protoPayload_;
    }
    /**
     * <code>bytes proto_payload = 5;</code>
     */
    public Builder setProtoPayload(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      protoPayload_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes proto_payload = 5;</code>
     */
    public Builder clearProtoPayload() {
      
      protoPayload_ = getDefaultInstance().getProtoPayload();
      onChanged();
      return this;
    }

    private long timestamp_ ;
    /**
     * <code>int64 timestamp = 3;</code>
     */
    public long getTimestamp() {
      return timestamp_;
    }
    /**
     * <code>int64 timestamp = 3;</code>
     */
    public Builder setTimestamp(long value) {
      
      timestamp_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>int64 timestamp = 3;</code>
     */
    public Builder clearTimestamp() {
      
      timestamp_ = 0L;
      onChanged();
      return this;
    }

    private java.lang.Object sign_ = "";
    /**
     * <code>string sign = 4;</code>
     */
    public java.lang.String getSign() {
      java.lang.Object ref = sign_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        sign_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string sign = 4;</code>
     */
    public com.google.protobuf.ByteString
        getSignBytes() {
      java.lang.Object ref = sign_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        sign_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string sign = 4;</code>
     */
    public Builder setSign(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      sign_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string sign = 4;</code>
     */
    public Builder clearSign() {
      
      sign_ = getDefaultInstance().getSign();
      onChanged();
      return this;
    }
    /**
     * <code>string sign = 4;</code>
     */
    public Builder setSignBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      sign_ = value;
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


    // @@protoc_insertion_point(builder_scope:kcwiki.msgtransfer.protobuf.WebsocketWapper)
  }

  // @@protoc_insertion_point(class_scope:kcwiki.msgtransfer.protobuf.WebsocketWapper)
  private static final kcwiki.msgtransfer.protobuf.proto.WebsocketWapper DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new kcwiki.msgtransfer.protobuf.proto.WebsocketWapper();
  }

  public static kcwiki.msgtransfer.protobuf.proto.WebsocketWapper getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<WebsocketWapper>
      PARSER = new com.google.protobuf.AbstractParser<WebsocketWapper>() {
    @java.lang.Override
    public WebsocketWapper parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new WebsocketWapper(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<WebsocketWapper> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<WebsocketWapper> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public kcwiki.msgtransfer.protobuf.proto.WebsocketWapper getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

