package com.uhban.travelsync.service.impl;

import com.uhban.travelsync.data.dto.tour.TourCreateDto;
import com.uhban.travelsync.data.dto.tour.TourDto;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class TourServiceImpl implements TourService {
    private final UserRepository userRepository;
    private final TourRepository tourRepository;

    public TourServiceImpl(UserRepository userRepository, TourRepository tourRepository) {
        this.userRepository = userRepository;
        this.tourRepository = tourRepository;
    }
    @Transactional
    public TourResponseDto getTour(Long tourId) {
        log.info("[TourService] getTour : {}", tourId);
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> {
                    log.error("해당 여행 : {}를 찾을 수 없습니다. : ", tourId);
                    throw new IllegalArgumentException("해당 여행을 찾을 수 없습니다. : " + tourId);
                });
        log.info("[TourService] getTour Success : {}", tourId);
        return TourResponseDto.builder()
                .tourId(tourId)
                .tourCompany(tour.getTourCompany())
                .tourName(tour.getTourName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<TourResponseDto> getTourList(String userId) {
        log.info("[TourService] getTourList : {}", userId);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("해당 사용자 : {}를 찾을 수 없습니다. : ", userId);
                    throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. : " + userId);
                });

        List<Tour> tourList = tourRepository.findAllByUser(user);
        log.info("[TourService] getTourList Success : {}", userId);
        return tourList.stream()
                .map(tour -> new TourResponseDto(
                        tour.getTourId(),
                        tour.getTourName(),
                        tour.getTourCompany()
                ))
                .collect(Collectors.toList());
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

    @Transactional
    public TourResponseDto changeTour(String userId, TourDto tourDto){
        log.info("[TourService] tourChange : {}", tourDto.getTourId());
        Tour tour = tourRepository.findById(tourDto.getTourId())
                .orElseThrow(() -> {
                    log.error("해당 여행 : {}를 찾을 수 없습니다. : ", tourDto.getTourId());
                    throw new IllegalArgumentException("해당 여행을 찾을 수 없습니다. : " + tourDto.getTourId());
                });
        if(!tour.getUser().getUserId().equals(userId)){
            log.error("해당 여행 : {}를 수정할 수 없습니다. : ", tourDto.getTourId());
            throw new IllegalArgumentException("해당 여행을 수정할 수 없습니다. : " + tourDto.getTourId());
        }
        tourRepository.save(Tour.builder()
                .tourId(tour.getTourId())
                .user(tour.getUser())
                .tourName(tourDto.getTourName())
                .tourCompany(tourDto.getTourCompany())
                .build());
        log.info("[TourService] tourChange Success : {}", tourDto.getTourId());
        return TourResponseDto.builder()
                .tourId(tourDto.getTourId())
                .tourName(tourDto.getTourName())
                .tourCompany(tourDto.getTourCompany())
                .build();
    }


    @Transactional
    public void deleteTour(String userId, Long tourId) {
        log.info("[TourService] deleteTour : {}", tourId);
        Tour tour = tourRepository.findById(tourId)
                .orElseThrow(() -> {
                    log.error("해당 여행 : {}를 찾을 수 없습니다. : ", tourId);
                    throw new IllegalArgumentException("해당 여행을 찾을 수 없습니다. : " + tourId);
                });
        if(!tour.getUser().getUserId().equals(userId)){
            log.error("해당 여행 : {}를 삭제할 수 없습니다. : ", tourId);
            throw new IllegalArgumentException("해당 여행을 삭제할 수 없습니다. : " + tourId);
        }
        tourRepository.deleteById(tourId);
        log.info("[TourService] deleteTour Success : {}", tourId);
    }
}
