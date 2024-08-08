package com.ecommerce.firstspring.service.product;

import com.ecommerce.firstspring.payload.ProductDTO;
import com.ecommerce.firstspring.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {
  ProductDTO addProduct(Long categoryId, ProductDTO product);

  ProductResponse getAllProducts();

  ProductResponse getProductsByCategory(Long categoryId);

  ProductResponse searchProductsByKeyword(String keyword);

  ProductDTO updateProduct(Long productId, ProductDTO product);

  ProductDTO deleteProduct(Long productId);

  ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
