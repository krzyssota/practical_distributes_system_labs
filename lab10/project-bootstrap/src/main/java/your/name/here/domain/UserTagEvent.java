package your.name.here.domain;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonProperty;


//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
// We have to use @JsonProperty annotations instead the above due to this bug:
// https://github.com/FasterXML/jackson-databind/issues/3102
public final class UserTagEvent {
    private final Instant time;
    private final String cookie;
    private final String country;
    private final Device device;
    private final Action action;
    private final String origin;
    @JsonProperty("product_info")
    private final Product productInfo;

    public UserTagEvent(Instant time, String cookie, String country, Device device, Action action,
                        String origin, @JsonProperty("product_info") Product productInfo) {
        this.time = time;
        this.cookie = cookie;
        this.country = country;
        this.device = device;
        this.action = action;
        this.origin = origin;
        this.productInfo = productInfo;
    }

    public Instant time() {
        return time;
    }

    public String cookie() {
        return cookie;
    }

    public String country() {
        return country;
    }

    public Device device() {
        return device;
    }

    public Action action() {
        return action;
    }

    public String origin() {
        return origin;
    }

    @JsonProperty("product_info")
    public Product productInfo() {
        return productInfo;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (UserTagEvent) obj;
        return java.util.Objects.equals(this.time, that.time) &&
                java.util.Objects.equals(this.cookie, that.cookie) &&
                java.util.Objects.equals(this.country, that.country) &&
                java.util.Objects.equals(this.device, that.device) &&
                java.util.Objects.equals(this.action, that.action) &&
                java.util.Objects.equals(this.origin, that.origin) &&
                java.util.Objects.equals(this.productInfo, that.productInfo);
    }

    @java.lang.Override
    public int hashCode() {
        return java.util.Objects.hash(time, cookie, country, device, action, origin, productInfo);
    }

    @java.lang.Override
    public String toString() {
        return "UserTagEvent[" +
                "time=" + time + ", " +
                "cookie=" + cookie + ", " +
                "country=" + country + ", " +
                "device=" + device + ", " +
                "action=" + action + ", " +
                "origin=" + origin + ", " +
                "productInfo=" + productInfo + ']';
    }


}
