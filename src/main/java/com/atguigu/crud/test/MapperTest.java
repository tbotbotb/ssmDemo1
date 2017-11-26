package com.atguigu.crud.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.crud.bean.Employee;
import com.atguigu.crud.dao.DepartmentMapper;
import com.atguigu.crud.dao.EmployeeMapper;


/**
 * 测试dao层,增删改查
 * @author 40362
 *1.导入SpringTest模块
 *2.@ContextConfiguration指定Spring配置文件位置
 *3.直接autowired需要测试的模块即可
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MapperTest {
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;
	@Test
	public void testCRUD(){
		/*
		//1.创建SpringIOC容器
		ApplicationContext ioc=new ClassPathXmlApplicationContext("applicationContext.xml");
		//2.从容器中获取mapper
		DepartmentMapper bean=ioc.getBean(DepartmentMapper.class);
		*/
		System.out.println(departmentMapper);
		//1.插入几个部门
//		departmentMapper.insertSelective(new Department(null,"开发部"));
//		departmentMapper.insertSelective(new Department(null,"测试部"));
		//2.插入员工数据
//		employeeMapper.insertSelective(new Employee(null, "Jerry", "M", "Jerry@atguigu.com", 1));
		//3.批量插入多个员工;使用sqlSession
//		EmployeeMapper mapper=sqlSession.getMapper(EmployeeMapper.class);
//		for (int i = 0; i < 1000; i++) {
//			String uid= UUID.randomUUID().toString().substring(0, 5)+i;
//			mapper.insertSelective(new Employee(null, uid, "M", uid+"@atguigu.com", 1));
//		}
//		System.out.println("批量完成");
	}
}
