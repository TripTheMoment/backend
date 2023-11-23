package com.ssafy.moment.util;

import com.ssafy.moment.exception.CustomException;
import com.ssafy.moment.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class MailUtil {

    private final JavaMailSender javaMailSender;

    public void sendAuthMail(String to, String uuid) {
        MimeMessage msg = javaMailSender.createMimeMessage();
        String mailText = "<h3>Trip, the Moment 가입을 축하드립니다!</h3><p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
            + "<div><a href='http://localhost/auth/email-auth?id=" + uuid + "'>요기 눌러줘잉</a></div>";

        System.out.println("mailText: " + mailText);
        try {
            msg.addRecipients(Message.RecipientType.TO, to);
            msg.setSubject("본인인증 및 회원 활성화 안내");
            msg.setText(mailText, "utf-8", "html");
            msg.setFrom(new InternetAddress("sngjae972@gmail.com","[Trip, the Moment]","UTF-8"));
        } catch (MessagingException e) {
            throw new CustomException(ErrorCode.EMAIL_ERROR);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(msg);
    }

    public void sendPwMail(String email, String password) {
        MimeMessage msg = javaMailSender.createMimeMessage();
        String mailText = "<h3>안녕하세요! [Trip, the Moment]에서 보내드리는 비밀번호 안내 메일입니다.</h3><p>초기화된 비밀번호는 아래와 같습니다. 하기 비밀번호로 로그인 후 비밀번호를 변경하세요!</p>" + "<p><b>" + password + "</b></p>";

        try {
            msg.addRecipients(Message.RecipientType.TO, email);
            msg.setSubject("[Trip, the Moment] 비밀번호 초기화 안내");
            msg.setText(mailText, "utf-8", "html");
            msg.setFrom(new InternetAddress("sngjae972@gmail.com","[Trip, the Moment]","UTF-8"));
        } catch (MessagingException e) {
            throw new CustomException(ErrorCode.EMAIL_ERROR);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        javaMailSender.send(msg);
    }

}