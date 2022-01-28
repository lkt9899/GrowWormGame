package GrowWormGame;

import java.util.LinkedList;
import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameFrame extends JFrame implements Function {
	static class XY {
		private int x, y;

		XY(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	// ���� �����
	static final int WIDTH = 600;
	static final int HEIGHT = 700;
	static final int FrameX = 500;
	static final int FrameY = 25;

	static JPanel Upside; // ��ܺ�
	static JPanel Center; // �ߴܺ�
	static JLabel Title; // ������ �ð� ǥ�ø� ���� Label
	static JLabel Message; // Game Over���� ǥ���ϱ� ���� Label

	static JPanel[][] panel = new JPanel[30][30];
	static int[][] map = new int[30][30];
	static LinkedList<XY> worm = new LinkedList<>();

	static int dir = 3;
	static int score = 0; // ���� score
	static int time = 0; // �÷��� Ÿ��
	static int timeTickCount = 0; // per 200ms
	static Timer timer = null; // �ð��� ��� ���� Ÿ�̸�

	public GameFrame() {				// ����â ����
		this.setTitle("Grow Worm Game");
		this.setBounds(FrameX, FrameY, WIDTH, HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		run();
	}

	public void run() {		// ���� ���� �� �ʿ��� �Լ����� �ҷ��ش�.
		// TODO Auto-generated method stub
		initUI();
		makeWorm();
		startTimer();
		setKeyListner();
		makeFood();
	}

	@Override
	public void initUI() { // ���� ȭ�� �ʱ�ȭ
		// TODO Auto-generated method stub
		this.setLayout(new BorderLayout());

		Upside = new JPanel();
		Upside.setBackground(Color.BLACK);
		Upside.setPreferredSize(new Dimension(600, 100));
		Upside.setLayout(new FlowLayout());

		Title = new JLabel("Score : 0, Time : 0 sec");
		Title.setPreferredSize(new Dimension(600, 50));
		Title.setFont(new Font("TimesRoman", Font.BOLD, 20));
		Title.setForeground(Color.WHITE);
		Title.setHorizontalAlignment(JLabel.CENTER);
		Upside.add(Title);

		Message = new JLabel("Enjoying Game!!");
		Message.setPreferredSize(new Dimension(600, 20));
		Message.setFont(new Font("TimesRoman", Font.BOLD, 20));
		Message.setForeground(Color.YELLOW);
		Message.setHorizontalAlignment(JLabel.CENTER);
		Upside.add(Message);

		this.add("North", Upside);

		Center = new JPanel();
		Center.setLayout(new GridLayout(30, 30));
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				map[i][j] = 0; 			// Init 0 : ��ĭ, 1 : ����
				panel[i][j] = new JPanel();
				panel[i][j].setPreferredSize(new Dimension(30, 30));
				panel[i][j].setBackground(Color.GRAY);
				Center.add(panel[i][j]);
			}
		}
		this.add("Center", Center);
		this.pack();
	}

	@Override
	public void makeWorm() { // ������ ����
		// TODO Auto-generated method stub
		worm.add(new XY(15, 15)); // �Ӹ�
		worm.add(new XY(14, 15)); // ����
		worm.add(new XY(13, 15)); // ����
	}

	@Override
	public void startTimer() {			// Ÿ�̸� ����
		// TODO Auto-generated method stub
		timer = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				timeTickCount += 1;
				if (timeTickCount % 5 == 0) {
					time++; // 1sec
				}
				moveWorm(); // �����̸� ������
				updateUI(); // �����ӿ� ���� ���� ��������
			}
		});
		timer.start();
	}

	@Override
	public void moveWorm() {
		// TODO Auto-generated method stub
		XY headXY = worm.get(0); // ������ �Ӹ��� XY ��ǥ�� �޾ƿ�
		int headX = headXY.x; //
		int headY = headXY.y; //

		// 0 : ��, 1 : �Ʒ�, 2 : ����, 3 : ������
		if (dir == 0) { 			// ��
			boolean isColl = checkCollision(headX, headY - 1);
			if (isColl == true) { 	// �浹 ��
				GameOver();			// ���ӿ���
			}
			worm.add(0, new XY(headX, headY - 1)); 	// �浹�� �ƴ϶�� �������� �տ� ��ĭ �߰����ְ�
			worm.remove(worm.size() - 1); 			// �� �� ��ĭ�� �����־� ���� �������� ��ĭ ���� ��Ų��.
		} else if (dir == 1) { 	// �Ʒ�
			boolean isColl = checkCollision(headX, headY + 1);
			if (isColl == true) {
				GameOver();
			}
			worm.add(0, new XY(headX, headY + 1)); // ���� ���� ����
			worm.remove(worm.size() - 1);
		} else if (dir == 2) { 	// ����
			boolean isColl = checkCollision(headX - 1, headY);
			if (isColl == true) {
				GameOver();
			}
			worm.add(0, new XY(headX - 1, headY)); // ���� ���� ����
			worm.remove(worm.size() - 1);
		} else if (dir == 3) { 	// ������
			boolean isColl = checkCollision(headX + 1, headY);
			if (isColl == true) {
				GameOver();
			}
			worm.add(0, new XY(headX + 1, headY)); // ���� ���� ����
			worm.remove(worm.size() - 1);
		}
	}

	@Override
	public boolean checkCollision(int x, int y) {		// �浹���θ� Ȯ��
		// TODO Auto-generated method stub
		// ���� ���� ����
		if (x < 0 || x > 29 || y < 0 || y > 29) {
			return true;
		}

		for (XY xy : worm) {
			if (x == xy.x && y == xy.y) {
				return true;
			}
		}
		// ���� ����
		if (map[y][x] == 1) {
			map[y][x] = 0;
			addTail();
			makeFood();
			score += 100;
		}
		return false;
	}

	@Override
	public void GameOver() {		// ���ӿ���
		// TODO Auto-generated method stub
		Message.setText("Game Over");
		timer.stop();
		finish();
	}

	@Override
	public void finish() {			// ���� ������ ������ ���� �� ��ŷâ�� ����Ѵ�.
		// TODO Auto-generated method stub
		ResultFrame.score = score;
		this.setVisible(false);
		this.dispose();
		initAll();
		new ResultFrame();
	}

	@Override
	public void initAll() {			// �ٽ��ϱ� �� �������� �ʱ�ȭ !
		// TODO Auto-generated method stub
		worm.clear();
		score = 0;
		time = 0;
		dir = 3;
		timer = null;
		timeTickCount = 0;
	}
	
	@Override
	public void updateUI() {
		// TODO Auto-generated method stub
		Title.setText("Score : " + score + " Time : " + time);
		// ���¿� ���� ������ �ʱ�ȭ ������
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				if (map[i][j] == 0) { // 0 : ��ĭ, 1 : ����
					panel[i][j].setBackground(Color.GRAY); // ��ĭ
				} else if (map[i][j] == 1) {
					panel[i][j].setBackground(Color.GREEN); // ����
				}
			}
		}

		// �����̸� �׷���
		int index = 0;
		for (XY xy : worm) {
			if(xy.x >= 0 && xy.x < 30 && xy.y >= 0 && xy.y < 30) {
				if (index == 0) { // �������� �Ӹ�
					panel[xy.y][xy.x].setBackground(Color.RED);
				} else { // �������� ��ü
					panel[xy.y][xy.x].setBackground(Color.YELLOW);
				}
			}
			else {
				makeWorm();
			}
			index++;
		}
	}

	@Override
	public void addTail() {
		// TODO Auto-generated method stub
		int tailX = worm.get(worm.size() - 1).x; // ������ ������ X��ǥ �����x
		int tailY = worm.get(worm.size() - 1).y; // ������ ������ Y��ǥ �����x

		int tailX2 = worm.get(worm.size() - 2).x; // ������ ���� �պκ��� X��ǥ ����x��
		int tailY2 = worm.get(worm.size() - 2).y; // ������ ���� �պκ��� Y��ǥ ����x��

		if (tailX < tailX2) { // ��������� ������ => ������ ���ʿ� �߰�
			worm.add(new XY(tailX - 1, tailY));
		} else if (tailX > tailX2) { // ��������� ���� => ������ �����ʿ� �߰�
			worm.add(new XY(tailX + 1, tailY));
		} else if (tailY < tailY2) { // ��������� �Ʒ��� => ������ ���ʿ� �߰�
			worm.add(new XY(tailX, tailY2 - 1));
		} else if (tailY > tailY2) { // ��������� ���� => ������ �Ʒ��ʿ� �߰�
			worm.add(new XY(tailX, tailY2 + 1));
		}
	}

	@Override
	public void makeFood() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int x = rand.nextInt(30);
		int y = rand.nextInt(30);

		map[x][y] = 1;
	}

	@Override
	public void setKeyListner() {
		// TODO Auto-generated method stub
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP) { // ���� ����Ű�� ������
					if (dir != 1)
						dir = 0; // �ݴ�� �������� �ƴϾ��ٸ� ���� ���� ����

				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) { // �Ʒ��� ����Ű�� ������
					if (dir != 0)
						dir = 1; // �ݴ�� �������� �ƴϾ��ٸ� �Ʒ��� ���� ����

				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) { // ���� ����Ű�� ������
					if (dir != 3)
						dir = 2; // �ݴ�� �������� �ƴϾ��ٸ� �������� ���� ����

				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // ������ ����Ű�� ������
					if (dir != 2)
						dir = 3; // �ݴ�� �������� �ƴϾ��ٸ� ���������� ���� ����

				}
			}
		});
	}
}