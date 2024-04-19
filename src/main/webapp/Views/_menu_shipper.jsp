<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <div style="padding: 5px; text-align: center;">
        <a href="${pageContext.request.contextPath}/shipperHome" style="text-decoration:none;color:blue;">Các đơn hàng</a>
        |
        <a href="${pageContext.request.contextPath }/shipperOrderList/delivered">Đơn hàng đã giao</a>
        |
        <a href="${pageContext.request.contextPath }/shipperOrderList/reject">Đơn hàng khách trả lại</a>
    </div>	