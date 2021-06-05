package com.project3.api.entities.post;

import com.project3.api.entities.post.dto.PostUpdate;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
TODO: Given a location and a radius or quantity limit, return nearest posts
TODO: Order and filter by time
TODO: Order and filter by user or group - DONE
TODO: Order and filter by user - DONE
TODO: CRUD Operations - DONE
TODO: Add all fields
*/

@RestController
@RequestMapping(path = "api/posts/")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public void createPost (@RequestParam Long userId, @RequestParam Long groupId, @RequestBody Post post){
        postService.addNewPost(userId, groupId, post);
    }

    @GetMapping(path = "postByUser")
    public List<Post> getPostByUser (@RequestParam Long userId){
        return postService.getPostsUser(userId);
    }

    @GetMapping(path = "postByUserOrGroup")
    public List<Post> getPostByUserOrGroup(@RequestParam(required = false) Long userId, @RequestParam(required = false) Long groupId){
        return postService.getPostsUserGroup(userId, groupId);
    }

    public List<Post> getPostBiggerTimestamp ()

    @DeleteMapping(path = "{postId}")
    public void deletePostByID (@PathVariable("postId") Long postId){
        postService.deletePostById(postId);
    }

    @PutMapping(path = "{postId}")
    public void updatePostText (@PathVariable("postId") Long postId, @RequestBody PostUpdate postUpdate){
        postService.updatePostText(postId, postUpdate);
    }




}
