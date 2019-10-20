package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import models.User;
import java.util.List;
import models.Role;

public class UserDB
{
    /**
     * This method inserts user elements and return the number of rows affected.
     *
     * @param user user
     * @return rows rows
     * @throws java.sql.SQLException
     */
    public int insert(User user) throws SQLException 
    {
        ConnectionPool connectionPool = null;
        Connection connection = null;

        int rows = 0;
        try 
        {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            
            String insert_stmt = "INSERT INTO user_table(email, fname, lname, password, roleID) VALUES(?, ?, ?, ?, ?)";
            
            PreparedStatement ps = connection.prepareStatement(insert_stmt);

            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFname());
            ps.setString(3, user.getLname());
            ps.setString(4, user.getPassword());
            ps.setInt(5, user.getRole().getRoleID());

            rows = ps.executeUpdate();     
            ps.close();
            
            return rows;    
        } 
        finally 
        {
            connectionPool.freeConnection(connection);         
        }
    }

    /**
     * This method update the User record.
     *
     * @param user User to be updated
     * @return successCount Number of records updated
     * @throws java.sql.SQLException
     */
    public int update(User user) throws SQLException 
    {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        
        int rows = 0;        
        try 
        {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            String update_stmt = "UPDATE user_table SET active = ?, fname = ?, lname = ?, password = ?, roleID = ? "
                    + "WHERE email = ?";
           
            PreparedStatement ps = connection.prepareStatement(update_stmt);
            ps.setBoolean(1, user.isActive());
            ps.setString(2, user.getFname());
            ps.setString(3, user.getLname());        
            ps.setString(4, user.getPassword());  
            ps.setInt(5, user.getRole().getRoleID());
            ps.setString(6, user.getEmail());

            rows = ps.executeUpdate();
            ps.close();    
            
            return rows;        
        } 
        finally 
        {
            connectionPool.freeConnection(connection);        
        }
    }

    /**
     * This method queries the database for all users. Every user is put into an
     * ArrayList of users
     *
     * @return ArrayList users - the list of users retrieved from the database.
     * @throws SQLException
     */
    public List<User> getAll() throws SQLException 
    {
        ConnectionPool connectionPool = null;
        Connection connection = null;
           
        List<User> userList = new ArrayList();
        
        try 
        {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();
            
            User user;
         
            String select_stmt = "SELECT active, email, fname, lname, password, roleID FROM user_table";
            
            PreparedStatement ps = connection.prepareStatement(select_stmt);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) 
            {
                boolean active = rs.getBoolean(1);
                String userEmail = rs.getString(2);
                String fname = rs.getString(3);
                String lname = rs.getString(4);
                String password = rs.getString(5);
                
                int roleID = rs.getInt(6);
                RoleDB roleDB = new RoleDB();
                Role role = roleDB.getRole(roleID);
                        
                user = new User(userEmail, fname, lname, password, role);
                user.setActive(active);
                userList.add(user);
            }
            return userList;
        } 
        finally 
        {
            connectionPool.freeConnection(connection);        
        }
    }

    /**
     * This method queries the database for a particular user (dude) that has a
     * matching email.
     *
     * @param email - the user's email to be searched for.
     * @return User dude - the user retrieved from the database.
     * @throws SQLException
     */
    public User getUser(String email) throws SQLException 
    {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        
        try 
        {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            User user = new User();
            
            String select_stmt = "SELECT active, email, fname, lname, password, roleID FROM user_table WHERE email = ?";          
            
            PreparedStatement ps = connection.prepareStatement(select_stmt);
            ps.setString(1, email);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                boolean active = rs.getBoolean(1);
                String userEmail = rs.getString(2);
                String fname = rs.getString(3);
                String lname = rs.getString(4);
                String password = rs.getString(5);
                int roleID = rs.getInt(6);
                
                RoleDB roleDB = new RoleDB();
                Role role = roleDB.getRole(roleID);
                
                user = new User(userEmail, fname, lname, password, role);
                user.setActive(active);
            }

            return user;
        } 
        finally 
        {
            connectionPool.freeConnection(connection);
        }
    }

    /**
     * This method physically deletes a user from the user_table
     *
     * @param user
     * @return false returns false if there's nothing to de
     * @throws java.sql.SQLException
     */
    public int delete(User user) throws SQLException 
    {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        
        int rows = 0;
        try 
        {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            String delete_stmt = "DELETE FROM user_table WHERE email = ?";
            
            PreparedStatement ps = connection.prepareStatement(delete_stmt);
            ps.setString(1, user.getEmail());

            rows = ps.executeUpdate();
            ps.close();
            
            return rows;
        } 
        finally 
        {
            connectionPool.freeConnection(connection);
        }
    }
}
