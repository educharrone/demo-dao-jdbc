package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.Statement;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao{

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
		
	}
	
	
	@Override
	public void insert(Seller obj) {
		
		PreparedStatement st = null;
		String sql = "INSERT INTO seller "
		           + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
		           + "VALUES (?, ?, ?, ?, ?)";
		
		try {

			st = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
					
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());
			
			int rowsAffects = st.executeUpdate();
			
			if(rowsAffects>0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) {
					int id = rs.getInt(1);
					obj.setId(id);
					DB.closeResultset(rs);
				}else {
					throw new  DbException("Unexpected error! No row affected!");
				}
				
			}
			
			
		} catch(SQLException e  ) {
			throw new  DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(st);
			
			
		}

	
		
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		// TODO Auto-generated method stub

		
		PreparedStatement st = null;
		ResultSet rs = null; 
		try {
			String sql = "SELECT seller.*,department.Name as DepName " + 
					"FROM seller INNER JOIN department " + 
					"ON seller.DepartmentId = department.Id " + 
					"WHERE seller.Id = ?";

			st=conn.prepareStatement(sql)	;
		
			st.setInt(1, id);
			rs=st.executeQuery();
		if(rs.next())
		{
			Department dep = instantiateDepartment(rs);
			
			Seller obj  = instantiateSeller(rs,dep);
			return obj;
		}
		
		return null;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(st);
			DB.closeResultset(rs);
			
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setName(rs.getString("Name"));
		seller.setEmail(rs.getNString("Email"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setDepartment(dep);
		return seller;
	}


	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
	}


	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null; 
		try {
			String sql  = "SELECT seller.*,department.Name as DepName"
				        + " FROM seller INNER JOIN department"
			            + " ON seller.DepartmentId = department.Id"
					        + " ORDER BY Name";

			st=conn.prepareStatement(sql)	;
		
			rs=st.executeQuery();
		
		List<Seller> list = new ArrayList<>();	
		Map<Integer,Department> map  = new HashMap<>();
		
		
		while(rs.next())
		{
			Department dep = map.get(rs.getInt("DepartmentId"));
			
			if(dep==null) {
				dep = instantiateDepartment(rs);
				map.put(rs.getInt("DepartmentId"), dep);
			}
			
			//Department dep = instantiateDepartment(rs);
			
			Seller obj  = instantiateSeller(rs,dep);
			list.add(obj);
		}
		
		return list;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(st);
			DB.closeResultset(rs);
			
		}
}


	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null; 
		try {
			String sql  = "SELECT seller.*,department.Name as DepName"
				        + " FROM seller INNER JOIN department"
			            + " ON seller.DepartmentId = department.Id"
				        + " WHERE DepartmentId = ?"
				        + " ORDER BY Name";

			st=conn.prepareStatement(sql)	;
		
			st.setInt(1, department.getId());
			rs=st.executeQuery();
		
		List<Seller> list = new ArrayList<>();	
		Map<Integer,Department> map  = new HashMap<>();
		
		
		while(rs.next())
		{
			Department dep = map.get(rs.getInt("DepartmentId"));
			
			if(dep==null) {
				dep = instantiateDepartment(rs);
				map.put(rs.getInt("DepartmentId"), dep);
			}
			
			//Department dep = instantiateDepartment(rs);
			
			Seller obj  = instantiateSeller(rs,dep);
			list.add(obj);
		}
		
		return list;
		}catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatment(st);
			DB.closeResultset(rs);
			
		}
	}


	

}
