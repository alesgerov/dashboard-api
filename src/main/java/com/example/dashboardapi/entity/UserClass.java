package com.example.dashboardapi.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Transactional
@ToString
public class UserClass {

    @Id
    @SequenceGenerator(
            name = "user_id_seq",
            sequenceName = "user_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_seq"
    )
    @JsonIgnore
    private long id;
    @Column(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String role = RoleClass.USER_ROLE;
}
