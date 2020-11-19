package application;

import java.util.Date;
import java.util.List;

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
		
		System.out.println("\n=== TESTE 2 == FINDBYDEPARTMENT");
		Department department = new Department(2,null);
		List<Seller> list = sellerDAO.findByDepartment(department);
		for(Seller obj: list)
		{
			System.out.println(obj);						
		}

		
		System.out.println("\n=== TESTE 3 == FINDALL");
		list = sellerDAO.findAll();
		for(Seller obj: list)
		{
			System.out.println(obj);						
		}
		
		System.out.println("\n=== TESTE 4 == seller insert");
		Seller newSeller = new Seller(null,"Greg","greg@gmail.com", new Date(), 4600.00, department);
		sellerDAO.insert(newSeller);
		System.out.println("Inserted! New ID =  " + newSeller.getId());
		
		
	}

}
