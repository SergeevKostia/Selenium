
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest<list> {
    private WebDriver driver;

    @BeforeAll // хук, который запускает драйвер
    public static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
        // Выполняет действия до выполнения теста
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
        // выполняет действие после завершения теста
    void tearDown() {
        driver.quit(); // заканчивает работу
        driver = null; // обнуляет его
    }

    @Test
    void shouldTestSomething() throws InterruptedException {
        // загрузить страницу
        driver.get("http://localhost:9999/");

        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петров-Водкин Федор");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();

        String expected = "  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals(expected,actual);
    }
}
