package com.project3.api.entities.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT c FROM comments c WHERE c.post.pid = ?1 ORDER BY c.madeAt ASC")
    List<Comment> findCommentsByPostOrderByMadeAtDesc(Long postId);
}
