package com.project.controller;

import com.project.model.Candidate;
import com.project.repository.CandidateRepository;
import com.project.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
public class CandidateController {
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private CandidateRepository candidateRepository;


    @GetMapping("/display/photo")
    public ResponseEntity<byte[]> displayPhoto(@RequestParam("id") long id) throws SQLException {
        Candidate candidate = candidateService.getById(id);
        byte[] photoBytes = candidate.getPhoto().getBytes(1, (int) candidate.getPhoto().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(photoBytes);
    }

    @GetMapping("/display/cv")
    public ResponseEntity<byte[]> displayCv(@RequestParam("id") long id) throws SQLException {
        Candidate candidate = candidateService.getById(id);
        byte[] cvBytes = candidate.getCv().getBytes(1, (int) candidate.getCv().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(cvBytes);
    }

    @GetMapping("/")
    public ModelAndView home(){
        ModelAndView mv = new ModelAndView("index");
        List<Candidate> candidateList = candidateService.getAllCandidates();
        mv.addObject("candidateList", candidateList);
        return mv;
    }

    @GetMapping("/edit/candidate")
    public String editCandidate(@RequestParam("id") long id,
            Model model){
        model.addAttribute("candidate", candidateService.getById(id));
        return "editCandidate";
    }

    @PostMapping("/edit/candidate")
    public String saveEdition(@RequestParam("id") long id,
                              @RequestParam("photo") MultipartFile photo,
                              @RequestParam("cv") MultipartFile cv,
                              @RequestParam("name") String name,
                              @RequestParam("surname") String surname,
                              @RequestParam("middleName") String middleName,
                              @RequestParam("description") String description) throws IOException, SQLException {


        Candidate candidateFromDB = candidateService.getById(id);
        Candidate candidateFromForm = new Candidate(id,
                                                    name,
                                                    surname,
                                                    middleName,
                                                    description,
                                                    candidateService.castToBlobs(photo),
                                                    candidateService.castToBlobs(cv));
        
        candidateFromDB = candidateService.compareObjects(candidateFromForm,candidateFromDB);
        candidateRepository.save(candidateFromDB);
        return "redirect:/";
    }

    @GetMapping("/add")
    public ModelAndView addImage(){
        return new ModelAndView("addCandidate");
    }

    @PostMapping("/add")
    public String addImagePost(@RequestParam("photo") MultipartFile photo,
                               @RequestParam("cv") MultipartFile cv,
                               @RequestParam("name") String name,
                               @RequestParam("surname") String surname,
                               @RequestParam("middleName") String middleName,
                               @RequestParam("description") String description
                               )
            throws IOException, SQLException {

        Candidate candidate = new Candidate();

        candidate.setPhoto(candidateService.castToBlobs(photo));
        candidate.setCv(candidateService.castToBlobs(cv));
        candidate.setName(name);
        candidate.setSurname(surname);
        candidate.setMiddleName(middleName);
        candidate.setDescription(description);

        candidateService.create(candidate);
        return "redirect:/";
    }
}
