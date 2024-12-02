package com.sung.jpaexs.one2one.unidirectional;

import java.util.Map;

import javax.persistence.*;

public class Main {
	
	public static void createMemberAndLocker(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		Member member1 = new Member("kim");
		em.persist(member1);
		Long memId = member1.getId();
		
		Locker locker = new Locker("locker1");
		em.persist(locker);
		Long lockerId = locker.getId();
		
		//////////////////////////
		member1.setLocker(locker);
		//////////////////////////
		
		em.flush();
		em.clear();
		
		Member member = em.find(Member.class, memId);
		Locker locker1 = member.getLocker();
		String lockerName = locker1.getName();
		
		tx.commit();
		
	}
	
	public static void main(String ...args) throws InterruptedException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

		createMemberAndLocker(emf);
	}

}
