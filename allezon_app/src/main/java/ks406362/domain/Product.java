package ks406362.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import ks406362.generated.ProductInfo;

import java.util.Objects;

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

    public ProductInfo getGeneratedProductInfoFromProduct() {
        CharSequence bid = String.valueOf(this.brandId);
        CharSequence cid = String.valueOf(this.categoryId);
        return new ProductInfo(this.productId, bid, cid, this.price);
    }

    public static Product getProductFromGeneratedProductInfo(ProductInfo pi) {
        String bid = pi.getBrandId().toString();
        String cid = pi.getCategoryId().toString();
        return new Product(pi.getProductId(), bid, cid, pi.getPrice());

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Product) obj;
        return this.productId == that.productId &&
                Objects.equals(this.brandId, that.brandId) &&
                Objects.equals(this.categoryId, that.categoryId) &&
                this.price == that.price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, brandId, categoryId, price);
    }

    @Override
    public String toString() {
        return "Product[" +
                "productId=" + productId + ", " +
                "brandId=" + brandId + ", " +
                "categoryId=" + categoryId + ", " +
                "price=" + price + ']';
    }


}
