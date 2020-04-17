package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.dto.CommentCreationDto;
import com.enoxus.xbetapi.dto.CommentDto;
import com.enoxus.xbetapi.dto.UserDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getCommentsForMatch(Long matchId);

    void createComment(CommentCreationDto dto, UserDto user);
}
