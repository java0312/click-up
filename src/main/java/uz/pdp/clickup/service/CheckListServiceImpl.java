package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.CheckList;
import uz.pdp.clickup.entity.CheckListItem;
import uz.pdp.clickup.entity.User;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.CheckListDto;
import uz.pdp.clickup.repository.CheckListItemRepository;
import uz.pdp.clickup.repository.CheckListRepository;
import uz.pdp.clickup.repository.TaskRepository;
import uz.pdp.clickup.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CheckListServiceImpl implements CheckListService {

    @Autowired
    CheckListRepository checkListRepository;

    @Autowired
    CheckListItemRepository checkListItemRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ApiResponse addCheckList(CheckListDto checkListDto) {
        checkListRepository.save(new CheckList(
                checkListDto.getName(),
                taskRepository.findById(checkListDto.getTaskId()).orElseThrow(() -> new ResourceNotFoundException("Task not found!"))
        ));
        return new ApiResponse("CheckList added!", true);
    }

    @Override
    public ApiResponse editCheckList(Long id, CheckListDto checkListDto) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(id);
        if (optionalCheckList.isEmpty())
            return new ApiResponse("CheckList not found!", false);
        CheckList checkList = optionalCheckList.get();
        checkList.setName(checkListDto.getName());
        checkListRepository.save(checkList);
        return new ApiResponse("Check List edited!", true);
    }

    @Override
    public ApiResponse deleteCheckList(Long id) {
        Optional<CheckList> optionalCheckList = checkListRepository.findById(id);
        if (optionalCheckList.isEmpty())
            return new ApiResponse("checkList not found!", false);

        checkListItemRepository.deleteAllByCheckListId(optionalCheckList.get().getId());

        checkListRepository.deleteById(id);
        return new ApiResponse("CheckList Deleted!", true);
    }

    @Override
    public List<CheckList> getAllCheckListByTaskId(Long taskId) {
        return checkListRepository.findAllByTaskId(taskId);
    }


    @Override
    public ApiResponse assignUserToCheckList(Long id, UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())
            return new ApiResponse("User not found!", false);

        List<CheckListItem> checkListItems = checkListItemRepository.findAllByCheckListId(id);
        for (CheckListItem checkListItem : checkListItems) {
            checkListItem.setAssignUser(optionalUser.get());
        }
        checkListItemRepository.saveAll(checkListItems);

        return new ApiResponse("Users assigned", true);
    }

    @Override
    public ApiResponse unAssignAllByCheckListId(Long id) {
        List<CheckListItem> checkListItems = checkListItemRepository.findAllByCheckListId(id);
        for (CheckListItem checkListItem : checkListItems) {
            checkListItem.setAssignUser(null);
        }
        checkListItemRepository.saveAll(checkListItems);

        return new ApiResponse("Users UnAssigned", true);
    }

    @Override
    public ApiResponse checkAllItemByCheckListId(Long id) {
        List<CheckListItem> checkListItems = checkListItemRepository.findAllByCheckListId(id);
        for (CheckListItem checkListItem : checkListItems) {
            checkListItem.setResolved(true);
        }
        checkListItemRepository.saveAll(checkListItems);
        return new ApiResponse("CheckListItems resolved", true);
    }

    @Override
    public ApiResponse unCheckAllItemByCheckListId(Long id) {
        List<CheckListItem> checkListItems = checkListItemRepository.findAllByCheckListId(id);
        for (CheckListItem checkListItem : checkListItems) {
            checkListItem.setResolved(false);
        }
        checkListItemRepository.saveAll(checkListItems);
        return new ApiResponse("CheckListItems UnResolved", true);
    }
}
