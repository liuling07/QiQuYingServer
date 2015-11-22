package com.lling.qiqu.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lling.qiqu.dao.ICollectionDAO;
import com.lling.qiqu.dao.base.BaseDAO;

@Transactional
@Repository("collectionDAO")
public class CollectionDAOImpl extends BaseDAO implements ICollectionDAO{
	
}
