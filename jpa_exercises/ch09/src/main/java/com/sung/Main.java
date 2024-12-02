package com.sung;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import com.sung.entity.Address;
import com.sung.entity.Member;
import com.sung.utilities.JpaBooks;

public class Main {

    static final int TEAM_NUNMBERS = 10;
    static final int MEMBER_NUNMBERS = 10;
    private static EntityManagerFactory emf = 
            Persistence.createEntityManagerFactory("jpabook");

    public static void main(String[] args) throws InterruptedException {

        List<Long> membersIds = JpaBooks.initMemberTeamSampleData(emf,
        		TEAM_NUNMBERS,
        		MEMBER_NUNMBERS);
 
        Thread.sleep(100);
        
        Long memberId = insertFavoriteFood(membersIds);
        
        Thread.sleep(100);
        
        searchFavoriteFood(memberId);
        
        Thread.sleep(100);
        
        updateFavoriteFood(memberId);
        
        Thread.sleep(100);
        
        Long insertedAddressMemberId = insertAddressAndAddressList(membersIds);
        
        updateAddress(membersIds);
        
        updateAddressList(insertedAddressMemberId);

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
            // favoriteFood
            member.getFavoriteFood().add("짬뽕");
            member.getFavoriteFood().add("떡뽁이");
            member.getFavoriteFood().add("바닐라아이스라떼");

            em.flush();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        return id;
    }

    public static void searchFavoriteFood(Long memberId) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Member member = em.find(Member.class, memberId);
            for (String str : member.getFavoriteFood()) {
                System.out.println("searchFavoriteFood, Food: " + str);
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public static void updateFavoriteFood(Long memberId) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Member member = em.find(Member.class, memberId);
            System.out.println("Member ID:" + member.getId());

            // 바닐라아이스라떼 오타 수정
            member.getFavoriteFood().remove("바닐라아이스라떼");
            member.getFavoriteFood().add("아이스 아메리카노");

            em.flush();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }

    public static Long insertAddressAndAddressList(List<Long> memberIds) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        Long memberId = -1L;

        try {
            tx.begin();

            Member member = em.find(Member.class, JpaBooks.generateRandomID(memberIds));
            memberId = member.getId();
            System.out.println("Member ID:" + member.getId());

            member.setAddress(new Address("123 Main street", "Daegu", "12345"));

            member.getAddressList().add(new Address("456 Main street", "Daegu", "67890"));
            member.getAddressList().add(new Address("789 Main street", "Daegu", "54321"));

            LocalDateTime flushBeforeTime = LocalDateTime.now();     
            em.flush();
            LocalDateTime flushAfterTime = LocalDateTime.now();

            em.clear(); // 1차 캐시를 날리기 위해서
            Member foundMember = em.find(Member.class, member.getId());
            for (Address address : foundMember.getAddressList()) {
                System.out.printf("street:%s, city:%s, zipCode:%s \n",
                        address.getStreet(), address.getCity(), address.getZipCode());
            }
            
            tx.commit();
            
//            LocalDateTime ldt = LocalDateTime.now();
//            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:sssssss");
//            
//            String formattedDateTime = ldt.format(dtf);            
            System.out.println("flush before time:" + flushBeforeTime.toString());
            System.out.println("clear after time:" + flushAfterTime.toString());

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        
        return memberId;
    }
    
    public static Long updateAddress(List<Long> memberIds) {
    	EntityManager em =  emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	Long memberId = -1L;
    	
    	try {
    		tx.begin();
    		
    		Member member = em.find(Member.class, JpaBooks.generateRandomID(memberIds));
    		memberId = member.getId();
    		member.setAddress(new Address("123 rodeo str", "Busan", "456789"));
    		em.flush();
    		tx.commit();
    	} catch (Exception e) {
    		e.printStackTrace();
    		tx.rollback();
    	} finally {
    		em.close();
    	}	
    	
    	em =  emf.createEntityManager();
    	tx = em.getTransaction();
    	
    	try {
    		tx.begin();
    		
    		Member foundMember = em.find(Member.class, memberId);
//    		Address addr = foundMember.getAddress();
    		foundMember.getAddress().setCity("Deagu");

    		/* 아래의 코드로 foundMember 객체의 address 필드의 필드를 수정한 값이 JAP에 의해 적용되지 않음!!!
    		 --> jpa 2.2에서 된다... 확인 요망
    		addr.setStreet("dddd");
    		addr.setZipCode("11111");
    		addr.setCity("Daegu");
    		*/
    		
//    		addr.setStreet("dddd");
//    		addr.setZipCode("11111");
//    		addr.setCity("Daegu");
    		
//    		foundMember.setAddress(new Address(addr.getStreet(), "Daegu", addr.getZipCode()));
//    		em.flush();
    		
    		tx.commit();
    	} catch (Exception e) {
    		e.printStackTrace();
    		tx.rollback();
    	} finally {
    		em.close();
    	}
    	
    	return memberId;
    }
    
    public static void updateAddressList(Long memberId) {

    	EntityManager em =  emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	try {
    		tx.begin();
    		Member foundMember = em.find(Member.class, memberId);
    		
    		List<Address> addrList = foundMember.getAddressList();
    		int targetRemovalElementIndex = -1;
    		for (int i=0; i<addrList.size(); i++) {
    			Address addr = addrList.get(i);
    			if (addr.equals(new Address("456 Main street", "Daegu", "67890"))) {
    				targetRemovalElementIndex = i;
    				break;
    			}
    		}
    		
    		if (targetRemovalElementIndex != -1) {
    			Address add = addrList.remove(targetRemovalElementIndex);
//    			em.remove(add);
    		}
    		
    		foundMember.getAddressList().add(new Address("347 Jongro stree",
    													 "Seoul", "2322l"));
    		
    		tx.commit();
    	} catch(Exception e) {
    		e.printStackTrace();
    		tx.rollback();
    	} finally {
    		em.close();
    	}
    }
    
}
