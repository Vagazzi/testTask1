package com.project.controller;

import com.project.model.Course;
import com.project.model.Test;
import com.project.repository.TestRepository;
import com.project.service.CourseService;
import com.project.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@RestController
public class TestController {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TestService testService;

    @GetMapping("/tests")
    public ModelAndView testHome() {
        ModelAndView mv = new ModelAndView("tests");
        List<Test> testList = testService.getAllTests();
        mv.addObject("testList", testList);
        return mv;
    }

    @GetMapping("/test/add")
    public ModelAndView showAddForm() {
        return new ModelAndView("addTest");
    }

    @PostMapping("/test/add")
    public String saveTest(@RequestParam("testCheckbox") List<String> coursesId,
                           @RequestParam("testName") String testName,
                           @RequestParam("testDescription") String testDescription) {


        List<Course> courses = testService.getCoursesById(coursesId);

        Test test = new Test();

        test.setTestName(testName);
        test.setTestDescription(testDescription);
        test.setAvailableCourses(courses);

        testService.create(test);
        return "redirect:/tests";
    }

    @GetMapping("/test/edit")
    public String showEditForm(@RequestParam("id") long id,
                               Model model) {
        model.addAttribute("test", testService.getById(id));
        model.addAttribute("courseList", courseService.getAllCourses());
        return "editTest";
    }

    @PostMapping("/test/edit")
    public String saveEditions(@RequestParam("id") long id,
                               @RequestParam("testCheckbox") List<String> coursesId,
                               @RequestParam("testName") String testName,
                               @RequestParam("testDescription") String testDescription) {

        Test fromDb = testService.getById(id);

        List<Course> courses = testService.getCoursesById(coursesId);

        Test fromForm = new Test(id,
                                 testName,
                                 testDescription,
                                 courses);

        fromDb = testService.compareObjects(fromForm, fromDb);

        testRepository.save(fromDb);
        return "redirect:/tests";

    }
}
