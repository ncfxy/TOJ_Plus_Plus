package cn.tjuscs.oj.wyh;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
	private String user;
	private String pwd;
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://www.ncfxy.com:3306/";
	
	private Connection conn = null;  
	private PreparedStatement pst = null; 
    
	private String table;  
	private String sql = "select * from";
	private String where = "";
	private String limit = "";
	private String order = "";
	private ResultSet ret;
    
	private List<String> retList1 = new ArrayList<String>();
	private List<String> retList2 = new ArrayList<String>();
    
    public DB(String user, String pwd, String database){
    	try {
			conn = DriverManager.getConnection(url+database, user, pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public void getByUid(String uid){
    	this.where = "where uid = " + "'" + uid + "'";
    }
    
    public void getBySid(int sid){
    	this.where = "where sid = " + sid;
    }
    
    public void setLimit(int rowNum){
    	int from = 0;
    	setLimit(from, rowNum);
    }
    
    public void setLimit(int from, int rowNum){
    	if(from == 0){
    		limit ="limit " + rowNum;
    	}else{
    		limit ="limit " + from + " , " + rowNum;
    	}
    }
    
    public void setOrder(String column){
    	String mode = "asc";
    	setOrder(column, mode);
    }
    
    public void setOrder(String column, String mode){
    	order ="order by" + " " +column + " " + mode;
    }
    
    public void query(){
    	this.sql +=" " + table + " " + where + " " + order + " "+ limit; 
    	System.out.println(this.sql);
    	try {
			pst = conn.prepareStatement(this.sql);
			ret = pst.executeQuery();
			
			ret.next();
			//System.out.println(ret.getRow());
			for(int i = 1; i <= 10; i++){
				retList1.add(ret.getString(i));
			}
			
			ret.next();
			for(int i = 1; i <= 10; i++){
				retList2.add(ret.getString(i));
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
    }
    
    public void queryFullSql(String fullSql){
    	try{
    		pst = conn.prepareStatement(fullSql);
			ret = pst.executeQuery();
    	}
    	catch(SQLException e){
    		e.printStackTrace();
    	}
    }
	
	public void finalize(){
		try {  
            conn.close();  
            pst.close();  
         } catch (SQLException e) {  
 			e.printStackTrace();
         }  
	}
	
	public String getTable(){
		return this.table;
	}
	
	public void setTable(String table){
    	this.table = table;
    }
	
	public List<String> getList1(){
		return this.retList1;
	}
	
	public List<String> getList2(){
		return this.retList2;
	}
}
