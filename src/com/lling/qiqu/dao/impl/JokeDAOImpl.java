package com.lling.qiqu.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lling.qiqu.beans.Joke;
import com.lling.qiqu.commons.Limit;
import com.lling.qiqu.commons.PageResult;
import com.lling.qiqu.dao.IJokeDAO;
import com.lling.qiqu.dao.base.BaseDAO;
import com.lling.qiqu.utils.Util;

/**
 * @ClassName: JokeDAOImpl
 * @Description: 
 * @author lling
 * @date 2015-5-30
 */
@Transactional
@Repository("jokeDAO")
public class JokeDAOImpl extends BaseDAO implements IJokeDAO{

	@Override
	public Integer addJoke(Joke joke) {
		return (Integer)super.save(joke);
	}

	@Override
	public PageResult<Joke> getJokes(Limit limit, int type, int newOrHotFlag) {
		if (newOrHotFlag == Joke.SORT_HOT) {
			return super
					.findPageByQuery(
							"from Joke where type = ? and isPass = ? and isDelete = ? order by supportsNum desc",
							limit, type, Joke.PASS, Joke.NOT_DELETE);
		} else {
			return super
					.findPageByQuery(
							"from Joke where type = ? and isPass = ? and isDelete = ? order by createDate desc",
							limit, type, Joke.PASS, Joke.NOT_DELETE);
		}
	}
	
	@Override
	public PageResult<Joke> getJokes(Limit limit, int newOrHotFlag) {
		if (newOrHotFlag == Joke.SORT_HOT) {
			return super
					.findPageByQuery(
							"from Joke where isPass = ? and isDelete = ? and isJingXuan = ? order by supportsNum desc",
							limit, Joke.PASS, Joke.NOT_DELETE, Joke.JINGXUAN);
		} else {
			return super
					.findPageByQuery(
							"from Joke where isPass = ? and isDelete = ? and isJingXuan = ? order by createDate desc",
							limit, Joke.PASS, Joke.NOT_DELETE, Joke.JINGXUAN);
		}
	}

	@Override
	public List<Joke> getJokesByIds(String ids) {
		return super.findBySQL("select * from t_joke where id in ("+ids+")", Joke.class);
	}

	@Override
	public void updateJokes(List<Joke> jokes) {
		for (Joke joke : jokes) {
			super.update(joke);
		}
	}

	@Override
	public Joke getById(int id) {
		return super.getUniqueResult("from Joke where id=?", id);
	}

	@Override
	public void updateJoke(Joke joke) {
		super.update(joke);
	}

	@Override
	public Joke getNextById(Integer id) {
//		List<Joke> jokes = super.executeSQLQuery("select * from t_joke where id>? order by id asc limit 1", id);
//		return Util.isNotEmpty(jokes)?jokes.get(0):null;
		List<Joke> jokes = super.findFirst("from Joke where id>? order by id asc", id);
		return Util.isNotEmpty(jokes)?jokes.get(0):null;
//		return (Joke)super.find("from Joke where id>? order by id asc", id).get(0);
	}

	@Override
	public Joke getLastById(Integer id) {
//		List<Joke> jokes = super.executeSQLQuery("select * from t_joke where id<? order by id desc limit 1", id);
//		return Util.isNotEmpty(jokes)?jokes.get(0):null;
		List<Joke> jokes = super.findFirst("from Joke where id<? order by id desc", id);
		return Util.isNotEmpty(jokes)?jokes.get(0):null;
		//return (Joke)super.find("from Joke where id<? order by id desc", id).get(0);
	}

}
