package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.ProjectRequest;
import com.facturacion.Afertech.dto.ProjectResponse;
import com.facturacion.Afertech.mapper.ProjectMapper;
import com.facturacion.Afertech.model.Project;
import com.facturacion.Afertech.repository.ProjectRepository;
import com.facturacion.Afertech.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;

    public ProjectServiceImpl(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProjectResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(ProjectMapper::toResponse)
                .toList();
    }

    @Override
    public ProjectResponse findById(Long id) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        return ProjectMapper.toResponse(project);
    }

    @Override
    public ProjectResponse create(ProjectRequest request) {
        Project project = ProjectMapper.toEntity(request);
        return ProjectMapper.toResponse(repository.save(project));
    }

    @Override
    public ProjectResponse update(Long id, ProjectRequest request) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());

        return ProjectMapper.toResponse(repository.save(project));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
