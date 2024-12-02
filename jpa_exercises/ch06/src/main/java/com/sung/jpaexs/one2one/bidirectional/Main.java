package com.sung.jpaexs.one2one.bidirectional;

import java.util.Map;

import javax.persistence.*;

public class Main {
	
	public static void createTravelerAndPassport(EntityManagerFactory emf) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		Traveler traveler1 = new Traveler("kim");
		em.persist(traveler1);
		Long travId = traveler1.getId();
		
		PassPort passPort = new PassPort("passport1");
		em.persist(passPort);
		Long passId = passPort.getId();
		
		////////////////////////////////
		traveler1.setPassPort(passPort);
		////////////////////////////////
		
		em.flush();
		em.clear();
		
//		Traveler traveler2 = em.find(Traveler.class, travId);
//		PassPort passPort1 = traveler.getPassPort();

		PassPort passPort2 = em.find(PassPort.class, passId);
		Traveler traveler2 = passPort.getTraveler();

		
		tx.commit();
		
	}
	
	public static void main(String ...args) throws InterruptedException {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

		createTravelerAndPassport(emf);
	}

}
