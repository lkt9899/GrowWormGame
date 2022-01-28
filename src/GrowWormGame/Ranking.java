package GrowWormGame;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

public class Ranking extends JFrame implements ActionListener, RankingFunction{
	// ���� ����
	private final int X_RANK = 150;		// ������ ǥ���� X��ǥ
    private final int X_NAME = 300;		// �̸��� ǥ���� X��ǥ
    private final int X_SCORE = 450;	// ������ ǥ���� X��ǥ
    private final int Y = 75;			// �̵��� Y��ǥ
    private final int WIDTH_LABEL = 100;	// �� �� �߰��� Label���� ũ��
    private final int HEIGHT_LABEL = 50;	//
    
    private JButton replay, Exit;					// �ٽ��ϱ�� ���� ��ư
    private JLabel labelRank, labelName, labelScore;// ������ ǥ���ϱ� ���� Label
    
    private ArrayList<String> ls = new ArrayList<>();	// �ؽ�Ʈ ���Ͽ��� ������ �޾ƿ� �迭����Ʈ
    
    public Ranking() {						// ��ŷ ������ ������
        this.setTitle("Score Ranking");								// ��ŷ �������� ���� ����
        this.setLayout(null);
        this.setBounds(GameFrame.FrameX, GameFrame.FrameY, GameFrame.WIDTH, GameFrame.HEIGHT);
        this.setSize(new Dimension(GameFrame.WIDTH, GameFrame.HEIGHT));
        this.setVisible(true);
        // �ٽ��ϱ� ��ư
        replay = new JButton("Replay");
        replay.setFont(new Font("TimesRoman", Font.BOLD, 20));
        replay.setBounds(20, GameFrame.HEIGHT - 100, 150, 80);
        replay.addActionListener(this);
        // ���� ��ư
        Exit = new JButton("Exit");
        Exit.setFont(new Font("TimesRoman", Font.BOLD, 20));
        Exit.setBounds(GameFrame.WIDTH - 100, GameFrame.HEIGHT - 100, 150, 80);
        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // ��ŷ�� ���
        printRanking();
        this.add(replay);
        this.add(Exit);
    }
    
    @Override
    public void printRanking() {				// ��ŷ�� �ۼ��ϴ� �Լ�
        printRankingTitle();
        printActualRanking();
    }
    
    @Override
    public void printRankingTitle() {				// ��ŷ â ������ �з����� ǥ�� -> Rank		Name	Score
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
    public void printActualRanking() {			// ���� ����ǥ�� �з��� ���� ���
        try (
                FileReader fr = new FileReader("ranking.txt");		// ����ǥ�� ����� ���Ͽ��� �����͸� �޾ƿ´�.
                BufferedReader br = new BufferedReader(fr);			//
        ) {															//
            String readLine = null;									//
            while ((readLine = br.readLine()) != null) {			//
                ls.add(readLine);									//
            }														//
        } catch (IOException e) {									//
        }															//
        
        
        List<RankList> lsScore = new ArrayList<RankList>();			// ������ �����ϱ� ���� RankList���� List ����
        for (int i = 1; i <= ls.size() / 2; i++) {					// ������� ¦����°�� �̸�, Ȧ����°�� ������ �޾ƿ�
            String name = ls.get(2 * i - 2);
            int score = Integer.valueOf(ls.get(2 * i - 1));
        	lsScore.add(new RankList(name, score));
        }
        Collections.sort(lsScore); //����								// ���ؿ� ���� ����
        ls.clear();													// ls�� ����ش�.
        for(int i = lsScore.size() - 1; i >= 0 ; i--) {				// ������ �����͸� �ٽ� ls�� �־��ش�.
        	RankList tmp = lsScore.get(i);
        	ls.add(tmp.getName());
        	ls.add(Integer.toString(tmp.getScore()));
        }
        
        int rank = 1;
        for (int i = 1; i <= lsScore.size(); i++) { 				// ����ǥ ����
            callAllGen(i, rank++);
        }
    }
    
    @Override
    public void callAllGen(int x, int rank) {			// ��ŷ�� �ۼ��ϴ� �Լ�
        genName(2 * x - 2, rank);						// �̸�
        genScore(2 * x - 1, rank);						// ����
        genRank(Integer.toString(rank), rank);			// ����
    }
    
    @Override
    public void genRank(String number, int rank) {		// ��ŷâ�� ���� ǥ��
        labelRank = new JLabel(number);
        labelRank.setBounds(X_RANK, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelRank);
    }
    
    @Override
    public void genName(int index, int rank) {			// ��ŷâ�� �̸� ǥ��
        labelName = new JLabel((String) ls.get(index));
        labelName.setBounds(X_NAME, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelName);
    }
    
    @Override
    public void genScore(int index, int rank) {			// ��ŷâ�� ���� ǥ��
        labelScore = new JLabel((String) ls.get(index));
        labelScore.setBounds(X_SCORE, Y + 25 * rank, WIDTH_LABEL, HEIGHT_LABEL);
        add(labelScore);
    }
    
    @Override
    public void reset() {
        this.setVisible(false);	// â �Ⱥ��̰�
        this.dispose(); 		// �ش������Ӹ�����
        new GameFrame(); 		// ������
    }

    @Override
    public void actionPerformed(ActionEvent e) {		// �ٽ��ϱ� ��ư�� ������ reset
        reset();
    }
}