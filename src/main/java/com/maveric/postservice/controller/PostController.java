package com.maveric.postservice.controller;

import com.maveric.postservice.dto.PostResponse;
import com.maveric.postservice.dto.Postdto;
import com.maveric.postservice.model.Post;
import com.maveric.postservice.services.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private static Logger log = LoggerFactory.getLogger(PostController.class);

    @Autowired
    PostService postService;

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getPosts(){
        log.info("getting post");
        return new ResponseEntity<List<PostResponse>> (postService.getPosts(), HttpStatus.OK);
    }


    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> getPostDetails(@PathVariable ("postId") String postId){
        log.info("getting post details");
        return new ResponseEntity<PostResponse> (postService.getPostDetails(postId), HttpStatus.OK);
    }

    
}