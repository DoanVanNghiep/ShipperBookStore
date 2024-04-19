package vnua.fita.bookstore.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.mysql.cj.xdevapi.UpdateResult;

import vnua.fita.bookstore.bean.CartItem;
import vnua.fita.bookstore.bean.Order;
import vnua.fita.bookstore.bean.User;
import vnua.fita.bookstore.model.OrderDAO;
import vnua.fita.bookstore.util.Constant;
import vnua.fita.bookstore.util.MyUtil;

/**
 * Servlet implementation class shipperRejectServlet
 */
@WebServlet("/deliveredShipper")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024
* 10, maxRequestSize = 1024 * 1024 * 20)
public class ShipperDeilveredServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private OrderDAO orderDAO;

	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		orderDAO = new OrderDAO(jdbcURL, jdbcUsername, jdbcPassword);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<String> errors = new ArrayList<String>();
		String orderIdStr = request.getParameter("orderId");
		String orderNo = request.getParameter("orderNo");
		int orderId = -1;
		try {
			orderId = Integer.parseInt(orderIdStr);
		} catch (Exception e) {
			// TODO: handle exception
			errors.add("Id không hợp lệ");
		}
		Boolean updateResult = false;
		Part filePart = request.getPart("file");
		String fileName = UUID.randomUUID().toString() + "_" + MyUtil.getTimeLabel()
				+ MyUtil.extracFileExtension(filePart);
		System.out.println(filePart);
		System.out.println(orderNo);
		String contextPath = getServletContext().getRealPath("/"); // Lấy đường dẫn thực của ứng dụng web
		String savePath = contextPath + "success-img-upload"; // Đường dẫn đến thư mục 'img'

		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir(); // Tạo thư mục 'img' nếu nó không tồn tại
		}

		String filePath = savePath + File.separator + fileName; // Đường dẫn file cuối cùng để lưu trữ ảnh
		filePart.write(filePath); // Lưu file ảnh
		String imagePath = "success-img-upload" + File.separator + fileName;
		updateResult = orderDAO.updateOrderShipperConfirm(orderId, imagePath, orderNo, Constant.DELIVERED_ORDER_STATUS);
		if(updateResult) {
			request.setAttribute("message", "Update successfully");
			RequestDispatcher rd = this.getServletContext()
					.getRequestDispatcher("/Views/shipperHomeView.jsp");
			rd.forward(request, response);
		}else {
			errors.add("Update fail");
		}
		if(!errors.isEmpty()) {
			request.setAttribute("errors", String.join(", ", errors));
		}
		RequestDispatcher rd = this.getServletContext()
				.getRequestDispatcher("/Views/shipperHomeView.jsp");
		rd.forward(request, response);
	}
}
