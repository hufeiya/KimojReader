package com.email;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.andorid.shu.love.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SendMail extends Activity {
	private Button btnClick;
	private EditText txtToAddress;
	private EditText txtSubject;
	private EditText txtContent;
	private static final String SAVE_INFORMATION = "save_information";
	String username;
	String password;
	private Vector<String> file = new Vector<String>();
    private String fileName="";
	public void addAttachfile(String fname){  
        file.addElement("/sdcard/lovereader/pic/" + "testpic" + ".png");  
    }  
	public void SendMail() throws MessagingException, IOException {
		// ��sharedpreference����ȡ��ֵ
		SharedPreferences pre = getSharedPreferences(SAVE_INFORMATION,
				MODE_WORLD_READABLE);
		String content = pre.getString("save", "");
		String[] Information = content.split(";");
		username = Information[0];
		password = Information[1];

		// �ò����д����ƣ���������������������������������������������������������������
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.qq.comm");// �洢�����ʼ�����������Ϣ
		props.put("mail.smtp.auth", "true");// ͬʱͨ����֤
		// �������ʼ��Ự
		Session session = Session.getInstance(props);
		session.setDebug(true);// ���õ��Ա�־
		// ������Ϣ��
		MimeMessage message = new MimeMessage(session);

		// ������ַ
		Address fromAddress = null;
		// fromAddress = new InternetAddress("sarah_susan@sina.com");
		fromAddress = new InternetAddress(username);
		message.setFrom(fromAddress);

		// �ռ���ַ
		Address toAddress = null;
		toAddress = new InternetAddress(txtToAddress.getText().toString());
		message.addRecipient(Message.RecipientType.TO, toAddress);

		// �����ʼ�����
        addAttachfile(fileName);
		message.setSubject(txtSubject.getText().toString());// �����ż��ı���
		message.setText(txtContent.getText().toString());// �����ż�����
		Multipart mp = new MimeMultipart();  
        MimeBodyPart mbp = new MimeBodyPart();  
        mbp.setContent(txtContent.getText().toString(), "text/html;charset=gb2312");  
        mp.addBodyPart(mbp);    
         if(!file.isEmpty()){//�и���  
             Enumeration efile=file.elements();  
             while(efile.hasMoreElements()){   
                 mbp=new MimeBodyPart();  
                 
                 fileName=efile.nextElement().toString(); //ѡ���ÿһ��������  
                 FileDataSource fds=new FileDataSource(fileName); //�õ�����Դ  
                 System.out.println(fileName+"SDFSDFSFDF");
                 mbp.setDataHandler(new DataHandler(fds)); //�õ�������������BodyPart  
                 mbp.setFileName(fds.getName());  //�õ��ļ���ͬ������BodyPart  
                 mp.addBodyPart(mbp);  
             }    
             file.removeAllElements();      
         }   
         message.setContent(mp); //Multipart���뵽�ż�  
		
		message.setSentDate(new Date());
		message.saveChanges(); // implicit with send()//�洢����Ϣ

		// send e-mail message

		Transport transport = null;
		transport = session.getTransport("smtp");
		transport.connect("smtp.qq.com", username, password);

		transport.sendMessage(message, message.getAllRecipients());
		transport.close();
		System.out.println("�ʼ����ͳɹ���");

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.send_email);

		txtToAddress = (EditText) findViewById(R.id.txtToAddress);
		txtSubject = (EditText) findViewById(R.id.txtSubject);
		txtContent = (EditText) findViewById(R.id.txtContent);

		txtToAddress.setText("�Լ�������@qq.com");
		txtSubject.setText("Hello~");
		txtContent.setText("��ã�������������~");

		btnClick = (Button) findViewById(R.id.btnSEND);
		btnClick.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					SendMail();
				} catch (MessagingException e) {

					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

}