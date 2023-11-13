package com.project.service;

import com.project.model.Candidate;
import com.project.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public Candidate create(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    public List<Candidate> getAllCandidates() {
        return (List<Candidate>) candidateRepository.findAll();
    }

    @Override
    public Candidate getById(long id) {
        return candidateRepository.findById(id).get();
    }

    @Override
    public Candidate compareObjects(Candidate fromForm, Candidate fromDB) {
        if(!fromForm.getName().equals(fromDB.getName())){
            fromDB.setName(fromForm.getName());
        }
        if(!fromForm.getSurname().equals(fromDB.getSurname())){
            fromDB.setSurname(fromForm.getSurname());
        }
        if(!fromForm.getMiddleName().equals(fromDB.getMiddleName())){
            fromDB.setMiddleName(fromForm.getMiddleName());
        }
        if(!fromForm.getPhoto().equals(fromDB.getPhoto())){
            fromDB.setPhoto(fromForm.getPhoto());
        }
        if(!fromForm.getDescription().equals(fromDB.getDescription())){
            fromDB.setDescription(fromForm.getDescription());
        }
        if(!fromForm.getCv().equals(fromDB.getCv())){
            fromDB.setCv(fromForm.getCv());
        }
        return fromDB;
    }

    @Override
    public Blob castToBlobs(MultipartFile object) throws IOException, SQLException {

        byte[] photoBytes = object.getBytes();
        return new javax.sql.rowset.serial.SerialBlob(photoBytes);

    }
}
