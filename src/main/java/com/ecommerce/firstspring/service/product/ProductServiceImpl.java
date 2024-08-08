package com.ecommerce.firstspring.service.product;

import com.ecommerce.firstspring.exceptions.ApiException;
import com.ecommerce.firstspring.exceptions.ResourceNotFoundException;
import com.ecommerce.firstspring.model.Category;
import com.ecommerce.firstspring.model.Product;
import com.ecommerce.firstspring.payload.ProductDTO;
import com.ecommerce.firstspring.payload.ProductResponse;
import com.ecommerce.firstspring.repository.CategoryRepository;
import com.ecommerce.firstspring.repository.ProductRepository;
import com.ecommerce.firstspring.service.FIle.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
  @Autowired
  ProductRepository productRepository;

  @Autowired
  CategoryRepository categoryRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  FileService fileService;

  @Value("${project.image}")
  private String path;

  boolean isProductNotPresent;

  @Override
  public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
    Category existingCategory = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

    isProductNotPresent = true;
    for (Product product : existingCategory.getProducts()) {
      if (product.getProductName().equals(productDTO.getProductName())) {
        isProductNotPresent = false;
        break;
      }
    }
    if (isProductNotPresent) {
      Product product = modelMapper.map(productDTO, Product.class);
      product.setCategory(existingCategory);
      product.setImage("default.png");
      double specialPrice = product.getPrice() - (product.getPrice() * product.getDiscount() / 100);
      product.setSpecialPrice(specialPrice);
      Product savedProduct = productRepository.save(product);
      return modelMapper.map(savedProduct, ProductDTO.class);
    } else {
      throw new ApiException("Product already exists");
    }
  }

  @Override
  public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
    Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    Page<Product> productPage = productRepository.findAll(pageable);
    List<Product> products = productPage.getContent();
    if (products.isEmpty()) {
      throw new ApiException("No products found");
    }
    List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
    ProductResponse productResponse = new ProductResponse();
    productResponse.setData(productDTOS);
    productResponse.setPageNumber(productPage.getNumber());
    productResponse.setPageSize(productPage.getSize());
    productResponse.setTotalElements(productPage.getTotalElements());
    productResponse.setTotalPages(productPage.getTotalPages());
    productResponse.setLastPage(productPage.isLast());
    return productResponse;
  }

  @Override
  public ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
    Category existingCategory = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

    Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
    Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    Page<Product> productPage = productRepository.findByCategoryOrderByPriceAsc(existingCategory, pageDetails);
    List<Product> products = productPage.getContent();
    if (products.isEmpty()) {
      throw new ApiException("No products found with category: " + existingCategory.getCategoryName());
    }
    List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
    ProductResponse productResponse = new ProductResponse();
    productResponse.setData(productDTOS);
    productResponse.setPageNumber(productPage.getNumber());
    productResponse.setPageSize(productPage.getSize());
    productResponse.setTotalElements(productPage.getTotalElements());
    productResponse.setTotalPages(productPage.getTotalPages());
    productResponse.setLastPage(productPage.isLast());
    return productResponse;
  }

  @Override
  public ProductResponse searchProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

    Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
    Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    Page<Product> productPage = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%', pageDetails);
    List<Product> products = productPage.getContent();

    if (products.isEmpty()) {
      throw new ApiException("No products found with keyword: " + keyword);
    }
    List<ProductDTO> productDTOS = products.stream().map(product -> modelMapper.map(product, ProductDTO.class)).toList();
    ProductResponse productResponse = new ProductResponse();
    productResponse.setData(productDTOS);
    productResponse.setPageNumber(productPage.getNumber());
    productResponse.setPageSize(productPage.getSize());
    productResponse.setTotalElements(productPage.getTotalElements());
    productResponse.setTotalPages(productPage.getTotalPages());
    productResponse.setLastPage(productPage.isLast());
    return productResponse;
  }

  @Override
  public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
    Product product = modelMapper.map(productDTO, Product.class);
    Product existingProduct = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

    existingProduct.setProductId(productId);
    existingProduct.setProductName(product.getProductName());
    existingProduct.setDescription(product.getDescription());
    existingProduct.setQuantity(product.getQuantity());
    existingProduct.setDiscount(product.getDiscount());
    existingProduct.setPrice(product.getPrice());
    existingProduct.setCategory(product.getCategory());
    double specialPrice = product.getPrice() - (product.getPrice() * product.getDiscount() / 100);
    existingProduct.setSpecialPrice(specialPrice);

    Product updatedProduct = productRepository.save(existingProduct);
    return modelMapper.map(updatedProduct, ProductDTO.class);
  }

  @Override
  public ProductDTO deleteProduct(Long productId) {
    Product existingProduct = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
    productRepository.delete(existingProduct);
    return modelMapper.map(existingProduct, ProductDTO.class);
  }

  @Override
  public ProductDTO updateProductImage(Long productId, MultipartFile file) throws IOException {
    Product existingProduct = productRepository.findById(productId)
            .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
    String fileName = fileService.uploadFile(file, path);
    existingProduct.setImage(fileName);
    Product updatedProduct = productRepository.save(existingProduct);
    return modelMapper.map(updatedProduct, ProductDTO.class);
  }
}
