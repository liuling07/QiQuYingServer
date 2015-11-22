package com.lling.qiqu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lling.qiqu.dao.ICommentDAO;
import com.lling.qiqu.service.ICommentService;

@Service("commentServie")
public class CommentServieImpl implements ICommentService {

	private ICommentDAO commentDAO;
	
	@Resource
	public void setCommentDAO(ICommentDAO commentDAO) {
		this.commentDAO = commentDAO;
	}
}
