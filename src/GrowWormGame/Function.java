package GrowWormGame;
//////////////////////////////
//////////////////////////////
////  함수 정리를 위한 인터페이스  ////
//////////////////////////////
//////////////////////////////

public interface Function {
	// GameFrame에 사용된 함수들
	public void run();					// 게임을 실행
	public void initUI();				// 시작 화면을 설정
	public void makeWorm();				// 지렁이 캐릭터를 생성
	public void startTimer();			// 타이머 시작
	public void makeFood();				// 지도에 먹이를 생성
	public void setKeyListner();		// 키 입력을 받을 함수
	public boolean checkCollision(int x, int y);	// 충돌 여부 확인 함수
	public void moveWorm();				// 지렁이를 움직이는 함수
	public void updateUI();				// 시간이 지남에 따라 화면을 갱신
	public void addTail();				// 먹이를 먹을 시 크기 증가
	public void GameOver();				// 게임오버
	public void finish();				// 결과창을 띄워줌
	public void initAll();				// 게임 재시작 시 초기화
}

interface RankingFunction {
	// Ranking에 사용된 함수들
	public void printRanking();			// 랭킹을 출력 하는 함수
    public void printRankingTitle();	// 랭킹의 맨 위 분류표를 출력
    public void printActualRanking();	// 실질적 순위표 출력
    public void callAllGen(int x, int rank);		// 순위 이름 점수를 분류에 맞춰 출력
    public void genRank(String number, int rank);	// 순위 출력
    public void genName(int index, int rank);		// 이름 출력
    public void genScore(int index, int rank);		// 점수 출력
    public void reset();							// 다시하기
}
