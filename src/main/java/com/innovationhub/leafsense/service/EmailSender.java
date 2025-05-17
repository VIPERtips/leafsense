package com.innovationhub.leafsense.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    private static final String SUPPORT_EMAIL = "support@leafsense.com";
    private static final String SUPPORT_LINK = "https://app.leafsense.com";
    private static final String BUSINESS_NAME = "LeafSense";

    private static final String PRIMARY_COLOR = "#28a745";     // Green
    private static final String ACCENT_COLOR = "#FF8800";       // Orange
    private static final String BACKGROUND_COLOR = "#f8f5f0";   // Light beige
    private static final String TEXT_COLOR = "#333";

    public void sendEmail(String toEmail, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(SUPPORT_EMAIL, BUSINESS_NAME));
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(wrapTemplate(body), true);
            javaMailSender.send(message);
            System.out.println("Email sent to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + toEmail + ": " + e.getMessage());
        }
    }

    private String wrapTemplate(String content) {
        return String.format("""
            <html>
            <body style="background-color: %s; font-family: Arial, sans-serif; color: %s;">
                <div style="background-color: %s; padding: 20px; text-align: center;">
                    <h1 style="color: white;">%s</h1>
                </div>
                <div style="padding: 30px;">%s</div>
                <div style="padding: 20px; text-align: center; font-size: 12px; color: #666;">
                    <p>&copy; %s. All rights reserved.</p>
                    <p><a href="%s" style="color: %s;">Visit LeafSense</a></p>
                </div>
            </body>
            </html>
        """, BACKGROUND_COLOR, TEXT_COLOR, PRIMARY_COLOR, BUSINESS_NAME, content, BUSINESS_NAME, SUPPORT_LINK, PRIMARY_COLOR);
    }

    // ==== AUTH EMAILS ====

    public void sendPasswordResetEmail(String userEmail, String username, String resetToken) {
        String resetLink = SUPPORT_LINK + "/reset-password?token=" + resetToken;
        String body = String.format("""
            <p>Hi <strong>%s</strong>,</p>
            <p>We received a request to reset your password. Click below to continue:</p>
            <p style="text-align: center;">
                <a href="%s" style="background-color: %s; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px;">Reset Password</a>
            </p>
            <p>This link will expire in 24 hours. If you didn't make this request, ignore this email.</p>
        """, username, resetLink, ACCENT_COLOR);

        sendEmail(userEmail, BUSINESS_NAME + " - Password Reset", body);
    }

    public void sendRegistrationEmail(String userEmail, String otp) {
        String body = String.format("""
            <p>Welcome <strong>%s</strong>,</p>
            <p>Thanks for joining LeafSense! Use the OTP below to activate your account:</p>
            <h2 style="text-align: center; color: %s;">%s</h2>
            <p>This OTP expires in 2 hours.</p>
        """, userEmail, ACCENT_COLOR, otp);

        sendEmail(userEmail, BUSINESS_NAME + " - Account Activation", body);
    }

    // ==== FARM MONITORING ====

    public void sendCropHealthAlert(String userEmail, String farmName, String cropType) {
        String body = String.format("""
            <p>Attention farmer,</p>
            <p>We’ve detected a potential issue in your <strong>%s</strong> crops at <strong>%s</strong>.</p>
            <p>Please check your LeafSense dashboard for early intervention recommendations.</p>
            <p><a href="%s/crop-health" style="color: %s;">View Health Report</a></p>
        """, cropType, farmName, SUPPORT_LINK, PRIMARY_COLOR);

        sendEmail(userEmail, BUSINESS_NAME + " - Crop Alert", body);
    }

    public void sendEquipmentMaintenanceAlert(String userEmail, String equipmentName) {
        String body = String.format("""
            <p>Hello,</p>
            <p>Your equipment <strong>%s</strong> is due for maintenance.</p>
            <p>Schedule a service to avoid disruptions.</p>
            <p><a href="%s/equipment" style="color: %s;">View Equipment</a></p>
        """, equipmentName, SUPPORT_LINK, PRIMARY_COLOR);

        sendEmail(userEmail, BUSINESS_NAME + " - Maintenance Alert", body);
    }

    public void sendIrrigationAlert(String userEmail, String zone) {
        String body = String.format("""
            <p>Hey!</p>
            <p>Our system recommends irrigation in <strong>Zone %s</strong> based on soil moisture and forecast data.</p>
            <p><a href="%s/irrigation" style="color: %s;">Update Schedule</a></p>
        """, zone, SUPPORT_LINK, PRIMARY_COLOR);

        sendEmail(userEmail, BUSINESS_NAME + " - Irrigation Reminder", body);
    }

    // ==== STAFF ====

    public void sendStaffInvitation(String userEmail, String farmName) {
        String body = String.format("""
            <p>Hello,</p>
            <p>You’ve been invited to join the team managing <strong>%s</strong> on LeafSense.</p>
            <p><a href="%s/signup" style="background-color: %s; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Join Now</a></p>
        """, farmName, SUPPORT_LINK, PRIMARY_COLOR);

        sendEmail(userEmail, BUSINESS_NAME + " - Farm Team Invitation", body);
    }

    // ==== SECURITY ====

    public void sendSuspiciousActivityAlert(String userEmail, String deviceInfo) {
        String body = String.format("""
            <p>Hi,</p>
            <p>We noticed a new login attempt from <strong>%s</strong>.</p>
            <p>If this wasn’t you, please secure your account.</p>
            <p><a href="%s/security" style="color: %s;">Review Activity</a></p>
        """, deviceInfo, SUPPORT_LINK, PRIMARY_COLOR);

        sendEmail(userEmail, BUSINESS_NAME + " - Security Alert", body);
    }

    // ==== REPORTING ====

    public void sendMonthlyReport(String userEmail, String farmName, String reportUrl) {
        String body = String.format("""
            <p>Hello,</p>
            <p>Your monthly report for <strong>%s</strong> is ready.</p>
            <p><a href="%s" style="background-color: %s; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px;">Download Report</a></p>
        """, farmName, reportUrl, PRIMARY_COLOR);

        sendEmail(userEmail, BUSINESS_NAME + " - Monthly Report", body);
    }

    public void sendYieldForecastEmail(String userEmail, String farmName, String yield, String confidence) {
        String body = String.format("""
            <p>Hello farmer,</p>
            <p>Based on real-time data, we predict a yield of <strong>%s</strong> for your farm <strong>%s</strong>.</p>
            <p>Model confidence: <strong>%s%%</strong></p>
            <p>Plan your harvest accordingly.</p>
        """, yield, farmName, confidence);

        sendEmail(userEmail, BUSINESS_NAME + " - Yield Forecast", body);
    }

    // ==== SYSTEM / ADMIN ====

    public void sendSystemAlertToAdmin(String message) {
        String body = String.format("""
            <p>System Alert:</p>
            <p>%s</p>
        """, message);

        sendEmail("tadiwachipungu2@gmail.com", BUSINESS_NAME + " - System Notification", body);
    }

    public void sendSensorOfflineAlert(String userEmail, String sensorId) {
        String body = String.format("""
            <p>Alert:</p>
            <p>Your sensor with ID <strong>%s</strong> has gone offline.</p>
            <p>Check connection and battery status immediately.</p>
            <p><a href="%s/sensors" style="color: %s;">Sensor Dashboard</a></p>
        """, sensorId, SUPPORT_LINK, PRIMARY_COLOR);

        sendEmail(userEmail, BUSINESS_NAME + " - Sensor Offline", body);
    }
}
