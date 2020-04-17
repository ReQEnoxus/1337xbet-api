package com.enoxus.xbetapi.service;

import com.enoxus.xbetapi.dto.CommentCreationDto;
import com.enoxus.xbetapi.dto.ReplyCreationDto;
import com.enoxus.xbetapi.dto.ReplyDto;
import com.enoxus.xbetapi.dto.UserDto;

public interface ReplyService {
    void createReply(ReplyCreationDto dto, UserDto user);
}
