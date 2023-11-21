package com.uhban.travelsync.service;

import com.uhban.travelsync.data.dto.notice.NoticeChangeDto;
import com.uhban.travelsync.data.dto.notice.NoticeCreateDto;
import com.uhban.travelsync.data.dto.notice.NoticeResponseDto;

import java.util.List;

public interface NoticeService {
    List<NoticeResponseDto> getNoticeList(Long groupId);
    NoticeResponseDto saveNotice(String userId, NoticeCreateDto noticeCreateDto);
    NoticeResponseDto changeNotice(String userId, NoticeChangeDto noticeChangeDto);
    void deleteNotice(String userId, Long noticeId);
}
