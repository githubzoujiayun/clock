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
	 * ���̵Ŀ��
	 */
	private static final int WIDTH = Images.BIAOPAN.getWidth(null);
	/**
	 * �������������ֵ
	 */
	private static final int CENTER = WIDTH >> 1;
	
	/**
	 * ʱ��ͼ�����Ͻǵ�x����
	 */
	private static final int SZ_X = CENTER - (Images.SHIZHEN.getWidth(null) >> 1);
	/**
	 * ʱ��ͼ�����Ͻǵ�y����
	 */
	private static final int SZ_Y = CENTER - (Images.SHIZHEN.getHeight(null) >> 1);
	/**
	 * ����ͼ�����Ͻǵ�x����
	 */
	private static final int FZ_X = CENTER - (Images.FENZHEN.getWidth(null) >> 1);
	/**
	 * ����ͼ�����Ͻǵ�y����
	 */
	private static final int FZ_Y = CENTER - (Images.FENZHEN.getHeight(null) >> 1);
	/**
	 * ����ͼ�����Ͻǵ�x����
	 */
	private static final int MZ_X = CENTER - (Images.MIAOZHEN.getWidth(null) >> 1);
	/**
	 * ����ͼ�����Ͻǵ�y����
	 */
	private static final int MZ_Y = CENTER - (Images.MIAOZHEN.getHeight(null) >> 1);
	/**
	 * Բ��ͼ�����Ͻǵ�x����
	 */
	private static final int YP_X = CENTER - (Images.YUANPAN.getWidth(null) >> 1);
	/**
	 * Բ��ͼ�����Ͻǵ�y����
	 */
	private static final int YP_Y = CENTER - (Images.YUANPAN.getHeight(null) >> 1);

	private Calendar calendar;

	/**
	 * ������
	 */
	private ClockFrame fatherFrame;
	
	public ClockPanel(ClockFrame fatherFrame) {
		this.fatherFrame = fatherFrame;
		setSize(WIDTH, WIDTH);
//		setBackground(Color.RED);
		setOpaque(true);
		setVisible(true);
		//�����߳�
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
	 * ��ȡ��ǰʱ���ͼ��
	 */
	private BufferedImage drawTimeImage() {
		// ����BufferedImage����
		BufferedImage timeImage = new BufferedImage(WIDTH, WIDTH,
				BufferedImage.TYPE_INT_RGB);
		// ��ȡGraphics2D����
		Graphics2D g2d = timeImage.createGraphics();
		
		// ��̭����䱳��Ϊ��ɫ
		// g2d.setBackground(Color.WHITE);
		// g2d.clearRect(0, 0, WIDTH, WIDTH);
		
		// ��䱳��Ϊ͸��
		timeImage = g2d.getDeviceConfiguration().createCompatibleImage(WIDTH, WIDTH, Transparency.TRANSLUCENT); 
		g2d.dispose();  
		g2d = timeImage.createGraphics(); 
		
		// ������
		g2d.drawImage(Images.BIAOPAN, 0, 0, null);
		// �õ�ָ����ת�ĽǶ�
		double[] rotates = getRotateValues();
		// ��ʱ��
		g2d.rotate(rotates[0], CENTER, CENTER);
		g2d.drawImage(Images.SHIZHEN, SZ_X, SZ_Y, null);
		g2d.rotate(-rotates[0], CENTER, CENTER);
		// ������
		g2d.rotate(rotates[1], CENTER, CENTER);
		g2d.drawImage(Images.FENZHEN, FZ_X, FZ_Y, null);
		g2d.rotate(-rotates[1], CENTER, CENTER);
		// ������
		g2d.rotate(rotates[2], CENTER, CENTER);
		g2d.drawImage(Images.MIAOZHEN, MZ_X, MZ_Y, null);
		g2d.rotate(-rotates[2], CENTER, CENTER);
		// ��������Բ��
		g2d.drawImage(Images.YUANPAN, YP_X, YP_Y, null);
		// �ͷ���Դ
		g2d.dispose();
		return timeImage;
	}

	/**
	 * ���ָ����ת�ĽǶ�
	 * @return 
	 *     <li>[0] ʱ����ת�Ƕ�</li> 
	 *     <li>[1] ������ת�Ƕ�</li> 
	 *     <li>[2] ������ת�Ƕ�</li>
	 */
	private double[] getRotateValues() {
		calendar = Calendar.getInstance();
		double[] rotates = new double[3];
		int hour = calendar.get(Calendar.HOUR);
		int min = calendar.get(Calendar.MINUTE);
		int sec = calendar.get(Calendar.SECOND);
		// ʱ�����ת�Ƕ�
		rotates[0] = Math.PI * (hour + min / 60.0) / 6.0;
		// �������ת�Ƕ�
		rotates[1] = Math.PI * (min + sec / 60.0) / 30.0;
		// �������ת�Ƕ�
		rotates[2] = sec * Math.PI / 30.0;

		return rotates;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// �����봰�ڱ�ֽ�غ�
		g.drawImage(Images.BIZHI, 0, 0, WIDTH, WIDTH, 
				fatherFrame.getMarginLeft(), fatherFrame.getMarginTop(), 
				fatherFrame.getMarginLeft() + WIDTH, fatherFrame.getMarginTop() + WIDTH, null);
		// ����ʱ��
		g.drawImage(drawTimeImage(), 0, 0, null);
	}
}
