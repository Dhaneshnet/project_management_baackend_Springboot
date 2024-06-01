package com.danesh.service;

import com.danesh.modal.Chat;
import com.danesh.modal.Project;
import com.danesh.modal.User;

import java.util.List;

public interface ProjectService {

    Project createProject(Project project, User user)throws Exception;

    List<Project>getProjectByTeam(User user,String categorie, String tags)throws Exception;

    Project getProjectById(Long projectId)throws Exception;

    void deleteProject(Long projectId, Long userId)throws Exception;

<<<<<<< HEAD
    Project updateProject(Project updateProject,Long id)throws Exception;
=======
    Project updateProject(Project updateProject)throws Exception;
>>>>>>> project_management_baackend_Springboot/master

    void addedUserToProject(Long projectId, Long userId)throws Exception;
    void removeUserFromProject(Long projectId,Long userId)throws Exception;
    Chat getChatByProjectId(Long projectId)throws Exception;
<<<<<<< HEAD
    List<Project>searchProjects(String keyword,User user)throws Exception;
=======
>>>>>>> project_management_baackend_Springboot/master

}
