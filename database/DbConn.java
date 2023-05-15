package src.database;

import java.sql.*;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.*;

import static src.utils.Oras.*;

public class DbConn
{
	private Connection conn;
	private String url;
	private String _user;
	private String _password;
	private DefaultTableModel model;

	public DbConn()
	{
		conn = null;
		url = "jdbc:mysql://localhost:3306/agila";
		_user = "root";
		_password = "";
	}

	public DbConn(String url, String usr, String pwd)
	{
		conn = null;
		this.url = url;
		this._user = usr;
		this._password = pwd;
	}

	public void cclose()
	{
		try
		{
			conn.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void oopen()
	{
		try
		{
			conn = DriverManager.getConnection(url, _user, _password);
		} catch (SQLException e)
		{
			System.out.println("Error in connecting to database");
		}

	}

	public Connection retConn() throws SQLException
	{
		return DriverManager.getConnection(url, _user, _password);
	}

	public void sessionState(int uid, String username)
	{
		String sql = "INSERT INTO session (uid, username) VALUES (?, ?)";
		try (PreparedStatement statement = conn.prepareStatement(sql))
		{
			statement.setInt(1, uid);
			statement.setString(2, username);
			statement.executeUpdate();
		} catch (SQLException e)
		{
			System.err.println("Error inserting session: " + e.getMessage());
		}
	}

	public boolean sessionCheck()
	{
		String checkQuery = "SELECT COUNT(*) FROM session";
		try (Statement stmt = conn.createStatement())
		{
			ResultSet rs = stmt.executeQuery(checkQuery);
			rs.next();

			// Someone didn't log in
			if(rs.getInt(1) >= 1)
			{
				System.out.println("Some one logined");
				return true;
			}
		} catch (SQLException e)
		{
			System.err.println("Error checking session: " + e.getMessage());
			return false; // Failed to check session
		}
		System.out.println("Some one didn't login");
		return false;
	}

	public void deleteSession()
	{
		String query = "DELETE FROM session";
		try (Statement stmt = conn.createStatement())
		{
			stmt.executeUpdate(query);
		} catch (SQLException e)
		{
			System.err.println("Error deleting session: " + e.getMessage());
		}
	}

	public int authenticateUser(String usr, String pwd)
	{
		try
		{

			if (usr.equals("Username") || pwd.equals("Password"))
				return -1;

			Connection conn = retConn();
			PreparedStatement stmt = conn
					.prepareStatement("SELECT uid FROM users WHERE username=? AND password=?");

			stmt.setString(1, usr);
			stmt.setString(2, pwd);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) //user is registered
			{
				int uid = rs.getInt("uid");
				sessionState(uid, usr); // Put the user into session state
				return 1;
			}
		}
		catch (Exception ex)
		{
			System.err.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		}
		return 0;
	}

	public int registerUser(String usr, String pwd)
	{
		// already exist
		int test = authenticateUser(usr, pwd);
		if (test == -1)
			return -1;

		try
		{
			Connection conn = retConn();
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO users" + "(username,password) VALUES(?, ?)");
			stmt.setString(1, usr);
			stmt.setString(2, pwd);
			stmt.executeUpdate();
			return 1;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return 0; // empty fields
	}

	public int citizensCount()
	{
		String query = "SELECT COUNT(*) AS count FROM residents";
		PreparedStatement stmt;
		try
		{
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			if (rs.next())
			{
				return rs.getInt("count");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return -1; // This line will be executed only if an exception occurs or if the result set
					// is empty
	}

	public boolean InsertData(String fName, String mName, String lName, String sex, int age, String purok, Date birth)
	{
		String selectQuery = "SELECT COUNT(*) AS num_matches FROM residents "
				+ "WHERE first_name = ? AND middle_name = ? "
				+ "AND last_name = ? AND sex = ? AND age = ? AND purok = ?" + "AND birthday = ?";
		String insertQuery = "INSERT INTO residents " + "(first_name, middle_name, last_name, sex, age, "
				+ "purok, birthday) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";
		String statusQuery = "INSERT INTO status (uid, deceased, deleted) " + "SELECT ?, false, false FROM DUAL "
				+ "WHERE NOT EXISTS (SELECT uid FROM status WHERE uid = ?)";

		java.sql.Date datebr = sqlDate(birth);

		try
		{
			PreparedStatement selstmt = conn.prepareStatement(selectQuery);

			selstmt.setString(1, fName);
			selstmt.setString(2, mName);
			selstmt.setString(3, lName);
			selstmt.setString(4, sex);
			selstmt.setInt(5, age);
			selstmt.setString(6, purok);
			selstmt.setDate(7, datebr);

			ResultSet rs = selstmt.executeQuery();
			if (rs.next() && rs.getInt("num_matches") == 0)
			{
				PreparedStatement instmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

				instmt.setString(1, fName);
				instmt.setString(2, mName);
				instmt.setString(3, lName);
				instmt.setString(4, sex);
				instmt.setInt(5, age);
				instmt.setString(6, purok);
				instmt.setDate(7, datebr);
				instmt.executeUpdate();

				try (ResultSet sr = instmt.getGeneratedKeys())
				{
					if (sr.next())
					// has a uid given from instmt
					{
						PreparedStatement statusStmt = conn.prepareStatement(statusQuery);
						int uid = sr.getInt(1);
						// set the parameter for the status table insert statement
						statusStmt.setInt(1, uid);
						statusStmt.setInt(2, uid);

						statusStmt.executeUpdate();
						System.out.println("Inserted row with uid: " + uid);
					}
				}
				return true;
			} else
			{
				return false;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}

	}

	public boolean data_fetch()
	{

		String query = "SELECT c.uid, c.first_name, c.middle_name, c.last_name, "
				+ "c.sex, c.birthday, c.age, c.purok, s.deceased" + " FROM residents c JOIN status s ON c.uid = s.uid"
				+ " WHERE s.deleted = 0"; // select only the non deleted
		// value

		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// Create table model and add column names
			model = new DefaultTableModel();
			model.setColumnIdentifiers(new String[] { "UID", "Name", "First Name", "Middle Name", "Middle Initial",
					"Last Name", "Sex", "Birthday", "Age", "Purok", "Deceased" });

			while (rs.next())
			{
				String name = rs.getString("first_name") + " " + rs.getString("middle_name") + " "
						+ rs.getString("last_name"), fname = rs.getString("first_name"),
						mdname = rs.getString("middle_name"), lname = rs.getString("last_name"),
						sex = rs.getString("sex"), bday = rs.getDate("birthday").toString(),
						purok = rs.getString("purok");

				String minit = rs.getString("middle_name");
				char midinit = ' ';
				// taking the middle initial
				if (minit != null && !minit.isEmpty())
					midinit = minit.charAt(0);

				int age = rs.getInt("age"), uid = rs.getInt("uid");
				boolean status = rs.getBoolean("deceased");
				String decstat = (status) ? "Yes" : "No";

				Object[] rowData = new Object[] { uid, name, fname, mdname, midinit, lname, sex, bday, age, purok,
						decstat };
				model.addRow(rowData);
			}
			return true;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public void setDeleted(int uid)
	{
		String updateQuery = "UPDATE status SET deleted = true WHERE uid = ?";
		try (PreparedStatement stmt = conn.prepareStatement(updateQuery))
		{
			stmt.setInt(1, uid);
			stmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public boolean updateCitizenAndStatus(int uid, boolean deceased, String firstName, String middleName,
			String lastName, String sex, int age, String purok, Date birthday)
	{
		// Update deceased column in status table
		System.out.println("I want you dead " + deceased);

		String updateStatusSql = "UPDATE residents c JOIN status s "
				+ "ON c.uid = s.uid  SET s.deceased = ? WHERE c.uid = ?";
		try
		{
			PreparedStatement updateStatusStmt = conn.prepareStatement(updateStatusSql);
			updateStatusStmt.setBoolean(1, deceased);
			updateStatusStmt.setInt(2, uid);
			updateStatusStmt.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}

		// Update columns in residents table
		String updateCitizensSql = "UPDATE residents SET first_name = ?, middle_name = ?, last_name = ?, sex = ?, age = ?, purok = ?, birthday = ? WHERE uid = ?";
		java.sql.Date datebr = sqlDate(birthday);

		try
		{
			PreparedStatement updateCitizensStmt = conn.prepareStatement(updateCitizensSql);
			updateCitizensStmt.setString(1, firstName);
			updateCitizensStmt.setString(2, middleName);
			updateCitizensStmt.setString(3, lastName);
			updateCitizensStmt.setString(4, sex);
			updateCitizensStmt.setInt(5, age);
			updateCitizensStmt.setString(6, purok);
			updateCitizensStmt.setDate(7, datebr);
			updateCitizensStmt.setInt(8, uid);
			updateCitizensStmt.executeUpdate();
			return true;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public DefaultComboBoxModel<String> getcboxModel()
	{
		// create a SQL query to retrieve the data from the residents table
		String query = "SELECT c.uid, c.first_name, c.middle_name, c.last_name"
				+ " FROM residents c JOIN status s ON c.uid = s.uid" + " WHERE s.deleted = 0"; // select only the non
																								// deleted
		DefaultComboBoxModel<String> cbox = new DefaultComboBoxModel<String>();
		// execute the query and retrieve the data
		try
		{
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// loop through the result set and add the data to the JComboBox
			while (rs.next())
			{
				String lastName = rs.getString("last_name");
				String firstName = rs.getString("first_name");
				String middleName = rs.getString("middle_name");
				int uid = rs.getInt("uid");

				// add the data to the JComboBox
				cbox.addElement("(" + uid + ") " + lastName + ", " + firstName + " " + middleName);
			}
		} catch (SQLException e)
		{
			System.err.println(e);
		}

		return cbox;
	}

	public DefaultTableModel getDataComboBox(int uid)
	{
		// create a SQL query to retrieve more information about the selected person
		// from the residents table
		String query = "SELECT c.uid, c.first_name, c.middle_name, c.last_name, "
				+ "c.sex, c.birthday, c.age, c.purok, s.deceased" + " FROM residents c JOIN status s ON c.uid = s.uid"
				+ " WHERE s.deleted = 0 AND c.uid = ?"; // select only the non deleted
		// value
		DefaultTableModel cmodel = new DefaultTableModel();

		try
		{
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, uid);
			// finding the result sets
			ResultSet rs = stmt.executeQuery();

			// Create table model and add column names
			cmodel.setColumnIdentifiers(new String[] { "First Name", "Middle Name", "Last Name", "Sex", "Birthday",
					"Age", "Purok", "Deceased" });

			while (rs.next())
			{
				String fname = rs.getString("first_name"), mdname = rs.getString("middle_name"),
						lname = rs.getString("last_name"), sex = rs.getString("sex"),
						bday = rs.getDate("birthday").toString(), purok = rs.getString("purok");

				int age = rs.getInt("age");
				boolean status = rs.getBoolean("deceased");
				String decstat = (status) ? "Yes" : "No";

				Object[] rowData = new Object[] { fname, mdname, lname, sex, bday, age, purok, decstat };
				cmodel.addRow(rowData);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return cmodel;
	}

	public int recoverDeletedDatas()
	{
		String query = "UPDATE status SET deleted = 0 WHERE deleted = 1";
		int raf = 0;
		try (PreparedStatement stmt = conn.prepareStatement(query))
		{
			raf = stmt.executeUpdate();
			return raf;
		} catch (SQLException e)
		{
			System.err.println(e);
		}
		return raf;
	}

	public int permanentDeleteDatas()
	{
		String query = "DELETE FROM status WHERE deleted = 1";
		int raf = 0;

		try (PreparedStatement stmt = conn.prepareStatement(query))
		{
			raf = stmt.executeUpdate();
			return raf;
		} catch (SQLException e)
		{
			System.err.println(e);
		}
		return raf;
	}

	public DefaultTableModel getModel()
	{
		return model;
	}
}
