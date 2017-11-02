/* 
 * 작성자 : 3조(김도형, 장연정, 성기진)
 * 최초 작성일 : 2017.09.28
 * 최종 작성일 : 2017.10.21
 * 목적 : 도둑키우기 프로그램
 * 개정 이력 : 김도형, 장연정, 성기진 / 2017.09.28(초기작성)
 *          김도형 / 2017.10.03(배열추가하여 코드 간략화)
 *          김도형 / 2017.10.13(보석금기능 추가)
 *          장연정 / 2017.10.14(로또게임추가)
 *          김도형 / 2017.10.21(최종 주석 수정)
 */

// util이라는 패키지에 있는 모든 클래스의 기능을 사용한다
import java.util.*;

public class Pet3_2 {
	
	// 필드 설정
	String name;              // 이름 저장하기 위한 String 필드
	long money;               // 물건을 훔치면 그에 따른 돈이 누적됨. 돈은 어른때 보석금으로 사용가능
	int skilLevel=10;         // 현재 숙련도 초기값 10 지정 물건을 훔칠때마다 계속 증가
	byte highMenuSelect=0;    // 고등학생 때 사용자가 메뉴 선택시 그 번호 저장
	byte adultMenuSelect=0;   // 성인 때 사용자가 메뉴 선택시 그 번호 저장
	byte studentCount=0;      // 교무실 끌려간 횟수 카운트 3회 이상 시 퇴학, 퇴학 시 게임끝(퇴학 결말)
	int prison;               // 어른때 훔치기에 실패하면 그 물건에 따른 징역살이를 하는데 16년 이상이되면 게임끝(무기징역 결말)
	byte closePoint=0;        // 종료 메뉴 선택 저장 closePoint가 1이되면 게임종료
	double rate;              // (훔칠물건/현재숙련도) 계산하여 그 값을 통해 성공확률 정해짐
	byte randomByte;          // 랜덤 값을 하나 추출 성공확률 정하는 곳에 사용
	byte result;              // 랜덤 수에 대한 결과값 저장
	
	
	// 고등학교에서의 필요한 값들을 배열로 저장하여 코드 간략화
	short[] hignPoint = {10, 25, 40, 85, 125, 185, 200, 700};    // 훔칠물건의 필요 숙련도
	short[] skilLevelPoint = {10, 10, 15, 20, 30, 45, 50, 200};  // 훔쳤을 시 증가할 숙련도
	byte[] menuCount = {1, 2, 3, 2, 2, 3, 2, 1};                 // 훔칠 수 있는 횟수 지정
	int[] highExchange = {30000, 100000, 50000, 100000, 200000, 300000, 400000, 0}; // 훔치기에 성공했을 때 획득금액
	// String 배열에 저장해놓고 나중에 나중에 성공하였을 때 사용하여 코드를 간편하게 하기위해 사용함
	String[] menuName = {"체육복", "학원비", "지갑", "MP3", "명품신발", "스마트폰", "아이패드", "시험지"}; 
	
	
	Scanner scan = new Scanner(System.in); // Scanner 객체 생성 / 사용자가 선택하는 값을 받기 위해 필요
	Pet3Image image = new Pet3Image();     // Pet3Image 객체 생성 / 훔쳤을 때 그림을 표련해주기 위해 필요
	
	
	// 전체 진행 메소드 Pet3Test에서 game 메소드를 호출하여 실행한다.
	public void game(){
		nameSet();         // nameSet호출 -> 이름입력 받기 위함
		highMenuAction();  // 사용자의 선택번호에 따라 고등학교에서의 행위가 실행되는 메소드  호출     
		
		// menuCount란 고등학교에서의 물건에 대한 훔칠 수 있는 횟수인데 그 배열의 7번 인덱스(시험지)가 0 이되면 고등학교에서 성인으로 넘어간다
		if(menuCount[7] == 0) {
			adult();       // 성인이 됨을 출력하는 메소드 호출
			adultAction(); // 사용자의 선택번호에 따라 성인에서의 행위가 실행되는 메소드 호출
		}
	} 
	
	
	// 이름 설정 메소드
	public void nameSet() {
		System.out.println(" -- 도둑 키우기 게임 -- ");
		System.out.print("키울려는 도둑의 이름을 정해주세요 : ");
		name = scan.next();
		
		// 이름 설정하고 게임배경을 간략하게 설명해준다
		System.out.println("\n" + name + "도둑 만들기를 시작합니다.\n\n"
				+ name + "의 성장 스토리\n"
				+ "아버지가 유명한 괴도라 어려서부터 아버지가 도둑질을 하는 것을 많이 보며 자라왔다.\n"
				+ "어려서부터 물건을 조금씩 훔치다보니 자신에게도 \n아버지의 대를 이을 훌륭한 도둑이 될 것을 직감한 이 아이는\n"
				+ "걷잡을 수 없는 도둑질을 해나가기 시작하는데...\n"
				+ "---------------------------------------------------------\n\n");
	}
	
	
	/*
	 * 메뉴 선택 메소드
	 * 사용자로부터 메뉴를 선택받는다
	 * []괄호 안에는 훔칠 수 있는 횟수가 나타난다
	 */
	public void highSchoolMenu() {
		System.out.println("훔칠 물건을 선택하세요\n"
				+ "--------------------------------------------------------------------\n"
				+ "1.체육복[" + menuCount[0] + "]   2.학원비[" + menuCount[1] + "]    3.지갑[" + menuCount[2] + "]   4.MP3[" + menuCount[3] + "]   5.명품신발[" + menuCount[4] + "]\n"
				+ "6.스마트폰[" + menuCount[5] + "]  7.아이패드[" + menuCount[6] + "]  8.시험지[" + menuCount[7] + "]  9.로또게임     10.게임룰       11.게임종료      \n"
				+ "--------------------------------------------------------------------");
		System.out.print("선택 >> ");
		highMenuSelect = scan.nextByte();
		System.out.println("\n");
	} 
	
	
	/*
	 * 고등학교에서의 메뉴 선택에 따른 행동들
	 * 사용자로부터 선택 받은 숫자에 따라 switch-case문을 사용하여 입력 번호에 메소드를 호출한다
	 * 1~7번까지는 highschoolPrint 메소드를 지정, 그 외에는 각각의 메소드를 따로 지정
	 */
	public void highMenuAction() {
		System.out.println("현재는 고등학생입니다. 고등학생 신분에 맞게 물건을 훔쳐보세요\n");
		while(true) {
			highSchoolMenu();
			switch(highMenuSelect) {
				case 1 : 
				case 2 : 
				case 3 : 
				case 4 : 
				case 5 : 
				case 6 : 
				case 7 : highschoolPrint(); break;
				case 8 : testPage(); break;
				case 9 : lotto(); break;
				case 10 : gameRule(); break;
				case 11 : System.out.println("도둑키우기 게임을 종료합니다 - GOOD BYE -"); closePoint=1; break;
				default : System.out.println("올바른 값을 입력하세요");
			}
			
			// closePoint는 사용자가 종료를 선택하였을 때 1이 되고 그 값을 판단하여 while 반복문을 끝냄으로써 전체 프로그램이 종료 
			if(closePoint==1) break;
			
			// 교무실에 끌려간횟수(studentCount)가 3회이상이면 퇴학결말 메소드 호출 후 전체 프로그램 종료
			if(studentCount>=3) {schoolOut(); break;}
			
			// 시험지를 훔치면 고등학교가 끝나고 성인으로 넘어간다(menuCount의 7번 인덱스는 시험지 훔칠 수 있는 횟수이다)
			if(menuCount[7]<=0) break;
		}
	} // end of highMenuAction


