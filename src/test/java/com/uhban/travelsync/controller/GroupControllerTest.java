package com.uhban.travelsync.controller;

import com.uhban.travelsync.TravelsyncApplication;
import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.data.dto.group.GroupCreateDto;
import com.uhban.travelsync.data.dto.group.GroupResponseDto;
import com.uhban.travelsync.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.text.SimpleDateFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.OK;


@SpringBootTest(classes = TravelsyncApplication.class)
public class GroupControllerTest {

    @InjectMocks
    private GroupController groupController;

    @Mock
    private GroupService groupService;

    private GroupCreateDto groupCreateDto;
    private PrincipalDetails principalDetails;
    private GroupResponseDto groupResponseDto;

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    void setUp() {
        try {
            groupCreateDto = GroupCreateDto.builder()
                    .groupName("TestGroup")
                    .guide("user1@uhban.com")
                    .startDate(formatter.parse("2021-06-01"))
                    .endDate(formatter.parse("2021-06-10"))
                    .groupPassword("1234")
                    .build(); // GroupCreateDto 초기화
        } catch (Exception e) {
            e.printStackTrace();
        }
        principalDetails = mock(PrincipalDetails.class); // PrincipalDetails 모의 객체 생성
        try{
            groupResponseDto = groupResponseDto.builder()
                    .groupName("TestGroup")
                    .guide("user1@uhban.com")
                    .startDate(formatter.parse("2021-06-01"))
                    .endDate(formatter.parse("2021-06-10"))
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }


        // 필요한 경우 DTO의 필드를 설정하세요.
        // 예: groupCreateDto.setGroupName("Test Group");
        // principalDetails의 메서드가 호출될 때 반환할 값을 설정하세요.
        when(principalDetails.getUserId()).thenReturn("user1@uhban.com");
    }

    @Test
    @DisplayName("createGroup 메서드 테스트")
    void createGroup_ValidData_ReturnsGroupResponseDto() {
        // GroupService의 saveGroup 메서드가 호출될 때 기대되는 결과 설정
        when(groupService.saveGroup(any(GroupCreateDto.class))).thenReturn(groupResponseDto);

        // 메서드 실행
        ResponseEntity<GroupResponseDto> response = groupController.createGroup(groupCreateDto, principalDetails);

        // 결과 검증
        assertEquals(OK, response.getStatusCode());
        assertEquals(groupResponseDto, response.getBody());
    }



}
