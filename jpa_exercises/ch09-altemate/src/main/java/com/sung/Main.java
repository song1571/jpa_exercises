package com.sung;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.sung.entity.Address;
import com.sung.entity.AddressEntity;
import com.sung.entity.FavoriteFood;
import com.sung.entity.Member;
import com.sung.utilities.JpaBooks;

public class Main {
	
	static final int TEAM_NUMBERS = 10;
	
	static final int MEMBER_NUMBERS = 10;
	
	private static EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("jpabook");
	
	public static void main(String ...args) throws InterruptedException {		
			
		
		List<Long> membersIds = JpaBooks.initMemberTeamSampleData(emf, 
				TEAM_NUMBERS, 
				MEMBER_NUMBERS);
		
		Long memberId = insertFavoriteFood(membersIds);	
		
		updateAddressList(memberId);
			
		emf.close();
		
	}
	
	public static Long insertFavoriteFood(List<Long> memberIds) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		Long id = -1L;
		try {
			tx.begin();
			
			Member member = em.find(Member.class, JpaBooks.generateRandomID(memberIds));
			id = member.getId();
			
			FavoriteFood pizza = FavoriteFood.builder()
					.foodName("피자")
					.member(member)
					.build();
			
			FavoriteFood donkatsu = FavoriteFood.builder()
					.foodName("돈까스")
					.member(member)
					.build();
			
			FavoriteFood jajangmyeon = FavoriteFood.builder()
					.foodName("짜장면")
					.member(member)
					.build();
			
			member.getFavoriteFoods().add(pizza);
			member.getFavoriteFoods().add(donkatsu);
			member.getFavoriteFoods().add(jajangmyeon);
			
			AddressEntity address1 = AddressEntity.builder()
					.address(new Address("456 Elm st", "Gwangju", "12345"))
					.member(member)
					.build();
			
			AddressEntity address2 = AddressEntity.builder()
					.address(new Address("789 Eldo st", "Daejeon", "67890"))
					.member(member)
					.build();
			
			member.getAddressList().add(address1);
			member.getAddressList().add(address2);
			
			em.persist(member);					
			
			em.flush();						
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
//			emf.close();
		}		
		
		return id;
	}
			
	public static void updateAddressList(Long memberId) {
		
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		try {
			tx.begin();
			
			Member member = em.find(Member.class, memberId);
			
			List<AddressEntity> addrList = member.getAddressList();
			if (!addrList.isEmpty()) {
				AddressEntity addressToRemove = addrList.get(0);
				addrList.remove(addressToRemove);
				//em.remove(addressToRemove);
			}
			
			AddressEntity newAddress = AddressEntity.builder()
					.address(new Address("St Free", "Newyork", "23231"))
					.member(member)
					.build();
			
			addrList.add(newAddress);
			
			em.flush();			
			
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
		
	}
}
