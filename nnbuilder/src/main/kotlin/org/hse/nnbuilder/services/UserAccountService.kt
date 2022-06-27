package org.hse.nnbuilder.services

import net.devh.boot.grpc.server.service.GrpcService
import org.hse.nnbuilder.user.UserService
import org.springframework.beans.factory.annotation.Autowired

@GrpcService
class UserAccountService : UserAccountServiceGrpcKt.UserAccountServiceCoroutineImplBase() {

    @Autowired
    private lateinit var util: Util

    @Autowired
    private lateinit var userService: UserService

    @Override
    override suspend fun getName(request: UserAccount.GetNameRequest): UserAccount.GetNameResponse {
        val name = util.getUser().getName()
        return UserAccount.GetNameResponse.newBuilder()
            .setName(name)
            .build()
    }

    @Override
    override suspend fun getEmail(request: UserAccount.GetEmailRequest): UserAccount.GetEmailResponse {
        val email = util.getUser().getEmail()
        return UserAccount.GetEmailResponse.newBuilder()
            .setEmail(email)
            .build()
    }

    @Override
    override suspend fun changeName(request: UserAccount.ChangeNameRequest): UserAccount.ChangeNameResponse {
        val id = util.getUser().getId()
        val newName = request.newName
        userService.changeName(id, newName)
        return UserAccount.ChangeNameResponse.newBuilder().build()
    }

    @Override
    override suspend fun changePassword(request: UserAccount.ChangePasswordRequest): UserAccount.ChangePasswordResponse {
        val id = util.getUser().getId()
        val oldPassword = request.oldPassword
        val newPassword = request.newPassword
        userService.changePassword(id, oldPassword, newPassword)
        return UserAccount.ChangePasswordResponse.newBuilder().build()
    }

    @Override
    override suspend fun getProjects(request: UserAccount.GetProjectsRequest): UserAccount.GetProjectsResponse {
        val projects = util.getUser().projects
        val projectBuilder = UserAccount.GetProjectsResponse.newBuilder()
        projects.forEach { project ->
            projectBuilder.addProject(
                UserAccount.ProjectInfo.newBuilder()
                    .setName(project.name)
                    .setId(project.getId())
                    .setVersions(project.getNNVersions().size)
                    .setActionType(project.actionType)
                    .build()
            )
        }
        return projectBuilder.build()
    }
}
