package scott.pages;
import scott.utils.ConfigurationReader;
import scott.utils.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class DropBoxPage {

        public DropBoxPage() {
        PageFactory.initElements(Driver.get(),this);
    }

        @FindBy(xpath = "//input[@type=\"email\"]")
        public WebElement dropBoxEmailInputBox;

        @FindBy(xpath = "//button[@type=\"submit\"]")
        public WebElement dropBoxSubmitBtn;

        @FindBy(xpath = "//input[@type=\"password\"]")
        public WebElement dropBoxPasswordInputBox;

        @FindBy(xpath = "///span[contains(@class, '_rc-hl-truncated-string__container_1fh0k_36') and contains(., 'court documents')]")
        public WebElement courtDocumentsFolder;

        @FindBy(xpath = "//span[@class='dig-Text dig-Text--variant-label dig-Text--size-small dig-Text--color-inherit dig-Text--isBold _browseActionBarSimpleButton-label_1y8yi_26' and text()='Upload']")
        public WebElement uploadBtn;

        @FindBy(xpath = "//h2[contains(@class, 'dig-Title') and contains(., 'Folder')]")
        public WebElement folderUpload;



}

