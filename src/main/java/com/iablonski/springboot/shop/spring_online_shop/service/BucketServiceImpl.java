package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.dao.BucketRepository;
import com.iablonski.springboot.shop.spring_online_shop.dao.ProductRepository;
import com.iablonski.springboot.shop.spring_online_shop.domain.Bucket;
import com.iablonski.springboot.shop.spring_online_shop.domain.Product;
import com.iablonski.springboot.shop.spring_online_shop.domain.User;
import com.iablonski.springboot.shop.spring_online_shop.dto.BucketDTO;
import com.iablonski.springboot.shop.spring_online_shop.dto.BucketDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    public Bucket createBucket(User user, List<Long> productIdList) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> products = getProductReferenceListByIds(productIdList);
        bucket.setProducts(products);
        return bucketRepository.save(bucket);
    }

    @Override
    @Transactional
    public void addProducts(Bucket bucket, List<Long> productIdList) {
        List<Product> products = bucket.getProducts();
        List<Product> updatedProducts = products == null ? new ArrayList<>(): new ArrayList<>(products);
        updatedProducts.addAll(getProductReferenceListByIds(productIdList));
        bucket.setProducts(updatedProducts);
        bucketRepository.save(bucket);
    }

    @Override
    // amounts of products if we double-clicked
    public BucketDTO getBucketByUser(String name) {
        User user = userService.findByName(name);
        if(user == null || user.getBucket() == null) return new BucketDTO();
        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, BucketDetailDTO> longBucketDetailDTOMap = new HashMap<>();
        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            BucketDetailDTO detail = longBucketDetailDTOMap.get(product.getId());
            if(detail == null) longBucketDetailDTOMap.put(product.getId(), new BucketDetailDTO(product));
            else {
                detail.setAmount(detail.getAmount().add(new BigDecimal(1.0)));
                detail.setSum(detail.getSum() + Double.valueOf(product.getPrice().toString()));
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(longBucketDetailDTOMap.values()));
        bucketDTO.aggregate();

        return bucketDTO;
    }

    private List<Product> getProductReferenceListByIds(List<Long> productIdList){
        return productIdList.stream().map(productRepository::getReferenceById).toList();
    }
}
