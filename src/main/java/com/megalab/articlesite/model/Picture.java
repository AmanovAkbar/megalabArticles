package com.megalab.articlesite.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "images")
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Size(min=5)
    @Column(name = "name")
    private String name;
    @Column(name="content_type")
    private String contentType;
    @Lob
    @Column(name = "data")
    private byte[] data;

    public Picture() {
    }

    public Picture(String name, byte[] data, String contentType) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.contentType = contentType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
