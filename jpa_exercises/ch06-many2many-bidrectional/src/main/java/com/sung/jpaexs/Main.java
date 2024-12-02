package com.sung.jpaexs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
	
	public static void main(String ...args) throws InterruptedException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		Member member = new Member();
		member.setName("kim");
		em.persist(member);
		Long memId = member.getId();
		
		
		
		Product product = new Product();
		product.setName("hwang");
		em.persist(product);
		Long productId = product.getId();
		
		member.getProducts().add(product);
		
		product.getMember().add(member);
		
		em.flush();
		em.clear();
		
//		Member foundMember1 = em.find(Member.class, memId);
//		List<Product> productList = foundMember1.getProducts();
//		
//		for (Product itme : productList) {
//			String name = itme.getName();
//		}
		
		Product foundProduct = em.find(Product.class, productId);
		String name = foundProduct.getName();
		
		//////////////////////////////////////////////
		Member mem =  foundProduct.getMember().get(0);
		//////////////////////////////////////////////

		tx.commit();
		
	}

}
