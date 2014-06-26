package cn.geekduxu.ui;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * ����ͼƬ��Դ
 * @author <b>����</b>
 */
public class Images {

	private Images(){}
	/**
	 * Mac�������
	 */
	public static final Image FRAME = new ImageIcon("images/frame.png").getImage();
	/**
	 * �����ѡ��ֽ
	 */
	public static final Image BIZHI = new ImageIcon("images/back"+ new Random().nextInt(2) +".jpg").getImage();
	
	
	/**
	 * ����ͼ��
	 */
	public static final Image BIAOPAN = new ImageIcon("images/bp.png").getImage();
	/**
	 * ʱ��ͼ��
	 */
	public static final Image SHIZHEN = new ImageIcon("images/sz.png").getImage();
	/**
	 * ����ͼ��
	 */
	public static final Image FENZHEN = new ImageIcon("images/fz.png").getImage();
	/**
	 * ����ͼ��
	 */
	public static final Image MIAOZHEN = new ImageIcon("images/mz.png").getImage();
	/**
	 * Բ��ͼ��
	 */
	public static final Image YUANPAN = new ImageIcon("images/yp.png").getImage();
	
}
