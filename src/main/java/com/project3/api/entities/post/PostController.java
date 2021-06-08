package com.project3.api.entities.post;

import com.project3.api.entities.comment.Comment;
import com.project3.api.entities.post.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/posts/")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public void createPost (@RequestParam Long userId, @RequestParam Long groupId, @RequestBody Post post){
        postService.addNewPost(userId, groupId, post);
    }

    @GetMapping(path = "byUser")
    public List<Post> getPostByUser (@RequestParam Long userId){
        return postService.getPostsUser(userId);
    }

    @GetMapping(path = "byUserOrGroup")
    public List<Post> getPostByUserOrGroup(@RequestParam(required = false) Long userId, @RequestParam(required = false) Long groupId){
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

    @DeleteMapping(path = "{postId}")
    public void deletePostByID (@PathVariable("postId") Long postId){
        postService.deletePostById(postId);
    }

    @PutMapping(path = "{postId}")
    public void updatePostText (@PathVariable("postId") Long postId, @RequestBody PostUpdate postUpdate){
        postService.updatePostText(postId, postUpdate);
    }

    //CRUD Comment
    @PostMapping("{postId}/comment")
    public void addComment(@PathVariable("postId") Long postId, @RequestBody PostComment postComment){
        postService.addNewComment(postId, postComment);
    }

    @GetMapping(path = "{postId}/comment")
    public List<Comment> getComments(@PathVariable("postId") Long postId){
        return postService.getCommentsforPost(postId);
    }

    @DeleteMapping("comment/{commentId}")
    public void deleteComment(@PathVariable("commentId") Long commentId){
        postService.deleteComment(commentId);
    }

    @PutMapping("comment/{commentId}")
    public void updateComment(@PathVariable("commentId") Long commentId, @RequestBody CommentUpdate commentUpdate){
        postService.updateComment(commentId, commentUpdate);
    }

}