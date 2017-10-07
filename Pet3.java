/* 
 * 파일이름 : Pet3.java
 * 작성자 : 3조(김도형, 장연정, 성기진)
 * 작성일 : 
 * 프로그램 이름 : 도둑키우기
 * 작성이유 : 다마고치 게임의 전체 틀에서 특이한 키우기를 해보고 싶어서
 */
import java.util.*;

public class Pet3 {
	
	// 필드 설정
	String name;
	int skilLevel=10;         // 숙련도
	double prison=0;          // 징역
	byte highMenuSelect=0;    // 고등학생 때의 메뉴 선택 저장
	byte adultMenuSelect=0;   // 성인 때의 메뉴 선택 저장
	byte closePoint=0;        // 종료 메뉴 선택 저장
	double rate;              // 훔칠물건/숙련도 값 저장
	byte randomByte;          // 랜덤 수 저장 값(1~10)
	byte result;              // 랜덤 수에 대한 결과값 저장
	byte studentCount=0;      // 교무실 끌려간 횟수 카운트 3회 이상 시 퇴학, 퇴학 시 게임끝(퇴학 결말)
	byte timerCount=0;
	
	// 고등학교에서의 훔칠 물건 숙련도, 훔쳤을 때의 증가 숙련도, 훔칠 수 있는 횟수 지정 
	short[] hignPoint = {10, 25, 40, 85, 125, 185, 200, 700};    // 훔칠물건의 필요 숙련도
	short[] skilLevelPoint = {10, 10, 15, 20, 30, 45, 50, 200};  // 훔쳤을 시 증가할 숙련도
	byte[] menuCount = {1, 2, 3, 2, 2, 3, 2, 1};                 // 훔칠 수 있는 횟수 지정
	
	
	
	Scanner scan = new Scanner(System.in); // Scanner 객체 생성
	Pet3Image image = new Pet3Image();     // Pet3Image 객체 생성
	
