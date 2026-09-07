package aji.intern.core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "CUSTOMER")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {
    @Id
    @Column(name = "customer_number", length = 37)
    private String customerNumber;

    @Column(name = "cif", length = 20)
    private String cif;

    @Column(name = "name")
    private String customerName;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;
}
