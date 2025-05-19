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

    private static final String PRIMARY_COLOR = "#28a745";
    private static final String ACCENT_COLOR = "#FF8800";
    private static final String BACKGROUND_COLOR = "#ffffff";
    private static final String TEXT_COLOR = "#212529";
    private static final String BORDER_RADIUS = "8px";

    public void sendEmail(String toEmail, String subject, String bodyContent) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(SUPPORT_EMAIL, BUSINESS_NAME));
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(wrapInTemplate(bodyContent), true);
            javaMailSender.send(message);
        } catch (Exception e) {
            System.err.println("Failed to send email to " + toEmail + ": " + e.getMessage());
        }
    }

    private String wrapInTemplate(String content) {
        return String.format("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body { margin: 0; padding: 0; font-family: 'Segoe UI', sans-serif; background-color: %s; color: %s; }
                    .container { width: 100%%; max-width: 600px; margin: auto; background-color: #ffffff; border-radius: %s; overflow: hidden; box-shadow: 0 0 10px rgba(0,0,0,0.05); }
                    .header { background-color: %s; padding: 24px; text-align: center; color: white; }
                    .content { padding: 24px; }
                    .footer { font-size: 12px; color: #777; text-align: center; padding: 16px; }
                    .btn { display: inline-block; background-color: %s; color: white; padding: 12px 24px; border-radius: %s; text-decoration: none; font-weight: bold; }
                    a { color: %s; text-decoration: none; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>%s</h1>
                    </div>
                    <div class="content">
                        %s
                    </div>
                    <div class="footer">
                        &copy; %s | <a href="%s">Visit Dashboard</a>
                    </div>
                </div>
            </body>
            </html>
        """, BACKGROUND_COLOR, TEXT_COLOR, BORDER_RADIUS, PRIMARY_COLOR, ACCENT_COLOR, BORDER_RADIUS, PRIMARY_COLOR, BUSINESS_NAME, content, BUSINESS_NAME, SUPPORT_LINK);
    }

    // ===== AUTH ====

    public void sendPasswordResetEmail(String userEmail, String username, String resetToken) {
        String resetLink = SUPPORT_LINK + "/reset-password?token=" + resetToken;
        String content = String.format("""
            <p>Hello <strong>%s</strong>,</p>
            <p>You requested to reset your password. Click below to proceed:</p>
            <p style="text-align: center;"><a class="btn" href="%s">Reset Password</a></p>
            <p>This link expires in 24 hours. If you didn't request this, ignore this message.</p>
        """, username, resetLink);

        sendEmail(userEmail, "Reset Your LeafSense Password", content);
    }

    public void sendRegistrationEmail(String userEmail, String otp) {
        String content = String.format("""
            <p>Welcome to <strong>%s</strong>!</p>
            <p>Your OTP code is:</p>
            <h2 style="text-align:center; color:%s;">%s</h2>
            <p>This OTP is valid for 2 hours. Do not share this with anyone.</p>
        """, BUSINESS_NAME, ACCENT_COLOR, otp);

        sendEmail(userEmail, "Your LeafSense OTP Code", content);
    }

    // ===== MONITORING ====

    public void sendCropHealthAlert(String email, String farm, String crop) {
        String content = String.format("""
            <p>Heads up!</p>
            <p>An issue has been detected in <strong>%s</strong> crops at your farm <strong>%s</strong>.</p>
            <p>Please review and apply preventive action in your dashboard.</p>
            <p><a class="btn" href="%s/crop-health">View Details</a></p>
        """, crop, farm, SUPPORT_LINK);

        sendEmail(email, "Crop Health Alert", content);
    }

    public void sendEquipmentMaintenanceAlert(String email, String equipmentName) {
        String content = String.format("""
            <p>Maintenance Reminder</p>
            <p><strong>%s</strong> is due for service based on your schedule.</p>
            <p><a class="btn" href="%s/equipment">Schedule Maintenance</a></p>
        """, equipmentName, SUPPORT_LINK);

        sendEmail(email, "Equipment Maintenance Alert", content);
    }

    public void sendIrrigationAlert(String email, String zone) {
        String content = String.format("""
            <p>Watering Advisory</p>
            <p>Our data suggests <strong>Zone %s</strong> needs irrigation soon.</p>
            <p><a class="btn" href="%s/irrigation">View Irrigation Plan</a></p>
        """, zone, SUPPORT_LINK);

        sendEmail(email, "Irrigation Alert", content);
    }

    public void sendYieldForecastEmail(String email, String farm, String yield, String confidence) {
        String content = String.format("""
            <p>Hey farmer!</p>
            <p>Based on current conditions, your estimated yield for <strong>%s</strong> is:</p>
            <h2 style="text-align: center; color: %s;">%s kg</h2>
            <p>Prediction Confidence: <strong>%s%%</strong></p>
        """, farm, PRIMARY_COLOR, yield, confidence);

        sendEmail(email, "Yield Forecast Update", content);
    }

    // ===== TEAM & SECURITY ====

    public void sendStaffInvitation(String email, String farmName) {
        String content = String.format("""
            <p>You've been invited to join the farm team for <strong>%s</strong>.</p>
            <p>Click below to complete your account setup:</p>
            <p><a class="btn" href="%s/signup">Join Now</a></p>
        """, farmName, SUPPORT_LINK);

        sendEmail(email, "Farm Team Invitation", content);
    }

    public void sendSuspiciousActivityAlert(String email, String deviceInfo) {
        String content = String.format("""
            <p>We detected a login from a new device:</p>
            <p><strong>%s</strong></p>
            <p>If this wasn't you, secure your account immediately.</p>
            <p><a class="btn" href="%s/security">Check Activity</a></p>
        """, deviceInfo, SUPPORT_LINK);

        sendEmail(email, "New Login Alert", content);
    }

    // ===== REPORTS & SYSTEM ====

    public void sendMonthlyReport(String email, String farmName, String reportUrl) {
        String content = String.format("""
            <p>Your monthly report for <strong>%s</strong> is ready.</p>
            <p><a class="btn" href="%s">Download Report</a></p>
        """, farmName, reportUrl);

        sendEmail(email, "Monthly Report Available", content);
    }

    public void sendSystemAlertToAdmin(String message) {
        String content = String.format("""
            <p><strong>System Alert:</strong></p>
            <p>%s</p>
        """, message);

        sendEmail("tadiwachipungu2@gmail.com", "LeafSense Admin Alert", content);
    }

    public void sendSensorOfflineAlert(String email, String sensorId) {
        String content = String.format("""
            <p>Sensor <strong>%s</strong> has gone offline.</p>
            <p>Please check power and signal status.</p>
            <p><a class="btn" href="%s/sensors">Go to Sensors</a></p>
        """, sensorId, SUPPORT_LINK);

        sendEmail(email, "Sensor Offline", content);
    }
}
