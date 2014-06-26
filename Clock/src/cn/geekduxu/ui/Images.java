package cn.geekduxu.ui;

import java.awt.Image;
import java.util.Random;

import javax.swing.ImageIcon;

/**
 * 所有图片资源
 * @author <b>杜旭</b>
 */
public class Images {

	private Images(){}
	/**
	 * Mac电脑外观
	 */
	public static final Image FRAME = new ImageIcon("images/frame.png").getImage();
	/**
	 * 随机挑选壁纸
	 */
	public static final Image BIZHI = new ImageIcon("images/back"+ new Random().nextInt(2) +".jpg").getImage();
	
	
	/**
	 * 表盘图像
	 */
	public static final Image BIAOPAN = new ImageIcon("images/bp.png").getImage();
	/**
	 * 时针图像
	 */
	public static final Image SHIZHEN = new ImageIcon("images/sz.png").getImage();
	/**
	 * 分针图像
	 */
	public static final Image FENZHEN = new ImageIcon("images/fz.png").getImage();
	/**
	 * 秒针图像
	 */
	public static final Image MIAOZHEN = new ImageIcon("images/mz.png").getImage();
	/**
	 * 圆盘图像
	 */
	public static final Image YUANPAN = new ImageIcon("images/yp.png").getImage();
	
}
