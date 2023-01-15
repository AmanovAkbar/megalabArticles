package com.megalab.articlesite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
        @JsonIgnore
        private String password;
        @Nullable
        @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        @JoinColumn(name = "picture_id")
        private Picture picture;
        @OneToMany(mappedBy = "creator")
        private Set<Article> userArticles = new HashSet<>();

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(name="user_favourites",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "article_id"))
        private Set<Article> favouriteArticles = new HashSet<>();

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

        public Picture getPicture() {
                return picture;
        }

        public void setPicture(Picture picture) {
                this.picture = picture;
        }

        public Set<Article> getUserArticles() {
                return userArticles;
        }

        public void setUserArticles(Set<Article> userArticles) {
                this.userArticles = userArticles;
        }

        public Set<Article> getFavouriteArticles() {
                return favouriteArticles;
        }

        public void setFavouriteArticles(Set<Article> favouriteArticles) {
                this.favouriteArticles = favouriteArticles;
        }
}
