package tesseractOCRSample;

import java.io.File;
import java.io.IOException;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class start_Tesseract {
	public static void main(String[] args) throws IOException {
		File imageFile = new File("img/test2.PNG");
		ITesseract instance = new Tesseract();
		instance.setDatapath("C:\\finalproject\\OpenCV&TesseractOCR");
		instance.setLanguage("kor");
		try {
			String result = instance.doOCR(imageFile);
			System.out.println(result);
		} catch (TesseractException e) {
			e.printStackTrace();
		}
	}
}
