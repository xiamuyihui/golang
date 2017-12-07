package com.kingthy.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.kingthy.service.ShippingMethodService;

/**
 * 物流模块相关操作
 * 
 * @author pany
 *
 */
@Service("shippingMethodService")
public class ShippingMethodServiceImpl implements ShippingMethodService {

	@Override
	public Object find(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> findList(long... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Object> findPage(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean exists(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object save(Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object update(Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object update(Object entity, String... ignoreProperties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(long... ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Object entity) {
		// TODO Auto-generated method stub

	}

}
