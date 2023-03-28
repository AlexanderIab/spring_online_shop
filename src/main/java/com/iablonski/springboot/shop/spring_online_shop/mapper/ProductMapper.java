package com.iablonski.springboot.shop.spring_online_shop.mapper;

import com.iablonski.springboot.shop.spring_online_shop.entity.Product;
import com.iablonski.springboot.shop.spring_online_shop.dto.ProductDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProductMapper {
    //"Entry point" to the instance after we generate the implementation.
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDTO productDTO);
    @InheritInverseConfiguration
    ProductDTO toDTO(Product product);

    List<Product> toProductList(List<ProductDTO> productDTOS);
    List<ProductDTO> toDTOList(List<Product> products);


}
