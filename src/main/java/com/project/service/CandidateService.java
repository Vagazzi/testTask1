package com.project.service;

import com.project.model.Candidate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;

import java.sql.SQLException;
import java.util.List;

@Service
public interface CandidateService {
    Candidate create(Candidate candidate);
    List<Candidate> getAllCandidates();
    Candidate getById(long id);

    Candidate compareObjects(Candidate fromForm, Candidate fromDB);

    Blob castToBlobs(MultipartFile object) throws IOException, SQLException;

}
