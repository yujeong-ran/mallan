package com.mallan.yujeongran.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("Mallan / 아이스브레이킹 프로젝트")
                .version("1.0.0")
                .description(
                        """
                        ## 🧊 아이스브레이킹 API 명세서
                        ---
                        ### 📍팀명
                        🥚Mallan
                        ### 🐣팀원
                        🙋🏻‍♀️유지수(프론트엔드)
                        \n\n
                        🙋🏻‍♂️이정훈(백엔드)
                        \n\n
                        ### 🎮게임 항목
                        - 밸런스게임
                        - 라이어게임
                        - 랜덤 퀘스천 뽑기게임
                        - 공통점 찾기 게임
                        - 동전 진실게임 
                        ### 🔗 링크
                        📌 [Mallan-Web] 등록 예정 
                        📌 [GitHub](https://github.com/yujeong-ran/mallan) 
                        📌 [Notion](https://www.notion.so/1eca7efc6b0380ee8f99f5d528ef8200)
                        📌 [Jira](https://qweqwerty12321-1740141206278.atlassian.net/jira/software/projects/MAL/pages)
                        """
                );
    }

}
