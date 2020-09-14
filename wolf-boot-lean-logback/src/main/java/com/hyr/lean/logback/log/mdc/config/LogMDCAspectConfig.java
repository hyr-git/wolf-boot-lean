package com.hyr.lean.logback.log.mdc.config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.core.util.ReflectUtil;

@Component
@Configuration
@Aspect
public class LogMDCAspectConfig {


    @Pointcut("execution(public * com.hyr.lean.logback.controller..*.*(..))")
    public void webLog(){}

    @Before("webLog()")
    public void before(JoinPoint joinPoint){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String queryName=getFieldsName(joinPoint,request);
        String x_request_id = request.getHeader("X-Request-Id");
        String x_real_ip = getCliectIp(request);
        
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class<?> clazz = joinPoint.getTarget().getClass();
        Method methodTarget = null;
        if(joinPoint.getThis().getClass() != clazz) {
        	methodTarget = ReflectUtil.getMethod(clazz, methodSignature.getMethod().getName(), methodSignature.getMethod().getParameterTypes());
        }
        if (methodTarget == null) {
        	methodTarget = methodSignature.getMethod();
        }
        
        String className = clazz.getName();

        
        LocalVariableTableParameterNameDiscoverer paramNames = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = paramNames.getParameterNames(methodTarget);
        Object[] args = joinPoint.getArgs();
        HashMap<String, Object> params = new HashMap<>();
        if (parameterNames != null && parameterNames.length != 0) {
            for (int i = 0; i < parameterNames.length; i++) {
                if (args[i] == null) {
                    continue;
                }
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.convertValue(args[i], args[i].getClass());
                params.put(parameterNames[i], JSON.toJSON(objectMapper.convertValue(args[i], args[i].getClass())));
            }
        }
      //服务的请求的参数
        MDC.put("REQUEST_PARAM",JSON.toJSONString(params));
        //nginx返回的唯一请求Id
        MDC.put("X_REQUEST_ID", x_request_id);
        //本项目自动生成的唯一请求Id
        MDC.put("REQUEST_ID",UUID.randomUUID().toString());
        //请求的服务器的真实的IP地址
        MDC.put("X_REAL_IP",x_real_ip);
        //服务请求路径
        MDC.put("REQUEST_URI",url);
      //服务请求路径
        MDC.put("CLASS_NAME",className);
        //方法名称
        MDC.put("REQUEST_METHOD",methodTarget.getName());
        //服务请求的方法，post或者get
        MDC.put("REMOTE_ADDR_METHOD",method);
        //服务的请求的参数
        MDC.put("REQUEST_PAREMAS",JSON.toJSONString(joinPoint.getArgs()));
        //服务的请求的参数
        MDC.put("QUERY_NAME",queryName);
    }

    /** * 对于涉及到ThreadLocal相关使用的接口，都需要去考虑在使用完上下文对象时， * 清除掉对应的数据，以避免内存泄露问题 * @param ret */
    @AfterReturning(pointcut = "webLog()", returning = "ret")
    public void afterReturning(Object ret){
        MDC.remove("X_REQUEST_ID");
        MDC.remove("TRACE_ID");
        MDC.remove("X_REAL_IP");
        MDC.remove("REQUEST_URI");
        MDC.remove("REMOTE_ADDR_METHOD");
        MDC.remove("QUERY_NAME");
    }

    /** * 获取客户段的IP * @param request * @return */
    private static String getCliectIp(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.trim() == "" || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 多个路由时，取第一个非unknown的ip
        final String[] arr = ip.split(",");
        for (final String str : arr) {
            if (!"unknown".equalsIgnoreCase(str)) {
                ip = str;
                break;
            }
        }
        return ip;
    }

    /** * 获取请求的参数 * @param joinPoint * @return */
    private static  String getFieldsName(JoinPoint joinPoint,HttpServletRequest request) {
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
    }

    /** * 获取post对象的参数参数值 * @param obj * @return */
    private static Map<String,Object> getKeyAndValue(Object obj){
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = (Class)obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置属性是可以访问的
            Object val = new Object();
            try {
                val = f.get(obj);
                // 得到此属性的值
                map.put(f.getName(), val);// 设置键值
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
