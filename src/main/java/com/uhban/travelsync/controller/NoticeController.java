package com.uhban.travelsync.controller;


import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.data.dto.notice.NoticeChangeDto;
import com.uhban.travelsync.data.dto.notice.NoticeCreateDto;
import com.uhban.travelsync.data.dto.notice.NoticeResponseDto;
import com.uhban.travelsync.data.entity.Notice;
import com.uhban.travelsync.service.GroupService;
import com.uhban.travelsync.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class NoticeController {

    private final NoticeService noticeService;
    private final GroupService groupService;

    public NoticeController(NoticeService noticeService, GroupService groupService) {
        this.noticeService = noticeService;
        this.groupService = groupService;
    }

    @GetMapping("/notice/{groupId}")
    public ResponseEntity<List<NoticeResponseDto>> getNoticeList(Long groupId){
        log.info("[NoticeController] getNoticeList groupId : {}", groupId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String userId = principalDetails.getUserId();

        if(!groupService.isUserInGroup(userId, groupId)){
            log.error("[NoticeController] getNoticeList 인증된 사용자가 그룹에 속해있지 않습니다.");
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(noticeService.getNoticeList(groupId));
        }
    }

    @PostMapping("/notice")
    public ResponseEntity<NoticeResponseDto> createNotice(@RequestBody NoticeCreateDto noticeCreateDto){
        log.info("[NoticeController] createNotice groupId : {}", noticeCreateDto.getGroupId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String userId = principalDetails.getUserId();

        return ResponseEntity.ok(noticeService.saveNotice(userId, noticeCreateDto));
    }

    @PutMapping("/notice")
    public ResponseEntity<NoticeResponseDto> editNotice(@RequestBody NoticeChangeDto noticeChangeDto){
        log.info("[NoticeController] editNotice noticeId : {}", noticeChangeDto.getNoticeId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String userId = principalDetails.getUserId();

        return ResponseEntity.ok(noticeService.changeNotice(userId, noticeChangeDto));
    }

    @DeleteMapping("/notice/{noticeId}")
    public ResponseEntity<Void> deleteNotice(@PathVariable Long noticeId){
        log.info("[NoticeController] deleteNotice noticeId : {}", noticeId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String userId = principalDetails.getUserId();

        noticeService.deleteNotice(userId, noticeId);
        return ResponseEntity.ok().build();
    }

}
