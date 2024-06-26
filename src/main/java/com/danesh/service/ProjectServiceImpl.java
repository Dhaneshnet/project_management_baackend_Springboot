package com.danesh.service;

import com.danesh.modal.Chat;
import com.danesh.modal.Project;
import com.danesh.modal.User;
import com.danesh.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;


    @Override
    public Project createProject(Project project, User user) throws Exception {
        Project createdProject=new Project();

        createdProject.setOwner(user);
        createdProject.setTags(project.getTags());
        createdProject.setName(project.getName());
        createdProject.setCategorie(project.getCategorie());
        createdProject.setDescription(project.getDescription());
        createdProject.getTeam().add(user);

        Project savedProject=projectRepository.save(createdProject);
        Chat chat =new Chat();
        chat.setProject(savedProject);

        Chat projectChat=chatService.createChat(chat);
        savedProject.setChat(projectChat);

        return savedProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String categorie, String tag) throws Exception {
        List<Project>projects=projectRepository.findByTeamContainingOrOwner(user, user);

        if(categorie!=null){
            projects=projects.stream().filter(project -> project.getCategorie().equals(categorie))
                    .collect(Collectors.toList());

        }
        if(tag!=null) {
            projects = projects.stream().filter(project -> project.getTags().contains(tag))
                    .collect(Collectors.toList());
        }
            return projects;
    }

    @Override
    public Project getProjectById(Long projectId) throws Exception {
        Optional<Project>optionalProject=projectRepository.findById(projectId);
        if (optionalProject.isEmpty()){
            throw new Exception("project not found");

        }
        return optionalProject.get();
    }

    @Override
    public void deleteProject(Long projectId, Long userId) throws Exception {

        getProjectById(projectId);
        //userService.findUserById(userId);
        projectRepository.deleteById(projectId);

    }

    @Override
    public Project updateProject(Project updateProject,Long id) throws Exception {
        Project project=getProjectById(id);

        project.setName(updateProject.getName());
        project.setDescription(updateProject.getDescription());
        project.setTags(updateProject.getTags());
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project updateProject) throws Exception {
        return null;
    }

    @Override
    public void addedUserToProject(Long projectId, Long userId) throws Exception {
        Project project=getProjectById(projectId);
        User user =userService.findUserById(userId);
        if (!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }
        projectRepository.save(project);

    }

    @Override
    public void removeUserFromProject(Long projectId, Long userId) throws Exception {
        Project project=getProjectById(projectId);
        User user =userService.findUserById(userId);
        if (project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }
        projectRepository.save(project);

    }

    @Override
    public Chat getChatByProjectId(Long projectId) throws Exception {

        Project project=getProjectById(projectId);
        return project.getChat();
    }

    @Override
    public List<Project> searchProjects(String keyword, User user) throws Exception {

//        String partialName="%" + keyword + "%";


        return projectRepository.findByNameContainingAndTeamContains(keyword, user);

    }
}
