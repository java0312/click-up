package uz.pdp.clickup.service;

import org.springframework.beans.factory.annotation.Autowired;
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
import uz.pdp.clickup.payload.WorkspaceRoleDto;
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
    public ApiResponse editWorkspace(Long id, WorkspaceDto workspaceDto, User user) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (optionalWorkspace.isEmpty())
            return new ApiResponse("Workspace not found!", false);

        boolean exists = workspaceRepository.existsByNameAndOwnerIdAndIdNot(workspaceDto.getName(), user.getId(), id);
        if (exists)
            return new ApiResponse("This workspace exists!", false);

        Workspace workspace = optionalWorkspace.get();
        workspace.setName(workspaceDto.getName());
        workspace.setColor(workspaceDto.getColor());
        workspace.setAvatar(
                workspaceDto.getAvatarId() == null ?
                        null :
                        attachmentRepository.findById(workspaceDto.getAvatarId()).orElseThrow(() -> new ResourceNotFoundException("Attachment not found!"))
        );
        workspaceRepository.save(workspace);

        return new ApiResponse("Workspace edited!", true);
    }

    /*
     * Long id - workspace ning id si
     * UUID ownerId - endi owner boladigan user id si
     * User user - owner
     * */
    @Override
    public ApiResponse changeOwnerWorkspace(Long id, UUID ownerId, User user) {

        Optional<User> optionalUser = userRepository.findById(ownerId);
        if (optionalUser.isEmpty())
            return new ApiResponse("User not found!", false);

        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(id);
        if (optionalWorkspace.isEmpty())
            return new ApiResponse("Workspace not found!", false);

        Workspace workspace = optionalWorkspace.get();
        workspace.setOwner(optionalUser.get());
        workspaceRepository.save(workspace);

        /*
         * Avvalgi owner
         * */
        Optional<WorkspaceUser> optionalWorkspaceUserOwner = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
        if (optionalWorkspaceUserOwner.isEmpty())
            return new ApiResponse("WorkspaceUser not found!", false);
        WorkspaceUser workspaceUser = optionalWorkspaceUserOwner.get();
        workspaceUser.setWorkspaceRole(workspaceRoleRepository.findByName("ROLE_ADMIN"));
        workspaceUserRepository.save(workspaceUser);

        /*
         * Bo'lajak owner
         * */
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, ownerId);
        if (optionalWorkspaceUser.isEmpty())
            return new ApiResponse("WorkspaceUser not found!", false);
        WorkspaceUser workspaceUser1 = optionalWorkspaceUser.get();
        workspaceUser1.setWorkspaceRole(workspaceRoleRepository.findByName("ROLE_OWNER"));
        workspaceUserRepository.save(workspaceUser1);

        return new ApiResponse("Role given", true);
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
            } catch (Exception e) {
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
        if (optionalWorkspaceUser.isPresent()) {
            WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
            workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
            workspaceUserRepository.save(workspaceUser);
            return new ApiResponse("User joined", true);
        }
        return new ApiResponse("User not joned!", false);
    }


    @Override
    public List<Workspace> getAllWorkspaces(User user) {
        return workspaceRepository.findAllByOwnerId(user.getId());
    }


    @Override
    public List<MemberDto> getMembersAndGuests(Long id) {
        List<WorkspaceUser> workspaceUsers = workspaceUserRepository.findAllByWorkspaceId(id);
//        List<MemberDto> members = new ArrayList<>();
//        for (WorkspaceUser workspaceUser : workspaceUsers) {
//            members.add(mapWorkspaceUserToMemberDto(workspaceUser));
//        }
//        return members;

        return workspaceUsers.stream().map(this::mapWorkspaceUserToMemberDto).toList();
    }


    @Override
    public List<WorkspaceDto> getMyWorkspaces(User user) {
        List<WorkspaceUser> workspaceUserList = workspaceUserRepository.findAllByUser_Id(user.getId());
        List<WorkspaceDto> workspaceDtoList = new ArrayList<>();
        return workspaceUserList.stream().map(workspaceUser -> mapWorkspaceToWorkspaceDto(workspaceUser.getWorkspace())).toList();
    }

    //TODO
    @Override
    public ApiResponse addOrRemovePermissionToRole(WorkspaceRoleDto workspaceRoleDto) {

        //role
        WorkspaceRole workspaceRole = workspaceRoleRepository.findById(workspaceRoleDto.getId()).orElseThrow(() -> new ResourceNotFoundException("Workspace role not found!"));

        //role va permission bolsa o'chirish aks holda qo'shish
        Optional<WorkspacePermission> optionalWorkspacePermission = workspacePermissionRepository.findByWorkspaceRoleIdAndPermission(workspaceRoleDto.getId(), workspaceRoleDto.getPermissionName());
        if (optionalWorkspacePermission.isPresent()) {
            if (workspaceRoleDto.getAddType().equals(AddType.ADD))
                return new ApiResponse("Permission exists for this role", false);
            else if (workspaceRoleDto.getAddType().equals(AddType.REMOVE)) {
                workspacePermissionRepository.delete(optionalWorkspacePermission.get());
                return new ApiResponse("Permission deleted from this role", true);
            }
        } else {
            if (workspaceRoleDto.getAddType().equals(AddType.ADD)) {
                workspacePermissionRepository.save(new WorkspacePermission(
                        workspaceRole,
                        workspaceRoleDto.getPermissionName()
                ));
                return new ApiResponse("Permission added", true);
            }
            else if (workspaceRoleDto.getAddType().equals(AddType.REMOVE)) {
                return new ApiResponse("Permission not found", false);
            }
        }
        return new ApiResponse("Command not found!", false);
    }


    //MY METHODS

    public WorkspaceDto mapWorkspaceToWorkspaceDto(Workspace workspace) {
        WorkspaceDto workspaceDto = new WorkspaceDto();
        workspaceDto.setId(workspace.getId());
        workspaceDto.setColor(workspace.getColor());
        workspaceDto.setInitialLetter(workspace.getInitialLetter());
        workspaceDto.setName(workspace.getName());
        workspaceDto.setAvatarId(workspace.getAvatar() == null ? null : workspace.getAvatar().getId());
        return workspaceDto;
    }


    public MemberDto mapWorkspaceUserToMemberDto(WorkspaceUser workspaceUser) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(workspaceUser.getUser().getId());
        memberDto.setFullName(workspaceUser.getUser().getFullName());
        memberDto.setEmail(workspaceUser.getUser().getEmail());
        memberDto.setRoleName(workspaceUser.getWorkspaceRole().getName());
        memberDto.setLastActive(workspaceUser.getUser().getLastActive());
        return memberDto;
    }

}
