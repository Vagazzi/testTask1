package com.project.service;

import com.project.model.Candidate;
import com.project.model.Course;
import com.project.model.Test;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService {

    Course create(Course course);
    List<Course> getAllCourses();
    Course getById(long id);

    Course compareObjects(Course fromForm, Course fromDB);

    List<Test> getTestsById(List<String> ids);
}
