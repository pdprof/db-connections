package pdprof.db.sharing;

import java.io.IOException;
import java.io.PrintWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SharableServlet
 */
@WebServlet(urlPatterns = {"/sharable", "/unsharable"})
public class SharingScopeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	@Inject
	PdprofShareableService share;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SharingScopeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		println(out, "==========================================================");
		println(out, this.getClass().getName() + ".doGet() : before begin transaction");
		String value = PdprofDataSourceMBean.showPoolContents(request.getServerName(), request.getServerPort(), request.getHeader("Authorization"));
		println(out, value);
		if (request.getRequestURI().endsWith("unsharable")) {
			PdprofUnshareableService unshare = new PdprofUnshareableService();
			unshare.execute(request.getServerName(), request.getServerPort(), request.getHeader("Authorization"), out);
		} else {
			share.execute(request.getServerName(), request.getServerPort(), request.getHeader("Authorization"), out);
		}
		value = PdprofDataSourceMBean.showPoolContents(request.getServerName(), request.getServerPort(), request.getHeader("Authorization"));
		println(out, "==========================================================");
		println(out, this.getClass().getName() + ".doGet() : after end transaction");
		println(out, value);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void println(PrintWriter out, String msg) {
		System.out.println(msg);
		out.println(msg);
	}

}
