package com.project.service;

import com.project.model.Course;
import com.project.model.Test;
import com.project.repository.CourseRepository;
import com.project.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private TestRepository testRepository;

    @Override
    public Course create(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public List<Course> getAllCourses() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course getById(long id) {
        return courseRepository.findById(id).get();
    }

    @Override
    public Course compareObjects(Course fromForm, Course fromDB) {

        if (!fromForm.getCourseName().equals(fromDB.getCourseName())) {
            fromDB.setCourseName(fromForm.getCourseName());
        }

        if (!fromForm.getCourseDescription().equals(fromDB.getCourseDescription())) {
            fromDB.setCourseDescription(fromForm.getCourseDescription());
        }

        return fromDB;
    }

    @Override
    public List<Test> getTestsById(List<String> testsId) {
        List<Integer> idTests = testsId.stream().map(Integer::valueOf).toList();
        List<Test> tests = new ArrayList<>();

        for (Integer idCourse : idTests) {
            tests.add(testRepository.findById(Long.valueOf(idCourse)).get());
        }

        return tests;
    }
}
