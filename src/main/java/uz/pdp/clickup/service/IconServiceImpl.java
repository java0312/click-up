package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.Attachment;
import uz.pdp.clickup.entity.Icon;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.IconDto;
import uz.pdp.clickup.repository.AttachmentRepository;
import uz.pdp.clickup.repository.IconRepository;

import java.util.List;
import java.util.Optional;

@Service
public class IconServiceImpl implements IconService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    IconRepository iconRepository;

    @Override
    public ApiResponse addIcon(IconDto iconDto) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(iconDto.getAttachmentId());
        if (optionalAttachment.isEmpty())
            return new ApiResponse("Attachment not found!", false);

        iconRepository.save(new Icon(
                optionalAttachment.get(),
                iconDto.getColor()
        ));
        return new ApiResponse("Icon saved!", true);
    }

    @Override
    public ApiResponse editIcon(Long id, IconDto iconDto) {

        Optional<Icon> optionalIcon = iconRepository.findById(id);
        if (optionalIcon.isEmpty())
            return new ApiResponse("Icon not found!", false);

        Optional<Attachment> optionalAttachment = attachmentRepository.findById(iconDto.getAttachmentId());
        if (optionalAttachment.isEmpty())
            return new ApiResponse("Attachment not found!", false);

        Icon icon = optionalIcon.get();
        icon.setAttachment(optionalAttachment.get());
        icon.setColor(iconDto.getColor());
        iconRepository.save(icon);

        return new ApiResponse("Icon edited!", true);
    }

    @Override
    public ApiResponse deleteIcon(Long id) {
        try {
            iconRepository.deleteById(id);
            return new ApiResponse("Icon deleted", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting!", false);
        }
    }

    @Override
    public List<Icon> getAllIcons() {
        return iconRepository.findAll();
    }

    @Override
    public Icon getIcon(Long id) {
        return iconRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("icon not found!"));
    }
}
