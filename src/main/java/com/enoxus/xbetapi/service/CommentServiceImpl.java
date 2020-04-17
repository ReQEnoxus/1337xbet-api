package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.dto.CommentCreationDto;
import com.enoxus.xbetapi.dto.CommentDto;
import com.enoxus.xbetapi.dto.ReplyDto;
import com.enoxus.xbetapi.dto.UserDto;
import com.enoxus.xbetapi.entity.Comment;
import com.enoxus.xbetapi.repository.CommentRepository;
import com.enoxus.xbetapi.repository.MatchesRepository;
import com.enoxus.xbetapi.repository.ReplyRepository;
import com.enoxus.xbetapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private UsersService userService;

    @Autowired
    private MatchesRepository matchRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CommentDto> getCommentsForMatch(Long matchId) {
        List<Comment> comments = commentRepository.getAllByMatchId(matchId);

        return comments.stream()
                .map(comment -> CommentDto.builder()
                        .id(comment.getId())
                        .owner(userService.getUserByLogin(comment.getOwner().getLogin()).orElseThrow(IllegalArgumentException::new))
                        .text(comment.getText())
                        .replies(replyRepository.getAllByParentId(comment.getId())
                                .stream()
                                .map(reply -> ReplyDto.builder()
                                        .owner(userService.getUserByLogin(reply.getOwner().getLogin()).orElseThrow(IllegalArgumentException::new))
                                        .text(reply.getText())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void createComment(CommentCreationDto dto, UserDto user) {
        Comment comment = Comment.builder()
                .match(matchRepository.findById(dto.getMatchId()).orElseThrow(IllegalArgumentException::new))
                .owner(userRepository.findById(user.getId()).orElseThrow(IllegalArgumentException::new))
                .text(dto.getText())
                .build();
        commentRepository.save(comment);
    }
}
