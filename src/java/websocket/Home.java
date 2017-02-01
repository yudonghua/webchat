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

public class Home {
	public int createuser(String username){
		String psw = null;
		Connection con =null;
		PreparedStatement pstmt =null;
		int rs=789;
		try {
			String driver ="com.mysql.jdbc.Driver";
			String url ="jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=utf-8";
			String user ="root";
			String password ="root";
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
                        Statement stat = con.createStatement();
                        stat.executeUpdate("USE test;");
			String sql = "create table "+username+" (title varchar(100),content text,conment text) charset=utf8";
		//	pstmt = con.prepareStatement(sql);
                        String checkTable="show tables like \""+username+"\"";
                        ResultSet resultSet=stat.executeQuery(checkTable);
			if(resultSet.next()){
                        
                        }else{
                            rs = stat.executeUpdate(sql);
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
		return rs;
	}
	public void addpaper(String username,String title,String content){
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
			String sql = "INSERT INTO "+username+" (title,content) VALUES (?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
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
//		String s = "777";
//                if(s.matches("^[0-9].*$"))System.out.print(s);
//
//
//	}
	
}
