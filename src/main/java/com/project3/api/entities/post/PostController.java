package com.project3.api.entities.post;

import com.project3.api.JwtUtil;
import com.project3.api.entities.comment.Comment;
import com.project3.api.entities.post.dto.*;
import com.project3.api.entities.user.User;
import com.project3.api.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//TODO: Update HTTPs
//TODO: Update Readme

@RestController
@RequestMapping(path = "api/posts/")
public class PostController {

    private final PostService postService;
    private final TokenUtil tokenUtil;

    @Autowired
    public PostController(
            PostService postService,
            TokenUtil tokenUtil
    ) {
        this.postService = postService;
        this.tokenUtil = tokenUtil;
    }


    // ----------------------------------------
    // POSTS
    // ----------------------------------------

    // GET
    // ----------------------------------------
    //User-only access
    @GetMapping(path = "allPosts")
    public List<Post> getAllPostsForUser (@RequestHeader("Authorization") String token){
        return postService.getAllPostsForUser(tokenUtil.getUserFromToken(token));
    }

    //User-only access//secured
    @GetMapping(path = "allPosts/location")
    public List<Post> getAllPostsForUserLocation(@RequestBody PostLocation postLocation, @RequestHeader("Authorization") String token){
        return postService.getAllPostsForUserLocation(
                postLocation,
                tokenUtil.getUserFromToken(token)
        );
    }

    //User-only access
    @GetMapping(path = "allPosts/site")
    public List<Post> getAllPostsForUserSite(@RequestBody PostSite postSite, @RequestHeader("Authorization") String token){
        return postService.getAllPostsForUserSite(
                postSite,
                tokenUtil.getUserFromToken(token)
        );
    }

    //User-only access
    @GetMapping(path = "allPosts/tag")
    public List<Post> getAllPostsForUserTag(@RequestParam String tag, @RequestHeader("Authorization") String token){
        return postService.getAllPostsForUserTag(
                tag,
                tokenUtil.getUserFromToken(token)
        );
    }

    //User-only access
    @GetMapping(path = "allPosts/tags")
    public List<String> getAllTagsForUserGroups(@RequestHeader("Authorization") String token){
        return postService.getAllTagsForUserGroups(tokenUtil.getUserFromToken(token));
    }

    //User-only access
    @GetMapping(path = "allPosts/group")
    public List<Post> getAllPostsForUserGroup(@RequestParam Long groupId, @RequestHeader("Authorization") String token){
        return postService.getAllPostsForUserGroup(
                groupId,
                tokenUtil.getUserFromToken(token)
        );
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
        return postService.getPostsUserOrGroup(userId, groupId);
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

    //User-only access
    @DeleteMapping(path = "{postId}")
    public void deletePostByID (
            @PathVariable("postId") Long postId,
            @RequestHeader("Authorization") String token
    ){
        postService.deletePostById(
                postId,
                tokenUtil.getUserFromToken(token)
        );
    }

    //User-only access
    @PostMapping
    public void createPost (
            @RequestParam Long groupId,
            @RequestBody Post post,
            @RequestHeader("Authorization") String token
    ){
        postService.addNewPost(
                tokenUtil.getUserFromToken(token),
                groupId,
                post
        );
    }

    //User-only access
    @PutMapping(path = "{postId}")
    public void updatePostText (
            @PathVariable("postId") Long postId,
            @RequestHeader("Authorization") String token,
            @RequestBody PostUpdate postUpdate
    ){
        postService.updatePostText(
                postId,
                tokenUtil.getUserFromToken(token),
                postUpdate
        );
    }

    // ----------------------------------------
    // COMMENT
    // ----------------------------------------

    //User-only access
    @PostMapping("{postId}/comment")
    public void addComment(
            @PathVariable("postId") Long postId,
            @RequestHeader("Authorization") String token,
            @RequestBody PostComment postComment
    ){
        postService.addNewComment(
                postId,
                tokenUtil.getUserFromToken(token),
                postComment
        );
    }

    @GetMapping(path = "{postId}/comment")
    public List<Comment> getComments(@PathVariable("postId") Long postId){
        return postService.getCommentsforPost(postId);
    }

    //User-only access
    @DeleteMapping("comment/{commentId}")
    public void deleteComment(
            @PathVariable("commentId") Long commentId,
            @RequestHeader("Authorization") String token
    ){
        postService.deleteComment(
                commentId,
                tokenUtil.getUserFromToken(token)
        );
    }

    //User-only access
    @PutMapping("comment/{commentId}")
    public void updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody CommentUpdate commentUpdate,
            @RequestHeader("Authorization") String token
    ){
        postService.updateComment(
                commentId,
                tokenUtil.getUserFromToken(token),
                commentUpdate
        );
    }
}