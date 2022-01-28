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

	// 변수 선언부
	static final int WIDTH = 600;
	static final int HEIGHT = 700;
	static final int FrameX = 500;
	static final int FrameY = 25;

	static JPanel Upside; // 상단부
	static JPanel Center; // 중단부
	static JLabel Title; // 점수와 시간 표시를 위한 Label
	static JLabel Message; // Game Over등을 표시하기 위한 Label

	static JPanel[][] panel = new JPanel[30][30];
	static int[][] map = new int[30][30];
	static LinkedList<XY> worm = new LinkedList<>();

	static int dir = 3;
	static int score = 0; // 게임 score
	static int time = 0; // 플레이 타임
	static int timeTickCount = 0; // per 200ms
	static Timer timer = null; // 시간을 재기 위한 타이머

	public GameFrame() {				// 게임창 실행
		this.setTitle("Grow Worm Game");
		this.setBounds(FrameX, FrameY, WIDTH, HEIGHT);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		run();
	}

	public void run() {		// 게임 실행 시 필요한 함수들을 불러준다.
		// TODO Auto-generated method stub
		initUI();
		makeWorm();
		startTimer();
		setKeyListner();
		makeFood();
	}

	@Override
	public void initUI() { // 시작 화면 초기화
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
				map[i][j] = 0; 			// Init 0 : 빈칸, 1 : 먹이
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
	public void makeWorm() { // 지렁이 생성
		// TODO Auto-generated method stub
		worm.add(new XY(15, 15)); // 머리
		worm.add(new XY(14, 15)); // 몸통
		worm.add(new XY(13, 15)); // 꼬리
	}

	@Override
	public void startTimer() {			// 타이머 시작
		// TODO Auto-generated method stub
		timer = new Timer(200, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				timeTickCount += 1;
				if (timeTickCount % 5 == 0) {
					time++; // 1sec
				}
				moveWorm(); // 지렁이를 움직임
				updateUI(); // 움직임에 따라 맵을 갱신해줌
			}
		});
		timer.start();
	}

	@Override
	public void moveWorm() {
		// TODO Auto-generated method stub
		XY headXY = worm.get(0); // 지렁이 머리의 XY 좌표를 받아옴
		int headX = headXY.x; //
		int headY = headXY.y; //

		// 0 : 위, 1 : 아래, 2 : 왼쪽, 3 : 오른쪽
		if (dir == 0) { 			// 위
			boolean isColl = checkCollision(headX, headY - 1);
			if (isColl == true) { 	// 충돌 시
				GameOver();			// 게임오버
			}
			worm.add(0, new XY(headX, headY - 1)); 	// 충돌이 아니라면 지렁이의 앞에 한칸 추가해주고
			worm.remove(worm.size() - 1); 			// 맨 뒤 한칸을 지워주어 진행 방향으로 한칸 진행 시킨다.
		} else if (dir == 1) { 	// 아래
			boolean isColl = checkCollision(headX, headY + 1);
			if (isColl == true) {
				GameOver();
			}
			worm.add(0, new XY(headX, headY + 1)); // 위와 같은 원리
			worm.remove(worm.size() - 1);
		} else if (dir == 2) { 	// 왼쪽
			boolean isColl = checkCollision(headX - 1, headY);
			if (isColl == true) {
				GameOver();
			}
			worm.add(0, new XY(headX - 1, headY)); // 위와 같은 원리
			worm.remove(worm.size() - 1);
		} else if (dir == 3) { 	// 오른쪽
			boolean isColl = checkCollision(headX + 1, headY);
			if (isColl == true) {
				GameOver();
			}
			worm.add(0, new XY(headX + 1, headY)); // 위와 같은 원리
			worm.remove(worm.size() - 1);
		}
	}

	@Override
	public boolean checkCollision(int x, int y) {		// 충돌여부를 확인
		// TODO Auto-generated method stub
		// 게임 오버 조건
		if (x < 0 || x > 29 || y < 0 || y > 29) {
			return true;
		}

		for (XY xy : worm) {
			if (x == xy.x && y == xy.y) {
				return true;
			}
		}
		// 먹이 먹음
		if (map[y][x] == 1) {
			map[y][x] = 0;
			addTail();
			makeFood();
			score += 100;
		}
		return false;
	}

	@Override
	public void GameOver() {		// 게임오버
		// TODO Auto-generated method stub
		Message.setText("Game Over");
		timer.stop();
		finish();
	}

	@Override
	public void finish() {			// 게임 오버시 게임을 끝낸 후 랭킹창을 출력한다.
		// TODO Auto-generated method stub
		ResultFrame.score = score;
		this.setVisible(false);
		this.dispose();
		initAll();
		new ResultFrame();
	}

	@Override
	public void initAll() {			// 다시하기 시 정보들을 초기화 !
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
		// 상태에 따라 지도를 초기화 시켜줌
		for (int i = 0; i < 30; i++) {
			for (int j = 0; j < 30; j++) {
				if (map[i][j] == 0) { // 0 : 빈칸, 1 : 먹이
					panel[i][j].setBackground(Color.GRAY); // 빈칸
				} else if (map[i][j] == 1) {
					panel[i][j].setBackground(Color.GREEN); // 먹이
				}
			}
		}

		// 지렁이를 그려줌
		int index = 0;
		for (XY xy : worm) {
			if(xy.x >= 0 && xy.x < 30 && xy.y >= 0 && xy.y < 30) {
				if (index == 0) { // 지렁이의 머리
					panel[xy.y][xy.x].setBackground(Color.RED);
				} else { // 지렁이의 몸체
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
		int tailX = worm.get(worm.size() - 1).x; // 지렁이 꼬리의 X좌표 ■□□□x
		int tailY = worm.get(worm.size() - 1).y; // 지렁이 꼬리의 Y좌표 ■□□□x

		int tailX2 = worm.get(worm.size() - 2).x; // 지렁이 꼬리 앞부분의 X좌표 ■□□x□
		int tailY2 = worm.get(worm.size() - 2).y; // 지렁이 꼬리 앞부분의 Y좌표 ■□□x□

		if (tailX < tailX2) { // 진행방향이 오른쪽 => 꼬리를 왼쪽에 추가
			worm.add(new XY(tailX - 1, tailY));
		} else if (tailX > tailX2) { // 진행방향이 왼쪽 => 꼬리를 오른쪽에 추가
			worm.add(new XY(tailX + 1, tailY));
		} else if (tailY < tailY2) { // 진행방향이 아래쪽 => 꼬리를 위쪽에 추가
			worm.add(new XY(tailX, tailY2 - 1));
		} else if (tailY > tailY2) { // 진행방향이 위쪽 => 꼬리를 아래쪽에 추가
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
				if (e.getKeyCode() == KeyEvent.VK_UP) { // 위쪽 방향키가 눌리면
					if (dir != 1)
						dir = 0; // 반대로 진행중이 아니었다면 위로 방향 변경

				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) { // 아래쪽 방향키가 눌리면
					if (dir != 0)
						dir = 1; // 반대로 진행중이 아니었다면 아래로 방향 변경

				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) { // 왼쪽 방향키가 눌리면
					if (dir != 3)
						dir = 2; // 반대로 진행중이 아니었다면 왼쪽으로 방향 변경

				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) { // 오른쪽 방향키가 눌리면
					if (dir != 2)
						dir = 3; // 반대로 진행중이 아니었다면 오른쪽으로 방향 변경

				}
			}
		});
	}
}