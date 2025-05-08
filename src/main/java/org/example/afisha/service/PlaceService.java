package org.example.afisha.service;

import lombok.RequiredArgsConstructor;
import org.example.afisha.entity.Place;
import org.example.afisha.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public void createPlace(String name, String address, String city) {
        Place place = Place.builder()
                .name(name)
                .address(address)
                .city(city)
                .build();
        placeRepository.save(place);
    }

    public List<Place> findAll() {
        return placeRepository.findAll();
    }
    //3.1 Создать сервис + контроллер, позволяющий заводить новые места проведения (на вход: наименования места, адрес, город)
    //и получать список всех существующих мест проведений
}
