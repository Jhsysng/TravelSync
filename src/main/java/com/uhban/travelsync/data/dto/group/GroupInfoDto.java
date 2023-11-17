package com.uhban.travelsync.data.dto.group;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class GroupInfoDto {
    private Long groupId;
    private String guide;// guide의 userName
    private String groupName;
    private Date startDate;
    private Date endDate;
    private String tourCompany;

    @Builder
    public GroupInfoDto(Long groupId, String guide, String groupName, Date startDate, Date endDate, String tourCompany) {
        this.groupId = groupId;
        this.guide = guide;
        this.groupName = groupName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.tourCompany = tourCompany;
    }

}
