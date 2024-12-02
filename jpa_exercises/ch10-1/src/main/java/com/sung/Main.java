package com.sung;

import java.util.List;

import com.querydsl.core.Tuple;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sung.entity.Address;
import com.sung.entity.AddressEntity;
import com.sung.entity.FavoriteFood;
import com.sung.entity.Member;
import com.sung.entity.Team;
import com.sung.entity.UserDTO;
import com.sung.qentity.QMember;
import com.sung.qentity.QTeam;
import com.sung.utilities.JpaBooks;

public class Main {

	static final int TEAM_NUMBERS = 10;

	static final int MEMBER_NUMBERS = 100;

	static final int POST_NUMBERS = 10;

	static final int COMMENT_NUMBERS = 10;

	static final long POST_STRING_MAX_SIZE = 1500L;

	static final long COMMENT_STRING_MAX_SIZE = 300L;

	private static EntityManagerFactory emf =
			Persistence.createEntityManagerFactory("jpabook");

	public static void main(String ...args) throws InterruptedException {


//		List<Long> membersIds = JpaBooks.initMemberTeamSampleData(emf,
//				TEAM_NUMBERS,
//				MEMBER_NUMBERS);

//		queryMemberOfTypedQuery();

//		queryColumnsOfQuery();

//		queryParameterBounding(membersIds);

//		useUserDTO(membersIds); 

//		testJPACriteria(membersIds);

//		getSingleRelationalShipEntity();

//		testInnerJoin();

//		testLeftOuterJoin();

//		testCrossJoin();

//		testAggregateFunction();

//		testGroupbyHavingPrderby();

//		setSubQueryUnSelect();

//		testSubQueryInWhere();

//		testSubQueryInFromAlternate();

//		testQueryDSLInsert();

//		testQueryDSLDelete();

//		testQueryDSLX();

//		testPagingAPIByJPQL();

//		testPagingAPIByQueryDSL();

//		testPaginaAPIWithoutOffsetByQueryDsl();

//		testFetchJoinByJPQL();

//		testFetchJoinByQueryDsl();

//		testCollectionFetchJoin();]

		List<Long> postIds = JpaBooks.initPostCommentSampleData(emf,
				POST_NUMBERS,
				POST_STRING_MAX_SIZE,
				COMMENT_NUMBERS,
				COMMENT_STRING_MAX_SIZE);

		emf.close();

	}

	public static void queryMemberOfTypedQuery() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			// JPQL을 사용하여 Member 엔티티 조회
			TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
			List<Member> members = query.getResultList();

			// 조회된 결과 출력
			for (Member m : members) {
				System.out.printf("Member ID: %d, Name: %s \n", m.getId(), m.getName());
			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}
	}

	public static void queryColumnsOfQuery() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			Query query = em.createQuery("SELECT m.name, m.age FROM Member m");
			List<Object[]> resultList = query.getResultList();

			for (Object[] result : resultList) {
				String name = (String) result[0];
				Integer age = (Integer) result[1];
				System.out.printf("name: %s, age: %d \n", name, age);
			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}
	}

	public static void queryParameterBounding(List<Long> membersIds) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			// Random memberId로 Member 엔티티 조회
			Long memberId = JpaBooks.generateRandomID(membersIds);
			Member foundMember = em.find(Member.class, memberId);
			em.clear();

			// 찾은 멤버의 이름으로 파라미터 바인딩
			String userNameParam = foundMember.getName();
			System.out.println("찾은 회원 이름: " + userNameParam);

			// JPQL을 사용한 파라미터 바인딩 쿼리
			List<Member> members = em.createQuery("SELECT m FROM Member m WHERE m.name = :name", Member.class)
					.setParameter("name", userNameParam)  // 파라미터 바인딩
					.getResultList();

			// 조회된 결과 출력
			for (Member m : members) {
				System.out.printf("조회된 회원 이름: %s \n", m.getName());
			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}
	}

	public static void useUserDTO() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			TypedQuery<UserDTO> query = em.createQuery(
					"SELECT new com.jinsu.entity.UserDTO(m.name, m.age)FROM Member m",
					UserDTO.class);

			List<UserDTO> resultList = query.getResultList();
			for (UserDTO d : resultList) {
				System.out.println("name = " + d.getName());
				System.out.println("age = " + d.getAge());
			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}
	}

	public static void testJPAcriteria(List<Long> membersIds) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			Long memberId = JpaBooks.generateRandomID(membersIds);
			Member foundMember = em.find(Member.class, memberId);
			em.clear();

			String name = foundMember.getName();
			System.out.println("name=" + name);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Member> cq = cb.createQuery(Member.class);

			Root<Member> member = cq.from(Member.class);
			cq.
					select(member).
					where(cb.equal(member.get("neme"), name));

			List<Member> members = em.createQuery(cq).getResultList();
			for (Member m : members) {
				System.out.println("member name: " + m.getName());
			}


			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}
	}

	public static void getSingleRelationalShipEntity() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();


			TypedQuery<Team> query = em.createQuery("SELECT m.team FROM Member m", Team.class);

			List<Team> teamList = query.getResultList();
			for (Team t : teamList) {
				System.out.printf("Team name:" + t.getName());
			}


			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}


	}

	public static void testInnerJoin() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			List<Team> teamList = em.createQuery(
					"Select distinct t from Member m join m.team t",
					Team.class).getResultList();

			for (Team t : teamList) {
				System.out.println("team = " + t.getName());
			}


			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}

	}

	public static void testLeftOuterJoin() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
