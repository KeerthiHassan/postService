package com.maveric.postservice.services;


import com.maveric.postservice.dto.PostResponse;
import com.maveric.postservice.dto.Postdto;
import com.maveric.postservice.exception.PostCannotbeUpdated;
import com.maveric.postservice.exception.PostsNotPresent;
import com.maveric.postservice.feign.CommentFeign;
import com.maveric.postservice.feign.LikeFeign;
import com.maveric.postservice.feign.UserFeign;
import com.maveric.postservice.model.Post;
import com.maveric.postservice.repo.PostRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImplementation implements PostService{
    private static Logger log = LoggerFactory.getLogger(PostServiceImplementation.class);

    @Autowired
    PostRepo postRepo;
    @Autowired
    CommentFeign commentfeign;
    @Autowired
    LikeFeign likefeign;
    @Autowired
    UserFeign userFeign;
@LoadBalanced
    @Override
    public List<PostResponse> getPosts() {
    
    List<Post> postList=postRepo.findAll();
        if(postList==null) {
            log.info("posts not found");
            throw new PostsNotPresent("Not able to fetch any post");
        }
        List<PostResponse> postResponseList=new ArrayList<>();
        for(Post post:postList)
        {
            postResponseList.add(setPostResponse(post));
        }
        log.info("posts successfully fetched");
        return postResponseList;
    }



    @Override
    public PostResponse getPostDetails(String postId) {
        Post post=postRepo.findBypostId(postId);
        if(post==null) {
            log.info("post details not found");
            throw new PostsNotPresent("Not able to fetch post");
        }
        log.info("post details fetched successfully");
        return setPostResponse(post);
    }

    @Override
    public PostResponse createPost(Postdto postdto) {
        Post posts=new Post();
   
}
