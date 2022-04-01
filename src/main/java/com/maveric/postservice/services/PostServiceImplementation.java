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
        posts.setCreatedAt(LocalDate.now());
        posts.setUpdatedAt(LocalDate.now());
        posts.setPost(postdto.getPost());
        posts.setPostedBy(postdto.getPostedBy());
        Post post=postRepo.save(posts);
        log.info("post created successfully");
        return new PostResponse(post.getPostId(), post.getPost(),
                userFeign.getUsersById(post.getPostedBy()).getBody(),
                0, 0
                , post.getCreatedAt(), post.getUpdatedAt());
    }
	
	@Override
    public PostResponse updatePost(String postId, Postdto updatePost) {
        Post posts=postRepo.findBypostId(postId);
        if(posts==null){
            log.info("post not found");
            throw new PostsNotPresent("Can't post,post not avilable");
        }
        if(!(posts.getPostedBy().equals(updatePost.getPostedBy()))){
            log.info("Post not belongs to you");
            throw new PostCannotbeUpdated("You can't update this post");
        }
        posts.setUpdatedAt(LocalDate.now());
        posts.setPostedBy(updatePost.getPostedBy());
        posts.setPost(updatePost.getPost());
        log.info("Updating post");
        return setPostResponse(postRepo.save(posts));
    }

	
	 public PostResponse setPostResponse(Post post) {
        return new PostResponse(post.getPostId(), post.getPost(),
                userFeign.getUsersById(post.getPostedBy()).getBody(),
                likefeign.getLikesCount(post.getPostId()).getBody(), commentfeign.getCommentsCount(post.getPostId()).getBody()
                , post.getCreatedAt(), post.getUpdatedAt());
    }
   
}
