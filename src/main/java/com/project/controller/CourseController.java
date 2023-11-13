package com.project.controller;

import com.project.model.Course;
import com.project.model.Test;
import com.project.repository.CourseRepository;
import com.project.service.CourseService;

import com.project.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private TestService testService;

    @GetMapping("/courses")
    public ModelAndView testHome() {
        ModelAndView mv = new ModelAndView("courses");
        List<Course> courseList = courseService.getAllCourses();
        mv.addObject("courseList", courseList);
        return mv;
    }

    @GetMapping("/courses/add")
    public ModelAndView showAddForm(Model model) {
        model.addAttribute("tests", testService.getAllTests());
        return new ModelAndView("addCourse");
    }

    @GetMapping("/edit/course")
    public String showEditForm(@RequestParam("id") long id,
                               Model model) {
        model.addAttribute("course", courseService.getById(id));
        return "editCourse";
    }

    @PostMapping("/edit/course")
    public String saveEditionsInCourse(@RequestParam("id") long id,
                                       @RequestParam("courseName") String courseName,
                                       @RequestParam("courseDescription") String courseDescription) {


        Course courseFromDB = courseService.getById(id);

        Course courseFromForm = new Course(id,
                courseName,
                courseDescription);

        courseFromDB = courseService.compareObjects(courseFromForm, courseFromDB);
        courseRepository.save(courseFromDB);
        return "redirect:/courses";
    }

    @PostMapping("/courses/add")
    public String addCourse(@RequestParam("courseName") String courseName,
                            @RequestParam("courseDescription") String courseDescription,
                            @RequestParam("testCheckbox") List<String> testsId) {

        Course course = new Course();

        List<Test> tests = courseService.getTestsById(testsId);

        course.setCourseName(courseName);
        course.setCourseDescription(courseDescription);
        course.setTests(tests);

        courseService.create(course);

        return "redirect:/courses";
    }

}
