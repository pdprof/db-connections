package pdprof.db.connections;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.annotation.Resource.AuthenticationType;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class DBServlet
 */
@WebServlet("/db")
public class DBServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(name="jdbc/derbyEmbedded", shareable=true)
	DataSource ds;

	String tableName = "EMPLOYEE";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DBServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Connection con = null;
			String mode = request.getParameter("mode");
			String action = request.getParameter("action");
			String name = request.getParameter("name");
			String idStr = request.getParameter("id");

			con = ds.getConnection();

			String result = null;

			if (tableExists(con)) {
				if (action != null) {
					if (action.equalsIgnoreCase("init")) {
						dropTable(con);
						initTable(con);
						result = tableName + " is recreated";
					} else if (action.equalsIgnoreCase("insert")) {
						insert(con, name);
						result = "Name:" + name + " is inserted into " + tableName;
					} else if (action.equalsIgnoreCase("update")) {
						try {
							int id = Integer.parseInt(idStr);
							update(con, id, name);
							result = "Id:" + id + " is updated with Name:" + name + " at " + tableName;
						} catch (NumberFormatException e) {
							result = "Update is failed with NumberFormatException of num:" + idStr;
						}
					}
				}
			} else {
				initTable(con);
				result = tableName + " is initialized";
			}
			
			request.setAttribute("result", result);
			LinkedHashMap<String, Employee> employee = select(con);
			request.setAttribute("employee", employee);
			
	        RequestDispatcher dispatcher =  request.getRequestDispatcher("employee.jsp");
	        dispatcher.forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean tableExists(Connection con) throws SQLException {
		DatabaseMetaData meta = con.getMetaData();
		ResultSet resultSet = meta.getTables(null, null, tableName, new String[] { "TABLE" });
		return resultSet.next();
	}

	private boolean dropTable(Connection con) throws SQLException {
		con.createStatement().executeUpdate("drop table EMPLOYEE");
		return true;
	}

	private boolean initTable(Connection con) throws SQLException {
		con.createStatement().executeUpdate(
				"create table EMPLOYEE (id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), name VARCHAR(128))");
		con.createStatement()
				.executeUpdate("insert into EMPLOYEE (name) VALUES ('Taro Suzuki'), ('Hanko Sato'), ('Sora Yamada')");
		return true;
	}

	private int insert(Connection con, String name) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement("insert into EMPLOYEE (name) VALUES (?)");
		pstmt.setString(0, name);
		return pstmt.executeUpdate();
	}

	private int update(Connection con, int id, String name) throws SQLException {
		PreparedStatement pstmt = con.prepareStatement("UPDATE EMPLOYEE SET name = ? WHERE ID = ?");
		pstmt.setString(0, name);
		pstmt.setInt(1, id);
		return pstmt.executeUpdate();
	}

	private LinkedHashMap<String, Employee> select(Connection con) throws SQLException {
		LinkedHashMap<String, Employee> map = new LinkedHashMap<String, Employee>();
		ResultSet rs = con.createStatement().executeQuery("select id,name from EMPLOYEE");
		while(rs.next()) {
			String id = rs.getString("id");
			String name = rs.getString("name");
			Employee emp = new Employee(id, name);
			map.put(id, emp);
		}
		return map;
	}

}
