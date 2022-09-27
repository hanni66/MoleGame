import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class moleGame extends JFrame implements ActionListener, Runnable {

	// 변수 선언 
	ImageIcon image = new ImageIcon("images/mole.png");
	private JButton jbt[] = new JButton[12];
	private JButton start = new JButton("시작");
	private JButton end = new JButton("종료");
	private JLabel jlb = new JLabel("점수 : 0");
	private JLabel time_jlb = new JLabel("10초안에 두더지 15마리 이상을 잡으면 성공!");
	// Layout
	private BorderLayout bl = new BorderLayout(10,10);
	private GridLayout gl1 = new GridLayout(3,4);
	private GridLayout gl2 = new GridLayout(1,2);
	private FlowLayout fl = new FlowLayout(FlowLayout.RIGHT);
	// Panel
	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();

	private int randomsu = 0;
	private int count = -1;


	public moleGame(String title){
		super(title);
		this.init();
		this.start();
		this.setSize(800,600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int)(screen.getWidth()/2 - super.getWidth()/2);
		int ypos = (int)(screen.getHeight()/2 - super.getHeight()/2);
		this.setLocation(xpos, ypos);	// 모니터 가운데 배치 
		this.setResizable(false);	// 크기 조정 비활성화 
		this.setVisible(true);
	}

	// 초기화 
	public void init(){
		Container c = this.getContentPane();
		c.setLayout(bl);
		c.add("North", time_jlb);
		c.add("Center", jp1);
		jp1.setLayout(gl1);	
		for(int i=0; i<12; ++i){
			jbt[i] = new JButton();		// 버튼 생성하기 
			jp1.add(jbt[i]);			// Panel에 버튼 추가 
		}

		// 두더지 버튼 비활성화 시키기 
		off_button();
		c.add("South", jp2);
		jp2.setLayout(gl2);
		jp2.add(this.jlb);

		jp2.add(jp3);
		jp3.setLayout(fl);
		jp3.add(start);
		jp3.add(end);

	}

	// 게임 start
	public void start(){

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		start.addActionListener(this);
		end.addActionListener(this);

		for(int i=0; i<12; ++i){
			jbt[i].addActionListener(this);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == start){		// 게임 시작하기 
			time_jlb.setText("시간 => 0:10");
			jlb.setText("점수 : 0");
			count=-1;
			Thread th = new Thread(this);
			th.start();
			on_button();
			random(0);

		}else if (e.getSource() == end){	// 게임 끝내기 
			System.exit(0);
		}

		for(int i=0; i<12; ++i){			// 두더지 배열
			if (e.getSource() == jbt[i]){
				random(i);		// 랜덤으로 나타내기 
			}
		}
	} 

	// 두더지 버튼 활성화 시키기 -> 시작버튼 누르면 활성화 됨.
	public void on_button(){
		for(int i=0; i<12; ++i){
			jbt[i].setEnabled(true);
		}
	}

	// 두더지 버튼 비활성화 시키기 
	public void off_button(){
		for(int i=0; i<12; ++i){
			jbt[i].setEnabled(false);
		}
	}

	// 게임이 시작 될 때 
	public void run() {
		int time = 10;	// 10초로 지정 
		start.setText("게임중");
		while(true){
			try{
				Thread.sleep(1000);
			}catch(InterruptedException e){}
			time--;		// 10초 타이머 
			// 10초가 끝나면 text 출력 후 두더지 버튼 비활성화
			if (time == 0) {	
				time_jlb.setText("게임이 끝났습니다.");
				if (count >= 15) {
					jlb.setText("점수 : " + count + " => 성공!");
				} else {
					jlb.setText("점수 : " + count + " => 실패!");
				}
				start.setText("다시시작");
				off_button();
				break;
			}
			time_jlb.setText("시간 => 0:0"+time);		// 시간 보여주기 
		}
	}

	// 두더지 버튼 랜덤 설정 
	public void random(int i){

		if (i != randomsu) return;
		count++;
		jbt[randomsu].setIcon(null);
		randomsu = (int)(Math.random() * 12);
		jbt[randomsu].setIcon(image);
		jlb.setText("점수 : " + count);
	}

	public static void main(String[] args) {
		moleGame game = new moleGame("두더지를 잡아라!");
	}

}

