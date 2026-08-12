package com.sparta.paymentsystem.domain.payment.repository;

import com.sparta.paymentsystem.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // 주문 단건 조회 화면에서 결제 ID 한 건만 필요하므로 Payment 엔티티 전체를 로딩하지 않고 ID만 프로젝션
    // 주문에 결제 ID를 붙이려면 이렇게 Payment 쪽에서 역으로 찾아야 함
    @Query("SELECT p.id FROM Payment p WHERE p.order.id = :orderId")
    Optional<Long> findIdByOrderId(@Param("orderId") Long orderId);

    //주문 '목록' 조회용 N+1 방지 일괄 조회
    //각 주문마다 findIdByOrderId를 돌리면 N번 쿼리가 나가는데, IN 절로 한 번에 가져오기
    @Query("SELECT p.order.id, p.id FROM Payment p WHERE p.order.id IN :orderIds")
    List<Object[]> findIdsByOrderIds(@Param("orderIds") List<Long> orderIds);
}
