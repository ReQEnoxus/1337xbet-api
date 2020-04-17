package com.enoxus.xbetapi.repository;

import com.enoxus.xbetapi.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> getAllByMatchId(Long matchId);
}
