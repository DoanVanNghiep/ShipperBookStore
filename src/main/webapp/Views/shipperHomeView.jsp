
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "vnua.fita.bookstore.util.Constant" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Trang chủ phía shipper</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/bookstore_style.css">
<script
src="https://cdn.ckeditor.com/ckeditor5/41.1.0/classic/ckeditor.js"></script>
</head>
<body>
	<jsp:include page="_header_shipper.jsp"></jsp:include>
	<jsp:include page="_menu_shipper.jsp"></jsp:include>
	<p style="color: red;">${errors}</p>
	<p style="color: green;">${message}</p>
		<div align="center" id="shipperHomeResult">
		<h3>DANH SÁCH ĐƠN HÀNG</h3>
		<form action="" id="adminOrderForm" method="post">
			<input type="hidden" name="orderId" id="orderIdOfAction" /> <input
				type="hidden" name="confirmType" id="confirmTypeOfAction" />
		</form>
		<table border="1">
			<tr>
				<th>Mã hóa đơn</th>
				<th>Tên khách</th>
				<th>Số điện thoại</th>
				<th>Ngày đặt mua</th>
				<th>Ngày xác nhận</th>
				<th>Địa chỉ nhận sách</th>
				<th>Phương thức thanh toán</th>
				<th>Trạng thái đơn hàng</th>
				<th>Thao tác</th>
			</tr>
			<c:forEach items="${orderListOfCustomer}" var="orderOfCustomer">
					<tr>
						<td>${orderOfCustomer.orderNo}</td>
						<td>${orderOfCustomer.customer.fullname}</td>
						<td>${orderOfCustomer.customer.mobile}</td>
						<td><fmt:formatDate value="${orderOfCustomer.orderDate}" pattern="dd-MM-yyyy HH:mm"/></td>
						<td><fmt:formatDate value="${orderOfCustomer.orderApproveDate}" pattern="dd-MM-yyyy HH:mm"/></td>
						<td>${orderOfCustomer.deliveryAddress}</td>
						<td>${orderOfCustomer.paymentModeDescription}</td>
						<td>${orderOfCustomer.orderStatusDescription}
							<c:if test="${Constant.WAITING_CONFIRM_ORDER_STATUS != orderOfCustomer.orderStatus}">
								&nbsp;-&nbsp;${orderOfCustomer.paymentStatusDescription}
							</c:if>
						</td>
						<td>
						<a style="text-decoration: none" href="detailOrderShipper?orderNo=${orderOfCustomer.orderNo}">xem chi tiết</a>
						</td>
					</tr>
				</c:forEach>	
			</table>
		</div>
</body>
</html>
