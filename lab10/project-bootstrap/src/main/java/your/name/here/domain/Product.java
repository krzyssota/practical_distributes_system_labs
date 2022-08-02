package your.name.here.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class Product {
    @JsonProperty("product_id")
    private final int productId;
    @JsonProperty("brand_id")
    private final String brandId;
    @JsonProperty("category_id")
    private final String categoryId;
    @JsonProperty("price")
    private final int price;

    public Product(@JsonProperty("product_id") int productId,
                   @JsonProperty("brand_id") String brandId,
                   @JsonProperty("category_id") String categoryId,
                   @JsonProperty("price") int price) {
        this.productId = productId;
        this.brandId = brandId;
        this.categoryId = categoryId;
        this.price = price;
    }

    @JsonProperty("product_id")
    public int productId() {
        return productId;
    }

    @JsonProperty("brand_id")
    public String brandId() {
        return brandId;
    }

    @JsonProperty("category_id")
    public String categoryId() {
        return categoryId;
    }

    @JsonProperty("price")
    public int price() {
        return price;
    }

    @java.lang.Override
    public boolean equals(java.lang.Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Product) obj;
        return this.productId == that.productId &&
                java.util.Objects.equals(this.brandId, that.brandId) &&
                java.util.Objects.equals(this.categoryId, that.categoryId) &&
                this.price == that.price;
    }

    @java.lang.Override
    public int hashCode() {
        return java.util.Objects.hash(productId, brandId, categoryId, price);
    }

    @java.lang.Override
    public String toString() {
        return "Product[" +
                "productId=" + productId + ", " +
                "brandId=" + brandId + ", " +
                "categoryId=" + categoryId + ", " +
                "price=" + price + ']';
    }

}
