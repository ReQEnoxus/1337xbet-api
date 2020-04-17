package com.enoxus.xbetapi.repository;

import com.enoxus.xbetapi.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> getAllByParentId(Long id);
}