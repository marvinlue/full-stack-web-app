package com.project3.api.entities.post;

import com.project3.api.entities.group.Group;
import com.project3.api.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {

    List<Post> findPostsByUserOrderByPostedAtDesc(User user);

    @Query("SELECT p FROM posts p WHERE p.user.id = ?1 OR p.group.gid = ?2 ORDER BY p.postedAt DESC")
    List<Post> findPostsByUserOrGroup (Long userId, Long groupId);

    List<Post> findPostsByPostedAtGreaterThanOrderByPostedAtDesc(Timestamp timestamp);

    List<Post> findPostsByPostedAtLessThanEqualOrderByPostedAtDesc(Timestamp timestamp);

    List<Post> findPostsByGroupOrderByPostedAtDesc(Group group);

    @Query("SELECT p FROM posts p WHERE ST_Distance_Sphere(p.location , ST_SRID(POINT(?1,?2) , 4326) ) < ?3")
    List<Post> findAllPostsAroundLocation (Double userLongitude, Double userLatitude, Integer radius);

    @Query("SELECT p FROM posts p, IN (p.group) g WHERE g in :groupList ORDER BY p.postedAt DESC")
    List<Post> findPostsByGroups(@Param(value = "groupList") List<Group> groupList);

    @Query("SELECT p FROM posts p, IN (p.group) g WHERE g in :groupList " +
            "AND ST_Distance_Sphere(p.location , ST_SRID(POINT(:lon,:lat) , 4326) ) < :radius ORDER BY p.postedAt DESC")
    List<Post> findPostsByGroupsAndLocation(
            @Param(value = "groupList") List<Group> groupsOfUser,
            @Param(value = "lon") Double longitude,
            @Param(value = "lat") Double latitude,
            @Param(value = "radius") Integer radius
    );
    @Query("SELECT p FROM posts p, IN (p.group) g WHERE g in :groupList AND p.category = :tag ORDER BY p.postedAt DESC")
    List<Post> findPostsByGroupsAndTag(
            @Param(value = "groupList") List<Group> groupsOfUser,
            @Param(value = "tag") String tag
    );

    @Query("SELECT DISTINCT p.category FROM posts p, IN (p.group) g WHERE g in :groupList")
    List<String> findTagsByGroups(
            @Param(value = "groupList") List<Group> groupsOfUser
    );
}
