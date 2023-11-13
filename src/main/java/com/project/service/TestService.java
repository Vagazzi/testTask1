package com.project.service;


import com.project.model.Course;
import com.project.model.Test;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestService {

    Test create(Test test);
    List<Test> getAllTests();
    Test getById(long id);
    Test compareObjects(Test fromForm, Test fromDB);

    List<Course> getCoursesById(List<String> ids);
}
