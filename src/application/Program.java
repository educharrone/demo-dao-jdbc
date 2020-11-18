package application;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Department obj = new Department(1, "Books");
		
	//	Seller seller = new Seller(1,"Bob","bppb",new Date(),0.90,obj);
		//System.out.println(seller);
		
		SellerDao sellerDAO = DaoFactory.createSellerDao();
		
		System.out.println("=== TESTE 1 == FINDBYID");
		
		Seller seller = sellerDAO.findById(3);
		System.out.println(seller);
	}

}
