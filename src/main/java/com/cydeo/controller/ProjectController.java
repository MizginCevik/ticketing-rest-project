package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/project")
@Tag(name = "ProjectController",description = "Project API")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Get projects")
    public ResponseEntity<ResponseWrapper> getProjects() {
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved.", projectService.listAllProjects(), HttpStatus.OK));
    }

    @GetMapping("/{code}")
    @RolesAllowed("Manager")
    @Operation(summary = "Get project by project code")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("code") String code) {
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully retrieved.", projectService.getByProjectCode(code), HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed({"Manager","Admin"})
    @Operation(summary = "Create project")
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO) {
        projectService.save(projectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseWrapper("Project is successfully created.", HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Update project")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO) {
        projectService.update(projectDTO);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully updated.", HttpStatus.OK));
    }

    @DeleteMapping("/{projectcode}")
    @RolesAllowed("Manager")
    @Operation(summary = "Delete project")
    public ResponseEntity<ResponseWrapper> deleteProject(@PathVariable("projectcode") String projectcode) {
        projectService.delete(projectcode);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully deleted.", HttpStatus.OK));
    }

    @GetMapping("/manager/project-status")
    @RolesAllowed("Manager")
    @Operation(summary = "Get project by manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager() {
        List<ProjectDTO> projectDTOList = projectService.listAllProjectDetails();
        return ResponseEntity.ok(new ResponseWrapper("Projects are successfully retrieved.", projectDTOList, HttpStatus.OK));
    }

    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed("Manager")
    @Operation(summary = "Complete project by manager")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode) {
        projectService.complete(projectCode);
        return ResponseEntity.ok(new ResponseWrapper("Project is successfully completed.", HttpStatus.OK));
    }

}
