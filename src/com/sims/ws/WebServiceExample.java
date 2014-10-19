package com.sims.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name="simsWSExample",targetNamespace="www.sims.com")
public class WebServiceExample {

	@WebMethod
	public String sayHello(@WebParam(name="name") String name){
		return "Hello "+name;
	}
}
