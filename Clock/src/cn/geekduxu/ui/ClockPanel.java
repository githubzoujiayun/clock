package cn.geekduxu.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.util.Calendar;

import javax.swing.JPanel;

public class ClockPanel extends JPanel {

	private static final long serialVersionUID = -6649486929250421496L;
	/**
	 * 表盘的宽度
	 */
	private static final int WIDTH = Images.BIAOPAN.getWidth(null);
	/**
	 * 表盘中央的坐标值
	 */
	private static final int CENTER = WIDTH >> 1;
	
	/**
	 * 时针图像左上角的x坐标
	 */
	private static final int SZ_X = CENTER - (Images.SHIZHEN.getWidth(null) >> 1);
	/**
	 * 时针图像左上角的y坐标
	 */
	private static final int SZ_Y = CENTER - (Images.SHIZHEN.getHeight(null) >> 1);
	/**
	 * 分针图像左上角的x坐标
	 */
	private static final int FZ_X = CENTER - (Images.FENZHEN.getWidth(null) >> 1);
	/**
	 * 分针图像左上角的y坐标
	 */
	private static final int FZ_Y = CENTER - (Images.FENZHEN.getHeight(null) >> 1);
	/**
	 * 秒针图像左上角的x坐标
	 */
	private static final int MZ_X = CENTER - (Images.MIAOZHEN.getWidth(null) >> 1);
	/**
	 * 秒针图像左上角的y坐标
	 */
	private static final int MZ_Y = CENTER - (Images.MIAOZHEN.getHeight(null) >> 1);
	/**
	 * 圆盘图像左上角的x坐标
	 */
	private static final int YP_X = CENTER - (Images.YUANPAN.getWidth(null) >> 1);
	/**
	 * 圆盘图像左上角的y坐标
	 */
	private static final int YP_Y = CENTER - (Images.YUANPAN.getHeight(null) >> 1);

	private Calendar calendar;

	/**
	 * 父窗体
	 */
	private ClockFrame fatherFrame;
	
	public ClockPanel(ClockFrame fatherFrame) {
		this.fatherFrame = fatherFrame;
		setSize(WIDTH, WIDTH);
//		setBackground(Color.RED);
		setOpaque(true);
		setVisible(true);
		//创建线程
		new Thread() {
			public void run() {
				while (true) {
					repaint();
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	/**
	 * 获取当前时间的图像
	 */
	private BufferedImage drawTimeImage() {
		// 创建BufferedImage对象
		BufferedImage timeImage = new BufferedImage(WIDTH, WIDTH,
				BufferedImage.TYPE_INT_RGB);
		// 获取Graphics2D对象
		Graphics2D g2d = timeImage.createGraphics();
		
		// 淘汰：填充背景为白色
		// g2d.setBackground(Color.WHITE);
		// g2d.clearRect(0, 0, WIDTH, WIDTH);
		
		// 填充背景为透明
		timeImage = g2d.getDeviceConfiguration().createCompatibleImage(WIDTH, WIDTH, Transparency.TRANSLUCENT); 
		g2d.dispose();  
		g2d = timeImage.createGraphics(); 
		
		// 画表盘
		g2d.drawImage(Images.BIAOPAN, 0, 0, null);
		// 得到指针旋转的角度
		double[] rotates = getRotateValues();
		// 画时针
		g2d.rotate(rotates[0], CENTER, CENTER);
		g2d.drawImage(Images.SHIZHEN, SZ_X, SZ_Y, null);
		g2d.rotate(-rotates[0], CENTER, CENTER);
		// 画分针
		g2d.rotate(rotates[1], CENTER, CENTER);
		g2d.drawImage(Images.FENZHEN, FZ_X, FZ_Y, null);
		g2d.rotate(-rotates[1], CENTER, CENTER);
		// 画秒针
		g2d.rotate(rotates[2], CENTER, CENTER);
		g2d.drawImage(Images.MIAOZHEN, MZ_X, MZ_Y, null);
		g2d.rotate(-rotates[2], CENTER, CENTER);
		// 画最上面圆盘
		g2d.drawImage(Images.YUANPAN, YP_X, YP_Y, null);
		// 释放资源
		g2d.dispose();
		return timeImage;
	}

	/**
	 * 获得指针旋转的角度
	 * @return 
	 *     <li>[0] 时针旋转角度</li> 
	 *     <li>[1] 分针旋转角度</li> 
	 *     <li>[2] 秒针旋转角度</li>
	 */
	private double[] getRotateValues() {
		calendar = Calendar.getInstance();
		double[] rotates = new double[3];
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		int sec = calendar.get(Calendar.SECOND);
		// 时针的旋转角度
		rotates[0] = Math.PI * (hour + min / 60.0) / 6.0;
		// 分针的旋转角度
		rotates[1] = Math.PI * (min + sec / 60.0) / 30.0;
		// 秒针的旋转角度
		rotates[2] = sec * Math.PI / 30.0;

		return rotates;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// 绘制与窗口壁纸重合
		g.drawImage(Images.BIZHI, 0, 0, WIDTH, WIDTH, 
				fatherFrame.getMarginLeft(), fatherFrame.getMarginTop(), 
				fatherFrame.getMarginLeft() + WIDTH, fatherFrame.getMarginTop() + WIDTH, null);
		// 绘制时钟
		g.drawImage(drawTimeImage(), 0, 0, null);
	}
}
