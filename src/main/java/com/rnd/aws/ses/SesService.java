package com.rnd.aws.ses;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

/*
Fahim created at 11/27/2021
*/
@Service
public class SesService {

  @Autowired ObjectMapper objectMapper;
  @Autowired public AmazonSimpleEmailService amazonSimpleEmailService;

  public void sendEmail() {

    String emailContent =
        "<!DOCTYPE html>\n"
            + "<html>\n"
            + "<head>\n"
            + "    <meta charset=\"utf-8\">\n"
            + "    <title>Example HTML Email</title>\n"
            + "</head>\n"
            + "<body style=\"background: whitesmoke; padding: 30px; height: 100%\">\n"
            + "<h5 style=\"font-size: 18px; margin-bottom: 6px\">Hello All,</h5>\n"
            + "<p style=\"font-size: 16px; font-weight: 500\">This is a simple email using AWS SES</p>\n"
            + "<p><a target=\"_blank\" style=\"background-color: #199319; color: white;padding: 15px 25px; \" href=\"https://www.google.com/\">Google</a></p>"
            + "</body>\n"
            + "</html>";

    String senderEmail = "sender@yopmail.com";
    String receiverEmail = "receiver@yopmail.com";
    String emailSubject = "Test Email Subject";

    try {
      SendEmailRequest sendEmailRequest =
          new SendEmailRequest()
              .withDestination(new Destination().withToAddresses(receiverEmail))
              .withMessage(
                  new Message()
                      .withBody(
                          new Body()
                              .withHtml(new Content().withCharset("UTF-8").withData(emailContent)))
                      .withSubject(new Content().withCharset("UTF-8").withData(emailSubject)))
              .withSource(senderEmail);
      SendEmailResult result = amazonSimpleEmailService.sendEmail(sendEmailRequest);
      System.out.println(result.getMessageId());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void createTemplate() {
    String emailContent =
        "<!DOCTYPE html>\n"
            + "<html lang=\"en\">\n"
            + "<head>\n"
            + "    <meta charset=\"utf-8\">\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
            + "    <title>Example HTML Email</title>\n"
            + "</head>\n"
            + "<body style=\"background: whitesmoke; padding: 30px; height: 100%\">\n"
            + "<h5 style=\"font-size: 18px; margin-bottom: 6px\">Hello {{firstName}},</h5>\n"
            + "<p style=\"font-size: 16px; font-weight: 500\">Your transaction PIN code is : {{pin}}</p>\n"
            + "</body>\n"
            + "</html>";

    Template template = new Template();
    template.setTemplateName("TransactionalTemplate");
    template.setSubjectPart("Hello {{firstName}}");
    template.setHtmlPart(emailContent);

    CreateTemplateRequest request = new CreateTemplateRequest();
    request.setTemplate(template);
    amazonSimpleEmailService.createTemplate(request);
  }

  public void updateTemplate() {
    String emailContent =
        "<!DOCTYPE html>\n"
            + "<html lang=\"en\">\n"
            + "<head>\n"
            + "    <meta charset=\"utf-8\">\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n"
            + "    <title>Example HTML Email</title>\n"
            + "</head>\n"
            + "<body style=\"background: whitesmoke; padding: 30px; height: 100%\">\n"
            + "<h5 style=\"font-size: 18px; margin-bottom: 6px\">Hello {{firstName}},</h5>\n"
            + "<p style=\"font-size: 16px; font-weight: 500\">Your transaction PIN is : {{pin}}</p>\n"
            + "</body>\n"
            + "</html>";

    Template template = new Template();
    template.setTemplateName("TransactionalTemplate");
    template.setSubjectPart("Hello {{firstName}}");
    template.setHtmlPart(emailContent);

    UpdateTemplateRequest request = new UpdateTemplateRequest();
    request.setTemplate(template);
    amazonSimpleEmailService.updateTemplate(request);
  }

  public void deleteTemplate() {
    DeleteTemplateRequest request = new DeleteTemplateRequest();
    request.setTemplateName("TransactionalTemplate");
    amazonSimpleEmailService.deleteTemplate(request);
  }

  public void sendTemplatedEmail() {
    String uuid = UUID.randomUUID().toString();
    uuid = uuid.substring(uuid.length() - 6);
    TransactionalEmailData transactionalEmailData = new TransactionalEmailData("Fahim", uuid);
    String templateData = null;
    try {
      templateData = this.objectMapper.writeValueAsString(transactionalEmailData);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    String senderEmail = "sender@yopmail.com";
    String receiverEmail = "receiver@yopmail.com";

    SendTemplatedEmailRequest sendTemplatedEmailRequest = new SendTemplatedEmailRequest();
    sendTemplatedEmailRequest.setTemplate("TransactionalTemplate");
    sendTemplatedEmailRequest.setSource(senderEmail);
    sendTemplatedEmailRequest.setDestination(new Destination(Arrays.asList(receiverEmail)));
    sendTemplatedEmailRequest.setTemplateData(templateData);
    amazonSimpleEmailService.sendTemplatedEmail(sendTemplatedEmailRequest);
  }
}
