package ks406362.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import ks406362.ProductInfo;

public record Product(@JsonProperty("product_id") int productId,
                      @JsonProperty("brand_id") String brandId,
                      @JsonProperty("category_id") String categoryId,
                      @JsonProperty("price") int price) {

    public ProductInfo getGeneratedProductInfoFromProduct() {
        CharSequence pid = String.valueOf(this.productId);
        CharSequence bid = String.valueOf(this.brandId);
        CharSequence cid = String.valueOf(this.categoryId);
        return new ProductInfo(pid, bid, cid, this.price);
    }

    public static Product getProductFromGeneratedProductInfo(ProductInfo pi) {
        int pid = Integer.parseInt(pi.getProductId().toString());
        String bid = pi.getProductId().toString();
        String cid = pi.getCategoryId().toString();
        return new Product(pid, bid, cid, pi.getPrice());

    }


}