	Timer timer1 = new Timer();
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			if(timerCount<3) {
				System.out.println("...");
				timerCount++;
			} else timer1.cancel();
		}
	};
	
	public void game(){
		nameSet();
		highSchool();
		highMenuAction();
		if(menuCount[7] == 0) {
			adult();
			adultAction();
		}
	} // 전체 진행 메소드
	
	public void nameSet() {
		System.out.println(" -- 도둑 키우기 게임 -- ");
		System.out.print("키울려는 도둑의 이름을 정해주세요 : ");
		name = scan.next();
		System.out.println("\n" + name + "도둑 만들기를 시작합니다.\n\n"
				+ name + "의 성장 스토리\n"
				+ "아버지가 유명한 괴도라 어려서부터 아버지가 도둑질을 하는 것을 많이 보며 자라왔다.\n"
				+ "어려서부터 물건을 조금씩 훔치다보니 자신에게도 \n아버지의 대를 이을 훌륭한 도둑이 될 것을 직감한 이 아이는\n"
				+ "걷잡을 수 없는 도둑질을 해나가기 시작하는데...\n"
				+ "---------------------------------------------------------\n\n");
	} // 이름 설정 메소드
	
	public void highSchool() {
		System.out.println("현재는 고등학생입니다. 고등학생 신분에 맞게 물건을 훔쳐보세요\n");
	} // 시작이 고등학생임을 알려주는 메소드
	
	public void highMenuAction() {
		while(true) {
			highSchoolMenu();
			switch(highMenuSelect) {
				case 1 : training(); break;
				case 2 : moneyMethod(); break;
				case 3 : wallet(); break;
				case 4 : mp3(); break;
				case 5 : sneakers(); break;
				case 6 : smartPhone(); break;
				case 7 : iPad(); break;
				case 8 : testPage(); break;
				case 9 : gameRule(); break;
				case 10 : System.out.println("도둑키우기 게임을 종료합니다 - GOOD BYE -"); closePoint=1; break;
				default : System.out.println("올바른 값을 입력하세요"); break;
			}
			if(closePoint==1) break;
			if(studentCount>=3) {schoolOut(); break;}
			if(menuCount[7]<=0) break;
		}
	} // 고등학교에서의 메뉴 선택에 따른 행동들
	
	public void highSchoolMenu() {
		System.out.println("훔칠 물건을 선택하세요\n"
				+ "---------------------------------------------------------\n"
				+ "1.체육복[" + menuCount[0] + "]   2.학원비[" + menuCount[1] + "]    3.지갑[" + menuCount[2] + "]   4.MP3[" + menuCount[3] + "]   5.명품신발[" + menuCount[4] + "]\n"
				+ "6.스마트폰[" + menuCount[5] + "]  7.아이패드[" + menuCount[6] + "]  8.시험지[" + menuCount[7] + "]  9.게임룰       10.게임종료      \n"
				+ "---------------------------------------------------------");
		System.out.print("선택 >> ");
		highMenuSelect = scan.nextByte();
		System.out.println("\n");
	} // 메뉴 선택 메소드
	
	public void training() {
		rate = (double)hignPoint[0]/skilLevel;
		random(rate);
		if(menuCount[0] > 0) {
			if(result == 1) {
				System.out.println("체육복 훔치기에 성공하였습니다!!");
				image.training();
				skilLevel += skilLevelPoint[0];
				studentCheck();
			} else {
				System.out.println("체육복 훔치기에 실패하였습니다... 교무실에 끌려갑니다...\n");
				studentCount += 1;
			}
			menuCount[0] -= 1;
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 체육복 훔치기 메소드
	
	public void moneyMethod() {
		rate = (double)hignPoint[1]/skilLevel;
		random(rate);
		if(menuCount[1] > 0) {
			if(result == 1) {
				System.out.println("학원비 훔치기에 성공하였습니다!!");
				image.moneyMethod();
				skilLevel += skilLevelPoint[1];
				studentCheck();
			} else {
				System.out.println("학원비 훔치기에 실패하였습니다... 교무실에 끌려갑니다...\n");
				studentCount += 1;
			}
			menuCount[1] -= 1;
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 돈훔치기 메소드
	
	public void wallet() {
		rate = (double)hignPoint[2]/skilLevel;
		random(rate);
		if(menuCount[2] > 0) {
			if(result == 1) {
				System.out.println("지갑 훔치기에 성공하였습니다!!");
				image.wallet();
				skilLevel += skilLevelPoint[2];
				studentCheck();
			} else {
				System.out.println("지갑 훔치기에 실패하였습니다... 교무실에 끌려갑니다...\n");
				studentCount += 1;
			}
			menuCount[2] -= 1;
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 지갑 훔치기 메소드
	
	public void mp3() {
		rate = (double)hignPoint[3]/skilLevel;
		random(rate);
		if(menuCount[3] > 0) {
			if(result == 1) {
				System.out.println("MP3 훔치기에 성공하였습니다!!");
				image.mp3();
				skilLevel += skilLevelPoint[3];
				studentCheck();
			} else {
				System.out.println("MP3 훔치기에 실패하였습니다... 교무실에 끌려갑니다...\n");
				studentCount += 1;
			}
			menuCount[3] -= 1;
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // mp3 훔치기 메소드
	
	public void sneakers() {
		rate = (double)hignPoint[4]/skilLevel;
		random(rate);
		if(menuCount[4] > 0) {
			if(result == 1) {
				System.out.println("명품신발 훔치기에 성공하였습니다!!");
				image.sneakers();
				skilLevel += skilLevelPoint[4];
				studentCheck();
			} else {
				System.out.println("명품신발 훔치기에 실패하였습니다... 교무실에 끌려갑니다...\n");
				studentCount += 1;
			}
			menuCount[4] -= 1;
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 명품신발 훔치기 메소드
	
	public void smartPhone() {
		rate = (double)hignPoint[5]/skilLevel;
		random(rate);
		if(menuCount[5] > 0) {
			if(result == 1) {
				System.out.println("스마트폰 훔치기에 성공하였습니다!!");
				image.smartPhone();
				skilLevel += skilLevelPoint[5];
				studentCheck();
			} else {
				System.out.println("스마트폰 훔치기에 실패하였습니다... 교무실에 끌려갑니다...\n");
				studentCount += 1;
			}
			menuCount[5] -= 1;
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 스마트폰 훔치기 메소드
	
	public void iPad() {
		rate = (double)hignPoint[6]/skilLevel;
		random(rate);
		if(menuCount[6] > 0) {
			if(result == 1) {
				System.out.println("아이패드 훔치기에 성공하였습니다!!");
				image.iPad();
				skilLevel += skilLevelPoint[6];
				studentCheck();
			} else {
				System.out.println("아이패드 훔치기에 실패하였습니다... 교무실에 끌려갑니다...\n");
				studentCount += 1;
			}
			menuCount[6] -= 1;
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 아이패드 훔치기 메소드
	
	public void testPage() {
		rate = (double)hignPoint[7]/skilLevel;
		random(rate);
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
		} else {
			System.out.println("아직 충분한 숙련도가 되지 않았습니다. 좀 더 경험을 쌓고 오세요!!\n");
		}
	} // 시험지 훔치기 메소드
	
	public void gameRule() {
		System.out.println("게임룰을 설명하겠습니다\n"
				+ "물건을 훔칠 때마다 숙련도가 바뀌며  숙련도에 따라 선택 항목들이 바뀌어 나갑니다.\n"
				+ "현재 숙련도에 맞지 않게 높은 숙련도를 필요로하는 물건을 훔치려고 할 때에는\n"
				+ "실패를 할 수도 있으며 실패 시는 상황에 따른 패널티가 부과됩니다.\n"
				+ "잘 선택하여 아버지의 대를 잇는 괴도를 만들어 보세요.\n"
				);
	} // 게임룰 선택시 게임 룰 출력해주는 메소드
	
	public byte random(double x) {
		byte temp = 0; // 비교값 저장
		randomByte = (byte)(Math.random()*10+1);
		
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
	} // 숙련도 차이에 대한 실패와 성공 결정하는 확률 메소드
	
	public void timer() {
		timer1.schedule(task, 500, 1000);
	} // 타이머 메소드
	
	public void schoolOut() {
		System.out.println("3번이나 실패 해 학교에서 퇴학 처리가 되었습니다...\n"
				+ name + "에겐 괴도가 될 자격이 없는 것 같습니다 다시 도전해 보세요...\n\n\n"
				+ " - GAME OVER - "
				);
	} // 퇴학 결말 메소드
	
	public void studentCheck() {
		System.out.println("현재 숙련도 : " + skilLevel+"\n");
	} // 현재 상황 출력 메소드
	
	
// -----------------------------------아래 성인때의 메소드 -----------------------------------------	
	
	
	// 성인에서의 훔칠 물건 숙련도, 훔쳤을 때의 증가 숙련도, 훔칠 수 있는 횟수 지정
	short[] adultPoint = {600, 750, 1000, 1500, 1800, 2500, 2000, 4000, 5000, 7000}; // 훔칠물건의 필요 숙련도
	short[] skilLevelPoint2 = {10, 40, 100, 100, 200, 300, 200, 500, 1000, 3000};    // 훔쳤을 시 증가할 숙련도
	byte [] menuCount2 = {9, 3, 3, 3, 2, 1, 5, 1, 1, 1};	                         // 훔칠 수 있는 횟수 지정
	
	public void adult() {
		System.out.println("---------------------------------------------------------\n"
				+ "고등학교를 졸업하고 이제 성인 되었습니다. \n"
				+ "성인 때는 실패 시 징역살이를 합니다. \n"
				+ "무기 징역이 되지않게 주의하여 최종 목적인 모나리자까지 훔쳐보세요!!\n"
				+ "---------------------------------------------------------\n");
	} // 성인 되고 나서 처음 알려주는 메소드
	
	public void adultAction() {
		while(true) {
			adultMenu();
			switch(adultMenuSelect) {
				case 1 : wallet2(); break;
				case 2 : notebook(); break;
				case 3 : bag(); break;
				case 4 : creditCard(); break;
				case 5 : motocycle(); break;
				case 6 : car(); break;
				case 7 : house(); break;
				case 8 : jewelleryShop(); break;
				case 9 : bank(); break;
				case 10 : monarisa(); break;
				case 11 : gameRule(); break;
				case 12 : System.out.println("도둑키우기 게임을 종료합니다 - GOOD BYE -"); closePoint=1; break;
				default : System.out.println("올바른 값을 입력하세요"); break;
			}
			if(closePoint==1) break;
			if(prison>=16) {prisonOut(); break;}
			if(menuCount2[9] == 0) {monarisaEnding(); break;}
		}
	} // 성인에서의 메뉴 선택에 따른 행동들
	
	public void adultMenu() {
		System.out.println("훔칠 물건을 선택하세요\n"
				+ "---------------------------------------------------------\n"
				+ "1.지갑[" + menuCount2[0] + "]   2.노트북[" + menuCount2[1] + "]     "
				+ "3.명품가방[" + menuCount2[2] + "]   4.신용카드[" + menuCount2[3] + "]   "
				+ "5.오토바이[" + menuCount2[4] + "]    " + "6.자동차[" + menuCount2[5] + "]\n"
				+ "7.집털기[" + menuCount2[6] + "]  8.금은방털기[" + menuCount2[7] + "]  "
				+ "9.은행털기[" + menuCount2[8] + "]  10.모나리자[" + menuCount2[9] + "]   "
				+ "11.게임룰             12.게임종료\n"
				+ "---------------------------------------------------------");
		System.out.print("선택 >> ");
		adultMenuSelect = scan.nextByte();
		System.out.println("\n");
	} // 성인 메뉴 선택 메소드
	
	public void wallet2() {
		rate = (double)adultPoint[0]/skilLevel;
		random(rate);
		if(menuCount2[0] > 0) {
			if(result == 1) {
				System.out.println("지갑 훔치기에 성공하였습니다!!");
				image.wallet();
				skilLevel += skilLevelPoint2[0];
				adultCheck();
			} else {
				System.out.println("지갑훔치기에 실패하여 징역 6개월이 선고되었습니다...\n");
				prison += 0.5;
			}
			menuCount2[0] -= 1;
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 지갑 훔치기 메소드
	
	public void notebook() {
		rate = (double)adultPoint[1]/skilLevel;
		random(rate);
		if(menuCount2[1] > 0) {
			if(result == 1) {
				System.out.println("노트북 훔치기에 성공하였습니다!!");
				image.notebook();
				skilLevel += skilLevelPoint2[1];
				adultCheck();
			} else {
				System.out.println("노트북 훔치기에 실패하여 징역 6개월이 선고되었습니다...\n");
				prison += 0.5;
			}
			menuCount2[1] -= 1;
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 노트북 훔치기 메소드
	
	public void bag() {
		rate = (double)adultPoint[2]/skilLevel;
		random(rate);
		if(menuCount2[2] > 0) {
			if(result == 1) {
				System.out.println("명품가방 훔치기에 성공하였습니다!!");
				image.bag();
				skilLevel += skilLevelPoint2[2];
				menuCount2[2] -= 1;
				adultCheck();
			} else {
				System.out.println("명품가방 훔치기에 실패하여 징역 1년이 선고되었습니다...\n");
				prison += 1;
			}
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 명품가방 훔치기 메소드
	
	public void creditCard() {
		rate = (double)adultPoint[3]/skilLevel;
		random(rate);
		if(menuCount2[3] > 0) {
			if(result == 1) {
				System.out.println("신용카드 훔치기에 성공하였습니다!!");
				image.creditCard();
				skilLevel += skilLevelPoint2[3];
				menuCount2[3] -= 1;
				adultCheck();
			} else {
				System.out.println("신용카드 훔치기에 실패하여 징역 2년이 선고되었습니다...\n");
				prison += 2;
			}
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 신용카드 훔치기 메소드
	
	public void motocycle() {
		rate = (double)adultPoint[4]/skilLevel;
		random(rate);
		if(menuCount2[4] > 0) {
			if(result == 1) {
				System.out.println("오토바이 훔치기에 성공하였습니다!!");
				image.motocycle();
				skilLevel += skilLevelPoint2[4];
				menuCount2[4] -= 1;
				adultCheck();
			} else {
				System.out.println("오토바이 훔치기에 실패하여 징역 2년이 선고되었습니다...\n");
				prison += 2;
			}
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 오토바이 훔치기 메소드
	
	public void car() {
		rate = (double)adultPoint[5]/skilLevel;
		random(rate);
		if(menuCount2[5] > 0) {
			if(result == 1) {
				System.out.println("자동차 훔치기에 성공하였습니다!!");
				image.car();
				skilLevel += skilLevelPoint2[5];
				menuCount2[5] -= 1;
				adultCheck();
			} else {
				System.out.println("자동차 훔치기에 실패하여 징역 3년이 선고되었습니다...\n");
				prison += 3;
			}
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 자동차 훔치기 메소드
	
	public void house() {
		rate = (double)adultPoint[6]/skilLevel;
		random(rate);
		if(menuCount2[6] > 0) {
			if(result == 1) {
				System.out.println("집털기에 성공하였습니다!!");
				image.house();
				skilLevel += skilLevelPoint2[6];
				menuCount2[6] -= 1;
				adultCheck();
			} else {
				System.out.println("집털기에 실패하여 징역 3년이 선고되었습니다...\n");
				prison += 3;
			}
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 집털기 메소드
	
	public void jewelleryShop() {
		rate = (double)adultPoint[7]/skilLevel;
		random(rate);
		if(menuCount2[7] > 0) {
			if(result == 1) {
				System.out.println("금은방털기에 성공하였습니다!!");
				image.jewelleryShop();
				skilLevel += skilLevelPoint2[7];
				menuCount2[7] -= 1;
				adultCheck();
			} else {
				System.out.println("금은방터기에 실패하여 징역 4년이 선고되었습니다...\n");
				prison += 4;
			}
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 금은방털기 메소드
	
	public void bank() {
		rate = (double)adultPoint[8]/skilLevel;
		random(rate);
		if(menuCount2[8] > 0) {
			if(result == 1) {
				System.out.println("은행털기에 성공하였습니다!!");
				image.bank();
				skilLevel += skilLevelPoint2[8];
				menuCount2[8] -= 1;
				adultCheck();
			} else {
				System.out.println("은행털기에 실패하여 징역 6년이 선고되었습니다...\n");
				prison += 6;
			}
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 은행털기 메소드
	
	public void monarisa() {
		rate = (double)adultPoint[9]/skilLevel;
		random(rate);
		if(menuCount2[9] > 0) {
			if(result == 1) {
				image.monarisa();
				skilLevel += skilLevelPoint2[9];
				menuCount2[9] -= 1;
			} else {
				System.out.println("모나리자 훔치기에 실패하여 징역 10년이 선고되었습니다...\n");
				prison += 10;
			}
		} else {
			System.out.println("훔칠 수 있는 횟수를 모두 사용하였습니다. 다른 물건을 훔쳐보세요!!\n");
		}
	} // 모나리자 훔치기 메소드
	
	public void monarisaEnding(){
		System.out.println("※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※\n"
				+ "드디어 마지막인 모나리자 훔치기에 성공하였습니다 !!\n"
				+ name + "은(는) 이제 완벽한 도둑놈이 되었습니다. 수고하셨습니다 !!\n"
				+ "다른 도둑도 만들어보세요 !\n"
				+ "※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※※\n\n"
				+ " - THE END - "
				);
	} // 모나리자 결말 메소드
	
	public void prisonOut() {
		System.out.println("무기 징역이 선고되었습니다...\n"
				+ name + "에겐 괴도가 될 자격이 없는 것 같습니다 다시 도전해 보세요...\n\n\n"
				+ " - GAME OVER - "
				);
	} // 무기징역 결말 메소드
	
	public void adultCheck() {
		System.out.println("현재 숙련도 : " + skilLevel + "  누적 징역 : " + prison + "년\n");
	} // 현재 상황 출력 메소드
	
} // end of class
