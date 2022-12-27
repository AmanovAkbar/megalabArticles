package com.megalab.articlesite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username")
        }
)
public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;
        @NotBlank
        @Size(max=100)
        @Column(name = "firstname")
        private String firstName;
        @NotBlank
        @Size(max=100)
        @Column(name = "lastname")
        private String lastName;
        @NotBlank
        @Size(max = 50)

        @Column(name = "username")
        private String username;

        @NotBlank
        @Size(max = 120)
        @Column(name = "password")
        private String password;

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(	name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Role> roles = new HashSet<>();


        public User(){}
        public User(String first, String last, String username, String password) {
                this.firstName = first;
                this.lastName = last;
                this.username = username;
                this.password = password;
        }

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public Set<Role> getRoles() {
                return roles;
        }

        public void setRoles(Set<Role> roles) {
                this.roles = roles;
        }
}
