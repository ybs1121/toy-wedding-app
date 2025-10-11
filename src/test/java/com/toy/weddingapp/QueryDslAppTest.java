package com.toy.weddingapp;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.weddingapp.domain.users.entity.QUser;
import com.toy.weddingapp.domain.users.entity.User;
import com.toy.weddingapp.domain.users.repository.UserJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class QueryDslAppTest {

    @Autowired
    UserJpaRepository userJpaRepository;
    @Autowired
    EntityManager em;

    @Test
    void contextLoads() {
        User user = new User();
        user.setUserId("tester");
        user.setPassword("tester");
        user.setName("김길동");
        user.setRole("USER");
        userJpaRepository.save(user);

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QUser qUser = QUser.user;
        User findUser = queryFactory.selectFrom(qUser).fetchOne();

        Assertions.assertNotNull(findUser);
        Assertions.assertEquals(user.getUserId(), findUser.getUserId());
    }
}
