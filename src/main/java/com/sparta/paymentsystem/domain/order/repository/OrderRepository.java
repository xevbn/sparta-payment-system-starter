package com.sparta.paymentsystem.domain.order.repository;

import com.sparta.paymentsystem.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // 내 주문 목록 조회
    // Left join fetch orderItems
    // DISTINCT: root가 orderItem 수만큼 중복 엔티티 제거
    // LEFT JOIN : 아이템이 하나도 없는 주문이 있더라도 목록에서 누락되지 않도록
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.member.id = :memberId ORDER BY o.createdAt DESC")
    List<Order> findByMemberIdOrderByCreatedAtDesc(@Param("memberId") Long memberId);

    // 내 주문 단건 조회
    // Left join fetch orderItems
    // DISTINCT: root가 orderItem 수만큼 중복 엔티티 제거
    // LEFT JOIN : 아이템이 하나도 없는 주문이 있더라도 목록에서 누락되지 않도록
    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderItems WHERE o.id = :order_id")
    Optional<Order> findByIdWithOrderItems(@Param("order_id") Long order_id);
}
