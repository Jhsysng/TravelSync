package com.uhban.travelsync.service;

import com.uhban.travelsync.data.dto.tour.TourCreateDto;
import com.uhban.travelsync.data.dto.tour.TourDto;
import com.uhban.travelsync.data.dto.tour.TourResponseDto;

import java.util.List;

public interface TourService {

    TourResponseDto getTour(Long tourId);
    List<TourResponseDto> getTourList(String userId);
    TourResponseDto saveTour(String userId, TourCreateDto tourCreateDto);
    TourResponseDto changeTour(String userId, TourDto tourDto);
    void deleteTour(String userId, Long tourId);
}
