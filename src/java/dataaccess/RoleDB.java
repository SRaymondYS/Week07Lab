package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import models.Role;

/**
 *
 * @author awarsyle
 */
public class RoleDB 
{
    public Role getRole(int roleID) throws SQLException 
    {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        
        try 
        {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            Role r = null;
            
            String select_stmt = "SELECT ID, role_name FROM role_table WHERE ID = ?";
            PreparedStatement ps = connection.prepareStatement(select_stmt);
            
            ps.setInt(1, roleID);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                String roleName = rs.getString(2);
                r = new Role(roleID, roleName);
            }
            
            return r;
        } 
        finally 
        {
            connectionPool.freeConnection(connection);
        }
    }
    
    
    public Role getRoleID(String roleName) throws SQLException 
    {
        ConnectionPool connectionPool = null;
        Connection connection = null;
        
        try 
        {
            connectionPool = ConnectionPool.getInstance();
            connection = connectionPool.getConnection();

            Role r = null;
            
            String select_stmt = "SELECT ID, role_name FROM role_table WHERE role_name = ?";
            PreparedStatement ps = connection.prepareStatement(select_stmt);
            
            ps.setString(1, roleName);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) 
            {
                int roleID = rs.getInt(1);
                r = new Role(roleID, roleName);
            }
            
            return r;
        } 
        finally 
        {
            connectionPool.freeConnection(connection);
        }
    }
}
