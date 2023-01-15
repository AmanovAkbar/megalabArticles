package com.megalab.articlesite.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Column(name="Title")
    @Size(max=100)
    private String title;

    @Nullable
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @NotBlank
    @Column(name="description")
    @Size(max=215)
    private String description;

    @NotBlank
    @Column(name="body")
    private String body;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Instant updatedAt;


    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
    @ManyToMany(mappedBy = "favouriteArticles", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<User> favoured = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")

    private Category category;

    public Article(String title, Picture picture, String description, String body, User creator, Category category) {
        this.title = title;
        this.picture = picture;
        this.description = description;
        this.body = body;
        this.creator = creator;
        this.category = category;
    }

    public Article() {
    }

    public Set<User> getFavoured() {
        return favoured;
    }

    public void setFavoured(Set<User> favorited) {
        this.favoured = favorited;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
