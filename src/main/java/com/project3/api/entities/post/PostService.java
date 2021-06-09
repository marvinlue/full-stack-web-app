package com.project3.api.entities.post;

import com.project3.api.entities.comment.Comment;
import com.project3.api.entities.comment.CommentRepository;
import com.project3.api.entities.group.Group;
import com.project3.api.entities.group.GroupRepository;
import com.project3.api.entities.member.Member;
import com.project3.api.entities.member.MemberRepository;
import com.project3.api.entities.member.MemberService;
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
    private final MemberService memberService;

    @Autowired
    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            GroupRepository groupRepository,
            CommentRepository commentRepository,
            SiteRepository siteRepository,
            MemberService memberService
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.commentRepository = commentRepository;
        this.siteRepository = siteRepository;
        this.memberService = memberService;
    }

    // ----------------------------------------
    // POSTS
    // ----------------------------------------
    //Getter Methods
    public List<Post> getPostsUser(Long userID) {
        Optional<User> userOptional = userRepository.findUserById(userID);
        if (userOptional.isPresent()) {
            return postRepository.findPostsByUserOrderByPostedAtDesc(userOptional.get());
        }
        else
            return Collections.<Post> emptyList();

    }

    public List<Post> getPostsUserGroup(Long userId, Long groupId) {
        return  postRepository.findPostsByUserOrGroup(userId, groupId);
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

    public List<Post> getPostBySite(PostSite postSite) {
        Site site = siteRepository.findSiteBySiteName(postSite.getSitename());
        return postRepository.findAllPostsAroundLocation(
                site.getLocation().getX(),
                site.getLocation().getY(),
                postSite.getRadius()*1000
        );
    }

    public List<Post> getAllPostsForUser(Long userId) {
        List<Group> groupsOfUser = getGroupsOfUser(userId);
        return postRepository.findPostsByGroups(groupsOfUser);
    }

    public List<Post> getAllPostsForUserLocation(PostLocation postLocation, Long userId) {
        List<Group> groupsOfUser = getGroupsOfUser(userId);
        return postRepository.findPostsByGroupsAndLocation(
                groupsOfUser,
                postLocation.getLongitude(),
                postLocation.getLatitude(),
                postLocation.getRadius()*1000
        );
    }

    public List<Post> getAllPostsForUserSite(PostSite postSite, Long userId) {
        Site site = siteRepository.findSiteBySiteName(postSite.getSitename());
        List<Group> groupsOfUser = getGroupsOfUser(userId);
        return postRepository.findPostsByGroupsAndLocation(
                groupsOfUser,
                site.getLocation().getX(),
                site.getLocation().getY(),
                postSite.getRadius()*1000
        );

    }

    public List<Post> getPostsByLocation(PostLocation postLocation) {
        return postRepository.findAllPostsAroundLocation(
                postLocation.getLongitude(),
                postLocation.getLatitude(),
                postLocation.getRadius()*1000
        );
    }

    public List<Post> getAllPostsForUserTag(String tag, Long userId) {
        List<Group> groupsOfUser = getGroupsOfUser(userId);
        return postRepository.findPostsByGroupsAndTag(groupsOfUser, tag);
    }


    public List<String> getAllTagsForUserGroups(Long userId) {
        List<Group> groupsOfUser = getGroupsOfUser(userId);
        return postRepository.findTagsByGroups(groupsOfUser);
    }

    // POST, DELETE, PUT
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

    public void deletePostById(Long postId, Long userId){
        if (validatePostUser(postId,userId)) {
            postRepository.deleteById(postId);
        }
    }

    public void updatePostText(Long postId, Long userId, PostUpdate postUpdate) {
        if (validatePostUser(postId,userId)){
            Post post = postRepository.getById(postId);
            post.setPost(postUpdate.getText());
            postRepository.save(post);
        }
    }


    // ----------------------------------------
    // Comments
    // ----------------------------------------
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

    public void deleteComment(Long commentId, Long userId) {
        if (validateCommentUser(commentId,userId)){
            commentRepository.deleteById(commentId);
        }
    }

    public void updateComment(Long commentId, Long userId, CommentUpdate commentUpdate) {
        if (validateCommentUser(commentId, userId)){
            Comment comment = commentRepository.getById(commentId);
            comment.setComment(commentUpdate.getUpdatedText());
            commentRepository.save(comment);
        }
    }

    public List<Comment> getCommentsforPost(Long postId) {
        return commentRepository.findCommentsByPostOrderByMadeAtDesc(postId);
    }


    // ----------------------------------------
    // HELPER
    // ----------------------------------------
    public boolean validateCommentUser (Long commentId, Long userId){
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()){
            return checkUserId(commentOptional.get().getUser().getId(), userId);
        }
        else {
            throw new IllegalStateException("Comment with Id:" + commentId +" does not exist!");
        }
    }

    public boolean validatePostUser(Long postId, Long userId){
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()){
            return checkUserId(postOptional.get().getUser().getId(), userId);
        }
        else {
            throw new IllegalStateException("Post with Id:" + postId +" does not exist!");
        }
    }

    public boolean checkUserId(Long storedId, Long providedId){
        if (storedId != providedId){
            throw new IllegalStateException("User with Id :" + providedId + " has no right to change that!");
        }
        return (storedId == providedId);
    }

    public User getUser(Long userId){
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()){
            return userOptional.get();
        }
        else {
            throw new IllegalStateException("User with Id:" + userId + " does not exist" );
        }
    }

    public List<Group> getGroupsOfUser(Long userId){
        User user = getUser(userId);
        return memberService.getGroupsForUser(user);
    }

}
