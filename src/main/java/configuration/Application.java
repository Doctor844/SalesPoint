package configuration;

import org.apache.catalina.startup.Tomcat;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Application {
    public static void main(String[] args) throws Exception {
        // Создаем экземпляр Tomcat
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);  // Устанавливаем порт, на котором будет работать ваше приложение

        // Указываем корень каталога web-приложения (если у вас есть отдельная директория для web)
        String webAppDir = "templates/auth"; // Замените на актуальный путь
        tomcat.addWebapp("/", webAppDir);

        // Создаем Spring контекст и инициализируем его
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        // Регистрируем все необходимые конфигурационные классы
        context.register(AppConfig.class);
        context.register(SpringConfig.class);
        context.register(SecurityConfig.class);

        // Создаем DispatcherServlet и связываем его с контекстом
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);

        // Регистрируем Servlet в Tomcat
        tomcat.addServlet("/", "dispatcher", dispatcherServlet);

        // Устанавливаем DispatcherServlet, чтобы он был загружен первым
        tomcat.getConnector();
        tomcat.start();

        // Ожидаем запросы
        tomcat.getServer().await();
    }
}