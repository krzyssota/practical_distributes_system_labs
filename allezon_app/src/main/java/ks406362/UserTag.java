/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package ks406362;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class UserTag extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 7608214942461050948L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"UserTag\",\"namespace\":\"ks406362.generated\",\"fields\":[{\"name\":\"time\",\"type\":\"string\"},{\"name\":\"country\",\"type\":\"string\"},{\"name\":\"device\",\"type\":{\"type\":\"enum\",\"name\":\"device_enum\",\"symbols\":[\"PC\",\"MOBILE\",\"TV\"]}},{\"name\":\"origin\",\"type\":\"string\"},{\"name\":\"product_info\",\"type\":{\"type\":\"record\",\"name\":\"ProductInfo\",\"fields\":[{\"name\":\"product_id\",\"type\":\"string\"},{\"name\":\"brand_id\",\"type\":\"string\"},{\"name\":\"category_id\",\"type\":\"string\"},{\"name\":\"price\",\"type\":\"int\"}]}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<UserTag> ENCODER =
      new BinaryMessageEncoder<UserTag>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<UserTag> DECODER =
      new BinaryMessageDecoder<UserTag>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<UserTag> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<UserTag> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<UserTag> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<UserTag>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this UserTag to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a UserTag from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a UserTag instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static UserTag fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public CharSequence time;
  @Deprecated public CharSequence country;
  @Deprecated public device_enum device;
  @Deprecated public CharSequence origin;
  @Deprecated public ProductInfo product_info;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public UserTag() {}

  /**
   * All-args constructor.
   * @param time The new value for time
   * @param country The new value for country
   * @param device The new value for device
   * @param origin The new value for origin
   * @param product_info The new value for product_info
   */
  public UserTag(CharSequence time, CharSequence country, device_enum device, CharSequence origin, ProductInfo product_info) {
    this.time = time;
    this.country = country;
    this.device = device;
    this.origin = origin;
    this.product_info = product_info;
  }

  public SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public Object get(int field$) {
    switch (field$) {
    case 0: return time;
    case 1: return country;
    case 2: return device;
    case 3: return origin;
    case 4: return product_info;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, Object value$) {
    switch (field$) {
    case 0: time = (CharSequence)value$; break;
    case 1: country = (CharSequence)value$; break;
    case 2: device = (device_enum)value$; break;
    case 3: origin = (CharSequence)value$; break;
    case 4: product_info = (ProductInfo)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'time' field.
   * @return The value of the 'time' field.
   */
  public CharSequence getTime() {
    return time;
  }


  /**
   * Sets the value of the 'time' field.
   * @param value the value to set.
   */
  public void setTime(CharSequence value) {
    this.time = value;
  }

  /**
   * Gets the value of the 'country' field.
   * @return The value of the 'country' field.
   */
  public CharSequence getCountry() {
    return country;
  }


  /**
   * Sets the value of the 'country' field.
   * @param value the value to set.
   */
  public void setCountry(CharSequence value) {
    this.country = value;
  }

  /**
   * Gets the value of the 'device' field.
   * @return The value of the 'device' field.
   */
  public device_enum getDevice() {
    return device;
  }


  /**
   * Sets the value of the 'device' field.
   * @param value the value to set.
   */
  public void setDevice(device_enum value) {
    this.device = value;
  }

  /**
   * Gets the value of the 'origin' field.
   * @return The value of the 'origin' field.
   */
  public CharSequence getOrigin() {
    return origin;
  }


  /**
   * Sets the value of the 'origin' field.
   * @param value the value to set.
   */
  public void setOrigin(CharSequence value) {
    this.origin = value;
  }

  /**
   * Gets the value of the 'product_info' field.
   * @return The value of the 'product_info' field.
   */
  public ProductInfo getProductInfo() {
    return product_info;
  }


  /**
   * Sets the value of the 'product_info' field.
   * @param value the value to set.
   */
  public void setProductInfo(ProductInfo value) {
    this.product_info = value;
  }

  /**
   * Creates a new UserTag RecordBuilder.
   * @return A new UserTag RecordBuilder
   */
  public static UserTag.Builder newBuilder() {
    return new UserTag.Builder();
  }

  /**
   * Creates a new UserTag RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new UserTag RecordBuilder
   */
  public static UserTag.Builder newBuilder(UserTag.Builder other) {
    if (other == null) {
      return new UserTag.Builder();
    } else {
      return new UserTag.Builder(other);
    }
  }

  /**
   * Creates a new UserTag RecordBuilder by copying an existing UserTag instance.
   * @param other The existing instance to copy.
   * @return A new UserTag RecordBuilder
   */
  public static UserTag.Builder newBuilder(UserTag other) {
    if (other == null) {
      return new UserTag.Builder();
    } else {
      return new UserTag.Builder(other);
    }
  }

  /**
   * RecordBuilder for UserTag instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<UserTag>
    implements org.apache.avro.data.RecordBuilder<UserTag> {

    private CharSequence time;
    private CharSequence country;
    private device_enum device;
    private CharSequence origin;
    private ProductInfo product_info;
    private ProductInfo.Builder product_infoBuilder;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(UserTag.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.time)) {
        this.time = data().deepCopy(fields()[0].schema(), other.time);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.country)) {
        this.country = data().deepCopy(fields()[1].schema(), other.country);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.device)) {
        this.device = data().deepCopy(fields()[2].schema(), other.device);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.origin)) {
        this.origin = data().deepCopy(fields()[3].schema(), other.origin);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.product_info)) {
        this.product_info = data().deepCopy(fields()[4].schema(), other.product_info);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
      if (other.hasProductInfoBuilder()) {
        this.product_infoBuilder = ProductInfo.newBuilder(other.getProductInfoBuilder());
      }
    }

    /**
     * Creates a Builder by copying an existing UserTag instance
     * @param other The existing instance to copy.
     */
    private Builder(UserTag other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.time)) {
        this.time = data().deepCopy(fields()[0].schema(), other.time);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.country)) {
        this.country = data().deepCopy(fields()[1].schema(), other.country);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.device)) {
        this.device = data().deepCopy(fields()[2].schema(), other.device);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.origin)) {
        this.origin = data().deepCopy(fields()[3].schema(), other.origin);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.product_info)) {
        this.product_info = data().deepCopy(fields()[4].schema(), other.product_info);
        fieldSetFlags()[4] = true;
      }
      this.product_infoBuilder = null;
    }

    /**
      * Gets the value of the 'time' field.
      * @return The value.
      */
    public CharSequence getTime() {
      return time;
    }


    /**
      * Sets the value of the 'time' field.
      * @param value The value of 'time'.
      * @return This builder.
      */
    public UserTag.Builder setTime(CharSequence value) {
      validate(fields()[0], value);
      this.time = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'time' field has been set.
      * @return True if the 'time' field has been set, false otherwise.
      */
    public boolean hasTime() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'time' field.
      * @return This builder.
      */
    public UserTag.Builder clearTime() {
      time = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'country' field.
      * @return The value.
      */
    public CharSequence getCountry() {
      return country;
    }


    /**
      * Sets the value of the 'country' field.
      * @param value The value of 'country'.
      * @return This builder.
      */
    public UserTag.Builder setCountry(CharSequence value) {
      validate(fields()[1], value);
      this.country = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'country' field has been set.
      * @return True if the 'country' field has been set, false otherwise.
      */
    public boolean hasCountry() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'country' field.
      * @return This builder.
      */
    public UserTag.Builder clearCountry() {
      country = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'device' field.
      * @return The value.
      */
    public device_enum getDevice() {
      return device;
    }


    /**
      * Sets the value of the 'device' field.
      * @param value The value of 'device'.
      * @return This builder.
      */
    public UserTag.Builder setDevice(device_enum value) {
      validate(fields()[2], value);
      this.device = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'device' field has been set.
      * @return True if the 'device' field has been set, false otherwise.
      */
    public boolean hasDevice() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'device' field.
      * @return This builder.
      */
    public UserTag.Builder clearDevice() {
      device = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'origin' field.
      * @return The value.
      */
    public CharSequence getOrigin() {
      return origin;
    }


    /**
      * Sets the value of the 'origin' field.
      * @param value The value of 'origin'.
      * @return This builder.
      */
    public UserTag.Builder setOrigin(CharSequence value) {
      validate(fields()[3], value);
      this.origin = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'origin' field has been set.
      * @return True if the 'origin' field has been set, false otherwise.
      */
    public boolean hasOrigin() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'origin' field.
      * @return This builder.
      */
    public UserTag.Builder clearOrigin() {
      origin = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'product_info' field.
      * @return The value.
      */
    public ProductInfo getProductInfo() {
      return product_info;
    }


    /**
      * Sets the value of the 'product_info' field.
      * @param value The value of 'product_info'.
      * @return This builder.
      */
    public UserTag.Builder setProductInfo(ProductInfo value) {
      validate(fields()[4], value);
      this.product_infoBuilder = null;
      this.product_info = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'product_info' field has been set.
      * @return True if the 'product_info' field has been set, false otherwise.
      */
    public boolean hasProductInfo() {
      return fieldSetFlags()[4];
    }

    /**
     * Gets the Builder instance for the 'product_info' field and creates one if it doesn't exist yet.
     * @return This builder.
     */
    public ProductInfo.Builder getProductInfoBuilder() {
      if (product_infoBuilder == null) {
        if (hasProductInfo()) {
          setProductInfoBuilder(ProductInfo.newBuilder(product_info));
        } else {
          setProductInfoBuilder(ProductInfo.newBuilder());
        }
      }
      return product_infoBuilder;
    }

    /**
     * Sets the Builder instance for the 'product_info' field
     * @param value The builder instance that must be set.
     * @return This builder.
     */
    public UserTag.Builder setProductInfoBuilder(ProductInfo.Builder value) {
      clearProductInfo();
      product_infoBuilder = value;
      return this;
    }

    /**
     * Checks whether the 'product_info' field has an active Builder instance
     * @return True if the 'product_info' field has an active Builder instance
     */
    public boolean hasProductInfoBuilder() {
      return product_infoBuilder != null;
    }

    /**
      * Clears the value of the 'product_info' field.
      * @return This builder.
      */
    public UserTag.Builder clearProductInfo() {
      product_info = null;
      product_infoBuilder = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public UserTag build() {
      try {
        UserTag record = new UserTag();
        record.time = fieldSetFlags()[0] ? this.time : (CharSequence) defaultValue(fields()[0]);
        record.country = fieldSetFlags()[1] ? this.country : (CharSequence) defaultValue(fields()[1]);
        record.device = fieldSetFlags()[2] ? this.device : (device_enum) defaultValue(fields()[2]);
        record.origin = fieldSetFlags()[3] ? this.origin : (CharSequence) defaultValue(fields()[3]);
        if (product_infoBuilder != null) {
          try {
            record.product_info = this.product_infoBuilder.build();
          } catch (org.apache.avro.AvroMissingFieldException e) {
            e.addParentField(record.getSchema().getField("product_info"));
            throw e;
          }
        } else {
          record.product_info = fieldSetFlags()[4] ? this.product_info : (ProductInfo) defaultValue(fields()[4]);
        }
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<UserTag>
    WRITER$ = (org.apache.avro.io.DatumWriter<UserTag>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<UserTag>
    READER$ = (org.apache.avro.io.DatumReader<UserTag>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeString(this.time);

    out.writeString(this.country);

    out.writeEnum(this.device.ordinal());

    out.writeString(this.origin);

    this.product_info.customEncode(out);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.time = in.readString(this.time instanceof Utf8 ? (Utf8)this.time : null);

      this.country = in.readString(this.country instanceof Utf8 ? (Utf8)this.country : null);

      this.device = device_enum.values()[in.readEnum()];

      this.origin = in.readString(this.origin instanceof Utf8 ? (Utf8)this.origin : null);

      if (this.product_info == null) {
        this.product_info = new ProductInfo();
      }
      this.product_info.customDecode(in);

    } else {
      for (int i = 0; i < 5; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.time = in.readString(this.time instanceof Utf8 ? (Utf8)this.time : null);
          break;

        case 1:
          this.country = in.readString(this.country instanceof Utf8 ? (Utf8)this.country : null);
          break;

        case 2:
          this.device = device_enum.values()[in.readEnum()];
          break;

        case 3:
          this.origin = in.readString(this.origin instanceof Utf8 ? (Utf8)this.origin : null);
          break;

        case 4:
          if (this.product_info == null) {
            this.product_info = new ProductInfo();
          }
          this.product_info.customDecode(in);
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










