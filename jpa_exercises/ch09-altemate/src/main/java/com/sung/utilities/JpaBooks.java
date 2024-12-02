package com.sung.utilities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.sung.entity.Member;
import com.sung.entity.Team;

public class JpaBooks {

    static final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static List<Long> initMemberTeamSampleData(EntityManagerFactory emf,
    		int teamNumbers,
    		int memberNumbers) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        List<Long> memberIds = new ArrayList<>();
        tx.begin();

        try {
            List<Long> teamIds = new ArrayList<>();

            for (int i = 0; i < teamNumbers; i++) {
                Team team = new Team();
                team.setName("team:" + i);
                em.persist(team);
                teamIds.add(team.getId());
            }

            Long minIdvalue = Collections.min(teamIds);
            Long maxIdValue = Collections.max(teamIds);

            for (int i = 0; i < memberNumbers; i++) {
                Member member = new Member();
                member.setName("kim:" + i);
                Long targetTeamId = generateRandomNumber(minIdvalue, maxIdValue);

                Team team = em.find(Team.class, targetTeamId);
                team.addMember(member);
                em.persist(member);
                memberIds.add(member.getId());
            }

            em.flush();
//            em.clear();

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        return memberIds;
    }

    public static Long generateRandomNumber(Long min, Long max) {
        Random random = new Random();
        return random.nextLong(max - min + 1) + min;
    }

    public static Long generateRandomID(List<Long> ids) {
        Long minIdvalue = Collections.min(ids);
        Long maxIdValue = Collections.max(ids);
        return generateRandomNumber(minIdvalue, maxIdValue);
    }
}
