package pdprof.db.connections;

import java.io.IOException;
import java.sql.Connection;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

/**
 * Servlet implementation class LockEmployee
 */
@WebServlet("/select")
public class SelectEmployee extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/derbyEmbedded", shareable = true)
	DataSource ds;
	
	@Resource
	UserTransaction ut;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectEmployee() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String timeoutStr = request.getParameter("timeout"); // unit second
			if (timeoutStr != null) {
				try { 
					int timeout = Integer.parseInt(timeoutStr);
					ut.setTransactionTimeout(timeout);
				} catch (Exception e) {
					timeoutStr = "default";
				};
			} else {
				timeoutStr = "default";
			}
			ut.begin();
			Connection con = ds.getConnection();
			System.out.println("SELECT : select * from EMPLOYEE table start");
			con.createStatement().executeQuery("select * from EMPLOYEE");
			System.out.println("SELECT : select * from EMPLOYEE table end");
			ut.commit();
			response.getWriter().append("SELECT : select * from EMPLOYEE table in transaction timeout with ").append(timeoutStr).append(".");
		} catch (Exception e) {
			System.out.println("SELECT : " + e.getMessage());
			e.printStackTrace();
			response.getWriter().append("SELECT : " + e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
