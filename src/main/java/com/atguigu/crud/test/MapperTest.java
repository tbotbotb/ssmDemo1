package com.atguigu.crud.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.crud.dao.DepartmentMapper;


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
	@Test
	public void testCRUD(){
		/*
		//1.创建SpringIOC容器
		ApplicationContext ioc=new ClassPathXmlApplicationContext("");
		//2.从容器中获取mapper
		DepartmentMapper bean=ioc.getBean(DepartmentMapper.class);
		*/
		System.out.println(departmentMapper);
	}
}
