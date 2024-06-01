package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailProvider;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static java.util.Calendar.DAY_OF_MONTH;

/**
 * Implementation of the EmailProvider interface for creating email content.
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailProvider {

    /**
     * Creates an email with the given recipient, title, and list of trainings.
     *
     * @param recipient the email address of the recipient
     * @param title     the title of the email
     * @param trainings the list of trainings to include in the email
     * @return the email content
     */
    @Override
    public EmailDto createEmail(String recipient, String title, List<TrainingDto> trainings) {
        final Date lastWeek = returnBeginningOfLastWeek();
        final Date yesterday = returnYesterday();
        final List<TrainingDto> lastWeekTrainings = trainings.stream()
                .filter(training -> training.getStartTime().after(lastWeek) && training.getStartTime().before(yesterday))
                .toList();
        log.info("Creating email for {}", recipient);
        final StringBuilder builder = new StringBuilder(String.format("""
                        You had %d trainings last week,
                        completing %.2f units of distance.
                        You have completed %d trainings overall.
                        Below you can find detailed rundown of your trainings last week:
                        ----
                        """, lastWeekTrainings.size(),
                lastWeekTrainings.stream().mapToDouble(TrainingDto::getDistance).sum(),
                trainings.size()));

        lastWeekTrainings.forEach(training -> {
            builder.append(String.format("""
                            Training start: %s
                            Training end: %s
                            Activity type: %s
                            Distance: %.2f
                            Average speed: %.2f
                            ----
                            """, training.getStartTime(),
                    training.getEndTime() == null ? "-" : training.getEndTime(),
                    training.getActivityType(),
                    training.getDistance(),
                    training.getAverageSpeed()));
        });
        EmailDto email = new EmailDto(recipient, title, builder.toString());
        log.info("Email created");
        System.out.println(builder);
        return email;
    }

    /**
     * Returns the date representing the beginning of last week.
     *
     * @return the date of the beginning of last week
     */
    private Date returnBeginningOfLastWeek() {
        final Calendar now = Calendar.getInstance();
        now.add(DAY_OF_MONTH, -7);
        return now.getTime();
    }

    /**
     * Returns the date representing yesterday.
     *
     * @return the date of yesterday
     */
    private Date returnYesterday() {
        final Calendar now = Calendar.getInstance();
        now.add(DAY_OF_MONTH, -1);
        return now.getTime();
    }
}
