package com.geekbrains.spring.market.controllers;

import com.geekbrains.spring.market.entities.Product;
import com.geekbrains.spring.market.entities.dtos.ProductDto;
import com.geekbrains.spring.market.exceptions.ResourceNotFoundException;
import com.geekbrains.spring.market.services.ProductsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/products")
public class ProductsController {
    private ProductsService productsService;

    @Autowired
    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping(produces = "application/json")
    @ApiOperation("Returns list of all products")
    public List<ProductDto> getAllProducts(){
        return productsService.getAllDtos();
    }

    @GetMapping("/{id}")
    @ApiOperation("Returns one product by id")
    public ResponseEntity<?> getOneProduct(@PathVariable Long id){
        if (productsService.existsById(id)) {
            return new ResponseEntity<>(productsService.findById(id), HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Product was not found by id - " + id);
        }
    }

    @DeleteMapping
    @ApiOperation("Removes all products")
    public String deleteAllProducts(){
        productsService.deleteAll();
        return "Successful";
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletes a product from the system. 404 if the product's identifier is not found.")
    public void deleteOneProduct(@PathVariable Long id) {
        if (!productsService.existsById(id)) {
            throw new ResourceNotFoundException("Product with id: " + id + " doesn't exists (for delete)");
        }
        productsService.deleteProductById(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new product. If id != null, then it will be cleared")
    public Product saveProduct(@RequestBody Product product){
        if(product.getId() != null) {
            product.setId(null);
        }
        return productsService.saveOrUpdate(product);
    }

    @PutMapping
    @ApiOperation("Modifies an existing product")
    public ResponseEntity<?> updateProduct(@RequestBody Product product){
        if(product.getId() == null || !productsService.existsById(product.getId())){
            throw new ResourceNotFoundException("Product was not found by id - " + product.getId());
        }
        if(product.getPrice().doubleValue() <= 0.0){
            return new ResponseEntity<>("Product`s price should be positive.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(productsService.saveOrUpdate(product), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(ResourceNotFoundException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

}
