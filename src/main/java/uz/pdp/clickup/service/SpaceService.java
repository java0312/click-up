package uz.pdp.clickup.service;

import uz.pdp.clickup.entity.Space;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.SpaceDto;

import java.util.List;

public interface SpaceService {

    ApiResponse addSpace(SpaceDto spaceDto, User owner);

    ApiResponse editSpace(Long id, SpaceDto spaceDto);

    ApiResponse deleteSpace(Long id);

    List<Space> getAllSpaces(User owner);

    Space getSpace(Long id, User owner);
}
