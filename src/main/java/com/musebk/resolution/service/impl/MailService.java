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
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
    TemplateEngine templateEngine = new TemplateEngine();
    private Session sessionMail;
    private @Value("${debug:false}") boolean debug;
    private @Value("${send.mail.template.dir:classpath:/mail/}") String dirTemplate;

    private ConcurrentMap<String, String> cacheTemplate = new ConcurrentHashMap();

    @Autowired
    public void setToEmail(MailProfileProperties mailProfileProperties) throws AddressException {
        toEmail = new InternetAddress(mailProfileProperties.getEmail());
    }

    @PostConstruct
    void initial() throws GeneralSecurityException {
        templateEngine.setTemplateResolver(new FileTemplateResolver());
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


    private String getContext(String templatePath) {
        return cacheTemplate.computeIfAbsent(templatePath, name -> {
            StringBuilder builder = new StringBuilder();
            File file = new File(dirTemplate);
            return builder.toString();
        });
    }

    private Map<String, Object> getContextVariable(Object obj) {
        return new HashMap<>();
    }


    private <T> String resolveTemplate(String templatePath, T data) throws NoSuchFieldException, IllegalAccessException {
        return templateEngine.process(getContext(templatePath), new Context(Locale.CHINESE, getContextVariable(data)));
    }
}
