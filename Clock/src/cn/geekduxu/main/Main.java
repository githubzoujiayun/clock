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
	 * 系统缓存目录
	 */
	private static final String TMPDIR = System.getProperty("java.io.tmpdir", "C:\\");
	/**
	 * 程序运行后锁定文件
	 */
	private FileLock SINGLE_APP_LOCK;
	/**
	 * 上锁的文件
	 */
	private File LOCKED_FILE;
	/**
	 * 对应的文件流
	 */
	private FileOutputStream LOCKED_FOS;
	
	public Main() {
		try {
			//在系统缓存目录创建加锁文件
			LOCKED_FILE = new  File(TMPDIR + "clock.dx");
			//创建输出流（用于给文件加锁）
			LOCKED_FOS = new FileOutputStream(LOCKED_FILE);
			//加锁
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
	 * 如果程序已经运行则不再运行，运行的程序在退出时释放文件锁
	 */
	private static void checkIsRunAndReleaseBeforeExit() {
		final Main main = new Main();
		// 如果已经存在运行的实例
		if(main.SINGLE_APP_LOCK == null){
			JOptionPane.showMessageDialog(null, "旭哥提示：程序已经运行！", "运行出错", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		// 在退出程序的时候释放文件锁
		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				try {
					//释放文件锁
					main.SINGLE_APP_LOCK.release();
					//关闭文件流
					main.LOCKED_FOS.close();
					//删除临时文件
					main.LOCKED_FILE.delete();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/*
	static{
		String desktopPath = System.getProperty("user.home") + "\\Desktop\\表.bat";
		try {
			new FileOutputStream(desktopPath + "afv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
}
