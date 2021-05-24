package com.project3.api.entities.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GroupRepository
        extends JpaRepository<Group, Long> {
    Optional<Group> findGroupByGroupName (String groupName);
    Optional<Group> findGroupByGid (Long groupId);
    Optional<Group> findGroupByGidAndGroupAdmin (Long groupId, Long groupAdmin);
    Optional<Group> findGroupByGidAndGroupName (Long groupId, String groupName);
}
