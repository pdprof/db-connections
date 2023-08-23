package pdprof.jpa;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JPAEmployeeServlet
 */
@WebServlet("/employee")
public class JPAEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("pdprof-jpa");
	EntityManager em = emf.createEntityManager();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public JPAEmployeeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		if (name != null) {
			this.addEmployee(name);
		}

		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		List<Employee> employees = this.getEmployees();
		out.print("[");
		for (int i=0; i<employees.size(); i++) {
			Employee emp = (Employee)employees.get(i);
			if (i != 0) {
				out.print(",");
			}
			out.print("{\"id\":\"" + emp.getId() + "\",\"name\":\"" + emp.getName() + "\"}");
		}
		out.print("]");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public List<Employee> getEmployees() {
		List<Employee> employees = em.createQuery("select a FROM Employee a", Employee.class).getResultList();
		return employees;
	}
	
	public void addEmployee(String name) {
		Employee employee = new Employee();
		employee.setName(name);
		
		em.getTransaction().begin();
		em.persist(employee);
		em.getTransaction().commit();
	}

}
