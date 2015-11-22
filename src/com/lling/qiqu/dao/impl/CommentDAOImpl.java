package com.lling.qiqu.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lling.qiqu.beans.Comment;
import com.lling.qiqu.commons.Limit;
import com.lling.qiqu.commons.PageResult;
import com.lling.qiqu.dao.ICommentDAO;
import com.lling.qiqu.dao.base.BaseDAO;

@Transactional
@Repository("commentDAO")
public class CommentDAOImpl extends BaseDAO implements ICommentDAO{

	@Override
	public PageResult<Comment> getCommentsByJokeId(Limit limit, int jokeId) {
		return super
				.findPageByQuery(
						"from Comment where jokeId = ? order by createDate desc",
						limit, jokeId);
	}
	
	@Override
	public List<Comment> getCommentsByJokeId(int jokeId) {
		return super.find("from Comment where jokeId = ? order by createDate desc", jokeId);
	}

	@Override
	public Integer addComment(Comment comment) {
		return (Integer)super.save(comment);
	}

}
