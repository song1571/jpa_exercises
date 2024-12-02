package com.sung.jpaexs;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args) {

        // JPA 프로그래밍을 하기 위해서는 항상 엔티티 매니저가 필요함!
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        
        List<Long> memIds = saveMebersAndReturnMemberIds(emf);
        printTeamNamesForMemberIds(emf, memIds);
//        EntityManager em = emf.createEntityManager();
//
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();
//
//        try {
//            
//        	Team team = new Team();
//        	team.setName("team2");
//        	em.persist(team);
//        	
//            out.println("-------------------------------------------------------------");
//
//        	Member member = new Member();
//        	member.setName("seo");
//        	///////////////////////
////            member.setTeam(team);
//            team.addMember(member);
//            ///////////////////////
////            Member gotXXX = team.getMembers().get(0);
//            
//            em.persist(member);
//            
////            Team ateam = member.getTeam();
//            
//            Long Id = member.getId();
//        	
//            out.println("-------------------------------------------------------------");
//            
////            em.flush();
//            em.clear();
//            
//            out.println("-------------------------------------------------------------");
//            
//            // eager 로딩이기 때문에,
//            // Member 엔티티 클래스 객체와 Team 엔티티 클래스 객체가 만들어져서
//            // 1차 캐시에 저장.
//            Member gotMember = em.find(Member.class, member.getId());
//            
////            out.println("-------------------------------------------------------------");
//
////            Thread.sleep(100);
//            
//            out.println("1============================================================");
//            Team gotTeam = gotMember.getTeam(); // 이 시점에 team 테이블에 대한 특정 row 쿼리를 수행
//            out.println("gotMember: " + gotMember); ///////////////////////////////////
//            out.println("2============================================================");
//            Long teamId = gotTeam.getId();
//            out.println("3============================================================");
//            String teamName = gotTeam.getName();
//            out.println("4============================================================");
//            out.println("team id: " + teamId);
//            out.println("5============================================================");
//            out.println("team name: " + teamName);
//
//            out.println("=============================================================");
//            
////            out.println("++++++++++++++++++++++++++");
////            try {
////                Thread.sleep(5000); // 5초 동안 대기
////            } catch (Exception e) {
////                em.clear(); // 예외 발생 시 em.clear() 실행
////            }
////            out.println("++++++++++++++++++++++++++");
// 
//            tx.commit(); // 더티 체킹 수행 through 영속 컨텍슽의 스냅샷 : update 쿼리 생성 후, 실행!
//            
//            int a = 1; 
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            tx.rollback();
//        } finally {
//            em.clear();
//            emf.close();
//        }
    }
    
    public static List<Long> saveMebersAndReturnMemberIds(EntityManagerFactory emf) {
    	EntityManager em =  emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
		List<Long> membersIds = new ArrayList<>();
    	
    	try {
    		tx.begin();
    		
    		Member member1 = new Member("회원1");
    		Member member2 = new Member("회원2");
    		
    		Team team1 = new Team("팀1");

    		em.persist(team1);
    		em.persist(member1);
    		em.persist(member2);
    		
    		team1.addMember(member1);
    		team1.addMember(member2);
    		
    		membersIds.add(member1.getId());
    		membersIds.add(member2.getId());
    		
    		em.flush();
    		em.clear();
    		
    		Member mem = em.find(Member.class, membersIds.get(0));
    		out.println("member 이름: " + mem.getName());

    		/*
    		select
		        member0_.MEMBER_ID as member_i1_0_0_,
		        member0_.name as name2_0_0_,
		        member0_.TEAM_ID as team_id3_0_0_,
		        team1_.TEAM_ID as team_id1_1_1_,
		        team1_.name as name2_1_1_ 
    		from
        		Member member0_ 
    		left outer join
        		Team team1_ 
            		on member0_.TEAM_ID=team1_.TEAM_ID 
    		where
        		member0_.MEMBER_ID=?
    		 */
    		out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    		out.println("member 이름: " + mem.getTeam().getMembers().get(0).getName());
    		/*
    		select
		        members0_.TEAM_ID as team_id3_0_0_,
		        members0_.MEMBER_ID as member_i1_0_0_,
		        members0_.MEMBER_ID as member_i1_0_1_,
		        members0_.name as name2_0_1_,
		        members0_.TEAM_ID as team_id3_0_1_ 
    		from
        		Member members0_ 
    		where
        		members0_.TEAM_ID=?
    		 */
    		
    		tx.commit();
    	} catch(Exception e) {
    		e.printStackTrace();
    		tx.rollback();
    	} finally {
    		em.close();
    	}
    	
    	return membersIds;
    }

    public static void printTeamNamesForMemberIds (EntityManagerFactory emf, List<Long>memIds) {
    	EntityManager em =  emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	List<Member> members = new ArrayList<>();
    	List<Team> teams = new ArrayList<>();
    	try {
    		
    		tx.begin();
    		
    		for(Long memberId : memIds) {
    			Member member = em.find(Member.class, memberId);
    			members.add(member);
    			if (teams.indexOf(member.getTeam()) == -1) {
    				teams.add(member.getTeam());
    			}
    		}
    		
    		for (Team team : teams) {
    			out.printf("팀 이름 : %s", team.getName());
    			out.println("");
    			// Team의 @OneToMany의 fetch 속성이 현재 LAZY...
    			for (Member member : team.getMembers()) { // select query...
    				out.printf("		멤버 ID:%d, 멤버 이름:%s", member.getId(),member.getName());
    				out.println("");
    			}
    		}
    		
    		tx.commit();
    	} catch (Exception e) {
    		tx.rollback();
    	} finally {
    		em.close();
    	}
    	
    }
}