//
//             List<Member> memList = em.createQuery(
//            		 				"Select m from Member m left outer join m.team t",
//            		 				Member.class).getResultList();
//
//             for(Member m: memList) {
//            	 System.out.println("Member name = " + m.getName());
//             }
//
			@SuppressWarnings("unchecked")
			List<Object[]> objsList =
					em.createQuery("Select m, t from Member m left outer join m.team t")
							.getResultList();

			for (Object[] objs : objsList) {
				Member m = (Member) objs[0];
				Team t = (Team) objs[1];
				System.out.printf("member name:%s, team name:%s \n",
						m.getName(), t.getName());
			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}
	}

	public static void testCrossJoin() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			String query = "select m, t from Member m, Team t";

			@SuppressWarnings("unchecked")
			List<Object[]> objsList =
					em.createQuery(query)
							.getResultList();

			for (Object[] objs : objsList) {
				Member m = (Member) objs[0];
				Team t = (Team) objs[1];
				System.out.println("member:" + m + "team:" + t);


			}


			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}
	}

	public static void testAggregateFunction() {

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			TypedQuery<Long> sumQuery =
					em.createQuery("select sum(m.age) from Member m", Long.class);

			Long totalAge = sumQuery.getSingleResult();
			System.out.println("Sum of Age:" + totalAge);

			Double averageAge =
					em.createQuery("select avg(m.age) from Member m", Double.class)
							.getSingleResult();
			System.out.println("Avg of Age:" + averageAge);

			Integer maxValue =
					em.createQuery("select max(m.age) from Member m", Integer.class)
							.getSingleResult();
			System.out.println("max of Age:" + maxValue);

			Integer minValue =
					em.createQuery("select min(m.age) from Member m", Integer.class)
							.getSingleResult();
			System.out.println("min of Age:" + minValue);


			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}

	}


	public static void testGroupbyHavingOrderby() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			String query =
					"SELECT t.name, AVG(m.age) " +        // 평균 나이가 25세 이상인 팀을을 쿼리
							"FROM Team t JOIN t.memberList m " +  // 팀에 속한 멤버들에서부터 시작.
							"GROUP BY t.name " +               // 팀 이름별로 그룹핑
							"HAVING AVG(m.age) > 30 " +        // 평균 나이가 30세 이상
							"ORDER BY AVG(m.age) DESC ";

			@SuppressWarnings("unchecked")
			List<Object[]> objsList =
					em.createQuery(query)
							.getResultList();

			for (Object[] objs : objsList) {
				String teamName = (String) objs[0];
				Double avgAge = (Double) objs[1];
				System.out.println("Team" + teamName + ", Average Age: " + avgAge);
			}


			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}
	}

	public static void setSubQueryInSelect() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			// 각 Member의 name과 해당 Member가 속한 Team의 총 Member 수를 구하는 쿼리
			String query =
					"SELECT m.name, " +
							"(SELECT COUNT(subM) FROM Member subM WHERE subM.team = m.team) " +
							"AS teamMemberCount " +
							"FROM Member m ";

			@SuppressWarnings("unchecked")
			List<Object[]> objsList =
					em.createQuery(query)
							.getResultList();

			for (Object[] objs : objsList) {
				String memName = (String) objs[0];
				Long count = (Long) objs[1];
				System.out.println("Member name: " + memName + ", count: " + count);
			}


			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}
	}

	public static void testSubQueryInWhere() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			// Team의 가장 나이가 많은 Member를 찾는 쿼리
			String query =
					"SELECT m " +
							"FROM Member m " +
							"WHERE m.age = (SELECT MAX(subM.age) FROM Member subM WHERE subM.team = m.team)";


			List<Member> memList =
					em.createQuery(query, Member.class)
							.getResultList();

			for (Member m : memList) {
				System.out.println("Team: " + m.getTeam() +
						", Member: " + m.getName() +
						", Max Age: " + m.getAge());
			}


			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}
	}

	/*SELECT m.name, subQuery.teamName
    FROM Member m,
         (SELECT t.TEAM_ID, t.name AS teamName
          FROM Team t
          JOIN Member m ON t.TEAM_ID = m.TEAM_ID
          GROUP BY t.TEAM_ID, t.name
          HAVING AVG(m.age) > 30) AS subQuery
    WHERE m.TEAM_ID = subQuery.TEAM_ID;*/
	public static void testSubQueryInFromAlternate() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			// 네이티브 SQL 쿼리로 팀의 멤버 평균 나이가 30 이상인 팀 이름과 그 팀에 속한 멤버들의 이름을 출력
			String query =
					"SELECT m.name, subQuery.teamName " +
							"FROM Member m " +
							"JOIN (SELECT t.TEAM_ID AS team_id, t.name AS teamName " +
							"      FROM Teams t " +
							"      JOIN Member m ON t.TEAM_ID = m.TEAM_ID " +
							"      GROUP BY t.TEAM_ID, t.name " +
							"      HAVING AVG(m.age) > 30) subQuery " +
							"ON m.TEAM_ID = subQuery.team_id";

			// 네이티브 쿼리 실행
			Query nativeQuery = em.createNativeQuery(query);
			@SuppressWarnings("unchecked")
			List<Object[]> objsList = nativeQuery.getResultList();

			for (Object[] objs : objsList) {
				String memName = (String) objs[0];
				String teamName = (String) objs[1];
				System.out.println("Member name: " + memName + ", Team name: " + teamName);
			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();  // EntityManager 닫기
		}
	}
	public static void testQueryDSLSelect() {
		EntityManager em = emf.createEntityManager();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		EntityTransaction tx = em.getTransaction(); // 트랜잭션 가져오기

		try {
			tx.begin();  // 트랜잭션 시작

			Member member = new Member();
			member.setName("sungwon");
			member.setAge(20);

			em.persist(member);  // 데이터 저장
			em.flush();
			em.clear();

			QMember qMember = QMember.member;
			List<Member> memberList = queryFactory
					.select(qMember)
					.where(qMember.id.eq(member.getId()))
					.fetch();

			for (Member mem : memberList) {
				System.out.println("Member name: " + mem.getName());
			}

			tx.commit();  // 트랜잭션 커밋
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();  // 에러 발생 시 롤백
			}
		} finally {
			if (em.isOpen()) {
				em.close();  // EntityManager 닫기
			}
		}
	}

	public static void testQueryDSLInsert() {
		EntityManager em = emf.createEntityManager();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		EntityTransaction tx = em.getTransaction(); // 트랜잭션 가져오기

		try {
			tx.begin();  // 트랜잭션 시작

			Member member = new Member();
			member.setName("1stQueryDSLInsert");
			member.setAge(30);

			em.persist(member);  // 데이터 저장

			tx.commit();  // 트랜잭션 커밋
			System.out.println("Insert complete: " + member.getName());
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();  // 에러 발생 시 롤백
			}
		} finally {
			if (em.isOpen()) {
				em.close();  // EntityManager 닫기
			}
		}
	}

	public static void testQueryDSLUpdate() {
		EntityManager em = emf.createEntityManager();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		EntityTransaction tx = em.getTransaction(); // 트랜잭션 가져오기

		try {
			tx.begin();  // 트랜잭션 시작

			Member member = new Member();
			member.setName("1stQueryDSLUpdate");
			member.setAge(30);

			em.persist(member);  // 데이터 저장
			em.flush();
			em.clear();

			QMember qMember = QMember.member;
			String newName = "New Name";
			long affectedRows = queryFactory
					.update(qMember)
					.set(qMember.name, newName)
					.where(qMember.id.eq(member.getId()))
					.execute();

			tx.commit();  // 트랜잭션 커밋
			System.out.println("Updated Rows: " + affectedRows);
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();  // 에러 발생 시 롤백
			}
		} finally {
			if (em.isOpen()) {
				em.close();  // EntityManager 닫기
			}
		}
	}

	public static void testQueryDSLDelete() {
		EntityManager em = emf.createEntityManager();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		EntityTransaction tx = em.getTransaction(); // 트랜잭션 가져오기

		try {
			tx.begin();  // 트랜잭션 시작

			Member member = new Member();
			member.setName("sungwon");
			member.setAge(30);

			em.persist(member);  // 데이터 저장
			em.flush();
			em.clear();

			QMember qMember = QMember.member;
			long affectedRows = queryFactory
					.delete(qMember)
					.where(qMember.id.eq(member.getId()))
					.execute();

			tx.commit();  // 트랜잭션 커밋
			System.out.println("Deleted Rows: " + affectedRows);
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();  // 에러 발생 시 롤백
			}
		} finally {
			if (em.isOpen()) {
				em.close();  // EntityManager 닫기
			}
		}
	}

	public static void testQueryDSLX() {
		EntityManager em = emf.createEntityManager();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			QTeam team = QTeam.team;
			QMember member = QMember.member;

			List<Tuple> result = queryFactory
					.select(team.name, member.age.avg())
					.from(team)
					.join(team.memberList, member)
					.groupBy(team.name)
					.having(member.age.avg().goe(30))
					.orderBy(member.age.avg().desc())
					.fetch();

			for (Tuple tuple : result) {
				String teamName = tuple.get(team.name);
				Double avgAge = tuple.get(member.age.avg());

				System.out.println("Team: " + teamName + ", Avg Age: " + avgAge);
			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
		} finally {
			if (em.isOpen()) {
				em.close();
			}
		}
	}

	public static void testPagingAPIByJPQL() {

		EntityManager em =  emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m ORDER BY m.id DESC", Member.class)
					.setFirstResult(10)
					.setMaxResults(20);

			List<Member> memberList = query.getResultList();

			for (Member member : memberList) {
				System.out.println("member = " + member.getId());
			}

			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public static void testPagingAPIByQueryDSL() {

		EntityManager em = emf.createEntityManager();
		QTeam team = QTeam.team;
		QMember member = QMember.member;
		EntityTransaction tx = em.getTransaction();

		JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

		try
		{
			tx.begin();

			//QueryDSL에서 정의한 클래스;
			// 파이썬에서 tuple을 지원함: List임. 그러나 불변객체()
			List<Member> memberList = jpaQueryFactory
					.selectFrom(member)
					.orderBy(member.id.asc())
					.offset(0)
					.limit(20)
					.fetch();
			for(Member m : memberList)
			{

				System.out.println("Member: " + m.toString());
			}
			tx.commit();
		}

		catch (Exception e)
		{
			tx.rollback();
			e.printStackTrace();

			// TODO: handle exception
		} finally {
			em.close();
		}
	}

	public static Long getLastIdofMember() {
		EntityManager em = emf.createEntityManager();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		EntityTransaction tx = em.getTransaction();

		Long lastMemberId = -1L;
		try {
			tx.begin();


			QMember member = QMember.member;
			lastMemberId = queryFactory
					.select(member.id)
					.from(member)
					.orderBy(member.id.desc())
					.limit(1)
					.fetchOne();


			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

		return lastMemberId;
	}

	public static Long getPagedMembers(Long lastMemberId, int limit) {
		EntityManager em = emf.createEntityManager();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		EntityTransaction tx = em.getTransaction();
		List<Member> members = null;

		try {
			tx.begin();

			QMember member = QMember.member;

			if (lastMemberId == null) {
				members = queryFactory
						.selectFrom(member)
						.orderBy(member.id.asc())
						.limit(limit)
						.fetch();
			} else {
				members = queryFactory
						.selectFrom(member)
						.where(member.id.gt(lastMemberId))
						.orderBy(member.id.asc())
						.limit(limit)
						.fetch();
			}

			for (Member m : members) {
				System.out.println(m);
			}

			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

		if (members != null) {
			if (!members.isEmpty()) {
				// members.size() - 1는 members 리스트의 마지막 엘리먼트 인덱스 값
				return members.get(members.size() - 1).getId();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static void testPaginaAPIWithoutOffsetByQueryDsl() {
		Long queryLastMemberId = getLastIdofMember();

		int pageSize = 20;

		// 총 페이지 수
		int totalPages = (MEMBER_NUMBERS + pageSize - 1) / pageSize; // 올림 계산

		Long lastMemberId = null;  // 첫 페이지의 페이징을 위한 코드...

		for (int currentPage = 1; currentPage <= totalPages; currentPage++) {
			System.out.println("Page " + currentPage + ":");

			lastMemberId = getPagedMembers(lastMemberId, pageSize);
		}

		if (queryLastMemberId == lastMemberId) {
			System.out.println("Okay");
		}
	}

	public static void testFetchJoinByJPQL() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			// JPQL을 사용한 fetch join 쿼리 실행
			List<Member> memberList = em.createQuery(
							"SELECT m FROM Member m JOIN FETCH m.team", Member.class)
					.getResultList();

			// 결과 출력
			for (Member m : memberList) {
				Team team = m.getTeam();
				System.out.println("Member name=" + m.getName() +
						", team id=" + team.getId() +
						", name=" + team.getName());
			}

			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

	public static void testFetchJoinByQueryDsl() {   //

		EntityManager em =  emf.createEntityManager();
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			QMember member = QMember.member;
			QTeam team = QTeam.team;

			List<Member> memberList = queryFactory
					.selectFrom(member)
							.join(member.team, team).fetchJoin()
							.fetch();

			for (Member m : memberList) {
				Team t = m.getTeam();
				System.out.println("Member name= " + m.getName() + ", team id = " + t.getId() + ",name = " + t.getName());
			}

			tx.commit();
		} catch(Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}

	}

	public static void testCollectionFetchJoin() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		String nameParameter = "team:5";

		try {
			tx.begin();

//			List<Team> teams = em.createQuery("select t from Team t join fetch t.memberList", Team.class)
//							.getResultList();
//
			// One : team
			// Many : Member
			// OneToMany join : 중복된 결과값이 발생함!!!
//			List<Team> teams = em.createQuery("select t from Team t join fetch t.memberList where t.name = :name",
//							Team.class)
//					.setParameter("name", nameParameter)
//			        .getResultList();

			List<Team> teams =
					em.createQuery("select distinct t from Team t join fetch t.memberList where t.name = :name",
									Team.class)
							.setParameter("name", nameParameter)
							.getResultList();

			for (Team t : teams) {
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				System.out.printf("Team id:%d \n", t.getId());
				for (Member m : t.getMemberList()) {
					System.out.printf("Member id:%d, Team name:%s \n", m.getId(), m.getTeam());
				}
				System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

			}


			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		} finally {
			em.close();
		}
	}

}
