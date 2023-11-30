package com.uhban.travelsync.data.dto.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class GroupResponseDto {
    private Long groupId;
    private String guide;// guide의 userId
    private String groupName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date endDate;
    private String nation;
    private String tourCompany;
    private Boolean toggleLoc;
    private Long tourId;

    @Builder
    public GroupResponseDto(Long groupId, String guide, String groupName, Date startDate, Date endDate, String nation,
                            String tourCompany, Boolean toggleLoc, Long tourId) {
        this.groupId = groupId;
        this.guide = guide;
        this.groupName = groupName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nation = nation;
        this.tourCompany = tourCompany;
        this.toggleLoc = toggleLoc;
        this.tourId = tourId;
    }
}
