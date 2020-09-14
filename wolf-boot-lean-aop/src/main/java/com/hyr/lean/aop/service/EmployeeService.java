package com.hyr.lean.aop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hyr.lean.aop.pojo.Employee;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {

	private static List<Employee> employeeList = new ArrayList<Employee>();
	static {
		employeeList.add(new Employee("1","zhangsang","23232@qq.com"));
		employeeList.add(new Employee("2","lisang","1223232@qq.com"));
		employeeList.add(new Employee("3","wangsan","22223232@qq.com"));
		employeeList.add(new Employee("4","zhujun","2333333232@qq.com"));
		employeeList.add(new Employee("5","wuchang","666623232@qq.com"));
		employeeList.add(new Employee("6","weisang","777723232@qq.com"));
	}
	
	public Employee getEmp(String empName) {
		employeeList = null;
		log.info("empName:"+empName);
		return employeeList.stream().filter(s->s.getName().equals(empName)).findFirst().orElse(null);
	}
	
	public Employee getEmployee(Employee employee) {
		employeeList = null;
		log.info("emp----->:"+employee);
		return employeeList.stream().filter(s->s.getName().equals(employee.getName())).findFirst().orElse(null);
	}
	
	public List<Employee> findEmpList(String empName) {
		if(null == empName) {
			return employeeList;
		}
		return employeeList.stream().filter(s->s.getName().equals(empName)).collect(Collectors.toList());
	}
}
