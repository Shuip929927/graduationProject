//package cn.yangcy.pzc_server.config;
//
//import cn.yangcy.pzc_server.component.LoginHandlerInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class MyMvcConfig implements WebMvcConfigurer {
//
//    @Bean
//    public WebMvcConfigurer webMvcConfigurerAdapter(){
//        return new WebMvcConfigurer(){
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//                registry.addInterceptor(new LoginHandlerInterceptor())
//                        .addPathPatterns("/**")
//                        .excludePathPatterns("/login","/login.html")
//                        .excludePathPatterns("/assets/**");
//            }
//
//            @Override
//            public void addViewControllers(ViewControllerRegistry registry) {
//                registry.addViewController("/login.html").setViewName("login");
//            }
//        };
//    }
//}
