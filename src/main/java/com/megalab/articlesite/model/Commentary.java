package com.megalab.articlesite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "commentary")
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
    private String body;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "replied_id")
    private Commentary replied;
    @OneToMany(mappedBy = "replied", cascade = CascadeType.ALL)
    private Set<Commentary> replies = new HashSet<>();
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;


    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Commentary() {
    }

    public Commentary(User creator, Commentary replied, Article article, String body) {
        this.creator = creator;
        this.replied = replied;
        this.article = article;
        this.body = body;
    }
    public Commentary(User creator, Article article, String body) {
        this.creator = creator;
        this.article = article;
        this.body = body;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Commentary getReplied() {
        return replied;
    }

    public void setReplied(Commentary replied) {
        this.replied = replied;
    }


    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Set<Commentary> getReplies() {
        return replies;
    }

    public void setReplies(Set<Commentary> replies) {
        this.replies = replies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
