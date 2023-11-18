package com.uhban.travelsync.service;

import com.uhban.travelsync.data.dto.tour.TourCreateDto;
import com.uhban.travelsync.data.dto.tour.TourResponseDto;

public interface TourService {
    TourResponseDto getTour(String userId);
    TourResponseDto saveTour(String userId, TourCreateDto tourCreateDto);
}
