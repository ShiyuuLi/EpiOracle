package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.*;

public class JDBCTools {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/mysql?useSSL=no";
    static final String USER = "root";
    static final String PASS = "579Lsy0106.";

    private static ComboPooledDataSource dataSource;

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        Connection con;
        try {
            con = JDBCTools.getConnection();
            if (!con.isClosed())
                System.out.println("Successfully connected to the Database!");
            else {
                System.out.println("Failure connection!");
            }
            Statement statement = con.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Statement statement = con.createStatement();
        String sql = "select * from EpiOracle.symptoms";
        ResultSet result = statement.executeQuery("select * from EpiOracle.symptoms");
        while (result.next()) {
            System.out.print("Column 2 returned ");
            System.out.println(result.getString(2));
        }
        result.close();
        statement.close();
    }
}
