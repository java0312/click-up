package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.pdp.clickup.entity.*;
import uz.pdp.clickup.entity.enums.AddType;
import uz.pdp.clickup.entity.enums.WorkspacePermissionName;
import uz.pdp.clickup.entity.enums.WorkspaceRoleName;
import uz.pdp.clickup.exceptions.ResourceNotFoundException;
import uz.pdp.clickup.payload.ApiResponse;
import uz.pdp.clickup.payload.MemberDto;
import uz.pdp.clickup.payload.WorkspaceDto;
import uz.pdp.clickup.repository.*;

import java.sql.Timestamp;
import java.util.*;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    WorkspaceUserRepository workspaceUserRepository;

    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;

    @Autowired
    WorkspacePermissionRepository workspacePermissionRepository;

    @Autowired
    JavaMailSender javaMailSender;


    @Override
    public ApiResponse addWorkspace(WorkspaceDto workspaceDto, User user) {

        //WORKSPACE OCHISH
        boolean exists = workspaceRepository.existsByNameAndOwnerId(workspaceDto.getName(), user.getId());
        if (exists)
            return new ApiResponse("You have already opened workspace with this name!", false);

        Workspace workspace = new Workspace(
                workspaceDto.getName(),
                workspaceDto.getColor(),
                user,
                workspaceDto.getName().substring(0, 1),
                workspaceDto.getAvatarId() == null ?
                        null :
                        attachmentRepository.findById(workspaceDto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("attachment not found!"))
        );
        Workspace savedWorkspace = workspaceRepository.save(workspace);


        //WORKSPACE ROLES

        WorkspaceRole ownerRole = workspaceRoleRepository.save(new WorkspaceRole(
                savedWorkspace,
                WorkspaceRoleName.ROLE_OWNER.name(),
                null
        ));

        WorkspaceRole adminRole = workspaceRoleRepository.save(new WorkspaceRole(
                savedWorkspace,
                WorkspaceRoleName.ROLE_ADMIN.name(),
                null
        ));

        WorkspaceRole memberRole = workspaceRoleRepository.save(new WorkspaceRole(
                savedWorkspace,
                WorkspaceRoleName.ROLE_MEMBER.name(),
                null
        ));

        WorkspaceRole guestRole = workspaceRoleRepository.save(new WorkspaceRole(
                savedWorkspace,
                WorkspaceRoleName.ROLE_GUEST.name(),
                null
        ));


        //WORKSPACE HUQUQLARI
        WorkspacePermissionName[] values = WorkspacePermissionName.values();

        List<WorkspacePermission> workspacePermissionList = new ArrayList<>();
        for (WorkspacePermissionName workspacePermissionName : values) {
            workspacePermissionList.add(new WorkspacePermission(
                    ownerRole,
                    workspacePermissionName
            ));

            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)) {
                workspacePermissionList.add(new WorkspacePermission(
                        adminRole,
                        workspacePermissionName
                ));
            }

            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)) {
                workspacePermissionList.add(new WorkspacePermission(
                        memberRole,
                        workspacePermissionName
                ));
            }

            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)) {
                workspacePermissionList.add(new WorkspacePermission(
                        guestRole,
                        workspacePermissionName
                ));
            }

        }
        workspacePermissionRepository.saveAll(workspacePermissionList);


        //WORKSPACE USER
        workspaceUserRepository.save(new WorkspaceUser(
                savedWorkspace,
                user,
                ownerRole,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        ));

        return new ApiResponse("Workspace added!", true);
    }

    @Override
    public ApiResponse editWorkspace(Long id, WorkspaceDto workspaceDto) {
        return null;
    }

    @Override
    public ApiResponse changeOwnerWorkspace(Long id, UUID ownerId) {
        return null;
    }

    @Override
    public ApiResponse deleteWorkspace(Long id) {
        try {
            workspaceRepository.deleteById(id);
            return new ApiResponse("Workspace deleted!", true);
        } catch (Exception e) {
            return new ApiResponse("Error in deleting workspace", false);
        }
    }


    /*
     *  - - - - - - -
     * */

    @Override
    public ApiResponse addOrEditOrRemove(Long id, MemberDto memberDto) {

        if (memberDto.getAddType().equals(AddType.ADD)) {
            WorkspaceUser workspaceUser = new WorkspaceUser(
                    workspaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Workspace not found!")),
                    userRepository.findById(memberDto.getId()).orElseThrow(() -> new ResourceNotFoundException("User not found!")),
                    workspaceRoleRepository.findById(memberDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("Role not found")),
                    new Timestamp(System.currentTimeMillis()),
                    null
            );
            workspaceUserRepository.save(workspaceUser);


            // EMAILGA TAKLIF XABARINI YUBORISH
            User one = userRepository.getOne(memberDto.getId());
            try {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom("Alisher@gmail.com");
                mailMessage.setTo(one.getEmail());
                mailMessage.setText("http://localhost:9090/api/workspace/join");
                mailMessage.setSubject("Join");
                javaMailSender.send(mailMessage);
            }catch (Exception e){
                return new ApiResponse("Error sending email", false);
            }



        } else if (memberDto.getAddType().equals(AddType.EDIT)) {
            Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, memberDto.getId());
            if (optionalWorkspaceUser.isEmpty())
                return new ApiResponse("Workspace user not found!", false);

            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setWorkspaceRole(workspaceRoleRepository.findById(memberDto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("Role not found")));
            workspaceUser.setWorkspace(workspaceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Workspace not found!")));
            workspaceUserRepository.save(workspaceUser);
        } else if (memberDto.getAddType().equals(AddType.REMOVE)) {
            workspaceUserRepository.deleteByWorkspaceIdAndUserId(id, memberDto.getId());
        }

        return new ApiResponse("Work has done", true);
    }


    @Override
    public ApiResponse joinToWorkspace(Long id, User user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
        if (optionalWorkspaceUser.isPresent()){
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepository.save(workspaceUser);
            return new ApiResponse("User joined", true);
        }
        return new ApiResponse("User not joned!", false);
    }


}
