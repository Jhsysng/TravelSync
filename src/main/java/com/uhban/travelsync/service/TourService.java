package com.uhban.travelsync.service;

import com.uhban.travelsync.data.dto.tour.TourCreateDto;
import com.uhban.travelsync.data.dto.tour.TourResponseDto;

import java.util.List;

public interface TourService {
    List<TourResponseDto> getTour(String userId);
    TourResponseDto saveTour(String userId, TourCreateDto tourCreateDto);
}
