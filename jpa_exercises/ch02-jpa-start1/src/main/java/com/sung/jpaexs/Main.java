package com.sung.jpaexs;

import static java.lang.System.out;

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
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
//      SessionFactory sf;

        try {
           

//            Long id = 9L;
//            Member mem9 = save(em, id, "jae", 30);
//            
//            id = 10L;
//            Member mem10 = save(em, id, "song", 31);
//
//            id = 11L;
//            Member mem11 = save(em, id, "lee", 32);
//            
//            id = 12L;
//            Member mem12 = save(em, id, "seo", 33);
//            
//            out.println("--------------------------------------------------------------");
//
//            
//            em.flush(); // 곧바로, CRUD가 실행이 됨.
            
//            Long id = 6L;
//            
//            Member mem10 = find(em, id); // find 메서드의 호출 결과는 이 메서드가 리턴한 클래스 객체가
////            							 // 영속 컨텍스트의 1차 캐시에 저장되어 있음을 의미!
////            mem12.setUsername("tom"); // 1차 캐시의 해당 엔티티 클래스 객체가 수정. -> 스냅샷 발생...
////            						  // 엔티티 클래스 객체 세터 메서드는 특별하다!!!
////            mem12.setAge(44); // @DynamicUpdate 적용 후, 테스트를 함!
////            
////            em.flush();
//            
////            Member mem10 = new Member();
////            mem10.setId(10L);
////            mem10.setUsername("song");
////            mem10.setAge(31);
//            
//            em.remove(mem10); // 1차 캐시, 즉 영속 상태의 엔티티 클래스 객체만 remove 함!!!
            
//            Member memberA = save(em, id, "knon", 11);
//            Member memberB = save(em, id+1, "jone", 12);
//            Member memberC = save(em, id+2, "dally", 13);
//            
//            out.println("--------------------------------------------------------------");
//
//            
//            // 중간에 JPQL 실행
//            
//            // select m from Member m 
//            List<Member> members = 
//            		em.createQuery("select m from Member m", Member.class).
//            		getResultList();
            
//            Member mem12 = find(em, id);
//            
//            em.clear();
//            
//            mem12.setUsername("good");
//            
//            em.flush();
            
            out.println("-------------------------------------------------------------");
            for (int i=0; i<60; i++) {
            	out.println("before persist: " + i);
            	Member mem = save(em, "dong", 10, RoleType.Guest);
            	out.println("cur counting: " + i);
            }
            out.println("=============================================================");

//            Member mem2 = save(em, "kim", 20, RoleType.Admin);
//            Member mem3 = save(em, "seo", 30, RoleType.Admin);
//            Member mem4 = save(em, "choi", 40, RoleType.Admin);
//            Member mem5 = save(em, "ryu", 50, RoleType.Admin);
//            Member mem6 = save(em, "ryu", 50, RoleType.Admin);
//            Member mem7 = save(em, "ryu", 50, RoleType.Admin);
//            Member mem8 = save(em, "ryu", 50, RoleType.Admin);


            
//            Long mem1Id = mem1.getId();
            
//            em.flush();
            
//            mem1.setAge(100);

            
//            out.println("=============================================================");
//            
//            try {
//            	Thread.sleep(5000);
//            } catch (Exception e) {
//            	em.clear();
//            }
//            
//            out.println("=============================================================");

//            // Select 쿼리 트랜잭션이 수행됨.
//            // 위 쿼리 수행 후, 리턴 값으로 Member 엔티티 클래스 객체를 1차 캐시로 생성
//            Member member = find(em,id);
//            
//            System.out.println("member.name: " + member.getUsername());
//            
//            // 영속 컨텍스트의 1차 캐시에 저장되어 있는 Member 엔티티 클래스 객체를 리턴
//            Member member2 = find(em,id);
//            
//            System.out.println("member2.name: " + member2.getUsername());
//            
//            find(em, id);
//            
//            Integer age = 70;
//            update(em, id, age);
//            
////            em.flush();
////            em.clear();
//            
//            delete(em, id);
            
            tx.commit(); // 더티 체킹 수행 through 영속 컨텍슽의 스냅샷 : update 쿼리 생성 후, 실행!

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.clear();
            emf.close();
        }
    }

    private static Member save(final EntityManager em, String name, Integer age, RoleType roleType) {
        Member member = new Member();
        member.setUsername(name);
        member.setAge(age);
        member.setRoleType(roleType);
        // 현재 member 객체는 비영속상태...
        
        // member 객체는 영속 상태가 됨
        em.persist(member);
        
        int a = 0;
        
        return member;
    }

    private static Member find(final EntityManager em, final Long id) {
        return em.find(Member.class, id);
    }

    private static List findList(final EntityManager em, final String query) {
        return (List) em.createQuery(query, Member.class).getResultList();
    }

    private static void update(final EntityManager em, final Long id, final Integer age) {
        Member member1 = em.find(Member.class, id);
        member1.setAge(age);
    }

    private static void delete(final EntityManager em, final Long id) {
        Member member = em.find(Member.class, id);
        if (member != null) {
            em.remove(member);
        }
    }

}
