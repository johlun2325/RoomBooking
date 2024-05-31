package com.example.roombooking.services.implementations;


import com.example.roombooking.models.EmailConfirmation;
import com.example.roombooking.repos.EmailConfirmationRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EmailConfigurationsService {

    private final EmailConfirmationRepo emailConfirmationRepo;

    public EmailConfigurationsService(EmailConfirmationRepo emailConfirmationRepo) {
        this.emailConfirmationRepo = emailConfirmationRepo;
    }

    public EmailConfirmation findEmailConfigurationByName(String name) {
        return emailConfirmationRepo.findByName(name).orElseThrow(NoSuchElementException::new);
    }

    public EmailConfirmation findEmailConfigurationById(Long id) {
        return emailConfirmationRepo.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public List<EmailConfirmation> findAllEmailConfirmations() {
        return emailConfirmationRepo.findAll();
    }

    public void updateConfirmationTemplate(Long id, String newTemplate) {
        var confirmation = findEmailConfigurationById(id);
        confirmation.setTemplate(newTemplate);
        emailConfirmationRepo.save(confirmation);
    }
}
