package com.example.roombooking;

import com.example.roombooking.models.Confirmation;
import com.example.roombooking.repos.ConfEmailRepo;
import com.example.roombooking.services.implementations.ContractCustomerImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ComponentScan
@RequiredArgsConstructor
public class LoadDefaultEmailTemplate implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContractCustomerImpl.class);
    private final ConfEmailRepo emailRepo;
    @Override
    public void run(String... args) throws Exception {
        String path = "src/main/resources/confirmationEmail.txt";

        StringBuilder lines = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            reader.lines().forEach(line -> lines.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String content = lines.toString();
        Confirmation conf = new Confirmation(content);
        emailRepo.save(conf);

        LOGGER.info("Email template saved to database");
    }
}