	/* 
	 * 메뉴선택에 따라 성공과 실패를 출력하기 위한 메소드
	 * 성공하면 돈과 숙련도가 증가하며 실패하면 교무실에 끌려간다
	 * 모든 것이 사용자가 선택한 번호(highMenuSelect)에 따라 값이 변화되는것을 볼 수 있다
	 */
	public void highschoolPrint() {
		
		// 훔칠물건의 필요 숙련도 / 현재 숙련도를 계산하여 rate에 저장
		rate = (double)hignPoint[highMenuSelect-1]/skilLevel;
		
		// rate값에 따라 random메소드에서 성공과 실패가 정해진다
		random(rate);
		
		// 고등학교 때의 사용자가 선택한 훔칠 물건의 훔칠 수 있는 횟수가 남아있는가를 판별하는 if문
		if(menuCount[highMenuSelect-1] > 0) {
			
			// 훔칠 수 있는 횟수가 1이상이고 위의 random메소드에 반환된 result 값이 1이라면 성공 그렇지 않은 경우 실패
			if(result == 1) {
				System.out.println(menuName[highMenuSelect-1] + " 훔치기에 성공하였습니다!!");
				System.out.println(highExchange[highMenuSelect-1] + "원을 획득하였습니다!!\n");
				image.stringCheck(menuName[highMenuSelect-1]);  // 사용자가 선택한 메뉴에 해당하는 그림이 Pet3image클래스의 stringCheck메소드를 통해 출력된다
				skilLevel += skilLevelPoint[highMenuSelect-1];  // 현재 숙련도에 훔치기에 성공하면 주어지는 숙련도가 누적된다
				money += highExchange[highMenuSelect-1];        // 돈 또한 훔치기에 성공하면 주어지는 돈이 누적된다
				studentCheck();  // 현재 숙련도와 현재 돈을 나타내는 studentCheck 메소드를 호출
				
			} else {
				System.out.println(menuName[highMenuSelect-1]+" 훔치기에 실패하였습니다... 교무실에 끌려갑니다...\n");
				// 교무실에 3번끌려가면 게임이 종료되므로 실패시 studentCount를 1증가시킴
				studentCount += 1;
			}
			menuCount[highMenuSelect-1] -= 1;  // 실패하더라도 그 메뉴의 훔칠 수 있는 횟수는 1회 감소한다(성인때는 성공했을 때만 감소하도록 설정)

		// 훔칠 수 있는 횟수가 남아있지 않을 때 출력문 후 종료
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // end of highschoolPrint
	
	
	/*
	 * highschoolPrint와 동일하나 마지막 시험지 훔치기에서는 현재 숙련도를 기준으로 시도 자체가 가능한지 불가능 한지 판별
	 */
	public void testPage() {
		rate = (double)hignPoint[7]/skilLevel;
		random(rate);
		
		// 현재 숙련도가 350 이상이면 시도 가능 그렇지 않을 경우 시도조차 못함
		if(skilLevel >= 350) {
			if(result == 1) {
				System.out.println("시험지 훔치기에 성공하였습니다!!");
				image.testPage();
				skilLevel += skilLevelPoint[7];
				studentCheck();
				menuCount[7] -= 1;
			} else {
				System.out.println("시험지 훔치기에 실패하였습니다... 교무실에 끌려갑니다...\n");
				studentCount += 1;
			}
			
		// 350미만 일 경우 출력문	
		} else {
			System.out.println("아직 충분한 숙련도가 되지 않았습니다. 좀 더 경험을 쌓고 오세요!!\n");
		}
	} 
	
	
	/*
	 * 로또 게임 메소드
	 * 사용자로부터 1~10까지 3개를 입력받고 컴퓨터가 랜덤수를 3개 뽑아 동일한 숫자의 개수를 비교하여 당첨금을 지급한다
	 */
	public void lotto() {
		
		// 로또게임에서만 사용할 변수 지정
		byte[] userInputNumber = new byte[3];       // 사용자 입력 넘버의 배열
		byte[] computerrandomNumber = new byte[3];  // 컴퓨터 랜덤 넘버의 배열
		long bet;                                   // 사용자 배팅금액 입력
		byte randomNumber;                          // 컴퓨터 랜덤 추출
		byte count=0; 								// 몇개가 일치하는지 저장
		
		// 로또게임 메소드 실행되었을 때 처음에 룰 설명해주는 출력문
		System.out.println("--------------- Lotto게임 ------------------\n\n"
				+ "게임방식 : 숫자 1 ~ 10까지 중복없이 3자리를 입력합니다.\n"
				+ "컴퓨터도 랜덤으로 숫자 3개를 뽑습니다 \n"
				+ "1개가 일치하는 경우 : 배팅금액 * 2배\n"
				+ "2개가 일치하는 경우 : 배팅금액 * 4배\n"
				+ "3개가 일치하는 경우 : 배팅금액 * 20배\n"
				+ "일치하는 숫자가 없을 경우 : - 배팅금액\n\n"
				+ "-------------------------------------------\n\n");
		
		// 배팅금액을 입력받는데 소지금보다 배팅금액보다 많으면 다시 입력받기위한 반복문
		while(true) {
			System.out.print("현재 나의 돈 : " + money + "\n");
			System.out.print("배팅 금액 입력 >> ");
			bet = scan.nextLong();
			
			// 배팅금액이 소지금보다 많으면 계속 반복 시키고 그렇지 않을 경우 break로 빠져나옴
			if(bet>money) {
				System.out.println("배팅금액이 소지금액보다 많습니다. 다시 입력하세요!!\n");
			} else break;
		}		
		
		// 사용자에게 숫자를 입력받기 위한 for문
		for(int i=0; i<3; i++) {
			System.out.print((i+1) + "번째 로또 수 입력 : ");
			userInputNumber[i] = scan.nextByte();
			
			// 사용자가 10을 초과한 숫자를 입력시 재입력 받기 위한 if문
			if(userInputNumber[i] > 10) {
				System.out.println("※ 1~10까지 숫자만 입력하시오 ※\n");
				i--;
				continue;
			}
			
			// 사용자가 같은 수를 입력했을 때 재입력 받기 위한 if문
			for(int j=0; j<i; j++) {
				if(userInputNumber[i] == userInputNumber[j]) {
					System.out.println("※ 중복금지! 다시 입력하시오 ※\n");
					i--;
					break;
				}
			}
		}
		
		// 사용자가 입력한 수 출력
		System.out.print("사용자가 입력한 수 : ");
		for(int i=0; i<3; i++) {
			System.out.print(userInputNumber[i] + " ");
		}
		System.out.println("");
		
		// 컴퓨터가 로또번호 랜덤으로 뽑기
		for(int i=0; i<3; i++) {
			randomNumber = (byte)(Math.random()*10+1);  // Math.random()*10+1을 해주면 1~10까지 중 정수가 뽑힌다
			computerrandomNumber[i] = randomNumber;     // 컴퓨터 랜덤 배열에 랜덤수를 대입한다
			
			// 이중 for문을 이용하여 중복 숫자 방지
			for(int j=0; j<i; j++) {
				if(computerrandomNumber[i] == computerrandomNumber[j]) {
					i--;
					break;
				}
			}
		}
		
		// 로또번호 출력
		System.out.print("로또 번호 : ");
		for(int i=0; i<3; i++) {
			System.out.print(computerrandomNumber[i] + " ");
		}
		System.out.println("");
		
		// 사용자가 입력한 수와 컴퓨터의 랜덤 수 비교하여 count에 저장
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				
				// 같은 숫자가 있으면 count는 1 더해진다
				if(userInputNumber[i] == computerrandomNumber[j]) count++;
			}
		}
		
		// count를 통한 결과 출력
		if(count == 1) {
			System.out.println("축하합니다 !! 한자리를 맞췄습니다!");
			System.out.println("당첨금 : " + bet*2 + "원\n");
			money = money + bet;
		} else if(count == 2) {
			System.out.println("축하합니다 !! 두자리를 맞췄습니다!!!");
			System.out.println("당첨금 : " + bet*5 + "원\n");
			money = money + bet*4;
		} else if(count == 3) {
			System.out.println("축하합니다 !! 세자리를 맞췄습니다!!!!!!!");
			System.out.println("당첨금 : " + bet*20 + "원\n");
			money = money + bet*19;
		} else {
			System.out.println("맞는 숫자가 하나도 없습니다... 아쉽네요 다시 도전해 보세요 !");
			System.out.println("배팅금액을 전부 잃었습니다...   -" + bet + "원\n");
			money = money - bet;
		}
		studentCheck();
	} // end of lotto
	
	
	
