package com.project3.api.entities.post;

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

    @Query("SELECT p FROM posts p WHERE ST_Distance_Sphere(p.location , ST_SRID(POINT(?1,?2) , 4326) ) < ?3")
    List<Post> findAllPostsAroundLocation (Double userLongitude, Double userLatitude, Integer radius);
}
