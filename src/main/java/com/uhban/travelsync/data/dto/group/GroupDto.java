package com.uhban.travelsync.data.dto.group;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class GroupDto {
    private Long groupId;
    private String guide;
    private String groupName;
    private String tourCompany;
    private String nation;
    private Date startDate;
    private Date endDate;
    private Boolean toggleLoc;
    private Long tourId;

    @Builder
    public GroupDto(Long groupId, String guide, String groupName, String tourCompany, String nation, Date startDate, Date endDate, Boolean toggleLoc, Long tourId) {
        this.groupId = groupId;
        this.guide = guide;
        this.groupName = groupName;
        this.tourCompany = tourCompany;
        this.nation = nation;
        this.startDate = startDate;
        this.endDate = endDate;
        this.toggleLoc = toggleLoc;
        this.tourId = tourId;
    }

}
