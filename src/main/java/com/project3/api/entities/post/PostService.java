package com.project3.api.entities.post;

import com.project3.api.entities.comment.Comment;
import com.project3.api.entities.comment.CommentRepository;
import com.project3.api.entities.group.Group;
import com.project3.api.entities.group.GroupRepository;
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
            return Collections.emptyList();

    }

    public List<Post> getPostsUserOrGroup(Long userId, Long groupId) {
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

    public List<Post> getAllPostsForUser(User user) {
        List<Group> groupsOfUser = getGroupsOfUser(user);
        return postRepository.findPostsByGroups(groupsOfUser);
    }

    public List<Post> getAllPostsForUserLocation(PostLocation postLocation, User user) {
        List<Group> groupsOfUser = getGroupsOfUser(user);
        return postRepository.findPostsByGroupsAndLocation(
                groupsOfUser,
                postLocation.getLongitude(),
                postLocation.getLatitude(),
                postLocation.getRadius()*1000
        );
    }

    public List<Post> getAllPostsForUserSite(PostSite postSite, User user) {
        Site site = siteRepository.findSiteBySiteName(postSite.getSitename());
        List<Group> groupsOfUser = getGroupsOfUser(user);
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

    public List<Post> getAllPostsForUserTag(String tag, User user) {
        List<Group> groupsOfUser = getGroupsOfUser(user);
        return postRepository.findPostsByGroupsAndTag(groupsOfUser, tag);
    }


    public List<String> getAllTagsForUserGroups(User user) {
        List<Group> groupsOfUser = getGroupsOfUser(user);
        return postRepository.findTagsByGroups(groupsOfUser);
    }

    public List<Post> getAllPostsForUserGroup(Long groupId, User user) {
        List<Group> groupsOfUser = getGroupsOfUser(user);
        if (groupsOfUser.stream().anyMatch(group -> group.getGid().equals(groupId))){
            Group group = groupRepository.getById(groupId);
            return postRepository.findPostsByGroupOrderByPostedAtDesc(group);
        }
        else {
            throw new IllegalStateException("User with Id: " + user.getId() +
                    " has no access right for group: " + groupId);
        }
    }

    // POST, DELETE, PUT
    public void addNewPost(User user, Long groupId, Post post) {
        List<Group> groupsOfUser = getGroupsOfUser(user);
        Optional<Group> groupOptional = groupRepository.findGroupByGid(groupId);

        if (groupsOfUser.stream().anyMatch(group -> group.getGid().equals(groupId))
            && groupOptional.isPresent()){
            post.setUser(user);
            post.setGroup(groupOptional.get());
            post.setPostedAt(Timestamp.valueOf(LocalDateTime.now()));
            System.out.println(post);
            postRepository.save(post);
        }
        else {
            throw new IllegalStateException("User with Id: " + user.getId() +
                    " has no access right for group: " + groupId + "OR Group does not exist");
        }
    }

    public void deletePostById(Long postId, User user){
        if (validatePostUser(postId,user)) {
            postRepository.deleteById(postId);
        }
    }

    public void updatePostText(Long postId, User user, PostUpdate postUpdate) {
        if (validatePostUser(postId,user)){
            Post post = postRepository.getById(postId);
            post.setPost(postUpdate.getText());
            postRepository.save(post);
        }
    }


    // ----------------------------------------
    // Comments
    // ----------------------------------------
    public void addNewComment(Long postId, User user, PostComment postComment) {
        List<Group> groupsOfUser = getGroupsOfUser(user);
        Post post = postRepository.getById(postId);
        Long groupId = post.getGroup().getGid();

        if (groupsOfUser.stream().anyMatch(group -> group.getGid().equals(groupId))){
            Comment comment = new Comment();
            comment.setPost(post);
            comment.setUser(user);
            comment.setComment(postComment.getCommentText());
            comment.setMadeAt(Timestamp.valueOf(LocalDateTime.now()));
            commentRepository.save(comment);
        }
        else {
            throw new IllegalStateException("User with Id: " + user.getId() +
                    " has no access right for group: " + groupId + "OR Group does not exist");
        }
    }

    public void deleteComment(Long commentId, User user) {
        if (validateCommentUser(commentId, user)){
            commentRepository.deleteById(commentId);
        }
    }

    public void updateComment(Long commentId, User user, CommentUpdate commentUpdate) {
        if (validateCommentUser(commentId, user)){
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
    public boolean validateCommentUser (Long commentId, User user){
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()){
            return checkUserId(commentOptional.get().getUser().getId(), user);
        }
        else {
            throw new IllegalStateException("Comment with Id:" + commentId +" does not exist!");
        }
    }

    public boolean validatePostUser(Long postId, User user){
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()){
            return checkUserId(postOptional.get().getUser().getId(), user);
        }
        else {
            throw new IllegalStateException("Post with Id:" + postId +" does not exist!");
        }
    }

    public boolean checkUserId(Long storedId, User user){
        Long providedId = user.getId();
        if (!storedId.equals(providedId)){
            throw new IllegalStateException("User with Id :" + providedId + " has no right to change that!");
        }
        return (storedId.equals(providedId));
    }

    public List<Group> getGroupsOfUser(User user){
        return memberService.getGroupsForUser(user);
    }
}
