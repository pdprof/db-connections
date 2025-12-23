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
@WebServlet("/lockemptmp")
public class LockEmpTmp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name = "jdbc/derbyEmbedded", shareable = true)
	DataSource ds;
	
	@Resource
	UserTransaction ut;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LockEmpTmp() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String sleepStr = request.getParameter("sleep"); // unit second
			int sleep = 110000;
			if (sleepStr != null) {
				try { sleep = Integer.parseInt(sleepStr) * 1000; } catch (Exception e) {};
			} else {
				sleepStr = "110";
			}
			ut.begin();
			Connection con = ds.getConnection();
			con.createStatement().execute("lock table EMPLOYEE in exclusive mode");
			System.out.println("LOCK : Locking EMPLOYEE table start");
			System.out.println("LOCK : Sleep " + sleep/1000 + " seconds");
			int times = sleep / 10000;
			for (int i = 0; i<times; i++) {
				Thread.sleep(10000);
				System.out.println("LOCK : " + (i + 1)*10 + " seconds elapsed");
			}
			if((sleep%10000)>0) {
				Thread.sleep(sleep%10000);
				System.out.println("LOCK : " + sleepStr + " seconds elapsed");
			}
			ut.commit();
			System.out.println("LOCK : released EMPLOYEE table.");
			response.getWriter().append("LOCK : Locking EMPLOYEE table ").append(sleepStr).append(" seconds elapsed and released it.");
			ut.begin();
			con = ds.getConnection();
			con.createStatement().execute("lock table TMPLOYEE in exclusive mode");
			System.out.println("LOCK : Locking TMPLOYEE table start");
			System.out.println("LOCK : Sleep " + sleep/1000 + " seconds");
			for (int i = 0; i<times; i++) {
				Thread.sleep(10000);
				System.out.println("LOCK : " + (i + 1)*10 + " seconds elapsed");
			}
			if((sleep%10000)>0) {
				Thread.sleep(sleep%10000);
				System.out.println("LOCK : " + sleepStr + " seconds elapsed");
			}
			ut.commit();
			System.out.println("LOCK : released TMPLOYEE table.");
			response.getWriter().append("LOCK : Locking TMPLOYEE table ").append(sleepStr).append(" seconds elapsed and released it.");
		} catch (Exception e) {
			System.out.println("LOCK : " + e.getMessage());
			e.printStackTrace();
			response.getWriter().append("LOCK : " + e.getMessage());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
