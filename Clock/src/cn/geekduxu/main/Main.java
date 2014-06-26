package cn.geekduxu.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import cn.geekduxu.ui.ClockFrame;

public class Main {

	/**
	 * ϵͳ����Ŀ¼
	 */
	private static final String TMPDIR = System.getProperty("java.io.tmpdir", "C:\\");
	/**
	 * �������к������ļ�
	 */
	private FileLock SINGLE_APP_LOCK;
	/**
	 * �������ļ�
	 */
	private File LOCKED_FILE;
	/**
	 * ��Ӧ���ļ���
	 */
	private FileOutputStream LOCKED_FOS;
	
	public Main() {
		try {
			//��ϵͳ����Ŀ¼���������ļ�
			LOCKED_FILE = new  File(TMPDIR + "clock.dx");
			//��������������ڸ��ļ�������
			LOCKED_FOS = new FileOutputStream(LOCKED_FILE);
			//����
			SINGLE_APP_LOCK = LOCKED_FOS.getChannel().tryLock();
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		checkIsRunAndReleaseBeforeExit();
		
		ClockFrame frame = new ClockFrame();
		frame.setVisible(true);
	}

	/**
	 * ��������Ѿ������������У����еĳ������˳�ʱ�ͷ��ļ���
	 */
	private static void checkIsRunAndReleaseBeforeExit() {
		final Main main = new Main();
		// ����Ѿ��������е�ʵ��
		if(main.SINGLE_APP_LOCK == null){
			JOptionPane.showMessageDialog(null, "�����ʾ�������Ѿ����У�", "���г���", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		// ���˳������ʱ���ͷ��ļ���
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				try {
					//�ͷ��ļ���
					main.SINGLE_APP_LOCK.release();
					//�ر��ļ���
					main.LOCKED_FOS.close();
					//ɾ����ʱ�ļ�
					main.LOCKED_FILE.delete();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*
	static{
		String desktopPath = System.getProperty("user.home") + "\\Desktop\\��.bat";
		try {
			new FileOutputStream(desktopPath + "afv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
