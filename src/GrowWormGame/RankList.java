package GrowWormGame;
// 랭킹을 sort하기 위한 배열
public class RankList implements Comparable<RankList> {
	private int score;
	private String name;
	
	public RankList(String n, int s) {
		this.score = s;
		this.name = n;
	}
	
	public int getScore() {		// 점수 반환
		return this.score;
	}
	
	public String getName() {	// 이름 반환
		return this.name;
	}

	@Override
	public int compareTo(RankList Rl) {		// 점수 순으로 정렬
		// TODO Auto-generated method stub
		return this.score - Rl.score;
	}
}
