package com.hyr.lean.aop.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hyr.lean.aop.annotation.LogAnnotation;
import com.hyr.lean.aop.pojo.Employee;
import com.hyr.lean.aop.service.EmployeeService;

@RestController
@RequestMapping("/")
public class IndexController {
    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    
    @Autowired
    private EmployeeService employeeService;
    
    @GetMapping(value = "/empName")
    public Employee getEmployeeByName(@RequestBody String empName) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        LOG.info("============打印日志开始============查询参数》》》。empName="+empName);
        LOG.info("URL: " + request.getRequestURL().toString());
        Employee emp = employeeService.getEmp(empName);
        LOG.info("============打印日志结束============返回结果emp=>>>"+emp);
        return emp;
    }
    
    
    @GetMapping(value = "/employee")
    public Employee getEmployeeByName(@RequestBody Employee employee) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        LOG.info("============打印日志开始============查询参数》》》。emp="+employee);
        LOG.info("URL: " + request.getRequestURL().toString());
        Employee emp = employeeService.getEmployee(employee);
        LOG.info("============打印日志结束============返回结果emp=>>>"+emp);
        return emp;
    }
    
    @GetMapping(value = "/empList")
    public List<Employee> findEmployeeList(@RequestBody String empName) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LOG.info("============打印日志开始============查询参数》》》empName："+empName);
        LOG.info("URL: " + request.getRequestURL().toString());
        List<Employee> empList = employeeService.findEmpList(empName);
        LOG.info("============打印日志结束============返回结果："+empList);
        return empList;
    }
    
    @GetMapping(value = "/index")
    public String index(HttpServletRequest request) {
        LOG.info("============打印日志开始============");
        LOG.info("URL: " + request.getRequestURL().toString());
        LOG.info("============打印日志结束============");
        return "hello jackie";
    }

    @GetMapping(value = "/test1")
    public String test1(HttpServletRequest request, String var1) {
        LOG.info("============打印日志开始============");
        LOG.info("URL: " + request.getRequestURL().toString());
        LOG.info("============打印日志结束============");
        return "test1";
    }

    @LogAnnotation
    @GetMapping(value = "/test2")
    public String test2(HttpServletRequest request, String var1, String var2) {
        LOG.info("============打印日志开始============");
        LOG.info("URL: " + request.getRequestURL().toString());
        LOG.info("============打印日志结束============");
//        int i = 1/0;
        if (1<2)
            throw new IllegalArgumentException("exception");
        return "test2";
    }
}
