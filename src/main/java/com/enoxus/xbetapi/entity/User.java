package com.enoxus.xbetapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "xbet_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String password;
    private Double balance;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "file_info_id")
    private FileInfo avatar;

    @OneToMany(mappedBy = "owner")
    @Where(clause = "active = true")
    private List<Bet> activeBets;

    @Enumerated(value = EnumType.STRING)
    private State state;

    private String confirmCode;
}
