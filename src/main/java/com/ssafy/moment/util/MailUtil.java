package com.ssafy.moment.util;

import com.ssafy.moment.exception.CustomException;
import com.ssafy.moment.exception.ErrorCode;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender javaMailSender;

    public void sendAuthMail(String to, String uuid) {
//        SimpleMailMessage msg = new SimpleMailMessage();
        MimeMessage msg = javaMailSender.createMimeMessage();
        String mailText = "<p>Trip, the Moment 가입을 축하드립니다!</p><p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
            + "<div><a href='http://localhost/auth/email-auth?id=" + uuid + "'>요기 눌러줘잉</a></div>";

        System.out.println("mailText: " + mailText);
        try {
            msg.addRecipients(Message.RecipientType.TO, to);
            msg.setSubject("[Trip, the Moment] 본인인증 메일입니다.");
            msg.setText(mailText, "utf-8", "html");
        } catch (MessagingException e) {
            throw new CustomException(ErrorCode.EMAIL_ERROR);
        }

        javaMailSender.send(msg);
        System.out.println("메일 발송 완료!!");
    }

    public void sendPwMail(String email, String password) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("[Trip, the Moment] 비밀번호 초기화 안내");
        msg.setText("<p>안녕하세요 Trip, the Moment에서 보내드리는 비밀번호 안내 메일입니다.</p><p>초기화된 비밀번호는 아래와 같습니다. 하기 비밀번호로 로그인 후 비밀번호를 변경하세요!</p>" + "<p>" + password + "</p>");
        msg.setFrom("sngjae972@gmail.com");
        msg.setReplyTo("sngjae972@gmail.com");
        System.out.println("[보낸 메일 msg] : "+msg);
        javaMailSender.send(msg);
    }

}