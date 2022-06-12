package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.CheckListItem;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CheckListItemDto;
import uz.pdp.clickup.repository.CheckListItemRepository;
import uz.pdp.clickup.repository.CheckListRepository;
import uz.pdp.clickup.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CheckListItemServiceImpl implements CheckListItemService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    CheckListItemRepository checkListItemRepository;

    @Autowired
    CheckListRepository checkListRepository;

    @Override
    public ApiResponse addCheckListItem(CheckListItemDto checkListItemDto) {

        boolean exists = checkListItemRepository.existsByNameAndCheckListId(checkListItemDto.getName(), checkListItemDto.getCheckListId());
        if(exists)
            return new ApiResponse("CheckListItem exists!", false);

        checkListItemRepository.save(new CheckListItem(
                checkListItemDto.getName(),
                checkListRepository.findById(checkListItemDto.getCheckListId()).orElseThrow(() -> new ResourceNotFoundException("Check list not found")),
                false,
                userRepository.findById(checkListItemDto.getAssignUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found!"))
        ));

        return new ApiResponse("CheckListItem added!", true);
    }

    @Override
    public ApiResponse editCheckListItem(Long id, CheckListItemDto checkListItemDto) {

        boolean exists = checkListItemRepository.existsByNameAndCheckListIdAndIdNot(checkListItemDto.getName(), checkListItemDto.getCheckListId(), id);
        if (exists)
            return new ApiResponse("CheckList exists!", false);

        Optional<CheckListItem> optionalCheckListItem = checkListItemRepository.findById(id);
        if (optionalCheckListItem.isEmpty())
            return new ApiResponse("CheckListItem not found!", false);

        CheckListItem checkListItem = optionalCheckListItem.get();
        checkListItem.setName(checkListItemDto.getName());
        checkListItem.setCheckList(checkListRepository.findById(checkListItemDto.getCheckListId()).orElseThrow(() -> new ResourceNotFoundException("Check list not found")));
        checkListItem.setResolved(checkListItemDto.isResolved());
        checkListItem.setAssignUser(checkListItemDto.getAssignUserId() == null ?
                null :
                userRepository.findById(checkListItemDto.getAssignUserId()).orElseThrow(() -> new ResourceNotFoundException("User not found!")));
        checkListItemRepository.save(checkListItem);

        return new ApiResponse("CheckListItem edited!", true);
    }

    @Override
    public ApiResponse deleteCheckListItem(Long id) {
        try {
            checkListItemRepository.deleteById(id);
            return new ApiResponse("CheckListItem deleted!", true);
        }catch (Exception e) {
            return new ApiResponse("CheckListItem not found!", false);
        }
    }

    @Override
    public List<CheckListItem> getAllCheckListItemByCheckListId(Long checkListId) {
        return checkListItemRepository.findAllByCheckListId(checkListId);
    }
}