	 // 게임룰 선택시 게임 룰 출력해주는 메소드
	public void gameRule() {
		System.out.println("게임룰을 설명하겠습니다\n"
				+ "물건을 훔칠 때마다 숙련도가 바뀌며  숙련도에 따라 선택 항목들이 바뀌어 나갑니다.\n"
				+ "현재 숙련도에 맞지 않게 높은 숙련도를 필요로하는 물건을 훔치려고 할 때에는\n"
				+ "실패를 할 수도 있으며 실패 시는 상황에 따른 패널티가 부과됩니다.\n"
				+ "잘 선택하여 아버지의 대를 잇는 괴도를 만들어 보세요.\n"
				);
	}
	
	
	/*
	 * 숙련도 차이에 대한 실패와 성공 결정하는 확률 메소드
	 * random 메소드는 return값을 가진다
	 * random 메소드를 호출할 때 (훔칠 물건의 숙련도 / 현재 숙련도)의 값을 매개변수로 준다.
	 * 예를 들어 처음에 체육복을 훔칠때 체육복의 숙련도는 10이고 현재 숙련도는 10이다 => (10/10)의 값 1.0을 매개값으로 준다
	 * 아래에서 x가 1.0이 된다 if문에 따라 temp에는 10이 대입된다
	 * randombyte에는 1~10까지의 숫자 중 랜덤으로 대입이 될 것이고 그 수는 temp인 10보다 무조건 작거나 같게된다
	 * 그러므로 확률이 100%가 되는것이고 result에 1을 대입한다 result가 1은 성공 2는 실패이다
	 * 마지막으로 호출했던 곳으로 result를 return 시켜 주고 호출한 곳은 result값으로 성공과 실패를 출력하는 것을 볼 수 있다 
	 */
	public byte random(double x) {
		byte temp = 0; // 비교값 저장
		randomByte = (byte)(Math.random()*10+1); // 1~10까지 하나의 숫자가 랜덤하게 대입된다
		
		if(x<=1) temp = 10;
		else if(x<=1.2) temp = 9;
		else if(x<=1.3) temp = 8;
		else if(x<=1.4) temp = 7;
		else if(x<=1.5) temp = 6;
		else if(x<=1.6) temp = 5;
		else if(x<=1.7) temp = 4;
		else if(x<=1.8) temp = 3;
		else if(x<=1.9) temp = 2;
		else temp = 1;
				
		if(randomByte<=temp) result = 1;
		else result = 2;
		
		return result;
	} 
	
	
	// 퇴학 결말 메소드
	public void schoolOut() {
		System.out.println("3번이나 실패 해 학교에서 퇴학 처리가 되었습니다...\n"
				+ name + "에겐 괴도가 될 자격이 없는 것 같습니다 다시 도전해 보세요...\n\n\n"
				+ " - GAME OVER - "
				);
	} 
	
	
	// 현재 상황 출력 메소드
	public void studentCheck() {
		System.out.println("현재 숙련도 : " + skilLevel + "  현재 나의 돈 : " + money + "원\n");
	}
	
	
	
	
// -----------------------------------아래 성인때의 메소드 -----------------------------------------	
	
	
	// 성인에서의 훔칠 물건 숙련도, 훔쳤을 때의 증가 숙련도, 훔칠 수 있는 횟수, 징역, 훔칠 물건 스트링 배열
	short[] adultPoint = {600, 1000, 1300, 1700, 2200, 2600, 4000, 6000, 7000, 11000}; // 훔칠물건의 필요 숙련도
	short[] skilLevelPoint2 = {10, 40, 100, 100, 200, 300, 300, 500, 1000, 3000};      // 훔쳤을 시 증가할 숙련도
	byte [] menuCount2 = {9, 3, 3, 3, 2, 2, 5, 1, 1, 1};	                           // 훔칠 수 있는 횟수 지정
	int[] prisonYear = {1, 1, 1, 2, 3, 4, 5, 6, 8, 13};                                // 실패할 때 징역(단위:년)
	int[] adultExchange = {100000, 500000, 1500000, 1000000, 5000000, 10000000, 3000000, 100000000, 1000000000, 2000000000}; // 훔치기에 성공했을 때 획득금액
	String[] menuName2 = {"지갑", "노트북", "명품가방", "신용카드", "오토바이", "자동차", "집털기", "금은방털기", "은행털기", "모나리자"};     // 훔칠물건 스트링 배열
	
	
	// 성인 되고 나서 처음 알려주는 멘트 메소드
	public void adult() {
		System.out.println("---------------------------------------------------------\n"
				+ "고등학교를 졸업하고 이제 성인 되었습니다. \n"
				+ "성인 때는 실패 시 징역살이를 합니다. \n"
				+ "무기 징역이 되지않게 주의하여 최종 목적인 모나리자까지 훔쳐보세요!!\n"
				+ "---------------------------------------------------------\n");
	}
	
	
	// 성인 메뉴 선택 메소드
	public void adultMenu() {
		System.out.println("훔칠 물건을 선택하세요\n"
				+ "------------------------------------------------------------------------------\n"
				+ "1.지갑[" + menuCount2[0] + "]   2.노트북[" + menuCount2[1] + "]     "
				+ "3.명품가방[" + menuCount2[2] + "]   4.신용카드[" + menuCount2[3] + "]   "
				+ "5.오토바이[" + menuCount2[4] + "]    " + "6.자동차[" + menuCount2[5] + "]\n"
				+ "7.집털기[" + menuCount2[6] + "]  8.금은방털기[" + menuCount2[7] + "]  "
				+ "9.은행털기[" + menuCount2[8] + "]  10.모나리자[" + menuCount2[9] + "]   "
				+ "11.로또게임          12.게임종료\n"
				+ "------------------------------------------------------------------------------");
		System.out.print("선택 >> ");
		adultMenuSelect = scan.nextByte();
		System.out.println("\n");
	}
	
	
	// 성인에서의 메뉴 선택에 따른 행동들
	public void adultAction() {
		while(true) {
			adultMenu();
			switch(adultMenuSelect) {
				case 1 : 
				case 2 : 
				case 3 : 
				case 4 : 
				case 5 : 
				case 6 : 
				case 7 : 
				case 8 : 
				case 9 : 
				case 10 : adultPrint(); break;
				case 11 : lotto(); break;
				case 12 : System.out.println("도둑키우기 게임을 종료합니다 - GOOD BYE -"); closePoint=1; break;
				default : System.out.println("올바른 값을 입력하세요");
			}
			
			// closePoint는 사용자가 종료를 선택하였을 때 1이 되고 그 값을 판단하여 while 반복문을 끝냄으로써 전체 프로그램이 종료 
			if(closePoint==1) break;
			
			// 누적징역이 16년 이상이면 prisonOut(무기징역 결말) 메소드를 호출하고 게임 전체 게임 종료
			if(prison>=16) {prisonOut(); break;}
			
			// 모나리자를 훔치면 훔칠물건의 횟수 배열의 9번 인덱스가 0이되고 monarisaEnding 메소드를 호출하고 최종 게임이 끝난다
			if(menuCount2[9] == 0) {monarisaEnding(); break;}
		}
	}
	

