package com.shop.order;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    @Query("select o from OrderEntity o left join fetch o.lines l"
            + " left join fetch l.product order by o.id")
    List<OrderEntity> findAllWithLines();
}
