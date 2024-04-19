package vnua.fita.bookstore.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vnua.fita.bookstore.bean.Book;
import vnua.fita.bookstore.bean.Order;
import vnua.fita.bookstore.model.OrderDAO;

/**
 * Servlet implementation class DetailOrderShipperServlet
 */
@WebServlet("/detailOrderShipper")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024
* 10, maxRequestSize = 1024 * 1024 * 20)
public class DetailOrderShipperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private OrderDAO orderDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		orderDAO = new OrderDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		String orderNo = request.getParameter("orderNo");
		if (errors.isEmpty()) {
			Order order = orderDAO.searchOrder(orderNo);
			if (order == null) {
				errors.add("Không có cuốn sách nào");
			} else {
				request.setAttribute("orderOfCustomer", order);
				RequestDispatcher rd = request.getServletContext()
						.getRequestDispatcher("/Views/detailOrderShipperView.jsp");
				rd.forward(request, response);
			}
		}
		
		if(!errors.isEmpty()) {
			request.setAttribute("errors", String.join(", ", errors));
			RequestDispatcher rd = request.getServletContext()
					.getRequestDispatcher("/shipperHome");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
