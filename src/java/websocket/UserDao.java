/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package websocket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {
	public String findUsername(String username){
		String psw = null;
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		try {
			String driver ="com.mysql.jdbc.Driver";
			String url ="jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf-8";
			String user ="root";
			String password ="root";
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
                        Statement stat = con.createStatement();
                        stat.executeUpdate("USE test");
			String sql = "select * from test where username=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			if(rs==null){
				return null;
			}
			if(rs.next()){
				psw=rs.getString("password");
			}else{
				psw=null;
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
				} 
			catch (SQLException e) {		
									}
		}
		return psw;
	}
	public void addUser(String username,String psw){
		Connection con =null;
		PreparedStatement pstmt =null;
		try {
			String driver ="com.mysql.jdbc.Driver";
			String url ="jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf-8";
			String user ="root";
			String password ="root";//改为自己的用户名密码和数据库名
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
                        Statement stat = con.createStatement();
                        stat.executeUpdate("USE test");
			String sql = "INSERT INTO test (username,password) VALUES (?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, psw);
			pstmt.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(pstmt!=null)pstmt.close();
				if(con!=null)con.close();
				} 
			catch (SQLException e) {		
									}
		}
	}
	//单独测试使用
//	public static void main(String[] args) {
//		String psw =new UserDao().findUsername("ff");
//		System.out.println(psw);
//              if(psw==null){
 //                   UserDao u = new UserDao();
//                    u.addUser("ff", "ff");
//                }
//		
//	}
	
}
