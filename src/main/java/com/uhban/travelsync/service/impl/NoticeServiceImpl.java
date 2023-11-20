package com.uhban.travelsync.service.impl;

import com.uhban.travelsync.data.dto.notice.NoticeChangeDto;
import com.uhban.travelsync.data.dto.notice.NoticeCreateDto;
import com.uhban.travelsync.data.dto.notice.NoticeResponseDto;
import com.uhban.travelsync.data.entity.Group;
import com.uhban.travelsync.data.entity.Notice;
import com.uhban.travelsync.data.repository.GroupRepository;
import com.uhban.travelsync.data.repository.NoticeRepository;
import com.uhban.travelsync.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;
    private final GroupRepository groupRepository;

    public NoticeServiceImpl(NoticeRepository noticeRepository, GroupRepository groupRepository) {
        this.noticeRepository = noticeRepository;
        this.groupRepository = groupRepository;
    }
    @Transactional
    public List<NoticeResponseDto> getNoticeList(Long groupId){
        log.info("[NoticeService] getNoticeList : {}", groupId);

        Optional<Group> optionalGroup = groupRepository.findById(groupId);
        if (optionalGroup.isEmpty()) {
            log.error("Group not found for ID: {}", groupId);
            throw new IllegalArgumentException("Group not found for ID: " + groupId);
        }

        List<Notice> noticeList = noticeRepository.findAllByGroup(optionalGroup.get().getGroupId());
        log.info("[NoticeService] getNoticeList Success : {}", groupId);
        return noticeList.stream()
                .map(notice -> new NoticeResponseDto(
                        notice.getNoticeId(),
                        notice.getNoticeDate(),
                        notice.getNoticeLatitude(),
                        notice.getNoticeLongitude()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public NoticeResponseDto saveNotice(String userId, NoticeCreateDto noticeCreateDto){
        log.info("[NoticeService] saveNotice groupId : {}", noticeCreateDto.getGroupId());
        Optional<Group> optionalGroup = groupRepository.findById(noticeCreateDto.getGroupId());
        if (optionalGroup.isEmpty()) {
            log.error("Group not found for ID: {}", noticeCreateDto.getGroupId());
            throw new IllegalArgumentException("Group not found for ID: " + noticeCreateDto.getGroupId());
        }else if(!optionalGroup.get().getGuide().getUserId().equals(userId)){
            log.info("[NoticeService] saveNotice 인증된 사용자가 그룹의 가이드가 아닙니다.");
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }


        Notice notice = noticeRepository.save(Notice.builder()
                .noticeDate(noticeCreateDto.getNoticeDate())
                .noticeLatitude(noticeCreateDto.getNoticeLatitude())
                .noticeLongitude(noticeCreateDto.getNoticeLongitude())
                .group(optionalGroup.get())
                .build());

        log.info("[NoticeService] saveNotice Success : {}", noticeCreateDto.getGroupId());
        return NoticeResponseDto.builder()
                .noticeId(notice.getNoticeId())
                .noticeDate(noticeCreateDto.getNoticeDate())
                .noticeLatitude(noticeCreateDto.getNoticeLatitude())
                .noticeLongitude(noticeCreateDto.getNoticeLongitude())
                .build();
    }

    @Transactional
    public NoticeResponseDto changeNotice(String userId, NoticeChangeDto noticeChangeDto){
        log.info("[NoticeService] changeNotice noticeId : {}", noticeChangeDto.getNoticeId());
        Optional<Notice> optionalNotice = noticeRepository.findById(noticeChangeDto.getNoticeId());
        if (optionalNotice.isEmpty()) {
            log.error("Notice not found for ID: {}", noticeChangeDto.getNoticeId());
            throw new IllegalArgumentException("Notice not found for ID: " + noticeChangeDto.getNoticeId());
        }else if(!optionalNotice.get().getGroup().getGuide().getUserId().equals(userId)){
            log.info("[NoticeService] changeNotice 인증된 사용자가 그룹의 가이드가 아닙니다.");
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }

        Notice changedNotice = noticeRepository.save(Notice.builder()
                .noticeId(noticeChangeDto.getNoticeId())
                .noticeDate(noticeChangeDto.getNoticeDate())
                .noticeLatitude(noticeChangeDto.getNoticeLatitude())
                .noticeLongitude(noticeChangeDto.getNoticeLongitude())
                .group(optionalNotice.get().getGroup())
                .build());
        log.info("[NoticeService] changeNotice Success : {}", noticeChangeDto.getNoticeId());
        return NoticeResponseDto.builder()
                .noticeId(changedNotice.getNoticeId())
                .noticeDate(changedNotice.getNoticeDate())
                .noticeLatitude(changedNotice.getNoticeLatitude())
                .noticeLongitude(changedNotice.getNoticeLongitude())
                .build();
    }

    @Transactional
    public void deleteNotice(String userId, Long noticeId){
        log.info("[NoticeService] deleteNotice noticeId : {}", noticeId);
        Optional<Notice> optionalNotice = noticeRepository.findById(noticeId);
        if (optionalNotice.isEmpty()) {
            log.error("Notice not found for ID: {}", noticeId);
            throw new IllegalArgumentException("Notice not found for ID: " + noticeId);
        }else if(!optionalNotice.get().getGroup().getGuide().getUserId().equals(userId)){
            log.info("[NoticeService] deleteNotice 인증된 사용자가 그룹의 가이드가 아닙니다.");
            throw new IllegalArgumentException("User not found for ID: " + userId);
        }
        noticeRepository.deleteById(noticeId);
        log.info("[NoticeService] deleteNotice Success : {}", noticeId);
    }

}
