<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<jsp:include page="./header.jsp"/>

        <div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">도서 리스트</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                                                          도서 리스트
                        </div>
                        <form name="searchForm" action="/book/list">
                        	<input type="hidden" name="pageNo" value="${ pageDto.cri.pageNo }">
                        	<div class="form-inline text-center">
                        	<p></p>
	                           <div class="form-group">
	                                <select class="form-control" name="searchField">
	                                    <option value="title" ${ pageDto.cri.searchField eq 'title'?"selected":"" }>제목</option>
	                                    <option value="author" ${ pageDto.cri.searchField eq 'author'?"selected":"" }>작가</option>
	                                </select>
	                            </div>
	                            <div class="form-group">
	                                <input class="form-control" name="searchWord" value="${ pageDto.cri.searchWord }">
	                            </div>
	                            <button type="submit" class="btn btn-default" onclick="go(1)">검색</button>
                            </div>
                        </form>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
                                <thead>
                                    <tr>
                                        <th width="5%"></th>
                                        <th width="10%">책번호</th>
                                        <th width="30%">제목</th>
                                        <th width="30%">작가</th>
                                        <th width="25%">대여여부</th>
                                    </tr>
                                </thead>
                                <tbody>
                                   <c:forEach items="${ list }" var="book">
	                                    <tr>
	                                        <td><input type="checkbox" name="check" value="${ book.no }"></td>
	                                        <td>${ book.no }</td>
	                                        <td><a href="/book/view?no=${ book.no }">${ book.title }</a></td>
	                                        <td>${ book.author }</td>
	                                        <td class="center">${ book.rentStr }</td>
	                                    </tr>
                                   </c:forEach>
                                </tbody>
                            </table>
                            <jsp:include page="../common/pageNavi.jsp"/>
                            <!-- /.table-responsive -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->

        </div>
        <!-- /#page-wrapper -->
	<jsp:include page="./footer.jsp"/>