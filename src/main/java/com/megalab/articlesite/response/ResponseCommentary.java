package com.megalab.articlesite.response;

import com.megalab.articlesite.model.Commentary;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ResponseCommentary {
    private Long id;
    private String body;
    private List<ResponseCommentary> replies;
    private Long article_id;
    private Long user_id;
    private String user_firstname;
    private String user_lastname;
    private String user_pfp_uri;

    public ResponseCommentary(Long id, String body, Set<Commentary> replies,
                              Long article_id, Long user_id, String user_firstname,
                              String user_lastname, String user_pfp_uri) {
        this.id = id;
        this.body = body;
        this.replies = mapToResponse(replies);
        this.article_id = article_id;
        this.user_id = user_id;
        this.user_firstname = user_firstname;
        this.user_lastname = user_lastname;
        this.user_pfp_uri = user_pfp_uri;
    }

    private List<ResponseCommentary> mapToResponse(Set<Commentary> replies) {
        return replies.stream().map(Commentary->{
            String userPfpUri = null;
            if(Commentary.getCreator().getPicture()!=null){
                userPfpUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("images/")
                        .path(String.valueOf(Commentary.getCreator().getPicture().getId())).toUriString();
            }
            return new ResponseCommentary(Commentary.getId(), Commentary.getBody(), Commentary.getReplies(),
                    Commentary.getArticle().getId(), Commentary.getCreator().getId(), Commentary.getCreator().getFirstName(),
                    Commentary.getCreator().getLastName(), userPfpUri);
        }).collect(Collectors.toList());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<ResponseCommentary> getReplies() {
        return replies;
    }

    public void setReplies(List<ResponseCommentary> replies) {
        this.replies = replies;
    }

    public Long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(Long article_id) {
        this.article_id = article_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUser_firstname() {
        return user_firstname;
    }

    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public String getUser_lastname() {
        return user_lastname;
    }

    public void setUser_lastname(String user_lastname) {
        this.user_lastname = user_lastname;
    }

    public String getUser_pfp_uri() {
        return user_pfp_uri;
    }

    public void setUser_pfp_uri(String user_pfp_uri) {
        this.user_pfp_uri = user_pfp_uri;
    }
}
