package Hepsiburada;


import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;



public class HepsiBuradaTest {

    WebDriver driver;
    Actions actions;

    @BeforeClass
    public void Start(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

        //Hepsiburada urlsine gider.
        driver.get("https://hepsiburada.com");

    }
    @AfterClass
    public void End(){
        //driver.close();
    }

    @Test(priority = 1)
    public void LoginTest() throws InterruptedException {

        //Actions tanımlanır ve giriş yap üzerinde fare durur.
        actions=new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//*[@id='myAccount']/span/span[1]"))).perform();

        //Login butonuna tıklar.
        WebElement loginButton=driver.findElement(By.id("login"));
        loginButton.click();

        //Facebook üzerinden giriş yapar.
        driver.findElement(By.id("btnFacebook")).click();
        driver.findElement(By.xpath("//input[@id='email']")).sendKeys("testhepsiburada@outlook.com");
        driver.findElement(By.xpath("//input[@id='pass']")).sendKeys("Testtest1234");
        driver.findElement(By.xpath("//button[@name='login']")).click();

        //giriş yaptıktan sonra actualName ile expectedName karşılaştırır, giriş yaptığına dair doğrulama yapar.
        String actualName= driver.findElement(By.xpath("//span[@class='sf-OldMyAccount-sS_G2sunmDtZl9Tld5PR']")).getText();
        String expectedName="Mehmet Kuzu";
        //giris yapılan ismin Mehmet Kuzu olduğu doğrulanır.
        Assert.assertTrue(actualName.contains(expectedName));




    }
    @Test(priority = 2)
    public void order() throws InterruptedException {
        //arama butonuna "bardak" yazılır ve araya tıklanır.
        driver.findElement(By.xpath("//input[@placeholder='Ürün, kategori veya marka ara']")).sendKeys("bardak");
        driver.findElement(By.xpath("//div[text()='ARA']")).click();

        //yapıla aramada sepete koyulacak isim orderActual a atanır ve sepete ürün eklenir.
        String orderActual = driver.findElement(By.xpath("//*[@id='i0']/div/a/div[2]/h3")).getText();
        actions=new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//*[@id='i0']/div/a/div[2]/h3"))).perform();
        driver.findElement(By.xpath("//*[@id='i0']/div/a/div[2]/button")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[@id='shoppingCart']")).click();

        //sepete eklenen isim ile sepetteki isimin aynı olup olmadığı sorgulanır.
        String orderExpected = driver.findElement(By.xpath("//a[text()='Lav Diamond 18 parça bardak seti']")).getText();
        Assert.assertEquals(orderActual,orderExpected);

        //satıcıları görüntülemek için ürüne tıklanır.
        driver.findElement(By.xpath("//a[text()='Lav Diamond 18 parça bardak seti']")).click();
        //satıcıların isimleri doğrulama yapmak için string değerlere aktarılır.
        String orderSellerActual2 = driver.findElement(By.xpath("//a[text()='on8']")).getText();
        String orderSellerActual = driver.findElement(By.xpath("//a[text()='kozmopol']")).getText();
        Thread.sleep(4000);

        driver.findElement(By.xpath("(//span[@class='price-text'])[1]")).click();
        driver.findElement(By.id("addToCart")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[@id='shoppingCart']")).click();
        //sepetteki ürünleri doğrulamak için satıcı isimleri string değerlere aktarılır.
        String orderSellerExpected2 = driver.findElement(By.xpath("//a[text()='on8']")).getText();
        String orderSellerExpected = driver.findElement(By.xpath("//a[text()='kozmopol']")).getText();

        //sepetteki ürünlerin doğru satıcılardan gelip gelmediği doğrulanır
        Assert.assertEquals(orderSellerActual.toLowerCase(),orderSellerExpected.toLowerCase());
        Assert.assertEquals(orderSellerActual2.toLowerCase(),orderSellerExpected2.toLowerCase());

    }


}
