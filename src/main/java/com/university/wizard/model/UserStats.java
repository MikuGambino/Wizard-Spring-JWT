package com.university.wizard.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Table(name = "user_stats")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "character_level")
    private Integer characterLevel;

    @Column(name = "game_level")
    private Integer gameLevel;

    @Column(name = "exp")
    private BigDecimal exp;

    @Column(name = "class")
    private String characterClass;
}
