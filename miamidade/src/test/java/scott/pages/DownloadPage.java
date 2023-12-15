package scott.pages;

import scott.utils.ConfigurationReader;
import scott.utils.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DownloadPage {
        public DownloadPage() {
        PageFactory.initElements(Driver.get(),this);
    }

        @FindBy(xpath = "//div[@class=\"col-md-12 text-center\"]//p/a[@class=\"TSDhyperlink\" and contains(text(), \"Logging In\")]")
        public WebElement loginButton;

        @FindBy(xpath = "(//a[@class='btn btn-lg btn-block coc-button--blue'])[1]")
        public WebElement standardSearchButton;

        @FindBy(xpath = "//span[@class=\"fa fa-file\"]")
        public WebElement localCaseNumberTab;

        @FindBy(xpath = "//input[@name='ctl00$cphPage$txtUserName']")
        public WebElement usernameInput;

        @FindBy(xpath = "//input[@id='ctl00_cphPage_txtPassword']")
        public WebElement passwordInput;

        @FindBy(xpath = "//input[@type='submit' and @value='Login']")
        public WebElement submitButton;

        @FindBy(xpath = "//input[@placeholder=\"YYYY\" and @class=\"form-control numericOnly yearOnly-dt\"]")
        public WebElement yearInputBox;

        @FindBy(xpath = "//span[@class='year active focused']")
        public WebElement focusedYear;

        @FindBy(xpath = "//input[@id='txtLCNSeqSTD_localCaseContent']")
        public WebElement caseNumberInputBox;

        @FindBy(xpath = "//select[@name='ctl00$ContentPlaceHolder1$localCaseCodesSelect_localCaseContent']")
        public WebElement caseCodeInputBox;

        @FindBy(xpath = "(//a[@class='btn btn-lg coc-button--primary '])[1]")
        public WebElement searchButton;

        @FindBy(xpath = "//h1[@id='MainTitle']")
        public WebElement title;

        @FindBy(xpath = "(//div[@class=' card-header TSDcardHeader'])[3]")
        public WebElement dockets;

        @FindBy(xpath = "//td[contains(text(), 'Minutes') or contains(text(), 'Verdict') or contains(text(), 'Mediators Report')]")
        public WebElement filteredSearch;

        //@FindBy(xpath = "//td[contains(text(), 'Minutes') or contains(text(), 'Verdict') or contains(text(), 'Mediators Report')]/parent::tr/td[1]")
        //public List<WebElement> pdfIcons;

        @FindBy(xpath = "//td[contains(text(), 'Minute') or contains(text(), 'Verdict') or contains(text(), 'Mediator')]/parent::tr/td[1]")
        public List<WebElement> pdfIcons;


        public void login(String userType) {

            usernameInput.sendKeys(ConfigurationReader.get(userType + "_username"));
            passwordInput.sendKeys(ConfigurationReader.get(userType + "_password"));
            loginButton.click();

        }
//    username
//    password
//    login click
//    login method


}
