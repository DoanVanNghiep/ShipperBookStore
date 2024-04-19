package vnua.fita.bookstore.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vnua.fita.bookstore.bean.Order;
import vnua.fita.bookstore.model.OrderDAO;
import vnua.fita.bookstore.util.Constant;

/**
 * Servlet implementation class shipperHomeServlet
 */
@WebServlet(urlPatterns = {"/shipperHome", "/delivering"})
public class ShipperHomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private OrderDAO orderDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
//		bookDAO = new BookDAO("jdbc:mysql://localhost:3306/bookstore", "root", "123456");
		orderDAO = new OrderDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String keyword = request.getParameter("keyword");
		List<Order> orders = orderDAO.searchOrders(keyword);
		request.setAttribute("orderListOfCustomer", orders);
		RequestDispatcher rd;
		if(keyword != null) {
			rd = this.getServletContext()
					.getRequestDispatcher("/Views/searchShipperArea.jsp");
		}else {
			rd = this.getServletContext()
					.getRequestDispatcher("/Views/shipperHomeView.jsp");
		}
		rd.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		String orderIdStr = request.getParameter("orderId");
		String confirmTypeStr = request.getParameter("confirmType");
		int orderId = -1;
		try {
			orderId = Integer.parseInt(orderIdStr);
		}catch (NumberFormatException e) {
			errors.add("Mã hóa đơn không hợp lệ");
		}
		byte confirmType = -1;
		try {
			confirmType = Byte.parseByte(confirmTypeStr);
		}catch (NumberFormatException e) {
			errors.add("Giá trị không hợp lệ");
		}
		if(errors.isEmpty()) {
			boolean updateResult = false;
			if(Constant.DELIVERING_ORDER_STATUS == confirmType) {
				//xac nhan va chuyen trang thai dang giao hang
				updateResult = orderDAO.updateOrderNo(orderId, confirmType);
			}else if(Constant.DELIVERED_ORDER_STATUS == confirmType) {
				updateResult = orderDAO.updateOrder(orderId, confirmType);
			}else if(Constant.REJECT_ORDER_STATUS == confirmType) {
				updateResult = orderDAO.updateOrder(orderId, confirmType);
			}
			if(updateResult) {
				request.setAttribute("message", "Update thành công");
			}else {
				errors.add("Update thất bại");
			}
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", String.join(", ", errors));
		}
		doGet(request, response);
	}

}
