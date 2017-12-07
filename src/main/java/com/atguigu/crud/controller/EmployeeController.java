package com.atguigu.crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
	public Msg saveEmp(@Valid Employee employee,BindingResult result){
		if(result.hasErrors()){
			List<FieldError> errors = result.getFieldErrors();
			Map<String, Object> map=new HashMap<String, Object>();
			for (FieldError fieldError : errors) {
				System.out.println("错误的字段名:"+fieldError.getField());
				System.out.println("错误信息"+fieldError.getDefaultMessage());
				map.put(fieldError.getField(),fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorField", map);
		}
		else{
			employeeService.saveEmp(employee);
			return Msg.success();
		}
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
