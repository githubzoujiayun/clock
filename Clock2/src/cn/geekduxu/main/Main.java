package cn.geekduxu.main;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cn.geekduxu.ui.ClockPanel;

public class Main extends JFrame {

	private static final long serialVersionUID = 1193635552529995909L;
	private static final Cursor HIDE_CURSOR = Toolkit.getDefaultToolkit()
			.createCustomCursor(new ImageIcon("img").getImage(),
					new Point(10, 10), "");
	private static final Cursor SHOW_CURSOR = new Cursor(0);
	private boolean isFull;
	private int width;
	private int height;
	private Point location;

	public Main() {
		super("Ê±ÖÓ ¼òÒ×°æ By \u675C\u65ED");
		setSize(500, 500);
		setMinimumSize(new Dimension(303, 323));
		setContentPane(new ClockPanel());
		init();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	private void init() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE && isFull) {
					exitFullScreen();
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (e.getClickCount() == 2) {
					if (!isFull) {
						isFull = true;
						width = getWidth();
						height = getHeight();
						location = getLocation();
						setLocation(0, 0);
						setSize(Toolkit.getDefaultToolkit().getScreenSize());
						dispose();
						setUndecorated(true);
						setVisible(true);
						setAlwaysOnTop(true);
						setCursor(HIDE_CURSOR);
						repaint();
					} else {
						exitFullScreen();
					}
				}
			}

		});
	}

	private void exitFullScreen() {
		isFull = false;
		dispose();
		setSize(width, height);
		setLocation(location);
		setUndecorated(false);
		setVisible(true);
		setAlwaysOnTop(false);
		setCursor(SHOW_CURSOR);
		repaint();
	}

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					new Main().setVisible(true);
				}
			});
		} catch (Exception e) {
		}
	}

}
