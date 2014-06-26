package cn.geekduxu.main;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import cn.geekduxu.ui.ClockPanel;

public class Main extends JFrame {
	
	private static final long serialVersionUID = 1193635552529995909L;
	private boolean isFull;
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
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if(e.getClickCount() == 2){
					if(!isFull){
						isFull = true;
						setLocation(0, 0);
				        setSize(Toolkit.getDefaultToolkit().getScreenSize());
				        dispose();
				        setUndecorated(true);
				        setVisible(true);
				        setAlwaysOnTop(true);
				        repaint();
					}else {
						isFull = false;
						dispose();
						setSize(500,500);
						setLocationRelativeTo(null);
						setUndecorated(false);
						setVisible(true);
						setAlwaysOnTop(false);
						repaint();
					}
				}
			}
		});
	}

	public static void main(String[] args) throws Exception{
		
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main().setVisible(true);
			}
		});
	}

}
