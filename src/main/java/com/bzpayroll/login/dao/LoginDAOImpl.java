package com.bzpayroll.login.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzpayroll.common.db.SQLExecutor;
import com.bzpayroll.dashboard.login.forms.MultiUserForm;
import com.bzpayroll.dashboard.sales.forms.CustomerForm;
import com.bzpayroll.login.forms.LoginForm;

@Service
public class LoginDAOImpl implements LoginDAO {
	public Connection con = null;

	@Autowired
	private static SQLExecutor db;

	public String checkUserRole(String username, String password, String companyid, HttpServletRequest request) {
		Connection con = null; /* comment this con when upload in live server */
		String Role = "";
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = " SELECT UM.Role " + " FROM bca_user as U join bca_usermapping as UM on U.ID = UM.UserID WHERE "
				+ "  U.Email_Address='" + username + "' OR U.LoginID= '" + username + "' AND U.Password='" + password
				+ "' and U.Active=1";
		try {
			con = db.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				Role = rs.getString("UM.Role");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (stmt != null) {
					db.close(stmt);
				}
				if (rs != null) {
					db.close(rs);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				System.out.println("Error in catch of final block of checkUserLogin:");
				e.printStackTrace();
			}
		}
		return Role;
	}

	public boolean checkUserLogin(String username, String password, String companyid, HttpServletRequest request) {
		Connection con = null; /* comment this con when upload in live server */
		boolean loginStatus = false;
		Statement stmt1 = null;
		ResultSet rs1 = null;
		MultiUserForm user = null;
 		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int companyID = -1;
		String Role = "";
		String comapnyname = "";

		/*
		 * String sql1 =" SELECT LoginID,Password,AccessPermissions,Role,UserID,"
		 * +"bca_usermapping.CompanyID" + " FROM "+"bca_user " +
		 * " INNER JOIN "+"bca_usermapping ON "+"bca_user.ID="+"bca_usermapping.UserID,"
		 * + ""+"bca_usergroup" + " WHERE  "+"bca_usermapping.Deleted=0 " +
		 * " AND "+"bca_usermapping.UserGroupID="+"bca_usergroup.GroupID" +
		 * " AND "+"bca_usermapping.Active=1" + " AND LoginID='" + username + "'" +
		 * " AND Password='" + password + "'";
		 */

//        String sql = " SELECT LoginID,Password,	AccessPermissions,Role,UserID,"            //changed by pritesh 23-04-2018
//                + ""+"bca_usermapping.CompanyID"
//                + " FROM "+"bca_user "
//                + "INNER JOIN "+"bca_usermapping ON "+"bca_user.ID="+"bca_usermapping.UserID,"
//                + ""+"bca_usergroup"
//                + " WHERE "+"bca_usermapping.Deleted=0 "
//                + " AND "+"bca_usermapping.UserGroupID="+"bca_usergroup.GroupID"
//                + " AND "+"bca_usermapping.Active=1"
//                + " AND LoginID='" + username + "'"
//                + " AND Password='" + password + "' ";

		String sql = " SELECT * " + " FROM bca_user as U join bca_usermapping as UM on U.ID = UM.UserID WHERE "
				+ "  U.Email_Address='" + username + "' OR U.LoginID= '" + username + "' AND U.Password='" + password
				+ "' and U.Active=1";

		int companyId = 0;
		int UserId = 0;

		try {
			/* for live server uncomment these 5 lines */
			/*
			 * Class.forName("com.mysql.jdbc.Driver"); con = DriverManager.getConnection
			 * ("jdbc:mysql://mysql3000.mochahost.com:3306/nextbits_bzcomposernx?autoReconnect=true"
			 * ,"nextbits_nxsol","nxsol123"); System.out.println("Connection successful");
			 * stmt = con.createStatement(); rs = stmt.executeQuery(sql);
			 */

			/* comment these 3 lines when upload this on live server */
			con = db.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			user = new MultiUserForm();
			if (rs.next()) {
				user = new MultiUserForm();
				String pass = rs.getString("Password");
				// user.setUserName(rs.getString("LoginID")); //changed by pritesh 23-04-2018
				String emailAddress = rs.getString("Email_Address");
				String LoginID = rs.getString("LoginID");
				user.setUserName(emailAddress);
				request.getSession().setAttribute("Email_Address", emailAddress);
				if (user.getUserName().trim().equals(username) && pass.trim().equals(password)) {
					loginStatus = true;
				} else if (LoginID.trim().equals(username) && pass.trim().equals(password)) {
					loginStatus = true;
				}
				user.setRole(rs.getString("Role"));
				String role = user.getRole();
				request.getSession().setAttribute("userRole", role);
				request.getSession().setAttribute("LoginID", LoginID);
				request.getSession().setAttribute("Role", role);
				user.setUserID(rs.getInt("UserID"));
				int userid = user.getUserID();
				// user.setAccessPermissions(rs.getString("AccessPermissions"));
				user.setPassword(pass);
				companyId = rs.getInt("CompanyID");
				user.setCompanyID(companyId);
				request.getSession().setAttribute("CID", companyId);
				UserId = user.getUserID();
				request.getSession().setAttribute("userID", UserId);
				user.setMembershipLevel(rs.getString("membershipLevel"));
				request.getSession().setAttribute("membershipLevel", user.getMembershipLevel());
			}

			String sqlcomanyname = "SELECT NAME FROM bca_company " + "  WHERE bca_company.CompanyID=" + companyId;

			stmt1 = con.createStatement();
			rs1 = stmt1.executeQuery(sqlcomanyname);
			LoginForm loginform = new LoginForm();
			if (rs1.next()) {
				comapnyname = rs1.getString("NAME");
				loginform.setCompanyName(comapnyname);
				loginform.setLoginID(rs.getString("LoginID")); /* Added on 12-06-2019 */
				request.getSession().setAttribute("user", loginform.getCompanyName());
				// request.getSession().setAttribute("username",loginform.getLoginID() );

				String u = rs.getString("Firstname") + " " + rs.getString("Lastname");
				System.out.println("user is:" + u);
				request.getSession().setAttribute("username",
						rs.getString("Firstname") + " " + rs.getString("Lastname"));
				// MultiUserForm sessionUser =
				// (MultiUserForm)request.getSession().getAttribute("user");
			}
		}
		// This catch used for live server uncomment this when upload on server
		/*
		 * catch (ClassNotFoundException e1) { e1.printStackTrace(); }
		 */
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (stmt != null) {
					db.close(stmt);
				}
				if (rs != null) {
					db.close(rs);
				}
				if (stmt1 != null) {
					db.close(stmt1);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				System.out.println("Error in catch of final block of checkUserLogin:");
				e.printStackTrace();
			}
		}
		return loginStatus;
	}

 	public   ArrayList getAllUserlist(HttpServletRequest request) {
		Connection con = null;
		
		Statement stmt1 = null;
		ResultSet rs1 = null;

		con = db.getConnection(); // comment this when upload in live server
		ArrayList<MultiUserForm> objList = new ArrayList<>();
		try {
			stmt1 = con.createStatement();
			String sql = "select * from  bca_user u LEFT JOIN bca_usermapping um ON ( u.ID = um.UserID) join bca_company as c on u.CompanyID = c.CompanyID where u.Active=1";
			rs1 = stmt1.executeQuery(sql);

			while (rs1.next()) {
				// User Name, Group, Status, Company Name
				MultiUserForm multiUserForm = new MultiUserForm();
				multiUserForm.setUserName(rs1.getString("u.Firstname") + " " + rs1.getString("u.Lastname"));
				multiUserForm.setGroupName(rs1.getString("um.Role"));
				multiUserForm.setStatus(rs1.getBoolean("u.Active"));
				multiUserForm.setCompanyName(rs1.getString("c.Name"));
				objList.add(multiUserForm);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs1 != null) {
					db.close(rs1);
				}
				if (stmt1 != null) {
					db.close(stmt1);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objList;

	}

	public static ArrayList getAllCustomerlist(HttpServletRequest request) {
		Connection con = null;
		
		Statement stmt1 = null;
		ResultSet rs1 = null;

		con = db.getConnection(); // comment this when upload in live server
		ArrayList<CustomerForm> objList = new ArrayList<>();
		try {
			stmt1 = con.createStatement();
			String sql = "SELECT c.ClientVendorID, company.Name, c.FirstName, c.LastName, c.Phone, c.Email "
					+ "FROM bca_clientvendor c LEFT JOIN bca_company company ON ( c.companyid = company.companyid )"
					+ " WHERE c.Status IN ('U', 'N' ) AND c.Deleted = 0 AND c.Active = 1 ORDER BY c.ClientVendorID";
			rs1 = stmt1.executeQuery(sql);

			while (rs1.next()) {
				CustomerForm customer = new CustomerForm();
				// customer.setCompanyid(rs1.getString("CompanyID"));
				customer.setClientVendorID(rs1.getString("c.ClientVendorID"));
				customer.setCompanyName(rs1.getString("company.Name"));
				customer.setFirstName(rs1.getString("c.FirstName") + " " + rs1.getString("c.LastName"));
				customer.setPhone(rs1.getString("c.Phone"));
				customer.setEmail(rs1.getString("c.Email"));
				objList.add(customer);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs1 != null) {
					db.close(rs1);
				}
				if (stmt1 != null) {
					db.close(stmt1);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objList;
	}

	public static ArrayList getAllCompany(HttpServletRequest request) {
		Connection con = null;
		
		Statement stmt1 = null;
		ResultSet rs1 = null;

		con = db.getConnection(); // comment this when upload in live server
		ArrayList<LoginForm> objList = new ArrayList<>();
		try {
			stmt1 = con.createStatement();
			String sql = " select * from bca_user as u join bca_usermapping as um on u.ID = um.UserID join bca_company as c on c.CompanyID = u.CompanyID where um.Role='Admin' and c.isCreated=1 and u.Active=1";
			rs1 = stmt1.executeQuery(sql);

			while (rs1.next()) {
				LoginForm loginForm = new LoginForm();
				//loginForm.setCompanyid(rs1.getString("c.CompanyID"));

				loginForm.setCompanyName(rs1.getString("c.Name"));
				//loginForm.setCompanyaddress(rs1.getString("c.Address1") + " " + rs1.getString("c.Address2"));
				loginForm.setMembershipLevel(rs1.getString("u.membershipLevel"));
				loginForm.setPaymentPlan(rs1.getInt("u.paymentPlan"));
				objList.add(loginForm);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				if (rs1 != null) {
					db.close(rs1);
				}
				if (stmt1 != null) {
					db.close(stmt1);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return objList;
	}

	public static ArrayList getCompanyDetails(String cId, HttpServletRequest request) {
		Connection con = null;
		
		Statement stmt1 = null;
		ResultSet rs1 = null;

		con = db.getConnection(); // comment this when upload in live server
		ArrayList<LoginForm> objList = new ArrayList<>();

		try {
			stmt1 = con.createStatement(); /* Comment this when upload in live server */

			/* for live server uncomment this next 4 lines */
			/*
			 * Class.forName("com.mysql.jdbc.Driver"); Connection con1 =
			 * DriverManager.getConnection
			 * ("jdbc:mysql://mysql3000.mochahost.com:3306/nextbits_bzcomposernx?autoReconnect=true"
			 * ,"nextbits_nxsol","nxsol123"); System.out.println("Connection successful");
			 * stmt1 = con1.createStatement();
			 */

			String sql = "" + "SELECT a.companyid, " + "       a.NAME, " + "       b.companylogopath, "
					+ "       a.businesstypeid " + "FROM   bca_company a " + "       LEFT JOIN bca_preference b "
					+ "              ON ( a.companyid = b.companyid ) " + "WHERE  a.active = 1 "
					+ "       AND a.iscreated = 0 " + "ORDER  BY a.NAME";

			// Loger.log(sql);
			rs1 = stmt1.executeQuery(sql);

			while (rs1.next()) {
				LoginForm a = new LoginForm();
				a.setCompanyName(rs1.getString(2));
				// a.setCompanyid(rs1.getString(1));
				objList.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs1 != null) {
					db.close(rs1);
				}
				if (stmt1 != null) {
					db.close(stmt1);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return objList;

	}

	public static ArrayList getCompanyDetails2(String cId, HttpServletRequest request) {
		Connection con = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		con = db.getConnection(); /* Commented on 05-06-2019 for live server */

		ArrayList<LoginForm> objList = new ArrayList<>();

		try {
			/* uncomment next 4 lines for live server */
			/*
			 * Class.forName("com.mysql.jdbc.Driver"); Connection con1 =
			 * DriverManager.getConnection
			 * ("jdbc:mysql://mysql3000.mochahost.com:3306/nextbits_bzcomposernx?autoReconnect=true"
			 * ,"nextbits_nxsol","nxsol123"); System.out.println("Connection successful");
			 * stmt = con1.createStatement();
			 */

			stmt = con.createStatement(); /* Commented on 05-06-2019.comment this line when uploading on live server */
			String sql2 = "" + "SELECT a.companyid, " + "       a.NAME, " + "       b.companylogopath, "
					+ "       a.businesstypeid " + "FROM   bca_company a " + "       LEFT JOIN bca_preference b "
					+ "              ON ( a.companyid = b.companyid ) " + "WHERE  a.active = 1 "
					+ "       AND a.iscreated = 1 " + "ORDER  BY a.NAME";

			// Loger.log(sql2);
			rs = stmt.executeQuery(sql2);

			while (rs.next()) {
				LoginForm b = new LoginForm();
				b.setCompanyName(rs.getString(2));
				// b.setCompanyid(rs.getString(1));
				objList.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (stmt != null) {
					db.close(stmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return objList;
	}

	public boolean checkUserLoginforCom1(String username, String password) {
		Connection con = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		boolean check = false;
		con = db.getConnection();
		try {
			stmt = con.createStatement();
			String sql = "select * from bca_user where LoginID = '" + username + "' and Password = '" + password + "'";
			rs = stmt.executeQuery(sql);
			check = rs.next();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (stmt != null) {
					db.close(stmt);
				}
				if (con != null) {
					db.close(con);
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		if (check) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public boolean checkUserLoginforCom(String username, String password, int companyid, HttpServletRequest request) {

		// Loger.log(LoginAction.class+".checkUserLogin()");
		boolean loginStatus = false;
		Statement stmt1 = null;
		ResultSet rs1 = null;
		MultiUserForm user = null;

		Connection con = null;
		
		Statement stmt = null;
		ResultSet rs = null;

		int companyID = -1;
		String Role = "";
		String comapnyname = "";

		
		Connection c = null;
		String sql1 = " SELECT LoginID,Password,AccessPermissions,Role,UserID," + "bca_usermapping.CompanyID" + " FROM "
				+ "bca_user " + " INNER JOIN " + "bca_usermapping ON " + "bca_user.ID=" + "bca_usermapping.UserID," + ""
				+ "bca_usergroup" + " WHERE  " + "bca_usermapping.Deleted=0 " + " AND " + "bca_usermapping.UserGroupID="
				+ "bca_usergroup.GroupID" + " AND " + "bca_usermapping.Active=1" + " AND LoginID='" + username + "'"
				+ " AND Password='" + password + "'";

		String sql = " SELECT Email_Address,Password,	AccessPermissions,Role,UserID," + ""
				+ "bca_usermapping.CompanyID" + " FROM " + "bca_user " + "INNER JOIN " + "bca_usermapping ON "
				+ "bca_user.ID=" + "bca_usermapping.UserID," + "" + "bca_usergroup" + " WHERE "
				+ "bca_usermapping.Deleted=0 " + " AND " + "bca_usermapping.UserGroupID=" + "bca_usergroup.GroupID"
				+ " AND " + "bca_usermapping.Active=1" + " AND Email_Address='" + username + "'" + " AND Password='"
				+ password + "' ";

		// System.out.println(sql);
		int companyId = 0;
		int UserId = 0;
		try {
			c = db.getConnection();
			// Loger.log("Opening the connection for checkUserLoginforCom method");
			con = db.getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(sql);

			user = new MultiUserForm();
			if (rs.next()) {
				user = new MultiUserForm();
				String pass = rs.getString("Password");
//                user.setUserName(rs.getString("LoginID"));					//changed by pritesh 23-04-2018
				user.setUserName(rs.getString("Email_Address"));
				if (user.getUserName().trim().equals(username) && pass.trim().equals(password)) {
					loginStatus = true;
				}
				user.setRole(rs.getString("Role"));
				user.setUserID(rs.getInt("UserID"));
				int userid = user.getCompanyID();
				user.setAccessPermissions(rs.getString("AccessPermissions"));
				user.setPassword(pass);
				companyId = rs.getInt("CompanyID");
				user.setCompanyID(companyId);
				UserId = user.getUserID();
				request.getSession().setAttribute("userID", UserId);
			}

			String sqlcomanyname = "SELECT NAME FROM bca_company " + "  WHERE bca_company.CompanyID=" + companyId;
			stmt1 = c.createStatement();
			rs1 = stmt1.executeQuery(sqlcomanyname);
			LoginForm loginform = new LoginForm();
			if (rs1.next()) {
				comapnyname = rs1.getString("NAME");
				loginform.setCompanyName(comapnyname);
				request.getSession().setAttribute("user", loginform.getCompanyName());
				// MultiUserForm sessionUser =
				// (MultiUserForm)request.getSession().getAttribute("user");
			}
			// This will set login user accessible in all modules.
			// ConstValue.loginUser = user;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (stmt != null) {
					db.close(stmt);
				}
				if (con != null) {
					db.close(con);
				}
				if (c != null) {
					db.close(c);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return loginStatus;
	}

	public static boolean isUserExists(String userId) {
		boolean loginStatus = false;
		Statement stmt = null, stmt1 = null;
		ResultSet rs = null, rs1 = null;
		MultiUserForm user = null;
		
		Connection c = null;
		c = db.getConnection();

		String sql = "SELECT LoginID FROM bca_user where Email_Address = " + "'" + userId + "'";
		try {

			stmt = c.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				loginStatus = true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null) {
					db.close(rs);
				}
				if (stmt != null) {
					db.close(stmt);
				}
				if (c != null) {
					db.close(c);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return loginStatus;
	}
}