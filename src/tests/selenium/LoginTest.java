package selenium;

        import com.sun.istack.internal.NotNull;
        import org.openqa.selenium.By;
        import org.openqa.selenium.WebDriver;
        import org.openqa.selenium.WebElement;
        import org.openqa.selenium.htmlunit.HtmlUnitDriver;
        import org.openqa.selenium.support.ui.ExpectedCondition;
        import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest  {
    public void main(String[] args) throws Exception {
        testLogin("localhost:8080/auth/signin", "dmitr", "");
    }
    public void testLogin(@NotNull String url,@NotNull String username,@NotNull String password) {
        WebDriver driver = new HtmlUnitDriver(true);
        driver.get(url);
// Find the text input element by its name
        WebElement elementUsername = driver.findElement(By.name("login"));
                elementUsername.sendKeys(username);
        WebElement elementPassword = driver.findElement(By.name("password"));
                elementPassword.sendKeys(password);
// Now submit the form. WebDriver will find the form for us from the element
        elementUsername.submit();
        elementPassword.submit();
// Wait for the page to load, timeout after 10 seconds
        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            @Override
            @NotNull
            public Boolean apply(@NotNull WebDriver d) {
                final String login = d.findElement(By.name("login")).getText();
                return login.equals(username);
            }
        });
        driver.quit();
    }
}