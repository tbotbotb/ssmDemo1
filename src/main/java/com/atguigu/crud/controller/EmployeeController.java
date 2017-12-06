package com.atguigu.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.bean.Msg;
import com.atguigu.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 处理员工crud请求
 * @author 40362
 *
 */
@Controller
public class EmployeeController {
	@Autowired
	EmployeeService employeeService;
	/**
	 * 查询员工数据(分页查询)
	 * @return
	 */
	//@RequestMapping("/emps")
	public String getEmps(@RequestParam(value="pn",defaultValue="1") Integer pn,
			Model model){
		//引入PageHelper
		PageHelper.startPage(pn,5);
		//startPage()方法后面紧跟查询
		List<Employee> emps=employeeService.getAll();
		//封装为PageInfo,连续显示5页
		PageInfo<Employee> page=new PageInfo<Employee>(emps,5);
		model.addAttribute("pageInfo",page);
		return "list";
	}
	
	@ResponseBody
	@RequestMapping("/emps")
	public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1") Integer pn,
			Model model){
			//引入PageHelper
			PageHelper.startPage(pn,5);
			//startPage()方法后面紧跟查询
			List<Employee> emps=employeeService.getAll();
			//封装为PageInfo,连续显示5页
			PageInfo<Employee> page=new PageInfo<Employee>(emps,5);
			model.addAttribute("pageInfo",page);
			return Msg.success().add("pageInfo",page);
	}
	
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(Employee employee){
		employeeService.saveEmp(employee);
		return Msg.success();
	}
	
	/**
	 * 检查用户名是否可用
	 * @param empName
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="checkuser",method=RequestMethod.POST)
	public Msg checkuser(@RequestParam("empName")String empName){
		boolean result= employeeService.checkuser(empName);
		if(result){
			return Msg.success();
		}
		else{
			return Msg.fail();
		}
	}
}
