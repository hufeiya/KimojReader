package com.android.FileBrowser;

import com.andorid.shu.love.R;

/** ��ʾһ���ļ�ʵ�� **/
public class FileInfo {
	public String Name;
	public String Path;
	public long Size;
	public boolean IsDirectory = false;
	public int FileCount = 0;
	public int FolderCount = 0;

	public int getIconResourceId() {
		if (IsDirectory) {
			return R.drawable.folder;//�����Ŀ¼��ʾ��ͼƬ��Ŀ¼����ʽ
		}
		return R.drawable.doc;//������ļ�,��ʾ����ʽ���ļ�����ʽ
	}
}