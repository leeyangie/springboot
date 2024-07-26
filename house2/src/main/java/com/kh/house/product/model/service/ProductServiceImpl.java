package com.kh.house.product.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.house.product.model.dao.ProductMapper;
import com.kh.house.product.model.vo.Attachment;
import com.kh.house.product.model.vo.Product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	
	private ProductMapper productMapper;
	
	@Transactional
	@Override
	public int save(Product house, Attachment at) {
		try {
			saveHouse(house);
			at.setRefHouseNo(house.getHouseNo());
			saveFile(at);
			
			return 1;
		} catch(Exception e) {
			return 0;
		}
		
		
		
	}
	
	public void saveHouse (Product house) {
		productMapper.saveHouse(house);
	}
	
	public void saveFile(Attachment at) {
		productMapper.saveFile(at);
	}

	@Override
	public List<Product> findAll(String type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*
	 
	private final ProductRepository productRepository;
	
	@Override
	public Product save(Product product) {
		return productRepository.save(product); 
	}

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Optional<Product> findById(int id) {
		return productRepository.findById(id);
	}
	*/

}
