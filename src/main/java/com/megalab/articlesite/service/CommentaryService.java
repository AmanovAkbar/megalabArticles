package com.megalab.articlesite.service;

import com.megalab.articlesite.model.Article;
import com.megalab.articlesite.model.Commentary;
import com.megalab.articlesite.model.ERole;
import com.megalab.articlesite.model.User;
import com.megalab.articlesite.repository.*;
import com.megalab.articlesite.request.RequestCommentary;
import com.megalab.articlesite.response.ResponseCommentary;
import com.megalab.articlesite.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CommentaryService {
    @Autowired
    CommentaryRepository commentaryRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    public List<ResponseCommentary> getAllCommentaries(){
       return mapCommentaries(commentaryRepository.findCommentariesByRepliedIsNull());
    }
    public List<ResponseCommentary>getArticleCommentaries(long article_id){
        return mapCommentaries(commentaryRepository
                .findCommentariesByArticleAndRepliedIsNull(articleRepository.findById(article_id)
                        .orElseThrow(()->new NoSuchElementException("There is no such article!"))));
    }
    public Commentary saveCommentary(RequestCommentary requestCommentary){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userRepository.findUserById(userDetails.getId()).orElseThrow(
                ()->new NoSuchElementException("There is no such user"));
        Article article = articleRepository.findById(requestCommentary.getArticle_id())
                .orElseThrow(()->new NoSuchElementException("There is no such article"));
        if(requestCommentary.getReplied_id()==null){
            return commentaryRepository.save(new Commentary(user, article, requestCommentary.getBody()));

        }else{
            Commentary replied = commentaryRepository.findById(requestCommentary.getReplied_id())
                    .orElseThrow(()->new NoSuchElementException("There is no such commentary!"));
            return commentaryRepository.save(new Commentary(user, replied, article, requestCommentary.getBody()));
        }
    }
    public void deleteCommentary(Long id){
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userRepository.findUserById(userDetails.getId()).orElseThrow(
                ()->new RuntimeException("There is no such user"));

       Commentary commentary = commentaryRepository.findById(id).orElseThrow(
                ()->new RuntimeException("There is no  such commentary!"));

        if(user.getId()!=commentary.getCreator().getId() ||
                !(user.getRoles().contains(roleRepository.findByName(ERole.ROLE_ADMIN).get()))){
            throw new RuntimeException("This user has no right to edit this article!");
        }

        commentaryRepository.deleteById(id);
    }
    public Commentary editCommentary(Long id, String body){
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userRepository.findUserById(userDetails.getId()).orElseThrow(
                ()->new RuntimeException("There is no such user"));

        Commentary commentary = commentaryRepository.findById(id).orElseThrow(
                ()->new RuntimeException("There is no  such commentary!"));

        if(user.getId()!=commentary.getCreator().getId() ||
                !(user.getRoles().contains(roleRepository.findByName(ERole.ROLE_ADMIN).get()))){
            throw new RuntimeException("This user has no right to edit this article!");
        }
        commentary.setBody(body);
        return commentaryRepository.save(commentary);
    }
    List<ResponseCommentary>mapCommentaries(List<Commentary>commentaries){
       return commentaries.stream().map(Commentary->{
            String userPfpUri = null;
            if(Commentary.getCreator().getPicture()!=null){
                userPfpUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/")
                        .path(String.valueOf(Commentary.getCreator().getPicture().getId())).toUriString();
            }
            return new ResponseCommentary(Commentary.getId(), Commentary.getBody(), Commentary.getReplies(),
                    Commentary.getArticle().getId(), Commentary.getCreator().getId(), Commentary.getCreator().getFirstName(),
                    Commentary.getCreator().getLastName(), userPfpUri);
        }).collect(Collectors.toList());
    }

}
