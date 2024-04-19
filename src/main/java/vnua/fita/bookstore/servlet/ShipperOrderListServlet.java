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
import vnua.fita.bookstore.util.MyUtil;

/**
 * Servlet implementation class ShipperOrderListServlet
 */
@WebServlet(urlPatterns = {"/shipperOrderList/delivered","/shipperOrderList/reject"})
public class ShipperOrderListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  private OrderDAO orderDAO;
	    
		public void init() {
			String jdbcURL = getServletContext().getInitParameter("jdbcURL");
			String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
			String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
			orderDAO = new OrderDAO(jdbcURL, jdbcUsername, jdbcPassword);
		}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShipperOrderListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				String servletPath= request.getServletPath();
				String pathInfo=MyUtil.getPathInfoFromServletPath(servletPath);
				List<Order> orders=new ArrayList<Order>();
				if(Constant.DELEVERING_ACTION.equals(pathInfo)) {
					orders=orderDAO.getOrderList(Constant.DELIVERING_ORDER_STATUS);
					request.setAttribute("listType", Constant.WAITING_FOR_DELIVERY);
				}else if(Constant.DELEVERED_ACTION.equals(pathInfo)) {
					orders=orderDAO.getOrderList(Constant.DELIVERED_ORDER_STATUS);
					request.setAttribute("listType", Constant.DELIVERED);
				}else if(Constant.REJECT_ACTION.equals(pathInfo)) {
					orders=orderDAO.getOrderList(Constant.REJECT_ORDER_STATUS);
					request.setAttribute("listType", Constant.CUSTOMER_RETURN);
				}
				
				request.setAttribute(Constant.ORDER_LIST_OF_SHIPPER, orders);
				RequestDispatcher dispatcher=this.getServletContext().getRequestDispatcher("/Views/shipperOrderListView.jsp");
				dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
