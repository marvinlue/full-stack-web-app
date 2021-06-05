package com.project3.api.entities.post;

import com.project3.api.entities.group.Group;
import com.project3.api.entities.group.GroupRepository;
import com.project3.api.entities.post.dto.PostLocation;
import com.project3.api.entities.post.dto.PostSite;
import com.project3.api.entities.post.dto.PostTime;
import com.project3.api.entities.post.dto.PostUpdate;
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

    @Autowired
    public PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            GroupRepository groupRepository
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
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
        return postRepository.findAllPostsAroundLocation(postLocation.getLongitude(), postLocation.getLatitude(), postLocation.getRadius());
    }

    //TODO: implement Site-Repository & Site-Service
    public List<Post> getPostBySite(PostSite postSite) {
        return Collections.<Post>emptyList();
    }
}
