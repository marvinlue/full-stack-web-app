package com.example.project3.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GroupRepository
        extends JpaRepository<Group, Long> {
    Optional<Group> findGroupByGroupName (String groupName);
    Optional<Group> findGroupByGidAndGroupAdmin (Long gid, Long groupAdmin);
}