	/* 
	 * 메뉴선택에 따라 성공과 실패를 출력하기 위한 메소드
	 * 성공하면 돈과 숙련도가 증가하며 실패하면 징역살이를 한다
	 * 모든 것이 사용자가 선택한 번호(adultMenuSelect)에 따라 값이 변화되는것을 볼 수 있다
	 */
	public void adultPrint() {
		
		// 훔칠물건의 필요 숙련도 / 현재 숙련도를 계산하여 rate에 저장
		rate = (double)adultPoint[adultMenuSelect-1]/skilLevel;
		
		// rate값에 따라 random메소드에서 성공과 실패가 정해진다
		random(rate);
		
		// 성인 때의 사용자가 선택한 훔칠 물건의 훔칠 수 있는 횟수가 남아있는가를 판별하는 if문
		if(menuCount2[adultMenuSelect-1] > 0) {
			
			// 훔칠 수 있는 횟수가 1이상이고 위의 random메소드에 반환된 result 값이 1이라면 성공 그렇지 않은 경우 실패
			if(result == 1) {
				System.out.println(menuName2[adultMenuSelect-1] + " 훔치기에 성공하였습니다!!");
				image.stringCheck(menuName2[adultMenuSelect-1]);
				skilLevel += skilLevelPoint2[adultMenuSelect-1];
				menuCount2[adultMenuSelect-1] -= 1;
				money += adultExchange[adultMenuSelect-1];
				
			} else {
				System.out.println(menuName2[adultMenuSelect-1]+" 훔치기에 실패하여 징역 " + prisonYear[adultMenuSelect-1] +"년이 선고되었습니다...\n\n");
				bail();  // 징역이 선고되면 보석금으로 면할 수 있도록 해주기 위한 메소드 호출
			}
			adultCheck();  // 끝으로 현재 숙련도와 돈과 징역을 나타내주는 메소드 호출
			
		// 훔칠 수 있는 횟수가 남아있지 않을 때 출력문 후 종료	
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // end of adultPrint
	
	
	// 보석금으로 징역을 면할 수 있는 메소드 보석금이 영어로 bail임
	public void bail() {
		byte bailSelect; // 사용자의 징역을 살 것인지 아니면 보석금을 낼 것인지 선택한 것이 저장되는 변수
		
		// bail이라는 메소드가 호출되면 처음에 설명해주기 위한 출력문
		System.out.println("보석금으로 징역을 면할 수도 있습니다. 많은 돈이 필요하며 징역에 따라 보석금은 달라집니다.\n"
				+ "징역 " + prisonYear[adultMenuSelect-1] + "년의 보석금은 " + (prisonYear[adultMenuSelect-1] * 5) + "백만원 입니다\n"
				+ "보석금을 지불하시겠습니까 ?? 1.예   2.아니오\n");
		
		// 반복문을 사용한 이유는 사용자가 1번 2번 외에 다른 키를 입력할 수 도있기 때문에 그럴 경우 제대로 입력받을 때 까지 계속 반복하기 위함
		while(true) {
			System.out.print("선택 >> ");
			bailSelect = scan.nextByte();
			
			// 선택을 받고 두줄을 띄워줌
			System.out.println("\n");
			
			// 예 아니오에 따라 징역을 살던가 보석금을 주고 풀려나던가 하게 된다
			if(bailSelect == 1) {
				
				// 예를 선택하여도 현재 소유금이 보석금보다 부족 할 경우 징역살이를 하게 된다
				// 그래서 예를 선택할 때 다시 if와 else로 나누어 결과를 다르게 출력한다 보석금은 징역 1년당 5백만원이다
				if(money < (prisonYear[adultMenuSelect-1] * 5000000)) {
					System.out.println("현재 보유금이 부족합니다 ... 징역살이를 합니다...");
					prison += prisonYear[adultMenuSelect-1];  // 징역이 누적된다
					break;
					
				} else {
					System.out.println((prisonYear[adultMenuSelect-1] * 5) + "백만원을 지불하고 징역을 면했습니다. ");
					money = money - (prisonYear[adultMenuSelect-1] * 5000000);  // 지불하였으니 그만큼 돈을 차감한다
					break;
				}
			
			// 아니오를 선택하면 징역을 살겠다는 말
			} else if(bailSelect == 2) {
				System.out.println("교도소에서 징역살이를 합니다...");
				prison += prisonYear[adultMenuSelect-1];  // 징역이 누적된다
				break;
			
			// 사용자가 1번 2번 외에 선택하였을 경우도 있으니 그럴 경우 알림을 출력하고 다시 입력받는다
			} else {
				System.out.println("예 or 아니오 만 선택해주세요 !!");
			}
		}
	} // end of bail
	
	
	// 무기징역 결말 메소드
	public void prisonOut() {
		System.out.println("무기 징역이 선고되었습니다...\n"
				+ name + "에겐 괴도가 될 자격이 없는 것 같습니다 다시 도전해 보세요...\n\n\n"
				+ " - GAME OVER - "
				);
	} 
	
	
	// 최종 엔딩인 모나리자엔딩 메소드
	public void monarisaEnding(){
		System.out.println("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※\n"
				+ "드디어 마지막인 모나리자 훔치기에 성공하였습니다 !!\n"
				+ name + "은(는) 이제 완벽한 도둑놈이 되었으며 !!\n"
				+ "그 후 은퇴하여 모은 돈으로  행복하게 살았답니다! \n"
				+ "※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※\n\n"
				+ " - THE END - "
				);
	}
	
	
	// 현재 상황 출력 메소드
	public void adultCheck() {
		System.out.println("현재 숙련도 : " + skilLevel + "  현재 나의 돈 : " + money + "원" + "  누적 징역 : " + prison + "년\n");
	}
	
} // end of class
