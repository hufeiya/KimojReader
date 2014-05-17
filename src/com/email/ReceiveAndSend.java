package com.email;

import com.andorid.shu.love.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ReceiveAndSend extends Activity {
	private Button btnSendEmail;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receive_send);
		btnSendEmail = (Button) findViewById(R.id.btnSendEmail);
		btnSendEmail.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent_third=new Intent();
				intent_third.setClass(ReceiveAndSend.this, SendMail.class);
				showDialog(0);//��ʾ�������Ի���'
				startActivity(intent_third);
			}
		});

	}

	protected Dialog onCreateDialog(int id) {// ��ʾ��������Dialog
		ProgressDialog dialog = new ProgressDialog(this);
		dialog.setTitle("���Ժ򡣡���");
		dialog.setIndeterminate(true);
		dialog.setMessage("�������ڼ��ء�����");
		return dialog;
	}

}
