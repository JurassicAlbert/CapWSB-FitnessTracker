package com.capgemini.wsb.fitnesstracker.mail.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties for mail configuration.
 */
@ConfigurationProperties(prefix = "mail")
@Getter
@RequiredArgsConstructor
public class MailProperties {

    private String host;
    private int port;
    private String username;
    private String password;

}
