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
    public ResponseEntity<List<PostResponse>> getPosts(@QueryParam("page") Integer page, @QueryParam("size") Integer size){
        log.info("getting post");
        return new ResponseEntity<List<PostResponse>> (postService.getPosts(page,size), HttpStatus.OK);
    }
	
	@PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable ("postId") String postId,@Valid @RequestBody Postdto updatePost){
        log.info("Updating post");
        return new ResponseEntity<PostResponse>(postService.updatePost(postId,updatePost),HttpStatus.OK);
    }

@PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(@Valid  @RequestBody Postdto postdto){
        log.info("Creating post");
        return new ResponseEntity<PostResponse>(postService.createPost(postdto),HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> getPostDetails(@PathVariable ("postId") String postId){
        log.info("getting post details");
        return new ResponseEntity<PostResponse> (postService.getPostDetails(postId), HttpStatus.OK);
    }

	 @DeleteMapping("/posts/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable ("postId") String postId){
        log.info("Deleting post");
        return new ResponseEntity<String>(postService.deletePost(postId),HttpStatus.OK);
    }
    
}
