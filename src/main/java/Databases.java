

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import java.util.List;
import java.util.ArrayList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.RequestDispatcher;
import java.sql.*;
/**
 * Servlet implementation class Databases
 */
@WebServlet("/Databases")
public class Databases extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String expenditure = new String();//waste
	List<String> wastes = new ArrayList<String>();//waste collection
    /**
     * Default constructor. 
     */
    public Databases() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getParameter("listing") != null) {
			PrintWriter printWriter = response.getWriter();//print formatted representations of objects to text-output stream
			printWriter.println("<html><table><ul>");
			for (int i = 0; i < this.wastes.size(); i++) {
				if (this.expenditure == new String()) return;
				printWriter.println("<li>" + this.wastes.toArray()[i] + "</li>");
			}
			printWriter.println("</ul></table></html>");
		}
		else {
			this.expenditure = new String();//waste reset
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("databases.jsp");//redirection query from interface with realization of increasing functional abilities of server
			requestDispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String total = request.getParameter("amount"), goal = request.getParameter("purpose"), jdbcUrl = "jdbc:postgresql://localhost:5432/postgres";//sum, target & connection string
		this.expenditure = new String();//waste reset
		if (total == null) return;
		if (goal == null) return;
		this.expenditure += total + "   " + goal;//whole waste
		this.wastes.add(this.expenditure);
		try {
			Connection connection = DriverManager.getConnection(jdbcUrl, "postgres", "postgres");//session with specific database
			Statement statement = connection.createStatement();//using for executing static statement & returning results it produced
			statement.execute("insert into waste(amount, target) values(" + Integer.valueOf(total) + ", " + goal + ")");
		}
		catch(SQLException sqle) { }
		this.doGet(request, response);
	}

}