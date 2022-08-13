package main;

import config.AppContext;
import domain.RegisterRequest;
import exception.DuplicateMemberException;
import exception.MemberNotFoundException;
import exception.WrongIdPasswordException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import service.ChangePasswordService;
import service.MemberRegisterService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainForSpring {
    private static ApplicationContext context = null;

    public static void main(String[] args) throws IOException {
        context = new AnnotationConfigApplicationContext(AppContext.class);

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.println("명령어 입력: ");
            String command = reader.readLine();
            if(command.equalsIgnoreCase("exit")){
                System.out.println("종료");
                break;
            }

            if(command.startsWith("new ")){
                processNewCommand(command.split(" "));
                continue;
            }else if(command.startsWith("change ")){
                processChangeCommand(command.split(" "));
                continue;
            }else if(command.equals("list")){
                processListCommand();
                continue;
            }else if(command.startsWith("info ")){
                processInfoCommand(command.split(" "));
                continue;
            }else if(command.equals("version")){
                processVersionCommand();
                continue;
            }
            printHelp();
        }

    }

    private static void processVersionCommand() {
        VersionPrinter versionPrinter = context.getBean("versionPrinter", VersionPrinter.class);
        versionPrinter.print();
    }

    private static void processInfoCommand(String[] arg) {
        if(arg.length!=2){
            printHelp();
            return;
        }
        MemberInfoPrinter infoPrinter = context.getBean("infoPrinter", MemberInfoPrinter.class);
        infoPrinter.printMemberInfo(arg[1]);
    }

    private static void processListCommand() {
        MemberListPrinter listPrinter = context.getBean("memberListPrinter", MemberListPrinter.class);
        listPrinter.printAll();
    }

//    private static Assembler assembler= new Assembler();

    private static void processNewCommand(String[] arg) {
        if(arg.length!=5){
            printHelp();
            return;
        }

        MemberRegisterService regSvc = context.getBean("memberRegisterService", MemberRegisterService.class);

        RegisterRequest request = new RegisterRequest();
        request.setEmail(arg[1]);
        request.setName(arg[2]);
        request.setPassword(arg[3]);
        request.setConfirmPassword(arg[4]);

        if(!request.isPasswordEqualToConfirmPassword()){
            System.out.println("암호와 확인이 일치하지 않습니다.\n");
            return;
        }
        try {
            regSvc.regist(request);
            System.out.println("등록 완료\n");

        }catch (DuplicateMemberException e){
            System.out.println("이미 존재하는 이메일입니다. \n");
        }
    }

    private static void processChangeCommand(String[] arg){
        if(arg.length!=4){
            printHelp();
            return;
        }
        ChangePasswordService changePasswordService = context.getBean("changePasswordService", ChangePasswordService.class);
        try {
            changePasswordService.changePassword(arg[1], arg[2], arg[3]);
            System.out.println("암호를 변경했습니다. \n");
        }catch (MemberNotFoundException e){
            System.out.println("존재하지 않는 이메일입니다.\n");
        }catch (WrongIdPasswordException e){
            System.out.println("이메일과 암호가 일치하지 않습니다. \n");

        }
    }

    private static void printHelp() {
        System.out.println();
        System.out.println("잘못된 명령입니다. 아래 명령어 사용법을 확인하세요.");
        System.out.println("명령어 사용법: ");
        System.out.println("new 이메일 이름 암호 암호확인");
        System.out.println("change 이메일 현재비번 변경비번");
        System.out.println();
    }
}
