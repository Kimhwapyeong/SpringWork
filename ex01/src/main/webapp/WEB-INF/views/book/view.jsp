<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<jsp:include page="./header.jsp"/>
	
	<!-- 상세보기 -->
	<div id="page-wrapper">
	<div class="row">
	   <div class="col-lg-4">
	       <div class="panel panel-primary">
	           <div class="panel-heading">
	               ${ book.title }
	           </div>
	           <div class="panel-body">
	               <p>하하호호</p>
	           </div>
	           <div class="panel-footer">
	               ${ book.author }
	           </div>
	       </div>
	   </div>
	</div>
	</div>

<jsp:include page="./footer.jsp"/>