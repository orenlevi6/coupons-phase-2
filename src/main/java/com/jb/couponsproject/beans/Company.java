package com.jb.couponsproject.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "companies")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, length = 30)
    private String name;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 15)
    private String password;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "company")
    @Transient
    @JsonIgnore
    private List<Coupon> coupons;
}
