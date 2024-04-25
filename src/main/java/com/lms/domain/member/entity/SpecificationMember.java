package com.lms.domain.member.entity;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class SpecificationMember {
    public static Specification<Member> withSubject(String subject) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.isEmpty(subject)) {
                return criteriaBuilder.equal(root.get("subject"), subject);
            }
            return null;
        };
    }

    public static Specification<Member> withFlag(int cno) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.isEmpty(cno)) {
                return criteriaBuilder.equal(root.get("cno"), cno);
            }
            return null;
        };
    }

    public static Specification<Member> withRole(String role) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.isEmpty(role)) {
                return criteriaBuilder.equal(root.get("role"), role);
            }
            return null;
        };

    }
}
