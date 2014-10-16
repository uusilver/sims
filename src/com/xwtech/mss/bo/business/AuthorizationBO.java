package com.xwtech.mss.bo.business;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xwtech.mss.pub.dao.business.MpmsAuthorizationDAO;
import com.xwtech.mss.pub.po.MpmsAuthorization;


public class AuthorizationBO {
	
	private static final Log log = LogFactory.getLog(AuthorizationBO.class);
	
	private MpmsAuthorizationDAO mpmsAuthorizationDAO;

	public void setMpmsAuthorizationDAO(MpmsAuthorizationDAO mpmsAuthorizationDAO) {
		this.mpmsAuthorizationDAO = mpmsAuthorizationDAO;
	}

	public void save(MpmsAuthorization transientInstance) {
		this.mpmsAuthorizationDAO.save(transientInstance);
	}
	
	public MpmsAuthorization findById( Long id) {
		return this.mpmsAuthorizationDAO.findById(id);
	}
	
	public MpmsAuthorization findOnlyOne() {
		return this.mpmsAuthorizationDAO.findOnlyOne();
	}
}
