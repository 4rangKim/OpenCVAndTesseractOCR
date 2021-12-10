package tesseractOCRSample;

import java.io.File;
import java.io.IOException;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class start_Tesseract {
	public static void main(String[] args) throws IOException {
		/* File imageFile = new File("img/kor_number.png"); */
		File imageFile = new File("resultImg/text7.jpg"); 
		ITesseract instance = new Tesseract();
		instance.setDatapath("C:\\finalproject\\OpenCV&TesseractOCR");
		instance.setLanguage("kor");
		try {
			String result = instance.doOCR(imageFile);
			System.out.println("=========°á°ú==========");
			System.out.println(result);
		} catch (TesseractException e) {
			e.printStackTrace();
		}
	}
}
