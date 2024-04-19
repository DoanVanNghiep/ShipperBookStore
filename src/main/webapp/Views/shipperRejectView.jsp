<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="vnua.fita.bookstore.util.Constant"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bookstore_style.css">
<script
	src="https://cdn.ckeditor.com/ckeditor5/41.1.0/classic/ckeditor.js"></script>
<title>Trang người giao hàng</title>
</head>
<body>
	<jsp:include page="_header_shipper.jsp"></jsp:include>
	<div align="center">
		<table border="1">
			<tr>
				<td>Mã hóa đơn</td>
				<td>${orderOfCustomer.orderNo}</td>
			</tr>
			<tr>
				<td>Tên khách hàng</td>
				<td>${orderOfCustomer.customer.username}</td>
			</tr>
			<tr>
				<td>Số điện thoại</td>
				<td>${orderOfCustomer.customer.mobile}</td>
			</tr>
			<tr>
				<td>Mã hóa đơn</td>
				<td>${orderOfCustomer.deliveryAddress}</td>
			</tr>
			<tr>
				<td>Thông tin</td>
				<td>
				<div id="div${orderOfCustomer.orderId}">
					<h3>Các cuốn sách trong hóa đơn</h3>
					<table border="1">
						<tr style="background-color: yellow;">
							<th>Tiêu đề</th>
							<th>Tác giả</th>
							<th>Giá tiền</th>
							<th>Số lượng mua</th>
							<th>Tổng thành phần</th>
						</tr>
						<c:forEach items="${orderOfCustomer.orderBookList }"
							var="cartItem">
							<tr>
								<td>${cartItem.selectedBook.title}</td>
								<td>${cartItem.selectedBook.author}</td>
								<td><fmt:formatNumber type="number"
										maxFractionDigits="0"
										value="${cartItem.selectedBook.price}" /> <sup>đ</sup></td>
								<td>${cartItem.quantity}</td>
								<td><fmt:formatNumber type="number"
										maxFractionDigits="0"
										value="${cartItem.selectedBook.price * cartItem.quantity}" />
									<sup>đ</sup></td>
							</tr>
						</c:forEach>
					</table>
					<br> Tổng số tiền: <b> <span> <fmt:formatNumber
								type="number" maxFractionDigits="0"
								value="${orderOfCustomer.totalCost}" /> <sup>đ</sup>
					</span>
					</b>
				</div>
				</td>
			</tr>
			<tr>
			<td>Ảnh minh chứng</td>
			<td>
			<img alt="" src="" id="bookImage" width="150">&nbsp;		
			<form action="${pageContext.request.contextPath}/rejectShipper" method="post" enctype="multipart/form-data">
				<input type="hidden" name="orderId" value="${orderId}"> <input
					type="hidden" name="orderNo" value="${orderNo}"> <input
					type="file" name="file" accept="image/*" onchange="loadImage(event)" />
				<br> <input type="submit" value="Xác nhận">
			</form>
			</td>
			</tr>
		</table>
	</div>
</body>
</html>