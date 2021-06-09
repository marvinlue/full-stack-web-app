package com.project3.api.entities.post;

import com.project3.api.entities.comment.Comment;
import com.project3.api.entities.post.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//TODO: Check on post such that user can only post in his groups
//TODO: Implement JWT userId getter
//TODO: Implement AllPosts for User - DONE
//TODO: Implement AllPostsFromLocation for User - DONE
//TODO: Implement AllPostsFromSite for User - DONE
//TODO:  Implement AllPostsFromTag for User + Additional get all Tags in from Groups User is in - DONE

@RestController
@RequestMapping(path = "api/posts/")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }


    // ----------------------------------------
    // POSTS
    // ----------------------------------------

    // GET
    // ----------------------------------------
    @GetMapping(path = "allPosts")
    public List<Post> getAllPostsForUser (@RequestParam Long userId){
        return postService.getAllPostsForUser(userId);
    }

    @GetMapping(path = "allPosts/location")
    public List<Post> getAllPostsForUserLocation(@RequestBody PostLocation postLocation, @RequestParam Long userId){
        return postService.getAllPostsForUserLocation(postLocation, userId);
    }

    @GetMapping(path = "allPosts/site")
    public List<Post> getAllPostsForUserSite(@RequestBody PostSite postSite, @RequestParam Long userId){
        return postService.getAllPostsForUserSite(postSite, userId);
    }

    @GetMapping(path = "allPosts/tag")
    public List<Post> getAllPostsForUserTag(@RequestParam String tag, @RequestParam Long userId){
        return postService.getAllPostsForUserTag(tag, userId);
    }

    @GetMapping(path = "allPosts/tags")
    public List<String> getAllTagsForUserGroups(@RequestParam Long userId){
        return postService.getAllTagsForUserGroups(userId);
    }

    @GetMapping(path = "byUser")
    public List<Post> getPostByUser (@RequestParam Long userId){
        return postService.getPostsUser(userId);
    }

    @GetMapping(path = "byUserOrGroup")
    public List<Post> getPostByUserOrGroup(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long groupId
    ){
        return postService.getPostsUserGroup(userId, groupId);
    }

    @GetMapping(path = "byTime")
    public List<Post> getPostByTimestamp (@RequestBody PostTime postTime){
        return postService.getPostByTimestamp(postTime);
    }

    @GetMapping(path = "byLocation")
    public List<Post> getPostByLocation(@RequestBody PostLocation postLocation){
        return postService.getPostsByLocation(postLocation);
    }

    @GetMapping(path = "bySite")
    public List<Post> getPostBySite(@RequestBody PostSite postSite){
        return postService.getPostBySite(postSite);
    }


    // DELETE, POST, PUT
    // ----------------------------------------
    @DeleteMapping(path = "{postId}")
    public void deletePostByID (
            @PathVariable("postId") Long postId,
            @RequestParam Long userId
    ){
        postService.deletePostById(postId, userId);
    }

    @PostMapping
    public void createPost (
            @RequestParam Long userId,
            @RequestParam Long groupId,
            @RequestBody Post post
    ){
        postService.addNewPost(userId, groupId, post);
    }


    @PutMapping(path = "{postId}")
    public void updatePostText (
            @PathVariable("postId") Long postId,
            @RequestParam Long userId,
            @RequestBody PostUpdate postUpdate
    ){
        postService.updatePostText(postId, userId, postUpdate);
    }

    // ----------------------------------------
    // COMMENT
    // ----------------------------------------
    @PostMapping("{postId}/comment")
    public void addComment(@PathVariable("postId") Long postId, @RequestBody PostComment postComment){
        postService.addNewComment(postId, postComment);
    }

    @GetMapping(path = "{postId}/comment")
    public List<Comment> getComments(@PathVariable("postId") Long postId){
        return postService.getCommentsforPost(postId);
    }

    @DeleteMapping("comment/{commentId}")
    public void deleteComment(
            @PathVariable("commentId") Long commentId,
            @RequestParam Long userId
    ){
        postService.deleteComment(commentId, userId);
    }

    @PutMapping("comment/{commentId}")
    public void updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentUpdate commentUpdate,
            @RequestParam Long userId
    ){
        postService.updateComment(commentId, userId, commentUpdate);
    }

}