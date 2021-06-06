package com.project3.api.entities.post;

import com.project3.api.entities.comment.Comment;
import com.project3.api.entities.comment.CommentRepository;
import com.project3.api.entities.group.Group;
import com.project3.api.entities.group.GroupRepository;
import com.project3.api.entities.post.dto.*;
import com.project3.api.entities.site.Site;
import com.project3.api.entities.site.SiteRepository;
import com.project3.api.entities.user.User;
import com.project3.api.entities.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final CommentRepository commentRepository;
    private final SiteRepository siteRepository;

    @Autowired
    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            GroupRepository groupRepository,
            CommentRepository commentRepository,
            SiteRepository siteRepository
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.commentRepository = commentRepository;
        this.siteRepository = siteRepository;
    }

    public List<Post> getPostsUser(Long userID) {
        Optional<User> userOptional = userRepository.findUserById(userID);
        if (userOptional.isPresent()) {
            return postRepository.findPostsByUserOrderByPostedAtDesc(userOptional.get());
        }
        else
            return Collections.<Post> emptyList();

    }

    public void addNewPost(Long userId, Long groupId, Post post) {
        Optional<User> userOptional = userRepository.findUserById(userId);
        Optional<Group> groupOptional = groupRepository.findGroupByGid(groupId);

        if (userOptional.isPresent() && groupOptional.isPresent()){
            post.setUser(userOptional.get());
            post.setGroup(groupOptional.get());
            post.setPostedAt(Timestamp.valueOf(LocalDateTime.now()));
            System.out.println(post);
            postRepository.save(post);
        }
        else {
            throw new IllegalStateException("No User: " + userId + " or Group: " + groupId + " exist!");
        }

    }


    public List<Post> getPostsUserGroup(Long userId, Long groupId) {
        return  postRepository.findPostsByUserOrGroup(userId, groupId);
    }

    public void deletePostById(Long postId){
        postRepository.deleteById(postId);
    }

    public void updatePostText(Long postId, PostUpdate postUpdate) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()){
            Post post = postOptional.get();
            post.setPost(postUpdate.getText());
            postRepository.save(post);
        }
        else {
            throw new IllegalStateException("No Post: " + postId + " exists");
        }
    }

    public List<Post> getPostByTimestamp(PostTime postTime) {
        if (postTime.isGreaterOperation()){
            return postRepository.findPostsByPostedAtGreaterThanOrderByPostedAtDesc(postTime.getTime());
        }
        if (postTime.isLessOperation()){
            return postRepository.findPostsByPostedAtLessThanEqualOrderByPostedAtDesc(postTime.getTime());
        }
        else {
            throw new IllegalStateException("No Operation: " + postTime.getOperation() + " selected");
        }
    }

    public List<Post> getPostsByLocation(PostLocation postLocation) {
        return postRepository.findAllPostsAroundLocation(
                postLocation.getLongitude(),
                postLocation.getLatitude(),
                postLocation.getRadius()*1000
        );
    }

    public List<Post> getPostBySite(PostSite postSite) {
        Site site = siteRepository.findSiteBySiteName(postSite.getSitename());
        return postRepository.findAllPostsAroundLocation(
                site.getLocation().getX(),
                site.getLocation().getY(),
                postSite.getRadius()*1000
        );
    }

    public void addNewComment(Long postId, PostComment postComment) {
        Optional<User> userOptional = userRepository.findById(postComment.getUserId());
        Post post = postRepository.getById(postId);

        if (userOptional.isPresent()){
            Comment comment = new Comment();
            comment.setPost(post);
            comment.setUser(userOptional.get());
            comment.setComment(postComment.getCommentText());
            comment.setMadeAt(Timestamp.valueOf(LocalDateTime.now()));
            commentRepository.save(comment);
        }
        else {
            throw new IllegalStateException("No User:" + postComment.getUserId() + " exists");
        }
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public void updateComment(Long commentId, CommentUpdate commentUpdate) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()){
            Comment comment = commentOptional.get();
            comment.setComment(commentUpdate.getUpdatedText());
            commentRepository.save(comment);
        }
        else {
            throw new IllegalStateException("No Comment: " + commentId + " exists");
        }
    }

    public List<Comment> getCommentsforPost(Long postId) {
        return commentRepository.findCommentsByPostOrderByMadeAtDesc(postId);
    }
}
