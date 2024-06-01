package com.capgemini.wsb.fitnesstracker.notification;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class WeeklyReportNotifier {

    private static final String TITLE = "Last week's training report";
    private final UserProvider userProvider;
    private final TrainingServiceImpl trainingService;

    @Scheduled(cron = "${cron.report}")
    public void generateReport() {
        log.info("Training reports generation...");
        final List<User> allUsers = userProvider.findAllUsers();
        allUsers.forEach(user -> {
            log.info("Report generated for {}", user.getEmail());
        });

        log.info("Generating finished");
    }

    @PostConstruct
    public void onStartup() {
        log.info("Application started: report generated");
    }
}
