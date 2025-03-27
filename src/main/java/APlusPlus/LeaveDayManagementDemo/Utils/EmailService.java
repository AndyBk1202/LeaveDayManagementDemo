package APlusPlus.LeaveDayManagementDemo.Utils;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailService {
    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private JavaMailSender getMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    public void sendLeaveRequestStatusEmail(String toEmail, String status) {
        try {
            JavaMailSender mailSender = getMailSender();

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Leave Request Status Update");

            String title, messageColor, message;
            if ("ACCEPTED".equalsIgnoreCase(status)) {
                title = "Your leave request has been accepted!";
                messageColor = "#28a745"; // Green
                message = "We are pleased to inform you that your leave request has been <b>approved</b> by the manager.";
            } else {
                title = "Your leave request has been rejected!";
                messageColor = "#dc3545"; // Red
                message = "Unfortunately, your leave request has been <b>rejected</b> by the manager.";
            }

            String content = """
                <html>
                <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f9f9f9; padding: 20px;">
                    <div style="max-width: 600px; margin: auto; padding: 20px; background-color: #ffffff; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); text-align: center;">
                        <h2 style="color: %s;">%s</h2>
                        <p>%s</p>
                        <p style="margin-top: 20px;">Best regards,<br><b>A Plus Plus Team</b></p>
                    </div>
                </body>
                </html>
                """.formatted(messageColor, title, message);

            helper.setText(content, true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
