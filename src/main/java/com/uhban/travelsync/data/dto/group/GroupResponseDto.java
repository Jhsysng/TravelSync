package com.uhban.travelsync.data.dto.group;

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
    private Date startDate;
    private Date endDate;
    private String nation;
    private String tourCompany;
    private Boolean toggleLoc;
    private String tourId;

    @Builder
    public GroupResponseDto(Long groupId, String guide, String groupName, Date startDate, Date endDate, String nation,
                            String tourCompany, Boolean toggleLoc, String tourId) {
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
