package GrowWormGame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class ResultFrame extends JFrame implements ActionListener{
	private JButton OK;
	private JTextField tf;
	
	private String PlayerName;
	static int score;
	// ���� ���� �� ��ŷ�� ����ϱ� ���� â
	public ResultFrame() {
		this.setLayout(null);
		// �⺻ ����
		this.setTitle("Result");
		this.setBounds(GameFrame.FrameX, GameFrame.FrameY, GameFrame.WIDTH, GameFrame.HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// �ݱ⸦ ������ â�� ����
		// �̸��� ���� �ؽ�Ʈ �ʵ�
		tf = new JTextField(20);
		tf.setBounds(GameFrame.WIDTH / 2 - 200 / 2, GameFrame.HEIGHT / 2 - 50 / 2, 200, 30);
		tf.addActionListener(this);
		// Ȯ�� ��ư
		OK = new JButton("OK");
		OK.setFont(new Font("TimesRoman", Font.BOLD, 20));
		OK.setBounds((GameFrame.WIDTH / 2) + (200 / 2), (GameFrame.HEIGHT / 2) - (50 / 2), 40, 30);
		OK.addActionListener(this);

        this.add(tf);
        this.add(OK);
	}

	@Override
	public void actionPerformed(ActionEvent e) {		// Ȯ�� ��ư�� ������ �������� �ؽ�Ʈ ���Ͽ� ����Ѵ�.
		// TODO Auto-generated method stub
		PlayerName = tf.getText();
		try (
                FileWriter fw = new FileWriter("ranking.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
        ) {
            bw.write(PlayerName);
            bw.newLine();
            bw.write(Integer.toString(score));
            bw.newLine();
            bw.flush();
        } catch (IOException ie) {
            System.out.println(ie);
        }
		setVisible(false);
		this.dispose();
        new Ranking();
	}
}
