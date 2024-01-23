package com.example.project_service;


import com.example.project_service.controller.ProjectControllerTest;
import com.example.project_service.service.ProjectServiceImplTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
    ProjectServiceImplTest.class,
    ProjectControllerTest.class
})
public class ProjectServiceApplicationTests {


}
