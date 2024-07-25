package com.kh.house.product.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.house.product.model.vo.Attachment;
import com.kh.house.product.model.vo.Product;

@Mapper
public interface ProductMapper {

	void saveFile(Attachment at);

	void saveHouse(Product house);

}
