package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.ProjectRequest;
import com.facturacion.Afertech.dto.ProjectResponse;

import java.util.List;

public interface ProjectService {

    List<ProjectResponse> findAll();

    ProjectResponse findById(Long id);

    ProjectResponse create(ProjectRequest request);

    ProjectResponse update(Long id, ProjectRequest request);

    void delete(Long id);
}
