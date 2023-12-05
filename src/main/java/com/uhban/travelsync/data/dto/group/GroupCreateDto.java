package com.uhban.travelsync.data.dto.group;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class GroupCreateDto {
    private String guide;
    private String groupName;
    private String tourCompany;
    private Date startDate;
    private Date endDate;
    private String nation;
    private String groupPassword;

    @Builder
    public GroupCreateDto(String guide, String groupName, String tourCompany, Date startDate, Date endDate, String nation, String groupPassword) {
        this.guide = guide;
        this.groupName = groupName;
        this.tourCompany = tourCompany;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nation = nation;
        this.groupPassword = groupPassword;
    }

}

