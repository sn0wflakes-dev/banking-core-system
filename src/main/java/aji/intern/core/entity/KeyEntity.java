package aji.intern.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Table(name = "KEY")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyEntity {
    @Id
    @Column(name = "service_id", length = 37)
    private String serviceId;

    @Column(name = "private_key")
    private String privateKey;

    @Column(name = "public_key")
    private String publicKey;

    @Column(name = "registered_at")
    private Instant registeredAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        if (this.registeredAt == null) {
            this.registeredAt = Instant.now();
        }

        if (this.updatedAt == null) {
            this.updatedAt = Instant.now();
        }
    }
}
