package ks406362.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonProperty;
import ks406362.UserTag;


//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
// We have to use @JsonProperty annotations instead the above due to this bug:
// https://github.com/FasterXML/jackson-databind/issues/3102
public record UserTagEvent(Instant time, String cookie, String country, Device device, Action action,
                           String origin, @JsonProperty("product_info") Product product) {

    public UserTagEvent(String cookie, Action action, UserTag tag) {
        this(LocalDateTime.parse(tag.getTime(), DateTimeFormatter.ISO_ZONED_DATE_TIME).toInstant(ZoneOffset.UTC),
                cookie,
                tag.getCountry().toString(),
                Device.getDeviceFromGeneratedDeviceEnum(tag.getDevice()),
                action,
                tag.getOrigin().toString(),
                Product.getProductFromGeneratedProductInfo(tag.getProductInfo()));
    }

}