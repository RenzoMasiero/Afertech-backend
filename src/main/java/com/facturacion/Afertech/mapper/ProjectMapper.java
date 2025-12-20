package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.ProjectRequest;
import com.facturacion.Afertech.dto.ProjectResponse;
import com.facturacion.Afertech.model.Project;

public class ProjectMapper {

    public static Project toEntity(ProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return project;
    }

    public static ProjectResponse toResponse(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setDescription(project.getDescription());
        return response;
    }
}
