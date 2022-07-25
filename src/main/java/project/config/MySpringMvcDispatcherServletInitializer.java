package project.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.EnumSet;


public class MySpringMvcDispatcherServletInitializer
    extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext aServletContext) throws ServletException {
        super.onStartup(aServletContext);
        registerCharacterEncodingFilter(aServletContext);
        registerHiddenFieldFilter(aServletContext);

    }

    private void registerHiddenFieldFilter(ServletContext aContext) {
        aContext.addFilter("hiddenHttpMethodFilter",
                new HiddenHttpMethodFilter()).addMappingForUrlPatterns(null ,true, "/*");
    }

    private void registerCharacterEncodingFilter(ServletContext aContext) {
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic characterEncoding = aContext.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
    }
}
/** Данный класс заменяет web.xml */
/** getRootConfigClasses()
Пока не понятно, что он делает
 */
/** getServletConfigClasses()
return new Class[]{SpringConfig.class};
мы прописываем путь к нашему классу,
SpringConfig.java
заменяющему applicationContextMVC.xml
(находится как правило в той же папке)
*/
/** getServletMappings()
return new String[]{"/"};
мы прописываем зону действия - все файлы
как в web.xml:
<servlet-name>dispatcher</servlet-name>
<url-pattern>/</url-pattern>
*/
/** onStartup()
Как я понимаю, подключение методов (фильтров) при
старте системы, в данном случае:
 */
/** registerHiddenFieldFilter()
Добавляем фильтр для того, чтобы POST
запросы со скрытым полем PATCH перенаправлялись
на метод, который обрабатывает PATCH запросы
 */
/** registerCharacterEncodingFilter()
Еслия правильно понимаю, это фильтр, который
позволяет установить кодировку символов
(в нашем случае, UTF-8), что, в свою очередь
позволит правильно отображать русские буквы.

Также часть настроек в соседнем классе
SpringConfig
 */