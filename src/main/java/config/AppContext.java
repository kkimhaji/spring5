package config;

import domain.MemberDao;
import main.MemberInfoPrinter;
import main.MemberListPrinter;
import main.MemberPrinter;
import main.VersionPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.ChangePasswordService;
import service.MemberRegisterService;

@Configuration
public class AppContext {

    @Bean
    public MemberDao memberDao(){
        return new MemberDao();
    }

    @Bean
    public MemberRegisterService memberRegisterService(){
        return new MemberRegisterService();
    }

    @Bean
    public ChangePasswordService changePasswordService(){
        ChangePasswordService passwordService = new ChangePasswordService();
//        passwordService.setMemberDao(memberDao());
// ChangePasswordService에 @Autowired로 의존성 자동 주입을 해뒀기 때문에 의존을 주입하지 않아도 된다
        return passwordService;
    }

    @Bean
    public MemberPrinter memberPrinter(){
        return new MemberPrinter();
    }
    @Bean
    public MemberListPrinter memberListPrinter(){
        return new MemberListPrinter();
    }

    @Bean
    public MemberInfoPrinter infoPrinter(){
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
//        infoPrinter.setPrinter(memberPrinter());
//        infoPrinter.setMemberDao(memberDao());
        return infoPrinter;
    }

    @Bean
    public VersionPrinter versionPrinter(){
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }
}
