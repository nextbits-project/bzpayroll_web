package com.bzpayroll.global.table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bzpayroll.common.ConstValue;
import com.bzpayroll.common.db.SQLExecutor;

@Service
public class TblTermLoader {
	
	@Autowired
	private SQLExecutor db;


	Vector<TblTerm> vTemp = new Vector<TblTerm>();
	private Vector <TblTerm> vRows = new Vector <TblTerm>();
	
	public TblTerm getObjectOfID(int id) throws SQLException {
		read();
        int i = 0;        
        for (i = 0 ; i < vRows.size(); i++ ) {
            TblTerm obj = vRows.get(i);
            if (obj.getTerm() == id)
                return vRows.get(i);
        }        
        return new TblTerm();      
      
    }
	public void read() throws SQLException
	{
		Connection con;
		Statement stmt = null;
		ResultSet rs = null;
		con = db.getConnection();
		
		String sql =  " SELECT TermID,Name,Days " +
                " FROM bca_term " +
                " WHERE CompanyID = " + ConstValue.companyId +
                " AND Active =1 " +
                " ORDER BY Name";
		try {
			stmt = con.createStatement();
			rs= stmt.executeQuery(sql);
			
				while (rs.next()) {
                
                TblTerm row = new TblTerm();
                row.setTerm(rs.getInt("TermID"));
                row.setName(rs.getString("Name"));
                row.setDays(rs.getInt("Days"));
                
                vTemp.add(row);
            }
		} catch (SQLException e) {
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
					if(con != null){
					db.close(con);
					}
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 vRows = (Vector)vTemp.clone();
	}
}
