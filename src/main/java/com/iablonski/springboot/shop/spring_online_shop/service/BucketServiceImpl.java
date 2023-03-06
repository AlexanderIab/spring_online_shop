package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.dao.BucketRepository;
import com.iablonski.springboot.shop.spring_online_shop.dao.ProductRepository;
import com.iablonski.springboot.shop.spring_online_shop.domain.Bucket;
import com.iablonski.springboot.shop.spring_online_shop.domain.Product;
import com.iablonski.springboot.shop.spring_online_shop.domain.User;
import com.iablonski.springboot.shop.spring_online_shop.dto.BucketDTO;
import com.iablonski.springboot.shop.spring_online_shop.dto.ProductInBucketDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BucketServiceImpl implements BucketService{
    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Autowired
    public BucketServiceImpl(BucketRepository bucketRepository, ProductRepository productRepository, UserService userService) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }



    @Override
    @Transactional
    public Bucket createBucket(User user, List<Long> productListById) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> products = getProductReferenceByIdList(productListById);
        bucket.setProducts(products);
        return bucketRepository.save(bucket);
    }

    @Override
    @Transactional
    public void addProducts(Bucket bucket, List<Long> productListById) {
        List<Product> products = bucket.getProducts();
        List<Product> updatedProducts = products == null ? new ArrayList<>(): new ArrayList<>(products);
        updatedProducts.addAll(getProductReferenceByIdList(productListById));
        bucket.setProducts(updatedProducts);
        bucketRepository.save(bucket);
    }

    @Override
    // amounts of products if we double-clicked
    public BucketDTO getBucketByUser(String name) {
        User user = userService.findByName(name);
        if(user == null || user.getBucket() == null) return new BucketDTO();
        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, ProductInBucketDetailDTO> mapByProductId = new HashMap<>();
        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            ProductInBucketDetailDTO detail = mapByProductId.get(product.getId());
            if(detail == null) mapByProductId.put(product.getId(), new ProductInBucketDetailDTO(product));
            else {
                detail.setQuantityOfOneProduct(detail.getQuantityOfOneProduct() + 1.0);
                detail.setTotalSumForOneProduct(detail.getTotalSumForOneProduct() + product.getPrice());
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.aggregate();

        return bucketDTO;
    }

    private List<Product> getProductReferenceByIdList(List<Long> productIdList){
        return productIdList.stream().map(productRepository::getReferenceById).toList();
    }
}
