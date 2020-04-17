package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.dto.CommentCreationDto;
import com.enoxus.xbetapi.dto.ReplyCreationDto;
import com.enoxus.xbetapi.dto.ReplyDto;
import com.enoxus.xbetapi.dto.UserDto;
import com.enoxus.xbetapi.entity.Reply;
import com.enoxus.xbetapi.repository.CommentRepository;
import com.enoxus.xbetapi.repository.MatchesRepository;
import com.enoxus.xbetapi.repository.ReplyRepository;
import com.enoxus.xbetapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchesRepository matchRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void createReply(ReplyCreationDto dto, UserDto user) {
        if (dto.getText() == null) throw new IllegalArgumentException("Text should not be null");
        Reply reply = Reply.builder()
                .owner(userRepository.findById(user.getId()).orElseThrow(IllegalArgumentException::new))
                .parent(commentRepository.findById(dto.getParentId()).orElseThrow(IllegalArgumentException::new))
                .text(dto.getText())
                .build();
        replyRepository.save(reply);
    }
}
