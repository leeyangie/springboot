package com.kh.house.product.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kh.house.product.model.service.ProductService;
import com.kh.house.product.model.vo.Attachment;
import com.kh.house.product.model.vo.Product;
import com.kh.house.resposnse.model.vo.ResponseData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProductController {
	
	private final ProductService productService;
	
	@PostMapping
	public ResponseEntity<ResponseData> save(@RequestParam("img") MultipartFile img,
											 @RequestParam("product")String product) throws JsonMappingException, JsonProcessingException {
		
//		log.info("product 테이블에 INSERT할 내용 : {}", product);
//		log.info("file 테이블에 INSERT 할 내용 : {}", img);
		
		Product house = new ObjectMapper().readValue(product, Product.class);
		house.setCreateDate(LocalDateTime.now());
		
		if(img.getOriginalFilename().equals("")) {
			
			String changeName = saveFile(img);
			String originName = img.getOriginalFilename();
			
			Attachment at = Attachment.builder()
									  .originName(originName)
									  .changeName(changeName)
									  .build();
			
			int result = productService.save(house, at);
			
			ResponseData rd = ResponseData.builder()
										  .message("성공")
										  .responseData(result)
										  .build();
			return ResponseEntity.status(HttpStatus.OK).body(rd);
			
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	}
	
	
	public String saveFile(MultipartFile upfile) {
		
		String originName = upfile.getOriginalFilename();
		String ext = originName.substring(originName.lastIndexOf("."));
		String currentTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		int ranNum = (int)Math.random() * 90000 + 10000;
		String changeName = "KH_" + currentTime + "_" + ranNum + ext;
		
		String absolutePath = new File("").getAbsolutePath() + "\\";
		String savePath = "images/";
		File file = new File(savePath);
		
		if(!file.exists()) {
			file.mkdirs();
		}
		
		try {
			upfile.transferTo(new File(absolutePath + savePath + "/" + changeName));
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		
		return savePath + changeName;
	}
	
	@GetMapping("/{type}")
	public ResponseEntity<ResponseData> findAll(@PathVariable("type") String type) {
		List<Product> homes = productService.findAll(type);
		ResponseData rd = ResponseData.builder()
									  .message(type + "조회 성공!")
									  .responseData(homes)
									  .build();
		return ResponseEntity.ok(rd);
	}
	
	@GetMapping("/images/{filename}")
	public Resource showImage(@PathVariable("filename") String fileName) throws MalformedURLException {
			return new UrlResource("file:images/" + fileName);
	}
	
	
	
	
	
	/*
	@PostMapping
	public ResponseEntity<ResponseData> save (@RequestBody String data) throws JsonMappingException, JsonProcessingException {
		
		
		Product product = new ObjectMapper().readValue(data, Product.class);
		product.setCreateDate(LocalDateTime.now());
		
		log.info("VO형태로 가공 : {}", product);
		
		Product saveObj = productService.save(product);
		log.info("반환받은 product : {} ", saveObj);
		
		return null;
	}
	
	@GetMapping
	public ResponseEntity<ResponseData> findAll() {
		
		List<Product> products = productService.findAll();
		ResponseData rd = ResponseData.builder()
									  .responseData(products)
									  .build(); 
		
		return ResponseEntity.ok(rd);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseData> findById(@PathVariable("id") int id) {
		Optional<Product> product = productService.findById(id);
		
		Product searched = product.get();
		
		ResponseData rd = ResponseData.builder()
									  .responseData(product)
									  .build();
		return ResponseEntity.ok(rd);
	}
	*/

}
