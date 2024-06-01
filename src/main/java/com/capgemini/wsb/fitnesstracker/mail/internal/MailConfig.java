package com.capgemini.wsb.fitnesstracker.mail.internal;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for mail properties.
 */
@Configuration
@EnableConfigurationProperties(MailProperties.class)
public class MailConfig {
}
