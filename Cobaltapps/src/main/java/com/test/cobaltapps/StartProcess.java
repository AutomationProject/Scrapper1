package com.test.cobaltapps;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class StartProcess extends JFrame {
	private static final long serialVersionUID = 1L;

	public StartProcess() {
		initUI();
	}

	public final void initUI() {
		JPanel panel = new JPanel();
		final JTextArea area = new JTextArea();
		area.setPreferredSize(new Dimension(400, 400));
		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");
		panel.add(start);
		panel.add(stop);
		/*
		 * JTree tree = new JTree(); panel.add(tree);
		 */
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area.setText("Process Started..................");
				area.setText("Starting Webdriver...............");
				try {
					Extract_Info.startProcess();
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area.setText("Process Stopped..................");
				area.setText("Stopping Webdriver...............");
				try {
					Extract_Info.stopProcess();
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		panel.add(area);
		add(panel);
		pack();
		setTitle("FlowLayout Example");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				StartProcess ex = new StartProcess();
				ex.setVisible(true);
			}
		});
	}
}