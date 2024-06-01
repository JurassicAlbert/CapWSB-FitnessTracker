package com.capgemini.wsb.fitnesstracker.mail.api;

/**
 * Data Transfer Object for Email.
 *
 * @param toAddress the recipient email address
 * @param subject   the subject of the email
 * @param content   the content of the email
 */
public record EmailDto(String toAddress, String subject, String content) {

}
