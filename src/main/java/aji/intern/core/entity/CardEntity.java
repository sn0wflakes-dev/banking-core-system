package aji.intern.core.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "cif", length = 20)
    private String cif;

    @Column(name = "status", length = 1)
    private String cardStatus;

    @Column(name = "expiry_date", length = 4)
    private String expiryDate;
}
