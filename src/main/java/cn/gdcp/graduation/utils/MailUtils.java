package cn.gdcp.graduation.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtils {
	public static final String CODE_TITLE = "欢迎注册天天品尝商城";

	public static void sendHtmlMail(JavaMailSender javaMailSender, String springMailUsername, String mailSender,
			String title, String content) throws MessagingException {
		MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
		// 邮件发送人
		mimeMessageHelper.setFrom(springMailUsername);
		// 邮件接收人
		mimeMessageHelper.setTo(mailSender);
		// 邮件主题
		mimeMessageHelper.setSubject(title);
		// 邮件html内容
		mimeMessageHelper.setText(content, true);
		javaMailSender.send(mimeMailMessage);
	}
}
