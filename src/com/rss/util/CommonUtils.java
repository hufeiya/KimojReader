package com.rss.util;

import java.io.File;
import java.util.HashMap;

import android.os.Environment;
import android.os.StatFs;
/**
 * @author Kiritor
 * ͨ�ù�����
 * �õ�sdcard�������Ϣ*/
public class CommonUtils {

	private void sd() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) { // �ж��Ƿ����SD��
			File filePath = Environment.getExternalStorageDirectory(); // ���sd
																		// card��·��
			StatFs stat = new StatFs(filePath.getPath()); // ����StatFs��������������ҪSD������Ϣ�Ϳ�����ȡ��
			long blockSize = stat.getBlockSize(); // ���block�Ĵ�С
			float totalBlocks = stat.getBlockCount(); // ���������
			int sizeInMb = (int) (blockSize * totalBlocks) / 1024 / 1024; // ת���ɵ�λ���׵�
			long availableBlocks = stat.getAvailableBlocks(); // ��ÿ�������
			float percent = availableBlocks / totalBlocks; // ��ÿ��ñ���
			percent = (int) (percent * 1000); // ��ȥ����С��λ��
			String a = "SD Cardʹ�������\n��������" + sizeInMb + "M��\n����"
					+ (1000 - percent) / 10.0f + "% ����" + percent / 10.f + "%��";
		} else {
		}
	}
}
