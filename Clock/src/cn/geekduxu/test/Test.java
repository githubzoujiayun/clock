package cn.geekduxu.test;

import javax.swing.JFrame;
import javax.swing.JTable;

public class Test extends JFrame {

	public Test() {
		setSize(300, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		add(new JTable(5, 7));
		
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		new Test().setVisible(true);
	}
	
}
