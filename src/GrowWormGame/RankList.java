package GrowWormGame;
// ��ŷ�� sort�ϱ� ���� �迭
public class RankList implements Comparable<RankList> {
	private int score;
	private String name;
	
	public RankList(String n, int s) {
		this.score = s;
		this.name = n;
	}
	
	public int getScore() {		// ���� ��ȯ
		return this.score;
	}
	
	public String getName() {	// �̸� ��ȯ
		return this.name;
	}

	@Override
	public int compareTo(RankList Rl) {		// ���� ������ ����
		// TODO Auto-generated method stub
		return this.score - Rl.score;
	}
}
