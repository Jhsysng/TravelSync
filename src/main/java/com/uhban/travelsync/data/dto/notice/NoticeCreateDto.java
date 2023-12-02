package com.uhban.travelsync.data.dto.notice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class NoticeCreateDto {
    private Long groupId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Date noticeDate;

    private String noticeTitle;
    private Double noticeLatitude;
    private Double noticeLongitude;

    @Builder
    public NoticeCreateDto(Long groupId, Date noticeDate, String noticeTitle, Double noticeLatitude, Double noticeLongitude) {
        this.groupId = groupId;
        this.noticeDate = noticeDate;
        this.noticeTitle = noticeTitle;
        this.noticeLatitude = noticeLatitude;
        this.noticeLongitude = noticeLongitude;
    }
}
