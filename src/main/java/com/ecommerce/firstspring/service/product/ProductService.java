package com.ecommerce.firstspring.service.product;

import com.ecommerce.firstspring.payload.ProductDTO;
import com.ecommerce.firstspring.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
  ProductDTO addProduct(Long categoryId, ProductDTO product);

  ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

  ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

  ProductResponse searchProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

  ProductDTO updateProduct(Long productId, ProductDTO product);

  ProductDTO deleteProduct(Long productId);

  ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
