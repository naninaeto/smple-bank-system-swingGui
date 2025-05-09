import java.sql.*;

public class BankOperations {

    public static void createAccount(String accNo, String name) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO accounts(account_no, holder_name, balance) VALUES (?, ?, ?)");
        ps.setString(1, accNo);
        ps.setString(2, name);
        ps.setDouble(3, 0.0);
        ps.executeUpdate();
        conn.close();
    }

    public static void deposit(String accNo, double amount) throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement ps = conn.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_no = ?");
        ps.setDouble(1, amount);
        ps.setString(2, accNo);
        ps.executeUpdate();

        PreparedStatement ts = conn.prepareStatement("INSERT INTO transactions (account_no, type, amount) VALUES (?, 'deposit', ?)");
        ts.setString(1, accNo);
        ts.setDouble(2, amount);
        ts.executeUpdate();

        conn.commit();
        conn.close();
    }

    public static void withdraw(String accNo, double amount) throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.setAutoCommit(false);

        PreparedStatement check = conn.prepareStatement("SELECT balance FROM accounts WHERE account_no = ?");
        check.setString(1, accNo);
        ResultSet rs = check.executeQuery();
        if (rs.next() && rs.getDouble("balance") >= amount) {
            PreparedStatement ps = conn.prepareStatement("UPDATE accounts SET balance = balance - ? WHERE account_no = ?");
            ps.setDouble(1, amount);
            ps.setString(2, accNo);
            ps.executeUpdate();

            PreparedStatement ts = conn.prepareStatement("INSERT INTO transactions (account_no, type, amount) VALUES (?, 'withdraw', ?)");
            ts.setString(1, accNo);
            ts.setDouble(2, amount);
            ts.executeUpdate();

            conn.commit();
        } else {
            conn.rollback();
            throw new SQLException("Insufficient balance.");
        }
        conn.close();
    }

    public static Account getAccount(String accNo) throws SQLException {
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM accounts WHERE account_no = ?");
        ps.setString(1, accNo);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Account(accNo, rs.getString("holder_name"), rs.getDouble("balance"));
        }
        conn.close();
        return null;
    }
}