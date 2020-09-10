package com.hyr.lean.aop.aspect;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyr.lean.aop.pojo.ApiAccessLog;
import com.hyr.lean.aop.utils.IpUtils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LogAspect {
	

	//Pointcut 后面的表达式用于控制切面的有效影响范围
	//** 表达式中，第一个表示返回任意类型,第二个标识任意方法名,后面的小括号标识任意参数值,这里是以test为前缀的,所以可以匹配上test1和test2方法
	//@Pointcut("execution(public * com.hyr.lean.aop.controller.IndexController.test*(..))")
	
	/*@Pointcut("execution(public * com.hyr.lean.aop.controller.*.*(..)) && @annotation(com.hyr.lean.aop.annotation.LogAnnotation)")
	public void addAdvice() {}
	
	@Before("addAdvice()")
	public void addAdvice(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		
		HttpServletRequest request = (HttpServletRequest)args[0];
		log.info("============打印日志开始===========");
		log.info("URL:"+request.getRequestURL().toString());
		log.info("============打印日志结束===========");
	}*/
	
	
	 /**
     * 切面controller
     */
	@Pointcut("execution(public * com.hyr.lean.aop.controller.*.*(..))")
	//@Pointcut("execution(public * com.hyr.lean.aop.controller.*.*(..)) && @annotation(com.hyr.lean.aop.annotation.LogAnnotation)")
    public void executeController() {
    }
	
	@Before("executeController()")
    public void beforeController(JoinPoint joinPoint) throws Exception {
        executeControllerBefore(joinPoint);
    }

    @AfterReturning(value = "executeController()" , returning = "rtv")
    public void afterController(JoinPoint joinPoint, Object rtv) {
        executeAfterController(joinPoint, rtv);
    }
	
   /* @Around(value = "executeController()")
    public Object around(ProceedingJoinPoint joinPoint){//通过joinPoint对象获取参数以及其他对象信息

    String MethodName = joinPoint.getSignature().getName();
    Object result = null;

    try {
        executeControllerBefore(joinPoint);
    result = joinPoint.proceed();
    MDC.put("response",JSONUtil.toJsonStr(result));
    } catch (Throwable e) {
       log.error("Method:"+MethodName+",Params:"+JSONUtil.toJsonStr(joinPoint.getArgs())+"error:"+e.getMessage());
    }

    return result;

    }*/
    
	private void executeControllerBefore(JoinPoint joinPoint) {
        try {
           /* HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.setAttribute("startTime", System.currentTimeMillis());
            request.setAttribute("requestBody", JSONUtil.toJsonStr(joinPoint.getArgs()));*/
            methodInvoke(joinPoint);
            
            RequestAttributes ra = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes sra = (ServletRequestAttributes) ra;
            assert sra != null;
            HttpServletRequest request = sra.getRequest();
            request.setAttribute("startTime", System.currentTimeMillis());
            request.setAttribute("requestBody", JSONUtil.toJsonStr(joinPoint.getArgs()));
            
            String url = request.getRequestURL().toString();
            String method = request.getMethod();
            //String queryName=getFieldsName(joinPoint,request);
            String x_request_id = request.getHeader("X-Request-Id");
            //String x_real_ip = getCliectIp(request);
	        String reuqestIp = InetAddress.getLocalHost().getHostAddress();

            //nginx返回的唯一请求Id
            MDC.put("requestId", UUID.randomUUID().toString());
            //本项目自动生成的唯一请求Id
            //MDC.put("TRACE_ID",UUID.randomUUID().toString());
            //请求的服务器的真实的IP地址
            MDC.put("requestIP",reuqestIp);
            //服务请求路径
            MDC.put("requestURI",url);
            
            //服务请求的方法，post或者get
            MDC.put("requestMethodType",method);
            //服务的请求的参数
            MDC.put("requestParam",JSONUtil.toJsonStr(joinPoint.getArgs()));
            
            
        } catch (Exception e) {
            //log.error("切面日志前置通知存在异常" + e.getMessage());
        }
    }
	
	 private void executeAfterController(JoinPoint joinPoint, Object rtv) {
	        try {
	            //executeAfterService(joinPoint, rtv);
	            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	            String url = request.getRequestURL().toString();
	            String method = request.getMethod();
	            Map<String, Object> headers = new HashMap<>();
	            Enumeration headerNames = request.getHeaderNames();
	            while (headerNames.hasMoreElements()) {
	                String key = (String) headerNames.nextElement();
	                String value = request.getHeader(key);
	                headers.put(key, value);
	            }
	            
	            long originHost = IpUtils.inet_aton(IpUtils.getIpAddress(request));
	            String contentType = request.getContentType();
	            long executeTime = System.currentTimeMillis() - (long) request.getAttribute("startTime");

	            ApiAccessLog apiAccessLog = new ApiAccessLog();
	            apiAccessLog.setRequestUrl(url);
	            apiAccessLog.setOriginHost(originHost);
	            apiAccessLog.setRequestMethod(method);
	            apiAccessLog.setRequestHeader(JSONUtil.toJsonStr(headers));
	            apiAccessLog.setRequestBody((String) request.getAttribute("requestBody"));
	            apiAccessLog.setStatusCode(response.getStatus());
	            apiAccessLog.setResponse(JSONUtil.toJsonStr(rtv));
	            //apiAccessLog.setResponse(JSON.toJSONString(rtv, SerializerFeature.WriteMapNullValue));
	            apiAccessLog.setExecuteTime(executeTime);
	            apiAccessLog.setApiRequestTime(new Date());
	            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>"+apiAccessLog);
	            
	            
	            MDC.put("requestBody",(String) request.getAttribute("requestBody"));
	            MDC.put("response",JSONUtil.toJsonStr(rtv));
	            MDC.put("returnCode",response.getStatus()+"");
	            
	            String requestId = MDC.get("requestId");
	            log.info("{requestId:{}}-");
	            MDC.remove("X_REQUEST_ID");
	            MDC.remove("TRACE_ID");
	            MDC.remove("X_REAL_IP");
	            MDC.remove("REQUEST_URI");
	            MDC.remove("REMOTE_ADDR_METHOD");
	            MDC.remove("QUERY_NAME");
	        } catch (Exception e) {
	            //log.error("切面日志后置通知异常" + e.getMessage());
	        }
	    }
	
	private void methodInvoke(JoinPoint joinPoint) {
        MethodSignature methodSignature = ((MethodSignature) joinPoint.getSignature());
        Method m = null;
        if (joinPoint.getThis().getClass() != joinPoint.getTarget().getClass()) {
            m = ReflectUtil.getMethod(joinPoint.getTarget().getClass(), methodSignature.getMethod().getName(), methodSignature.getMethod().getParameterTypes());
        }
        if (m == null) {
            m = methodSignature.getMethod();
        }
        LocalVariableTableParameterNameDiscoverer paramNames = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = paramNames.getParameterNames(m);
        Object[] args = joinPoint.getArgs();
        HashMap<String, Object> params = new HashMap<>();
        if (parameterNames != null && parameterNames.length != 0) {
            for (int i = 0; i < parameterNames.length; i++) {
                if (args[i] == null) {
                    continue;
                }
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.convertValue(args[i], args[i].getClass());
                params.put(parameterNames[i], JSONUtil.toJsonStr(objectMapper.convertValue(args[i], args[i].getClass())));
            }
        }
        log.info(" {class:{}} - [methodName:{}] - params:{}", joinPoint.getTarget().getClass().getName(), m.getName(), JSONUtil.toJsonStr(params));
        log.info(" {{}} - [{}] - params:{}", joinPoint.getTarget().getClass().getSimpleName(), m.getName(), JSONUtil.toJsonStr(params));
        //nginx返回的唯一请求Id
        //MDC.put("X_REQUEST_ID", UUID.randomUUID().toString());
        //本项目自动生成的唯一请求Id
        //MDC.put("TRACE_ID",UUID.randomUUID().toString());
        //请求的服务器的真实的IP地址
        MDC.put("classPath",joinPoint.getTarget().getClass().getName());
        
        MDC.put("methodName",m.getName());
        //服务请求的方法，post或者get
        //服务的请求的参数
        MDC.put("QUERY_NAME",JSONUtil.toJsonStr(params));
	}

	/** * 获取请求的参数 * @param joinPoint * @return */
    /*private static  String getFieldsName(JoinPoint joinPoint,HttpServletRequest request) {
        String method = request.getMethod();
        String params = "";
        Object[] args = joinPoint.getArgs();
        String queryString = request.getQueryString();
        if (args.length > 0) {
            if ("POST".equals(method)) {
                Object object = args[0];
                Map map = getKeyAndValue(object);
                params = JSON.toJSONString(map);
                ;
            } else if ("GET".equals(method)) {
                params = queryString;
            }
        }
        return params;
    }*/
	
	
	
}
