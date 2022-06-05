package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Space;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.entity.Workspace;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.SpaceDto;
import uz.pdp.clickup.repository.AttachmentRepository;
import uz.pdp.clickup.repository.SpaceRepository;
import uz.pdp.clickup.repository.WorkspaceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    SpaceRepository spaceRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Override
    public ApiResponse addSpace(SpaceDto spaceDto, User owner) {
        boolean existsSpace = spaceRepository.existsByNameAndWorkspaceId(spaceDto.getName(), spaceDto.getWorkspaceId());
        if (existsSpace)
            return new ApiResponse("This space already exists!", false);

        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(spaceDto.getWorkspaceId());
        if (optionalWorkspace.isEmpty())
            return new ApiResponse("Workspace not found!", false);

        spaceRepository.save(new Space(
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
