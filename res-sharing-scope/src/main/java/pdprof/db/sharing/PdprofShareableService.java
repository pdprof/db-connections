package pdprof.db.sharing;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PdprofShareableService {

	@Resource(name = "jdbc/derbyEmbedded", shareable = true)
	DataSource ds;
	
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
