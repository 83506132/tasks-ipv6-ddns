package com.musebk.resolution.service.impl;

import com.musebk.resolution.domain.DataError;
import com.musebk.resolution.domain.config.InformEmailProperties;
import com.musebk.resolution.domain.config.MailProfileProperties;
import com.musebk.resolution.service.InformService;
import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.musebk.resolution.ResolverEntrance.PROPERTY_PREFIX;

/**
 * description:
 *
 * @Author ZhaoMuse
 * @date 2022/4/30 21:54
 * @Since
 */
@Service
@ConditionalOnProperty(prefix = PROPERTY_PREFIX, name = "send.mail.enable", havingValue = "true")
public class MailService implements InformService {
    @Resource
    InformEmailProperties emailProperties;

    InternetAddress toEmail;

    private Session sessionMail;

    private @Value("${debug:false}") boolean debug;

    @Autowired
    public void setToEmail(MailProfileProperties mailProfileProperties) throws AddressException {
        toEmail = new InternetAddress(mailProfileProperties.getEmail());
    }

    @PostConstruct
    void initial() throws GeneralSecurityException {
        final PasswordAuthentication authentication = new PasswordAuthentication(emailProperties.getEmail(), emailProperties.getPwd());
        sessionMail = Session.getInstance(emailProperties.instanceProperty(), new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                if (emailProperties.isAuth()) {
                    return authentication;
                }
                return super.getPasswordAuthentication();
            }
        });
        sessionMail.setDebug(debug);
    }


    @Override
    public void send(DataError dataError) throws MessagingException, NoSuchFieldException, IllegalAccessException {
        MimeMessage msg = new MimeMessage(sessionMail);
        msg.setFrom(emailProperties.getEmail());
        msg.setRecipients(Message.RecipientType.TO, new Address[]{toEmail});
        msg.setSubject(dataError.getTitle(), Charset.defaultCharset().name());
        msg.setContent(resolveTemplate(DataError.TEMPLATE_NAME, dataError), "text/html;charset=utf-8");
    }


    private List<Map.Entry<String, String>> getContext(String templatePath) {

        return new ArrayList<>();
    }

    private <T> String resolveTemplate(String templatePath, T data) throws NoSuchFieldException, IllegalAccessException {
        StringBuilder context = new StringBuilder();
        for (Map.Entry<String, String> o : getContext(templatePath)) {
            context.append(o.getKey());
            if (o.getValue() == null) {
                continue;
            }
            Class<?> clazz = data.getClass();
            Field field = clazz.getDeclaredField(o.getValue());
            field.setAccessible(true);
            context.append(field.get(data));
        }
        return context.toString();

    }
}
