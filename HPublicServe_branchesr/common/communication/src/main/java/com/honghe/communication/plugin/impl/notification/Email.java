package com.honghe.communication.plugin.impl.notification;

import jodd.mail.*;


/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2015/12/25
 */
public final class Email implements Notification {

    private String subject;
    private String content;
    private String[] to;
    boolean isHtml;
    private String smtp;
    private String account;
    private String password;
    private Attachment[] attachment = null;

    public Email(String subject, String content, String[] to, boolean isHtml) {
        this(subject, content, to, isHtml, "smtp.263xmail.com", "Hhtlb@honghe-tech.com", "hht@11111");
    }


    public Email(String subject, String content, String[] to, Attachment[] attachment) {
        this(subject, content, to, attachment, "smtp.263xmail.com", "Hhtlb@honghe-tech.com", "hht@11111");
    }

    public Email(String subject, String content, String[] to, Attachment[] attachment, String smtp, String account, String password) {
        this(subject, content, to, false, smtp, account, password);
        this.attachment = attachment;
    }

    public Email(String subject, String content, String[] to, boolean isHtml, String smtp, String account, String password) {
        this.subject = subject;
        this.content = content;
        this.to = to;
        this.isHtml = isHtml;
        this.smtp = smtp;
        this.account = account;
        this.password = password;
    }


    @Override
    public boolean send() {
        jodd.mail.Email email = jodd.mail.Email.create();
        email.from(account).to(to).subject(subject);
        if (isHtml) {
            email.addHtml(content);
        } else {
            email.addText(content, "UTF-8");
        }
        if (attachment != null) {
            for (Attachment _attachment : attachment) {
                byte[] content = _attachment.getContent();
                if (content == null) continue;
                email.attach(EmailAttachment.attachment().setName(_attachment.getName()).bytes(content));
            }
        }
        try {
            SmtpServer smtpServer = SmtpServer.create(smtp)
                    .authenticateWith(account, password);
            SendMailSession mailSession = smtpServer.createSession();
            mailSession.open();
            mailSession.sendMail(email);
            mailSession.close();
            return true;
        } catch (Exception e) {

        }
        return false;

    }

    public static void main(String[] args) throws Exception {
        Attachment[] aaa = AttachmentUtil.getAttachment(new String[]{"http://192.168.16.173:8181/storage/32/2016-05-24_10-22-03/648417355/648417355.xlsx==士大夫士大夫"});
        Email e = new Email("xxxx", "sfdsfdsfd", new String[]{"zhanghongbin@qq.com"}, aaa);
        System.out.println(e.send());
    }

}
