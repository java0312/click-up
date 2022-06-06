package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.*;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.SpaceDto;
import uz.pdp.clickup.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SpaceUserRepository spaceUserRepository;

    @Autowired
    SpaceClickAppsRepository spaceClickAppsRepository;

    @Autowired
    ClickAppsRepository clickAppsRepository;

    @Autowired
    ViewRepository viewRepository;

    @Autowired
    SpaceViewRepository spaceViewRepository;

    @Autowired
    WorkspaceUserRepository workspaceUserRepository;

    @Override
    public ApiResponse addSpace(SpaceDto spaceDto, User owner) {
        boolean existsSpace = spaceRepository.existsByNameAndWorkspaceId(spaceDto.getName(), spaceDto.getWorkspaceId());
        if (existsSpace)
            return new ApiResponse("This space already exists!", false);

        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(spaceDto.getWorkspaceId());
        if (optionalWorkspace.isEmpty())
            return new ApiResponse("Workspace not found!", false);

        //SPACE NI SAQLASH
        Space savedSpace = spaceRepository.save(new Space(
                spaceDto.getName(),
                spaceDto.getColor(),
                optionalWorkspace.get(),
                null,
                spaceDto.getIconId() == null ?
                        null :
                        attachmentRepository.findById(spaceDto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("Icon not found!")),
                spaceDto.getAvatarId() == null ?
                        null :
                        attachmentRepository.findById(spaceDto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("Avatar not found!")),
                spaceDto.getAccessType(),
                owner
        ));


        //SPACE GA USERLARNI BIRLASHTIRISH
        List<SpaceUser> spaceUserList = new ArrayList<>();
        for (Long id : spaceDto.getWorkspaceUsersId()) {
            Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findById(id);
            optionalWorkspaceUser.ifPresent(workspaceUser -> spaceUserList.add(new SpaceUser(
                    savedSpace,
                    workspaceUser.getUser()
            )));
        }
        spaceUserRepository.saveAll(spaceUserList);


        //SPACE GA CLICK APPS LARNI BIRLASHTIRISH
        List<SpaceClickApps> spaceClickAppsList = new ArrayList<>();
        for (Long id : spaceDto.getClickAppsId()) {
            Optional<ClickApps> optionalClickApps = clickAppsRepository.findById(id);
            optionalClickApps.ifPresent(clickApps -> spaceClickAppsList.add(new SpaceClickApps(
                    savedSpace,
                    clickApps
            )));
        }
        spaceClickAppsRepository.saveAll(spaceClickAppsList);


        //SPACE GA VIEW LARNI QO"SHISH
        List<SpaceView> spaceViewList = new ArrayList<>();
        for (Long viewId : spaceDto.getViewsId()) {
            Optional<View> optionalView = viewRepository.findById(viewId);
            optionalView.ifPresent(view -> spaceViewList.add(new SpaceView(
                    savedSpace,
                    view
            )));
        }
        spaceViewRepository.saveAll(spaceViewList);


        return new ApiResponse("space created!", true);
    }

    @Override
    public ApiResponse editSpace(Long id, SpaceDto spaceDto) {
        boolean existsSpace = spaceRepository.existsByNameAndWorkspaceId(spaceDto.getName(), spaceDto.getWorkspaceId());
        if (existsSpace)
            return new ApiResponse("This space already exists!", false);

        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(spaceDto.getWorkspaceId());
        if (optionalWorkspace.isEmpty())
            return new ApiResponse("Workspace not found!", false);

        Optional<Space> optionalSpace = spaceRepository.findById(id);
        if (optionalSpace.isEmpty())
            return new ApiResponse("Space not found!", false);

        Space space = optionalSpace.get();
        space.setName(spaceDto.getName());
        space.setColor(spaceDto.getColor());
        space.setWorkspace(optionalWorkspace.get());
        space.setIconId(
                spaceDto.getIconId() == null ?
                null :
                attachmentRepository.findById(spaceDto.getIconId()).orElseThrow(() -> new ResourceNotFoundException("Icon not found!"))
        );
        space.setAvatarId(
                spaceDto.getAvatarId() == null ?
                null :
                attachmentRepository.findById(spaceDto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("Avatar not found!"))
        );

        spaceRepository.save(space);
        return new ApiResponse("Space edited!", true);
    }

    @Override
    public ApiResponse deleteSpace(Long id) {
        try {
            spaceRepository.deleteById(id);
            return new ApiResponse("Space deleted!", true);
        }catch (Exception e){
            return new ApiResponse("Error in deleting!", false);
        }
    }

    @Override
    public List<Space> getAllSpaces(User owner) {
        return spaceRepository.findAllByOwnerId(owner.getId());
    }

    @Override
    public Space getSpace(Long id, User owner) {
        boolean exists = spaceRepository.existsByIdAndOwnerId(id, owner.getId());
        if (!exists)
            return null;

        Optional<Space> optionalSpace = spaceRepository.findById(id);
        return optionalSpace.orElse(null);
    }

}
