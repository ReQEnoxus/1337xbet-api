package com.enoxus.xbetapi.controllers;

import com.enoxus.xbetapi.dto.CommentCreationDto;
import com.enoxus.xbetapi.dto.CommentDto;
import com.enoxus.xbetapi.dto.ReplyCreationDto;
import com.enoxus.xbetapi.security.jwt.details.UserDetailsImpl;
import com.enoxus.xbetapi.service.CommentService;
import com.enoxus.xbetapi.service.ReplyService;
import com.enoxus.xbetapi.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ReplyService replyService;

    @GetMapping("{matchId}")
    public ResponseEntity<List<CommentDto>> commentsForMatch(@PathVariable Long matchId) {
        return ResponseEntity.ok(commentService.getCommentsForMatch(matchId));
    }

    @PostMapping("/createComment")
    public ResponseEntity<List<CommentDto>> createComment(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentCreationDto dto) {
        commentService.createComment(dto, usersService.getUserByLogin(userDetails.getLogin()).get());
        return ResponseEntity.ok(commentService.getCommentsForMatch(dto.getMatchId()));
    }

    @PostMapping("/createReply")
    public ResponseEntity<List<CommentDto>> createReply(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ReplyCreationDto dto) {
        replyService.createReply(dto, usersService.getUserByLogin(userDetails.getLogin()).get());
        return ResponseEntity.ok(commentService.getCommentsForMatch(dto.getMatchId()));
    }
}
