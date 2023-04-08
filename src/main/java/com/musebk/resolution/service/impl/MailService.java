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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

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
    private final ConcurrentMap<String, String> cacheTemplate = new ConcurrentHashMap();
    private final ConcurrentMap<Class, List<Field>> classParamTemplate = new ConcurrentHashMap();
    @Resource
    InformEmailProperties emailProperties;
    InternetAddress toEmail;
    TemplateEngine templateEngine = new TemplateEngine();
    private Session sessionMail;
    private @Value("${debug:false}") boolean debug;
    private @Value("${send.mail.template.dir}")
    @Nullable String dirTemplate = "./mail/";


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
            try {
                StringBuilder template = new StringBuilder();
                InputStream resource = ClassLoader.getSystemClassLoader().getResourceAsStream(dirTemplate + name);
                if (resource == null) {
                    throw new IllegalArgumentException("找不到模板文件");
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(resource));
                String str;
                while ((str = reader.readLine()) != null) {
                    template.append(str);
                }
                return template.toString();
            } catch (IOException e) {
                throw new Error(e);
            }
        });
    }

    private Map<String, Object> getContextVariable(Object obj) {
        return classParamTemplate.computeIfAbsent(obj.getClass(), clazz -> {
            Field[] declaredFields = Arrays.stream(clazz.getDeclaredFields()).filter(field -> !Modifier.isStatic(field.getModifiers())).toArray(Field[]::new);
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
            }
            return Arrays.asList(declaredFields);
        }).stream().collect(Collectors.toMap(Field::getName, field -> get(obj, field)));
    }

    private Object get(Object obj, Field attribute) {
        try {
            return attribute.get(obj);
        } catch (IllegalAccessException e) {
            throw new Error(e);
        }
    }

    private <T> String resolveTemplate(String templatePath, T data) {
        return templateEngine.process(getContext(templatePath), new Context(Locale.CHINESE, getContextVariable(data)));
    }
}
