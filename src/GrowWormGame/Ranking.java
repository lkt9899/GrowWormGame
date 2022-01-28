package GrowWormGame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

public class Ranking extends JFrame implements ActionListener, RankingFunction{
	// 변수 생성
	private final int X_RANK = 150;		// 순위를 표시할 X좌표
    private final int X_NAME = 300;		// 이름을 표시할 X좌표
    private final int X_SCORE = 450;	// 점수를 표시할 X좌표
    private final int Y = 75;			// 이들의 Y좌표
    private final int WIDTH_LABEL = 100;	// 이 후 추가될 Label들의 크기
    private final int HEIGHT_LABEL = 50;	//
    
    private JButton replay, Exit;					// 다시하기와 종료 버튼
    private JLabel labelRank, labelName, labelScore;// 순위를 표시하기 위한 Label
    
    private ArrayList<String> ls = new ArrayList<>();	// 텍스트 파일에서 순위를 받아올 배열리스트
    
    public Ranking() {						// 랭킹 프레임 생성자
        this.setTitle("Score Ranking");								// 랭킹 프레임의 각종 설정
        this.setLayout(null);
        this.setBounds(GameFrame.FrameX, GameFrame.FrameY, GameFrame.WIDTH, GameFrame.HEIGHT);
        this.setSize(new Dimension(GameFrame.WIDTH, GameFrame.HEIGHT));
        this.setVisible(true);
        // 다시하기 버튼
        replay = new JButton("Replay");
        replay.setFont(new Font("TimesRoman", Font.BOLD, 20));
        replay.setBounds(20, GameFrame.HEIGHT - 100, 150, 80);
        replay.addActionListener(this);
        // 종료 버튼
        Exit = new JButton("Exit");
        Exit.setFont(new Font("TimesRoman", Font.BOLD, 20));
        Exit.setBounds(GameFrame.WIDTH - 100, GameFrame.HEIGHT - 100, 150, 80);
        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // 랭킹을 출력
        printRanking();
        this.add(replay);
        this.add(Exit);
    }
    
    @Override
    public void printRanking() {				// 랭킹을 작성하는 함수
        printRankingTitle();
        printActualRanking();
    }
    
    @Override
    public void printRankingTitle() {				// 랭킹 창 맨위의 분류들을 표시 -> Rank		Name	Score
    	// Rank
        labelRank = new JLabel("Rank");
        labelRank.setFont(new Font("TimesRoman", Font.BOLD, 20));
        labelRank.setBounds(X_RANK, Y, WIDTH_LABEL, HEIGHT_LABEL);
        this.add(labelRank);
        // Name
        labelName = new JLabel("Name");
        labelName.setFont(new Font("TimesRoman", Font.BOLD, 20));
        labelName.setBounds(X_NAME, Y, WIDTH_LABEL, HEIGHT_LABEL);
        this.add(labelName);
        // Score
        labelScore = new JLabel("Score");
        labelScore.setFont(new Font("TimesRoman", Font.BOLD, 20));
        labelScore.setBounds(X_SCORE, Y, WIDTH_LABEL, HEIGHT_LABEL);
        this.add(labelScore);
    }
    
    @Override
    public void printActualRanking() {			// 실제 순위표를 분류에 맞춰 출력
        try (
                FileReader fr = new FileReader("ranking.txt");		// 순위표가 저장된 파일에서 데이터를 받아온다.
                BufferedReader br = new BufferedReader(fr);			//
        ) {															//
            String readLine = null;									//
            while ((readLine = br.readLine()) != null) {			//
                ls.add(readLine);									//
            }														//
        } catch (IOException e) {									//
        }															//
        
        
        List<RankList> lsScore = new ArrayList<RankList>();			// 순위를 저장하기 위한 RankList들의 List 생성
        for (int i = 1; i <= ls.size() / 2; i++) {					// 순서대로 짝수번째는 이름, 홀수번째는 점수를 받아옴
            String name = ls.get(2 * i - 2);
            int score = Integer.valueOf(ls.get(2 * i - 1));
        	lsScore.add(new RankList(name, score));
        }
        Collections.sort(lsScore); //정렬								// 기준에 맞춰 정렬
        ls.clear();													// ls를 비워준다.
        for(int i = lsScore.size() - 1; i >= 0 ; i--) {				// 정렬한 데이터를 다시 ls에 넣어준다.
        	RankList tmp = lsScore.get(i);
        	ls.add(tmp.getName());
        	ls.add(Integer.toString(tmp.getScore()));
        }
        
        int rank = 1;
        for (int i = 1; i <= lsScore.size(); i++) { 				// 순위표 생성
            callAllGen(i, rank++);
        }
    }
    
    @Override
    public void callAllGen(int x, int rank) {			// 랭킹을 작성하는 함수
        genName(2 * x - 2, rank);						// 이름
        genScore(2 * x - 1, rank);						// 점수
        genRank(Integer.toString(rank), rank);			// 순위
    }
    
    @Override
    public void genRank(String number, int rank) {		// 랭킹창에 순위 표시
        labelRank = new JLabel(number);
        labelRank.setBounds(X_RANK, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelRank);
    }
    
    @Override
    public void genName(int index, int rank) {			// 랭킹창에 이름 표시
        labelName = new JLabel((String) ls.get(index));
        labelName.setBounds(X_NAME, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelName);
    }
    
    @Override
    public void genScore(int index, int rank) {			// 랭킹창에 점수 표시
        labelScore = new JLabel((String) ls.get(index));
        labelScore.setBounds(X_SCORE, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelScore);
    }
    
    @Override
    public void reset() {
        this.setVisible(false);	// 창 안보이게
        this.dispose(); 		// 해당프레임만종료
        new GameFrame(); 		// 새게임
    }

    @Override
    public void actionPerformed(ActionEvent e) {		// 다시하기 버튼을 누르면 reset
        reset();
    }
}