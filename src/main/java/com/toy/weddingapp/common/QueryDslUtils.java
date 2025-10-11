package com.toy.weddingapp.common;

import com.querydsl.jpa.impl.JPAQuery;

import java.util.List;
import java.util.Optional;

public class QueryDslUtils {

    public static <T> Optional<T> optional(T value) {
        return Optional.ofNullable(value);
    }

//    public static <T> Optional<List<T>> fetchOptional(JPAQuery<T> query) {
//        return Optional.ofNullable(query.fetch());
//    }

    public static <T> Optional<T> fetchOneOptional(JPAQuery<T> query) {
        return Optional.ofNullable(query.fetchOne());
    }
}
