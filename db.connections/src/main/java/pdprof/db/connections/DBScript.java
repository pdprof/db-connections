package pdprof.db.connections;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DBScript
 */
@WebServlet("/script")
public class DBScript extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBScript() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int threads = 30;
		int timeout = 180000;
		String threadsStr = request.getParameter("threads");
		String timeoutStr = request.getParameter("timeout");
		try {
			if(threadsStr != null) 
				threads = Integer.parseInt(threadsStr);
			if(timeoutStr != null)
				timeout = Integer.parseInt(timeoutStr);
		} catch (Exception e) {}
		request.setAttribute("threads", ""+ threads);
		request.setAttribute("timeout", ""+ timeout);
        RequestDispatcher dispatcher =  request.getRequestDispatcher("script.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
