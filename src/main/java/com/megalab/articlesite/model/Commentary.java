package com.megalab.articlesite.model;

import jakarta.persistence.*;

@Entity
@Table(name = "commentary")
public class Commentary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "replied_id")
    private Commentary replied;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    public Commentary() {
    }

    public Commentary(User creator, Commentary replied, Article article) {
        this.creator = creator;
        this.replied = replied;
        this.article = article;
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
