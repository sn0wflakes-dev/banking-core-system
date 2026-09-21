package aji.intern.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "CARD")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardEntity {
    @Id
    @Column(name = "pan", length = 19)
    private String pan;

    @Column(name = "pin")
    private String pin;

    @Column(name = "status", length = 20)
    private String cardStatus;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_number", nullable = false)
    private CustomerEntity customerEntity;
}
