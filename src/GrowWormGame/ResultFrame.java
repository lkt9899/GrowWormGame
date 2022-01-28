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
	// 게임 종료 후 랭킹에 기록하기 위한 창
	public ResultFrame() {
		this.setLayout(null);
		// 기본 설정
		this.setTitle("Result");
		this.setBounds(GameFrame.FrameX, GameFrame.FrameY, GameFrame.WIDTH, GameFrame.HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	// 닫기를 누르면 창이 닫힘
		// 이름을 적을 텍스트 필드
		tf = new JTextField(20);
		tf.setBounds(GameFrame.WIDTH / 2 - 200 / 2, GameFrame.HEIGHT / 2 - 50 / 2, 200, 30);
		tf.addActionListener(this);
		// 확인 버튼
		OK = new JButton("OK");
		OK.setFont(new Font("TimesRoman", Font.BOLD, 20));
		OK.setBounds((GameFrame.WIDTH / 2) + (200 / 2), (GameFrame.HEIGHT / 2) - (50 / 2), 40, 30);
		OK.addActionListener(this);

        this.add(tf);
        this.add(OK);
	}

	@Override
	public void actionPerformed(ActionEvent e) {		// 확인 버튼을 누르면 정보들을 텍스트 파일에 기록한다.
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
