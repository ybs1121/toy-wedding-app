package com.toy.weddingapp.domain.users.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.weddingapp.common.QueryDslUtils;
import com.toy.weddingapp.domain.users.dto.UserFindRequest;
import com.toy.weddingapp.domain.users.dto.UserResponse;
import com.toy.weddingapp.domain.users.entity.QUser;
import com.toy.weddingapp.domain.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserQueryDslRepository {

    private final JPAQueryFactory query;


    // V1
//    public Optional<User> findByUserIdAndPassword(String userId, String password) {
//        QUser quser = QUser.user;
//
//        User user = query.select(quser)
//                .from(quser)
//                .where(
//                        quser.userId.eq(userId),
//                        quser.password.eq(password))
//                .where()
//                .fetchOne();
//
//        return Optional.ofNullable(user);
//    }

    //V2
    public Optional<User> findByUserIdAndPassword(String userId, String password) {
        QUser quser = QUser.user;

        return QueryDslUtils.fetchOneOptional(
                query.select(quser)
                        .from(quser)
                        .where(
                                userIdEquals(userId),
                                passwordEquals(password))
        );
    }


    public List<UserResponse> searchUsers(UserFindRequest userFindRequest, PageRequest pageRequest) {
        QUser qUser = QUser.user;
        return query.select(
                        Projections.bean(
                                UserResponse.class,
                                qUser.userId,
                                qUser.name,
                                qUser.role
                        )
                )
                .from(qUser)
                .where(
                        userIdEquals(userFindRequest.getUserId()),
                        nameEquals(userFindRequest.getName()),
                        roleEquals(userFindRequest.getRole())
                ).orderBy(qUser.createTime.desc(), qUser.updateTime.desc().nullsLast())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

    }

    //V2 : 통계정보
    public UserResponse.Results searchUsersByResult(UserFindRequest userFindRequest) {
        QUser qUser = QUser.user;
        List<UserResponse> userList = query.select(
                        Projections.bean(
                                UserResponse.class,
                                qUser.userId,
                                qUser.name,
                                qUser.role
                        )
                )
                .from(qUser)
                .where(
                        userIdEquals(userFindRequest.getUserId()),
                        nameEquals(userFindRequest.getName()),
                        roleEquals(userFindRequest.getRole())
                ).fetch();

        Long count = query.select(
                        qUser.userId.count()
                )
                .from(qUser)
                .where(
                        userIdEquals(userFindRequest.getUserId()),
                        nameEquals(userFindRequest.getName()),
                        roleEquals(userFindRequest.getRole())
                ).fetchOne();
        JPAQuery<Long> cnt = query.select(
                        qUser.userId.count()
                )
                .from(qUser)
                .where(
                        userIdEquals(userFindRequest.getUserId()),
                        nameEquals(userFindRequest.getName()),
                        roleEquals(userFindRequest.getRole())
                );
        Pageable pageable = Pageable.ofSize(10);
        Page<UserResponse> page = PageableExecutionUtils.getPage(userList, pageable, cnt::fetchCount);

        return new UserResponse.Results(userList, (count != null) ? count : 0L);

    }

    public List<String> findUserIdsBiggerThenAvg() {
        QUser qUser = QUser.user;
        QUser userSub = new QUser("userSub");

        return query.select(qUser.userId)
                .from(qUser)
                .where(
                        qUser.age.gt(
                                JPAExpressions.select(userSub.age.avg())
                                        .from(userSub)
                        )
                )
                .fetch();
    }


    public BooleanExpression userIdEquals(String userId) {
        QUser quser = QUser.user;
        return userId == null ? null : quser.userId.eq(userId);
    }

    public BooleanExpression passwordEquals(String password) {
        QUser quser = QUser.user;
        return password == null ? null : quser.password.eq(password);
    }

    public BooleanExpression roleEquals(String role) {
        QUser quser = QUser.user;
        return role == null ? null : quser.role.eq(role);
    }

    public BooleanExpression nameEquals(String name) {
        QUser quser = QUser.user;
        return name == null ? null : quser.name.eq(name);
    }
}
