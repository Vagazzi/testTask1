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
public class TestServiceImpl implements TestService{

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Test create(Test test) {
        return testRepository.save(test);
    }

    @Override
    public List<Test> getAllTests() {
        return (List<Test>) testRepository.findAll();
    }

    @Override
    public Test getById(long id) {
        return testRepository.findById(id).get();
    }

    @Override
    public Test compareObjects(Test fromForm, Test fromDB) {

        if(!fromForm.getTestName().equals(fromDB.getTestName())){
            fromDB.setTestName(fromForm.getTestName());
        }

        if(!fromForm.getTestDescription().equals(fromDB.getTestDescription())){
            fromDB.setTestDescription(fromForm.getTestDescription());
        }

        if(!fromForm.getAvailableCourses().equals(fromDB.getAvailableCourses())){
            fromDB.setAvailableCourses(fromForm.getAvailableCourses());
        }

        return fromDB;
    }

    @Override
    public List<Course> getCoursesById(List<String> coursesId) {
        List<Integer> idCourses = coursesId.stream().map(Integer::valueOf).toList();
        List<Course> courses = new ArrayList<>();

        for (Integer idCourse : idCourses) {
            courses.add(courseRepository.findById(Long.valueOf(idCourse)).get());
        }

        return courses;
    }
}
