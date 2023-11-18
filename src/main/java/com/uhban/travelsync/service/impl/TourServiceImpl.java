package com.uhban.travelsync.service.impl;

import com.uhban.travelsync.data.dto.tour.TourCreateDto;
import com.uhban.travelsync.data.dto.tour.TourResponseDto;
import com.uhban.travelsync.data.entity.Tour;
import com.uhban.travelsync.data.entity.User;
import com.uhban.travelsync.data.repository.TourRepository;
import com.uhban.travelsync.data.repository.UserRepository;
import com.uhban.travelsync.service.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class TourServiceImpl implements TourService {
    private final UserRepository userRepository;
    private final TourRepository tourRepository;

    public TourServiceImpl(UserRepository userRepository, TourRepository tourRepository) {
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
    }

    @Transactional(readOnly = true)
    public List<TourResponseDto> getTour(String userId) {
        return null;
    }

    @Transactional
    public TourResponseDto saveTour(String userId, TourCreateDto tourCreateDto) {
        log.info("[TourService] saveTour : {}", userId);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("해당 사용자 : {}를 찾을 수 없습니다. : ", userId);
                    throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. : " + userId);
                });
        Tour tour = Tour.builder()
                .user(user)
                .tourName(tourCreateDto.getTourName())
                .tourCompany(tourCreateDto.getTourCompany())
                .build();
        Tour savedTour = tourRepository.save(tour);
        log.info("[TourService] saveTour Success {}", savedTour.getTourId());

        return TourResponseDto.builder()
                .tourId(savedTour.getTourId())
                .tourName(savedTour.getTourName())
                .tourCompany(savedTour.getTourCompany())
                .build();
    }

}
