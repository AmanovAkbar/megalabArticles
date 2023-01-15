package com.megalab.articlesite.service;

import com.megalab.articlesite.model.*;
import com.megalab.articlesite.repository.ArticleRepository;
import com.megalab.articlesite.repository.CategoryRepository;
import com.megalab.articlesite.repository.RoleRepository;
import com.megalab.articlesite.repository.UserRepository;
import com.megalab.articlesite.request.RequestArticle;
import com.megalab.articlesite.response.ResponseArticle;
import com.megalab.articlesite.response.ResponseMainPageArticle;
import com.megalab.articlesite.security.jwt.response.ResponseMessage;
import com.megalab.articlesite.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PictureService pictureService;


    public ResponseEntity<Map<String, Object>> getArticlesForMainPage(String query, int page,
                                                                      int size, String categoryName){
        try{
            List<Article> articles = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Article> pageArticles;

            if(query.equals("")){

                if(categoryName.equals("")){

                    pageArticles=articleRepository.findAll(paging);
                }else{
                    Category category = categoryRepository.findByName(categoryName)
                            .orElseThrow(()->new RuntimeException("There is no such category!"));
                    pageArticles=articleRepository.findArticlesByCategoryId(category.getId(), paging);
                }
            }else{
                if(categoryName.equals("")){
                    pageArticles=articleRepository
                            .searchArticles(query,  paging);
                }else{
                    Category category = categoryRepository.findByName(categoryName)
                            .orElseThrow(()->new RuntimeException("There is no such category!"));
                    pageArticles=articleRepository
                            .searchArticlesByCategoryId(query, category.getId(),paging);
                }
            }
            return mapResponseEntity(pageArticles);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<ResponseMessage>addToFavourites(long articleId){
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            User user = userRepository.findUserById(userDetails.getId()).orElseThrow(
                    () -> new NoSuchElementException("There is no such user"));
            Article article = articleRepository.findById(articleId)
                    .orElseThrow(() -> new NoSuchElementException("There is no such article!"));
            user.getFavouriteArticles().add(article);
            userRepository.save(user);
            return ResponseEntity.ok(new ResponseMessage("Article was added to favourites!"));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(e.getMessage()));
        }
    }
    public Article saveArticle (RequestArticle requestArticle){
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userRepository.findUserById(userDetails.getId()).orElseThrow(
                ()->new RuntimeException("There is no such user"));
        Picture picture = pictureService.savePicture(requestArticle.getArticleCover());
        Category category = categoryRepository.findByName(requestArticle.getCategory()).orElseThrow(
                ()-> new RuntimeException("There is no such category!"));
        Article article = new Article(requestArticle.getTitle(), picture,
                requestArticle.getDescription(), requestArticle.getBody(), user, category);
        return articleRepository.save(article);
    }
    @CachePut(
            value = "articleCache",
            key = "#id")
    public Article editArticle (long id, RequestArticle requestArticle){
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userRepository.findUserById(userDetails.getId()).orElseThrow(
                ()->new RuntimeException("There is no such user"));

        Article article = articleRepository.findById(id).orElseThrow(
                ()->new RuntimeException("There is no  such article!"));
        if(user.getId()!=article.getCreator().getId() ||
                !(user.getRoles().contains(roleRepository.findByName(ERole.ROLE_ADMIN).get()))){
            throw new RuntimeException("This user has no right to edit this article!");
        }
        if(requestArticle.getTitle()!=null){
            article.setTitle(requestArticle.getTitle());
        }
        if(requestArticle.getArticleCover()!=null){
            Picture picture = pictureService.savePicture(requestArticle.getArticleCover());
            pictureService.deletePicture(article.getPicture().getId());
            article.setPicture(picture);
        }
        if(requestArticle.getCategory()!=null){
            Category category = categoryRepository.findByName(requestArticle.getCategory()).orElseThrow(
                    ()->new RuntimeException("There is no such category!"));
            article.setCategory(category);
        }
        if(requestArticle.getDescription()!=null){
            article.setDescription(requestArticle.getDescription());
        }
        if(requestArticle.getBody()!=null){
            article.setBody(requestArticle.getBody());
        }


        return articleRepository.save(article);
    }
    @CacheEvict(value = "articleCache",
            key = "#id")
    public void deleteArticle (long id){
        UserDetailsImpl userDetails = (UserDetailsImpl)SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User user = userRepository.findUserById(userDetails.getId()).orElseThrow(
                ()->new RuntimeException("There is no such user"));

        Article article = articleRepository.findById(id).orElseThrow(
                ()->new RuntimeException("There is no  such article!"));

        if(user.getId()!=article.getCreator().getId() ||
                !(user.getRoles().contains(roleRepository.findByName(ERole.ROLE_ADMIN).get()))){
            throw new RuntimeException("This user has no right to delete this article!");
        }

        articleRepository.deleteById(id);
    }
    @Cacheable(
            value = "articleCache",
            key = "#articleId"
    )
    public Article getArticle(long articleId){
        return articleRepository.findById(articleId)
                .orElseThrow(()->new NoSuchElementException("There is no such article!"));
    }


    public ResponseEntity<Map<String, Object>> getFavouriteArticle(int page, int size, long user_id){
        try{
            List<Article> articles = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);
            Page<Article> pageArticles = articleRepository.findArticlesByFavoured_Id(user_id, paging);

            return mapResponseEntity(pageArticles);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    public ResponseEntity<Map<String, Object>> getArticlesByCreator(int page, int size, long user_id){
        try{
            Pageable paging = PageRequest.of(page, size);
            User user = userRepository.findUserById(user_id)
                    .orElseThrow(()-> new NoSuchElementException("There is no such article!"));
            Page<Article> pageArticles = articleRepository.findArticlesByCreator(user, paging);
            return mapResponseEntity(pageArticles);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<Map<String, Object>> mapResponseEntity(Page<Article> pageArticles) {
        List<Article> articles = pageArticles.getContent();
        if(articles.isEmpty()){
            System.out.println("articles is empty");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        ArrayList<ResponseMainPageArticle> mainPageArticles =
                (ArrayList<ResponseMainPageArticle>)articles.stream().map(Article -> {
                    String pictureUri=null;
                    if(Article.getPicture()!=null){
                        pictureUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/images/").path(String.valueOf(Article.getPicture().getId())).toUriString();
                    }
                    String articleUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/article/")
                            .path(String.valueOf(Article.getId())).toUriString();
                    return new ResponseMainPageArticle(Article.getId(), Article.getTitle(), pictureUri,
                            Article.getDescription(), Article.getCreatedAt(), articleUrl, Article.getCategory().getName());
                }).collect(Collectors.toList());
        Map<String, Object> response = new HashMap<>();
        response.put("articles", mainPageArticles);
        response.put("currentPage", pageArticles.getNumber());
        response.put("totalElements", pageArticles.getTotalElements());
        response.put("totalPages", pageArticles.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

