package GrowWormGame;
//////////////////////////////
//////////////////////////////
////  �Լ� ������ ���� �������̽�  ////
//////////////////////////////
//////////////////////////////

public interface Function {
	// GameFrame�� ���� �Լ���
	public void run();					// ������ ����
	public void initUI();				// ���� ȭ���� ����
	public void makeWorm();				// ������ ĳ���͸� ����
	public void startTimer();			// Ÿ�̸� ����
	public void makeFood();				// ������ ���̸� ����
	public void setKeyListner();		// Ű �Է��� ���� �Լ�
	public boolean checkCollision(int x, int y);	// �浹 ���� Ȯ�� �Լ�
	public void moveWorm();				// �����̸� �����̴� �Լ�
	public void updateUI();				// �ð��� ������ ���� ȭ���� ����
	public void addTail();				// ���̸� ���� �� ũ�� ����
	public void GameOver();				// ���ӿ���
	public void finish();				// ���â�� �����
	public void initAll();				// ���� ����� �� �ʱ�ȭ
}

interface RankingFunction {
	// Ranking�� ���� �Լ���
	public void printRanking();			// ��ŷ�� ��� �ϴ� �Լ�
    public void printRankingTitle();	// ��ŷ�� �� �� �з�ǥ�� ���
    public void printActualRanking();	// ������ ����ǥ ���
    public void callAllGen(int x, int rank);		// ���� �̸� ������ �з��� ���� ���
    public void genRank(String number, int rank);	// ���� ���
    public void genName(int index, int rank);		// �̸� ���
    public void genScore(int index, int rank);		// ���� ���
    public void reset();							// �ٽ��ϱ�
}
