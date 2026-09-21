package aji.intern.core.repository;

import aji.intern.core.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CardJpaRepository extends JpaRepository<CardEntity, String> {
    Optional<CardEntity> findByPan(String pan);

    @Query("""
        select c
                from CardEntity c
                            join fetch c.customerEntity
                                        where c.pan = :pan
        """)
    Optional<CardEntity> findByPanWithCustomer(@Param("pan") String pan);
}
