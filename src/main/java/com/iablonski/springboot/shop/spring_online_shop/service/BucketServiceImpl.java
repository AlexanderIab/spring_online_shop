package com.iablonski.springboot.shop.spring_online_shop.service;

import com.iablonski.springboot.shop.spring_online_shop.exception_handling.exception.UserNotFoundException;
import com.iablonski.springboot.shop.spring_online_shop.repository.BucketRepository;
import com.iablonski.springboot.shop.spring_online_shop.repository.ProductRepository;
import com.iablonski.springboot.shop.spring_online_shop.entity.*;
import com.iablonski.springboot.shop.spring_online_shop.dto.BucketDTO;
import com.iablonski.springboot.shop.spring_online_shop.dto.ProductInBucketDetailDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService {
    private final BucketRepository bucketRepository;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final OrderService orderService;

    @Autowired
    public BucketServiceImpl(BucketRepository bucketRepository,
                             ProductRepository productRepository,
                             UserService userService,
                             OrderService orderService) {
        this.bucketRepository = bucketRepository;
        this.productRepository = productRepository;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public Bucket createBucket(User user, List<Long> productListById) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> products = getProductReferenceByIdList(productListById);
        bucket.setProducts(products);
        return bucketRepository.save(bucket);
    }

    @Override
    public void addProductsToBucket(Bucket bucket, List<Long> productListById) {
        List<Product> products = bucket.getProducts();
        List<Product> updatedProducts = products == null ? new ArrayList<>() : new ArrayList<>(products);
        updatedProducts.addAll(getProductReferenceByIdList(productListById));
        bucket.setProducts(updatedProducts);
        bucketRepository.save(bucket);
    }

    @Override
    public BucketDTO getBucketByUser(String name) {
        User user = userService.findUserByName(name);
        if (user == null) throw new UserNotFoundException("User not found");
        if (user.getBucket() == null) return new BucketDTO();
        BucketDTO bucketDTO = new BucketDTO();
        Map<Long, ProductInBucketDetailDTO> mapByProductId = new HashMap<>();
        List<Product> products = user.getBucket().getProducts();
        for (Product product : products) {
            ProductInBucketDetailDTO detail = mapByProductId.get(product.getId());
            if (detail == null) mapByProductId.put(product.getId(), new ProductInBucketDetailDTO(product));
            else {
                detail.setQuantityOfOneProduct(detail.getQuantityOfOneProduct() + 1.0);
                detail.setTotalSumForOneProduct(detail.getTotalSumForOneProduct() + product.getPrice());
            }
        }
        bucketDTO.setBucketDetails(new ArrayList<>(mapByProductId.values()));
        bucketDTO.collectBucket();
        return bucketDTO;
    }

    @Override
    public void deleteProductFromBucket(String name, Long productId) {
        User user = userService.findUserByName(name);
        if (user == null) throw new UserNotFoundException("User not found");
        List<Product> products = user.getBucket().getProducts();
        products.removeIf(product -> Objects.equals(product.getId(), productId));
        Bucket bucket = user.getBucket();
        bucket.setProducts(products);
        userService.save(user);
    }

    @Override
    public void getOrderFromBucket(String name, String address) {
        User user = userService.findUserByName(name);
        if (user == null) throw new UserNotFoundException("User not found");
        Bucket bucket = user.getBucket();
        if (bucket == null || bucket.getProducts().isEmpty()) return;
        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setUser(user);
        Map<Product, Long> groupingProductMap = bucket.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));
        List<OrderDetails> orderDetails = groupingProductMap.entrySet().stream()
                .map(kv -> new OrderDetails(order, kv.getKey(), kv.getValue())).toList();

        double totalSum = orderDetails.stream()
                .map(detail -> detail.getPrice() * detail.getQuantity())
                .mapToDouble(Double::doubleValue).sum();

        order.setDetails(orderDetails);
        order.setSum(totalSum);
        order.setAddress(address);

        orderService.saveOrder(order);
        bucket.getProducts().clear();
        bucketRepository.save(bucket);
    }

    private List<Product> getProductReferenceByIdList(List<Long> productIdList) {
        return productIdList.stream().map(productRepository::getReferenceById).toList();
    }
}