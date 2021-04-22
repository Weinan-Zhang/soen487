package com.soen487.rest.project.repository.core.entity;

import com.soen487.rest.project.repository.core.configuration.UserGroup;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="tab_user")
@Data
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue
    @Column(name="u_uid")
    private long uid;
    @Column(name="u_username")
    private String username;
    @Column(name="u_password")
    private String password;
    @Column(name="u_email")
    private String email;
    @Column(name="u_group")
    private UserGroup group;
}
