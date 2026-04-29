package pdprof.db.sharing;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.transaction.Transactional;

public class PdprofUnshareableService {
	
	DataSource ds = null;
	
	public PdprofUnshareableService() {
		try {
			InitialContext ic = new InitialContext();
			ds = (DataSource)ic.lookup("java:comp/env/jdbc/Unshareable");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(Transactional.TxType.REQUIRED)
	public void execute(String hostname, int port, String authorization, PrintWriter out) {
		
		Connection con = null;
		try {
			println(out, "==========================================================");
			println(out, this.getClass().getName() + " execute : begin transaction / before ds.getConnection()");
			println(out, PdprofDataSourceMBean.showPoolContents(hostname, port, authorization));
			con = ds.getConnection();
			println(out, "==========================================================");
			println(out, this.getClass().getName() + " execute : after ds.getConnection()");
			println(out, PdprofDataSourceMBean.showPoolContents(hostname, port, authorization));
			con.createStatement().executeQuery("select * from EMPLOYEE");
			con.close();
			println(out, "==========================================================");
			println(out, this.getClass().getName() + " execute : after con.close()");
			println(out, PdprofDataSourceMBean.showPoolContents(hostname, port, authorization));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void println(PrintWriter out, String msg) {
		System.out.println(msg);
		out.println(msg);
	}

}
