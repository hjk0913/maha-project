package com.maha.common.service;

import javax.annotation.Resource;

import com.maha.common.dao.DAO;
import com.maha.common.util.BeanManager;

public class CommonService implements Service {


	@Resource(name = "BeanManager")
	private BeanManager beanManager;

	private DAO dao;

	public void DAOBeanSetting() {
		Class cls = this.getClass();

		DAOBean daoBean = (DAOBean) cls.getAnnotation(DAOBean.class);

		String value = daoBean.value();

		setDao((DAO) beanManager.getBean(value));
	}

	public DAO getDao() {

		if (dao == null) {
			DAOBeanSetting();
		}

		return dao;
	}

	public void setDao(DAO dao) {
		this.dao = dao;
	}

}
