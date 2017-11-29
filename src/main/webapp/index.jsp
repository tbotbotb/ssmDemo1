<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
 -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<!-- pageContext.getContextPath(),"http://localhost:3306/crud" -->
<%
pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!-- 引入jquery -->
<script type="text/javascript" src="${APP_PATH}/static/jquery-1.11.3/jquery.min.js"></script>
<!-- 引入bootstrap -->
<link href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button class="btn btn-primary">新增</button>
				<button class="btn btn-danger">删除</button>
			</div>
		</div>
		<!-- 显示表格 -->
		<div class="row">
			<div class="col-md-12">
				<table id="emps_table" class="table table-hover">
					<thead>
						<tr>
							<th>#</th>
							<th>empName</th>
							<th>gender</th>
							<th>emil</th>
							<th>deptName</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pageInfo.list }" var="emp">
							<tr>
								<th>${emp.empId }</th>
								<th>${emp.empName }</th>
								<th>${emp.gender=="M"?"男":"女" }
								<th>${emp.email }</th>
								<th>${emp.department.deptName }</th>
								<th>
									<button class="btn btn-primary btn-sm"><span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>编辑</button>
									<button class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span>删除</button>
								</th>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<!-- 显示分页 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6" id="page_info_area">
			</div>
			<!-- 分页条信息 -->
			<div class="col-md-6" id="page_nav_area">
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//页面加载完成后发送ajax请求,获取分页数据
		$(function() {
			//跳转到首页
			to_page(1);
		});
		function build_emps_table(result) {
			var emps=result.extend.pageInfo.list;
			$.each(emps,function(index,item){
				var empIdTd=$("<td></td>").append(item.empId);
				var empNameTd=$("<td></td>").append(item.empName);
				var empEmailTd=$("<td></td>").append(item.email);
				var genderTd=$("<td></td>").append(item.gender=="M"?"男":"女");
				var deptNameTd=$("<td></td>").append(item.department.deptName);
				var editBtn=$("<button></button>").addClass("btn btn-primary btn-sm")
				.append($("<span></span>").addClass("glyphicon glyphicon-pencil").append("编辑"));
				var delBtn=$("<button></button>").addClass("btn btn-danger btn-sm")
				.append($("<span></span>").addClass("glyphicon glyphicon-trash").append("删除"));
				$("<tr></tr>").append(empIdTd)
				.append(empNameTd)
				.append(genderTd)
				.append(empEmailTd)
				.append(deptNameTd)
				.append($("<td></td>").append(editBtn).append(" ").append(delBtn))
				.appendTo("#emps_table tbody");
			})
		}
		//解析显示分页信息
		function build_page_info(result) {
			var pageInfo=result.extend.pageInfo;
			$("#page_info_area").append("当前记录数"+pageInfo.pageNum+",总"+pageInfo.pages+",总"+pageInfo.total+"条记录");
		}
		//解析显示分页条
		function build_page_nav(result) {
			var ul=$("<ul></ul>").addClass("pagination");
			var firstPageLi=$("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
			firstPageLi.click(function(){
				to_page(1);
			});
			var lastPageLi=$("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
			lastPageLi.click(function(){
				to_page(result.extend.pageInfo.pages);
			});
			var prePageLi=$("<li></li>").append($("<a></a>").append("&laquo;").attr("href","#"));
			prePageLi.click(function(){
				to_page(result.extend.pageInfo.pageNum-1);
			});
			var nextPageLi=$("<li></li>").append($("<a></a>").append("&raquo;").attr("href","#"));
			nextPageLi.click(function(){
				to_page(result.extend.pageInfo.pageNum+1);
			});
			if(result.extend.pageInfo.isFirstPage){
				firstPageLi.addClass("disabled");
				prePageLi.addClass("disabled");
			}
			if(result.extend.pageInfo.isLastPage){
				nextPageLi.addClass("disabled");
				lastPageLi.addClass("disabled");
			}
			ul.append(firstPageLi).append(prePageLi);
			$.each(result.extend.pageInfo.navigatepageNums,function(index,item){
				var numLi=$("<li></li>").append($("<a></a>").append(item).attr("href","#"));
				if(result.extend.pageInfo.pageNum==item){
					numLi.addClass("active");
				}
				numLi.click(function() {
					to_page(item);
				})
				ul.append(numLi);
			})
			ul.append(nextPageLi).append(lastPageLi);
			var navEle=$("<nav></nav>").append(ul).attr("aria-label","Page navigation");
			navEle.appendTo("#page_nav_area");
		}
		
		function to_page(pn) {
			//先清空表格
			$("#emps_table tbody").empty();
			$("#page_info_area").empty();
			$("#page_nav_area").empty();
			$.ajax({
				url:"${APP_PATH}/emps",
				data:"pn="+pn,
				type:"get",
				success:function(result){
					build_emps_table(result);
					build_page_info(result);
					build_page_nav(result);
				}
			});
		}
	</script>
</body>
</html>