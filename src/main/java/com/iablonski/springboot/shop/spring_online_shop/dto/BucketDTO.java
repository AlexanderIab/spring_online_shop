package com.iablonski.springboot.shop.spring_online_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BucketDTO {
    private Long id;
    private int quantityOfProducts;
    private double totalSumForAllProducts;
    private String address;

    private List<ProductInBucketDetailDTO> bucketDetails = new ArrayList<>();

    public void collectBucket(){
        this.quantityOfProducts = bucketDetails.size();
        this.totalSumForAllProducts = bucketDetails.stream()
                .map(ProductInBucketDetailDTO::getTotalSumForOneProduct)
                .mapToDouble(Double::doubleValue)
                .sum();
        this.address = null;
    }
}
