package com.jb.couponsproject.beans;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private Categories title;
}
