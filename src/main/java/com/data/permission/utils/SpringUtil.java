
package com.data.permission.utils;

import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * ApplicationContext
 * 普通java程序可以从此处获取bean
 * @author yangjunda1
 */
@Component
public class SpringUtil implements ApplicationContextAware {
	
	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		context = ctx;
	}
	
	public static ApplicationContext getContext() {
		return context;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name){
		if(context != null) {
			return (T)context.getBean(name);
		}
		return null;
	}
	
	public static <T> T getBean(Class<T> clz){
		if(context != null) {
			return context.getBean(clz);
		}
		return null;
	}

	public static String getProperty(String name) {
		return context.getEnvironment().getProperty(name);
	}

	/**
	 * 将指定的被spring代理的对象反代理解析成真实的用户定义的对象
	 * @param proxyObj
	 * @param <T>
	 * @return
	 * @throws Exception
	 */
	public static <T> T unproxy(Object proxyObj) throws Exception {
		if(AopUtils.isAopProxy(proxyObj) && proxyObj instanceof Advised) {
			Object target = ((Advised)proxyObj).getTargetSource().getTarget();
			if (AopUtils.isAopProxy(target) && target instanceof Advised) {
				return unproxy(target);
			}
			else {
				return (T)target;
			}
		}
		return (T) proxyObj;
	}

	/**
	 * Try to resolve the message. Treat as an error if the message can't be found.
	 * @param code the code to lookup up, such as 'calculator.noRateSet'
	 * @param locale the Locale in which to do the lookup
	 * @return
	 */
	public static String getMessage(String code, Locale locale) {
		if (context != null) {
			return context.getMessage(code, null, locale);
		}
		return null;
	}

	/**
	 * @param language An ISO 639 alpha-2 or alpha-3 language code, or a language subtag
	 * up to 8 characters in length.  See the <code>Locale</code> class description about
	 * valid language values.
	 * @return
	 */
	public static String getMessage(String code, String language) {
		return getMessage(code, new Locale(language));
	}
}
