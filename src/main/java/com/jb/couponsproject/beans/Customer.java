package com.jb.couponsproject.beans;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customers")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 15)
    private String firstName;

    @Column(nullable = false, length = 15)
    private String lastName;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 15)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "customers_vs_coupons")
    private List<Coupon> coupons;
}
