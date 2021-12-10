package finalProject;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.osgi.OpenCVInterface;
import org.opencv.osgi.OpenCVNativeLoader;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class OATUtils {
	// OpenCV
	static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	public void testOpenCV(String imgPath) {
		System.out.println("Welcome to OpenCV " + Core.VERSION);
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		Mat m = Mat.eye(3, 3, CvType.CV_8UC1);
		System.out.println("m = " + m.dump());
	}

	public Mat GrayScale(String imgPath) {
		Mat origin = Imgcodecs.imread(imgPath);
		Mat result = new Mat();
		
		// GrayScale
		Imgproc.cvtColor(origin, result, Imgproc.COLOR_BGR2GRAY);
		
		return result;
	}
	public void GrayScaleSave(String inPath, String outPath) {
		Mat origin = Imgcodecs.imread(inPath);
		Mat result = new Mat();

		// GrayScale
		Imgproc.cvtColor(origin, result, Imgproc.COLOR_BGR2GRAY);

		// Save
		Imgcodecs.imwrite(outPath, result); 
		System.out.println("GrayScaleSave: "+outPath+" 이미지 저장 완료");
	}
	public Mat THRESH_BINARY(String inPath) {
		Mat origin = Imgcodecs.imread(inPath);
		Mat result = new Mat();
		
		// Thresholding
		Imgproc.threshold(origin, result, 127, 255, Imgproc.THRESH_BINARY);
		
		return result;
	}
	public void THRESH_BINARYSave(String inPath, String outPath) {
		Mat origin = Imgcodecs.imread(inPath);
		Mat result = new Mat();
		
		// Thresholding
		Imgproc.threshold(origin, result, 127, 255, Imgproc.THRESH_BINARY);
		
		// Save
		Imgcodecs.imwrite(outPath, result);
	}
	public Mat THRESH_BINARY_INV(String inPath) {
		Mat origin = Imgcodecs.imread(inPath);
		Mat result = new Mat();
		
		// Thresholding
		Imgproc.threshold(origin, result, 127, 255, Imgproc.THRESH_BINARY_INV);
		
		return result;
	}
	public void THRESH_BINARY_INVSave(String inPath, String outPath) {
		Mat origin = Imgcodecs.imread(inPath);
		Mat result = new Mat();
		
		// Thresholding
		Imgproc.threshold(origin, result, 127, 255, Imgproc.THRESH_BINARY_INV);
		
		// Save
		Imgcodecs.imwrite(outPath, result);
	}
	public int getMatWidth(Mat img) {
		int width = img.width();
		return width;
	}
	public int getMatHeight(Mat img) {
		int height = img.height();
		return height;
	}
	public int getImageWidth(String inPath) {
		Mat origin = Imgcodecs.imread(inPath);
		int width = origin.width();
		return width;
	}
	public int getImageHeight(String inPath) {
		Mat origin = Imgcodecs.imread(inPath);
		int height = origin.height();
		return height;
	}
	public void RotateSave(String inPath, String outPath, double angle) {
		Mat origin = Imgcodecs.imread(inPath);
		Mat rotate = new Mat();
		Mat result = new Mat();
		int width = origin.width();
		int height = origin.height();
		
		// Rotate
		rotate = Imgproc.getRotationMatrix2D(new Point(width/2,height/2), angle, 1);
		Imgproc.warpAffine(origin, result, rotate, new Size(width,height), Imgproc.INTER_AREA);
		
		// Save
		Imgcodecs.imwrite(outPath, result);
		
	}
	public Mat Rotate(String inPath, double angle) {
		Mat origin = Imgcodecs.imread(inPath);
		Mat rotate = new Mat();
		Mat result = new Mat();
		int width = origin.width();
		int height = origin.height();
		
		// Rotate
		rotate = Imgproc.getRotationMatrix2D(new Point(width/2,height/2), angle, 1);
		Imgproc.warpAffine(origin, result, rotate, new Size(width,height), Imgproc.INTER_AREA);
		
		return result;	
	}
	public void ShowImage(Mat img) {
		MatOfByte imgByte = new MatOfByte();
		Imgcodecs.imencode(".jpg", img, imgByte);
		byte[] imgByteArray = imgByte.toArray();
		InputStream in = new ByteArrayInputStream(imgByteArray);
		BufferedImage out = null;
		try {
			out = ImageIO.read(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame();
		frame.getContentPane().add(new JLabel(new ImageIcon(out)));
		frame.pack();
		frame.setVisible(true);
		System.out.println("Image Loaded");
	}
	public void Roi(Mat img,int x, int y, int w, int h) {
		Rect roi = new Rect(x, y, w, h);
	}
	// Tesseract OCR
	public String tessOCR(String imgPath) {
		File imageFile = new File(imgPath);
		ITesseract instance = new Tesseract();
		instance.setDatapath("C:\\finalproject\\OpenCV&TesseractOCR");
		instance.setLanguage("kor");
		String result = "";
		try {
			result = (instance.doOCR(imageFile)).trim();
			System.out.println("=========결과==========");
			System.out.println(result);
		} catch (TesseractException e) {
			e.printStackTrace();
		}
		return result;
	}
	public String tessOCRSetLang(String imgPath, String language) {
		File imageFile = new File(imgPath);
		ITesseract instance = new Tesseract();
		instance.setDatapath("C:\\finalproject\\OpenCV&TesseractOCR");
		instance.setLanguage(language);
		String result = "";
		try {
			result = (instance.doOCR(imageFile)).trim();
			System.out.println("=========결과==========");
			System.out.println(result);
		} catch (TesseractException e) {
			e.printStackTrace();
		}
		return result;
	}
}
