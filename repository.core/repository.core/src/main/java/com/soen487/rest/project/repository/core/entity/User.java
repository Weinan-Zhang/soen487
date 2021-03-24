package com.soen487.rest.project.repository.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
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
}
