/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package ks406362;

@org.apache.avro.specific.AvroGenerated
public enum device_enum implements org.apache.avro.generic.GenericEnumSymbol<device_enum> {
  PC, MOBILE, TV  ;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"device_enum\",\"namespace\":\"com.rtbhouse.nosqllab\",\"symbols\":[\"PC\",\"MOBILE\",\"TV\"]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
}