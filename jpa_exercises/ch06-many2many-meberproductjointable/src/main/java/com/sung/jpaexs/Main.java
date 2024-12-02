package com.sung.jpaexs;

import static java.lang.System.out;

import java.util.List;

import javax.persistence.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class Main {
	
	public static void main(String ...args) throws InterruptedException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
		
			tx.begin();
			
			Member member1 = new Member();
			member1.setName("kim");
			em.persist(member1);
			Long memId1 = member1.getId();
			
			Member member2 = new Member();
			member2.setName("lee");
			em.persist(member2);
			Long memId2 = member2.getId();
			
			Product product1 = new Product();
			product1.setName("hwang");
			em.persist(product1);
			Long productId1 = product1.getId();
			
			Product product2 = new Product();
			product2.setName("shampu");
			em.persist(product2);
			Long productId2 = product2.getId();
			
			MemberProduct memberProduct1 = new MemberProduct();
			memberProduct1.setMember(member1);
			memberProduct1.setProduct(product1);
			memberProduct1.setOrderAmount(10);
			em.persist(memberProduct1);
			
			MemberProduct memberProduct2 = new MemberProduct();
			memberProduct2.setMember(member2);
			memberProduct2.setProduct(product2);
			memberProduct2.setOrderAmount(10);
			em.persist(memberProduct2);
			
			MemberProduct memberProduct3 = new MemberProduct();
			memberProduct3.setMember(member1);
			memberProduct3.setProduct(product2);
			memberProduct3.setOrderAmount(10);
			em.persist(memberProduct3);
			
			MemberProduct memberProduct4 = new MemberProduct();
			memberProduct4.setMember(member2);
			memberProduct4.setProduct(product1);
			memberProduct4.setOrderAmount(10);
			em.persist(memberProduct4);
			
			em.flush();
			em.clear();
			///////////////////////////////////////////////////
//			Member member = em.find(Member.class, memId1);
//			List<MemberProduct> memberProducts1 = member.getMemberProducts();
//			for (MemberProduct mp : memberProducts1) {
//				Product product = mp.getProduct();
//	//			mp.getMember()
//				out.printf("상품 id:%d, 상품 name:%s \n", product.getId(),product.getName());
//			}
			
			Product product = em.find(Product.class, productId1);
			List<MemberProduct> memberProducts2 = product.getMemberProducts();
			for (MemberProduct mp : memberProducts2) {
			    Member member = mp.getMember();
			    out.printf("멤버 id: %d, 멤버 name: %s \n", member.getId(), member.getName());
			}
		tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
	        emf.close();
		}
	}

}
