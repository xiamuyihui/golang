package com.kingthy.conf;

import com.alibaba.fastjson.JSONObject;
import com.kingthy.response.WebApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 全局统一异常处理<br>
 * 将跑向controller的异常以json格式进行输出
 * 
 * @author rico
 *
 */
@Component
public class ExceptionManager extends ExceptionHandlerExceptionResolver
{

	Logger log = LoggerFactory.getLogger(ExceptionManager.class);

	private final static String REGEX = "(?<=\\()(.+?)(?=\\))";

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response,
			HandlerMethod handlerMethod, Exception exception) {
		try {
			WebApiResponse code = new WebApiResponse();
			if (exception.getMessage() != null && !"".equals(exception.getMessage())) {

				Pattern pattern = Pattern.compile(REGEX);
				Matcher matcher = pattern.matcher(exception.getMessage());
				while (matcher.find()) {
					String[] str = matcher.group().split("=");
					for (int i = 0; i < str.length; i++) {
						if ("msg".equals(str[i])) {
							code.setMessage(str[i + 1]);
						} else if ("code".equals(str[i])) {
							code.setMessage(str[i + 1]);
						}
					}
				}
			} else {
				code.setMessage("系统内部发生为空异常");
			}
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().write(JSONObject.toJSONString(code));
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
}
