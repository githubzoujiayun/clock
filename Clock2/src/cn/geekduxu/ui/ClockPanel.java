package cn.geekduxu.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.Transparency;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Calendar;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ClockPanel extends JPanel {

	private static final long serialVersionUID = -6649486929250421496L;
//	private static final Image BACK_IMG = new ImageIcon("images/back"+(System.currentTimeMillis() % 4)+".jpg").getImage();
	//ⅠⅡⅢⅣⅤⅥⅦⅨⅩⅪⅫ
	private static final String[] TIMES = {"Ⅰ", "Ⅱ", "Ⅲ", "Ⅳ", "Ⅴ", "Ⅵ", "Ⅶ", "Ⅷ", "Ⅸ", "Ⅹ", "Ⅺ", "Ⅻ"};
	private static final int FPS = 50;
	private Image backImage;
	private Calendar calendar;
	public ClockPanel() {
		new Thread(){
			public void run() {
				File[] backs = new File("images").listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return name.endsWith("jpg") || name.endsWith("png");
					}
				});
				Image[] backImages = new Image[backs.length];
				for (int i = 0; i < backs.length; i++) {
					backImages[i] = new ImageIcon(backs[i].getAbsolutePath()).getImage();
				}
				for(int i = 0; ; i++){
					backImage = backImages[i%backImages.length];
					try {
						Thread.sleep(10000);
					} catch (Exception e) {
					}
				}
			}
		}.start();
		new Thread(){
			public void run() {
				while(true){
					repaint();
					try {
						Thread.sleep(1000 / FPS);
					} catch (Exception e) {
					}
				}
			}
		}.start();
	}
	
	private BufferedImage getImage(){
		int width = getWidth();
		int height = getHeight();
		int min = (width < height ? width : height);
		int centerX = width >> 1;
		int centerY = height >> 1;
		int biaopanX = (int)(width - min * 0.9) >> 1; //左上角x坐标
		int biaopanY = (int)(height - min * 0.9) >> 1;//左上角y坐标
		int radius = (int) (min * 0.9) >> 1;//半径
		BufferedImage image = new  BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
		// 填充背景为透明
		image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); 
		g2d.dispose();  
		g2d = image.createGraphics(); 
		
		// 画表盘
		g2d.setColor(new Color(0x800000));
		Stroke stk = g2d.getStroke();
		g2d.setStroke(new BasicStroke(radius >> 6, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); 
		g2d.drawOval(biaopanX, biaopanY, radius << 1, radius << 1);
		g2d.setStroke( stk ); //将画刷复原 
		g2d.setFont(new Font("微软雅黑", Font.PLAIN, radius >> 3));
		for(int x = 0; x < 60; x++){
			if(x % 5 == 0){
				g2d.setColor(new Color(0x800000));
				Stroke stroke = g2d.getStroke(); //得到当前的画刷 
                g2d.setStroke(new BasicStroke(radius >> 6, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND)); //设置新的画刷 
                g2d.draw(new Line2D.Float(centerX, centerY-radius, centerX, centerY-radius+(radius >> 4)));//画线 
                g2d.drawString(""+TIMES[(x / 5)], centerX - (radius >> 4), centerY - radius + (radius / 5) );
                g2d.setStroke( stroke ); //将画刷复原 
			} else if((x % 5 != 0) && (radius > 150)){
				g2d.setColor(new Color(0x800000));
				g2d.drawLine(centerX, centerY-radius, centerX, centerY-radius+(radius >> 4));
			}
			g2d.rotate(Math.PI/30, centerX, centerY);
		}
		
		
		// 得到指针旋转的角度
		double[] rotates = getRotateValues();
		int arc = radius >> 5;
		// 画时针
		g2d.setColor(new Color(0x8B0000));
		g2d.rotate(rotates[0], centerX, centerY);
		g2d.fillRoundRect(centerX - (radius>>6), centerY - (radius>>1), radius>>5, (radius >> 4)+(radius>>1), arc, arc);
		g2d.rotate(-rotates[0], width >> 1, height >> 1);
		
		// 画分针
		g2d.setColor(new Color(0x228B22));
		g2d.rotate(rotates[1], centerX, centerY);
		g2d.fillRoundRect(centerX - (radius>>7), centerY - ((radius*3)>>2), radius>>6, (radius >> 4)+((radius*3)>>2), arc, arc);
		g2d.rotate(-rotates[1], centerX, centerY);
		
		// 画秒针
		g2d.setColor(new Color(0x00008));
		g2d.rotate(rotates[2], centerX, centerY);
		g2d.fillRoundRect(centerX - (radius>>8), centerY - ((radius*7)>>3), radius>>7, (radius >> 3)+((radius*7)>>3), arc>>2, arc>>2);
		g2d.rotate(-rotates[2], centerX, centerY);

		g2d.setColor(new Color(0xFFD700));
		int r = radius / 36;
		g2d.fillOval(centerX - (r), centerY - (r), r << 1, r << 1);
		g2d.dispose();
		return image;
	}
	
	/**
	 * 获得指针旋转的角度
	 * 
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
		int ms  = calendar.get(Calendar.MILLISECOND);
		// 时针的旋转角度
		rotates[0] = Math.PI * (hour + min / 60.0) / 6.0;
		// 分针的旋转角度
		rotates[1] = Math.PI * (min + sec / 60.0) / 30.0;
		// 秒针的旋转角度
		rotates[2] = sec * Math.PI / 30.0 + (Math.PI / 30) * (ms / 1000.0);
		return rotates;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(backImage, 0, 0, getWidth(), getHeight(), 0, 0, backImage.getWidth(null), backImage.getHeight(null), null);
		g.drawImage(getImage(), 0, 0, null);
	}
	
}
