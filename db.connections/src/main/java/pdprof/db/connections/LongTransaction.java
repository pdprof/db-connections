package pdprof.db.connections;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;

/**
 * Servlet implementation class LongTransaction
 */
@WebServlet("/longtran")
public class LongTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Resource
	UserTransaction ut;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LongTransaction() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ut.begin();
			try {
				for (int i=0; i<13; i++) {
					Thread.sleep(10000);
					System.out.println("LONG : " + (i+1)*10 + " seconds elpased");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			ut.commit();
			response.getWriter().append("LONG : transaction finshed in 130 seconds.");
		} catch (Exception e) {
			response.getWriter().append("LONG : " + e.getClass() + " caught");
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
