package com.sung;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.sung.entity.Member;

public class Main {
	
	public static void main(String ...args) throws InterruptedException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Member member = new Member();
			member.setName("lee");
			em.persist(member);
			
			em.flush();
			em.clear();
			
			Member foundMem = em.find(Member.class, member.getId());
			
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.clear();
		}
		emf.close();
	}
}
