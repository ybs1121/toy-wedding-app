package com.toy.weddingapp.domain.invitation.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.toy.weddingapp.common.constant.Status;
import com.toy.weddingapp.domain.invitation.dto.InvitationResponse;
import com.toy.weddingapp.domain.invitation.dto.InvitationStatusStatisticsResponse;
import com.toy.weddingapp.domain.invitation.entity.QInvitation;
import com.toy.weddingapp.domain.weddings.dto.WeddingResponse;
import com.toy.weddingapp.domain.weddings.entity.QWeddings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class InvitationQuerydslRepository {

    private final JPAQueryFactory query;


    public InvitationStatusStatisticsResponse getInvitationStatusStatistics() {
        QInvitation qInvitation = QInvitation.invitation;

//        query.select(
//                qInvitation.count()
//                ).from(qInvitation)
//                .groupBy(qInvitation.status)
//                .fetch();

        Tuple result = query.select(
                        qInvitation.status.when(Status.WAITING).then(1L).otherwise(0L).sum(),
                        qInvitation.status.when(Status.PROGRESS).then(1L).otherwise(0L).sum(),
                        qInvitation.status.when(Status.CLOSED).then(1L).otherwise(0L).sum()
                )
                .from(qInvitation)
                .fetchOne();
//
//        // 만약 날짜 기준으로 날짜가 임박한지 알고 싶어요 기준 (현재 기준으로 90일 이상 남으면 여유, 90~30일 남으면 곧 예정, 30일이면 임박
//        NumberTemplate<Integer> daysLeft = Expressions.numberTemplate(
//                Integer.class,
//                "DATEDIFF({0}, {1})",
//                qInvitation.weddings.weddingDate,
//                LocalDate.now()
//        );
//
//        StringExpression status = new CaseBuilder()
//                .when(daysLeft.goe(90))
//                .then("RELAXED")      // 90일 이상 남음: 여유
//                .when(daysLeft.between(30, 89))
//                .then("UPCOMING")     // 30~89일: 곧 예정
//                .when(daysLeft.lt(30))
//                .then("IMMINENT")     // 30일 미만: 임박
//                .otherwise("UNKNOWN")
//                .as("status");
//
//        query.select(
//                        status
//                )
//                .from(qInvitation)
//                .fetchOne();


        InvitationStatusStatisticsResponse response = new InvitationStatusStatisticsResponse();

        if (result != null) {
            response.setWaitingCount(result.get(0, Long.class));
            response.setProgressCount(result.get(1, Long.class));
            response.setClosedCount(result.get(2, Long.class));
        } else {
            response.setWaitingCount(0L);
            response.setProgressCount(0L);
            response.setClosedCount(0L);
        }


        return response;

    }

    public List<InvitationResponse> searchInvitations() {
        QInvitation qInvitation = QInvitation.invitation;
        QWeddings qWeddings = QWeddings.weddings;
        return query.select(Projections.bean(
                        InvitationResponse.class,
                        qInvitation.id,
                        qInvitation.url,
                        qInvitation.shortUrl,
                        qInvitation.templateId,
                        qInvitation.message,
                        qInvitation.status,
                        qInvitation.startDate,
                        qInvitation.endDate,
                        Projections.bean(WeddingResponse.class,
                                qWeddings.id,
                                qWeddings.title,
                                qWeddings.groomName,
                                qWeddings.brideName,
                                qWeddings.weddingDate
                        ).as("weddings")
                ))
                .from(qInvitation)
                .leftJoin(qInvitation.weddings, qWeddings)
//                .fetchJoin() // 안해주면 LAZY 일때는 지연로딩 그대로 적용(단 Entity 일때만 가능하다)
//                .leftJoin(qWeddings)
//                .on(qInvitation.weddings.id.eq(qWeddings.id))
                .orderBy(qInvitation.id.desc())
                .fetch();
    }
}
