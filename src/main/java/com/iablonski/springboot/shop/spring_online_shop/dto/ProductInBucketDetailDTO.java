package com.iablonski.springboot.shop.spring_online_shop.dto;

import com.iablonski.springboot.shop.spring_online_shop.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductInBucketDetailDTO {
    private String productTitle;
    private Long productId;
    private double priceOfProduct;
    private double quantityOfOneProduct;
    private double totalSumForOneProduct;

    public ProductInBucketDetailDTO(Product product) {
        this.productTitle = product.getTitle();
        this.productId = product.getId();
        this.priceOfProduct = product.getPrice();
        this.quantityOfOneProduct = 1.0;
        this.totalSumForOneProduct = product.getPrice();
    }
}
