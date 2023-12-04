package com.justyou.justme.base.component;

import com.justyou.justme.domains.member.dto.SendMailDto;
import com.justyou.justme.domains.member.model.entity.Member;
import com.justyou.justme.domains.company.model.entity.RedisCompany;
import com.justyou.justme.domains.member.model.entity.RedisUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
@Slf4j
public class MailComponent {

    private final JavaMailSender javaMailSender;

    public void signUpSender(RedisUser redisUser, String key) {
        SendMailDto sendMailDto = SendMailDto.builder()
                .to(redisUser.getEmail())
                .from("JUST ME")
                .subject(" JUST ME - 이메일 인증")
                .text("<H1>JUST ME 회원 가입을 축하드립니다.</H1>" +
                        "</br>" +
                        "아래 링크를 클릭하여 이메일 인증을 완료 해주세요." +
                        "</br>" +
                        "<div><a target='_blank' href='https://localhost:8080/users/email-auth?id=" +
                        key + "'> 이메일 인증하기 </a></div>" +
                        "</br>")
                .build();
        log.info("이메일 인증 메일 발송완료 TO: " + redisUser.getEmail());

        sendEmail(sendMailDto);
    }

    public boolean changePasswordAuthSender(Member member) {
        long startTime = System.currentTimeMillis();

        SendMailDto sendMailDto = SendMailDto.builder()
                .to(member.getEmail())
                .from("JUST ME")
                .subject(" JUST ME - 비밀번호 재설정")
                .text("아래 링크를 클릭하여 비밀번호를 변경 해주세요."
                        + "</br>" +
                        "<div><a target='_blank' href='https://localhost:8080/password/new?uuid="
                        + member.getPasswordChangeKey() + "'> 비밀번호 재설정 링크 </a></div>"
                        + "</br>")
                .build();
        log.info("비밀번호 재설정 메일 발송완료 TO: " + member.getEmail());

        boolean result = sendEmail(sendMailDto);
        long stopTime = System.currentTimeMillis();
        log.info("비밀번호 발송 결과 : " + result + " | 메일발송 걸린시간 : " + (stopTime - startTime));

        return result;
    }

    public void companySignUpSender(RedisCompany redisCompany, String key) {
        SendMailDto sendMailDto = SendMailDto.builder()
                .to(redisCompany.getEmail())
                .from("JUST ME")
                .subject(" JUST ME - 이메일 인증")
                .text("<H1>JUST ME 회원 가입을 축하드립니다.</H1>" +
                        "</br>" +
                        "아래 링크를 클릭하여 이메일 인증을 완료 해주세요." +
                        "</br>" +
                        "<div><a target='_blank' href='https://localhost:8080/users/email-auth?id=" +
                        key + "'> 이메일 인증하기 </a></div>" +
                        "</br>")
                .build();
        log.info("이메일 인증 메일 발송완료 TO: " + redisCompany.getEmail());

        sendEmail(sendMailDto);
    }

    public boolean sendEmail(SendMailDto mailDto) {

        boolean result = false;

        MimeMessagePreparator msg = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper mimeMessageHelper
                        = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                mimeMessageHelper.setTo(mailDto.getTo());
                mimeMessageHelper.setFrom(mailDto.getTo());
                mimeMessageHelper.setSubject(mailDto.getSubject());
                mimeMessageHelper.setText(mailDto.getText(), true);
            }
        };

        try {
            javaMailSender.send(msg);
            result = true;
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }

        return result;
    }
}