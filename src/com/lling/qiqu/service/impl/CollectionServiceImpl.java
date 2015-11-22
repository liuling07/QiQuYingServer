package com.lling.qiqu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lling.qiqu.dao.ICollectionDAO;
import com.lling.qiqu.service.ICollectionService;

@Service("collectionService")
public class CollectionServiceImpl implements ICollectionService {
	
	private ICollectionDAO collectionDAO;
	
	@Resource
	public void setCollectionDAO(ICollectionDAO collectionDAO) {
		this.collectionDAO = collectionDAO;
	}
	
	
}
