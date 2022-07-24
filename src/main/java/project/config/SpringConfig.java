package project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.util.Objects;

@PropertySource("classpath:database.properties")
@Configuration
@ComponentScan("project")
@EnableWebMvc
public class SpringConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;
    private final Environment environment;

    @Autowired
    public SpringConfig(ApplicationContext applicationContext, Environment environment) {
        this.applicationContext = applicationContext;
        this.environment = environment;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(resolver);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("driver")));
        dataSource.setUrl(environment.getProperty("url"));
        dataSource.setUsername(environment.getProperty("user_name"));
        dataSource.setPassword(environment.getProperty("password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}

/*
@PropertySource("classpath:database.properties")
Здесь указываем, где хранятся настройки и доступы
к  используемой базе данных.
В данном случае этот файл находится в папке resources
Далее в коде подключаем
import org.springframework.core.env.Environment;
для подключения переменной environment
к этим настройкам
*/
/*
Интерфейс WebMvcConfigurer реализуется,
когда мы под себя хотим настроить Spring MVC
 */
/*
@Configuration
Означает, что в это файле находится конфигурация проекта
Как я понимаю, это может быть не один файл
 */
/*
@ComponentScan("project")
Указываем Спрингу, какие папки сканировать,
(находящиеся по уровню ниже java)
в которых есть классы и методы,
которые помечены разнми аннотациями
спринга например @Component @Controller
которые могут понадобится спрингу для внедрения
 */
/*
@EnableWebMvc
эта аннотация разрешает нашему проекту использовать MVC;
Это аналог команды
<mvc:annotation-driven/>
в файле
applicationContextMVC.xml
когда настройка проекта идет через
web.xml и applicationContextMVC.xml
 */
/*
@Autowired
public SpringConfig()
Внедрение компонентов может (и рекомендовано) производить через конструктор,
с добавлением (по желанию) аннотации @Autowired
В данном случае через конструктор мы внедряем applicationContext.
applicationContext мы используем, чтобы настроить в методе
templateResolver() Thymeleaf
*/
/*
templateResolver(), templateEngine(), configureViewResolvers()
как я понимаю в этих методах мы подключаем шаблонизатор Thymeleaf
к проекту, с указанием некоторых дополнитеьных настроек.
В частности -
в  templateResolver() мы указываем

templateResolver.setPrefix("/WEB-INF/views/");
- где будут находится файлы представления

templateResolver.setSuffix(".html");
- с каким расширением представлений мы будем работать

templateResolver.setCharacterEncoding("UTF-8");
- желаемая кодировка файлов в представлении
(вкупе с настрйками из MySpringMvcDispatcherServletInitializer
это позволит нормально отображать русский язык)

также для этого в метод configureViewResolvers()
добавляется строка
resolver.setCharacterEncoding("UTF-8");
 */
/*
После выполнения всех настроек можно удалить файл
web.xml из папки WEB-INF, т.к. он не нужен
 */
/*
dataSource()
Как я понимаю, данный бин содержит настройки базы данных, которые
будут использоваться JdbcTemplate
 */
/*
jdbcTemplate()
Создаем бин JdbcTemplate,
который будет использовать
бин DataSource
 */

