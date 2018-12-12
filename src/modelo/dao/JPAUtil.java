package modelo.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	
	private JPAUtil(){
		super();
	}
	
	private static EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("banco");
	
	public static EntityManagerFactory getFabrica(){
		return fabrica;
	}
}
