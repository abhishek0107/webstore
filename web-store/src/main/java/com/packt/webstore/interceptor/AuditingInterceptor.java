package com.packt.webstore.interceptor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuditingInterceptor extends HandlerInterceptorAdapter{

	Logger logger=Logger.getLogger("auditLogger");
	private String user;
	private String productId;
	public boolean preHandle(HttpServletRequest request, HttpServletResponse resp, Object handler) throws Exception{
		if(request.getRequestURI().endsWith("products/add") && request.getMethod().equals("POST")){
			user=request.getRemoteUser();
			productId=request.getParameterValues("productId")[0];
		}
		return true;
	}
	public void afterCompletion(HttpServletRequest request, HttpServletResponse resp, Object handler, Exception exception) throws Exception{
		
		if(request.getRequestURI().endsWith("products/add") && resp.getStatus()==302){
			logger.info(String.format("A new Product [%s] added by %son %s", productId,user,getCurrentTime()));
		}
	}
	private String getCurrentTime(){
		DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm:ss");
		Calendar calendar=Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		return formatter.format(calendar.getTime());
	}
}
