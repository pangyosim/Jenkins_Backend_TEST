package com.devcv.order.repository;

import com.devcv.member.domain.Member;
import com.devcv.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    Optional<Order> findOrderByOrderIdAndMember(String orderId, Member member);
    // 주문id 조회
    Optional<Order> findByMember_MemberIdAndResume_ResumeId(Long memberId, Long resumeId);
}