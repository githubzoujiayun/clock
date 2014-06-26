package cn.geekduxu.ui;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.awt.AWTUtilities;

public class ClockFrame extends JFrame {

	private static final long serialVersionUID = 8637177852509461853L;
	
	/**
	 * Mac���ϱ߿�
	 */
	private static final int FRAME_BORDER_TOP = 28;
	/**
	 * Mac����߿�
	 */
	private static final int FRAME_BORDER_LEFT = 30;
	/**
	 * ��ȡ��Ļ�ı߽�
	 */
	private Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
	/**
	 * ��ȡ�ײ��������߶�
	 */
	private int taskBarHeight = screenInsets.bottom; 
	/**
	 * ͼ���y����
	 */
	private int bottomY = Toolkit.getDefaultToolkit().getScreenSize().height - Images.FRAME.getHeight(null) - taskBarHeight;
	
	/**
	 * ��갴��ʱ�ĵ�
	 */
	private Point mouseLocation = new Point(0, bottomY);
	/**
	 * ����
	 */
	private JPanel clockPanel = new ClockPanel(this);
	/**
	 * ���̾����ϱ߿�ľ���
	 */
	private int marginTop = (Images.BIZHI.getHeight(null)-Images.BIAOPAN.getHeight(null)) >> 1;
	/**
	 * ���̾�����߿�ľ���
	 */
	private int marginLeft = (Images.BIZHI.getWidth(null)-Images.BIAOPAN.getWidth(null)) >> 1;
	
	public ClockFrame() {
		super("\u65f6\u949f  BY \u65ed\u54e5");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//ȡ������װ��
		setUndecorated(true);
		setSize(Images.FRAME.getWidth(null), Images.FRAME.getHeight(null));
		//���ô���͸��
		AWTUtilities.setWindowOpaque(this, false);
		//��ʼ���ƶ����巽��
		initMove();
		//��ʼ������
		initUI();
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - Images.FRAME.getWidth(null)) >> 1, 
				bottomY);
	}
	
	private void initUI() {
		setLayout(null);
		//  ���ñ��̣���������ʱ��������һ��ͨ���Ը��� 
//		clockPanel.setBounds(
//				((Images.BIZHI.getWidth(null)-Images.BIAOPAN.getWidth(null)) >> 1) + FRAME_BORDER_LEFT,
//				((Images.BIZHI.getHeight(null)-Images.BIAOPAN.getHeight(null)) >> 1) + FRAME_BORDER_TOP,
//				Images.BIAOPAN.getWidth(null), Images.BIAOPAN.getWidth(null));
		clockPanel.setBounds(
				FRAME_BORDER_LEFT + marginLeft, FRAME_BORDER_TOP + marginTop,
				Images.BIAOPAN.getWidth(null), Images.BIAOPAN.getWidth(null));
		clockPanel.setVisible(true);
		
//		AWTUtilities.setWindowOpacity(this, 0.6f);
		add(clockPanel);
		
	}

	/**
	 * �����ƶ�����
	 */
	private void initMove() {
		addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseLocation.x = e.getX();
                // mouseLocation.y = e.getY();
            }
            // �����ϵ�������Ҽ��رճ���
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3)
                    System.exit(0);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point frameLocation = getLocation();
//                setLocation(frameLocation.x + e.getX() - mouseLocation.x, frameLocation.y + e.getY() - mouseLocation.y);
                setLocation(frameLocation.x + e.getX() - mouseLocation.x, bottomY);
            }
        });
	}

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		g.drawImage(Images.FRAME, 0, 0, null);
		g.drawImage(Images.BIZHI, FRAME_BORDER_LEFT, FRAME_BORDER_TOP, null);
	}
	
	public int getMarginTop() {
		return marginTop;
	}

	public int getMarginLeft() {
		return marginLeft;
	}
	
}
