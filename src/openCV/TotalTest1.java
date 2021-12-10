package openCV;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Range;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class TotalTest1 {
	static String InPath = "img/img3.png";
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public void ShowImage(String title, Mat img) {
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
		frame.setTitle(title);
		frame.setVisible(true);
		System.out.println("Image Loaded");
	}

	void Total(Mat inputMat) {
		// ShowImage("origin",inputMat);
		Mat blank = Imgcodecs.imread("img/blank.png");
		Mat edit = new Mat();
		inputMat.copyTo(edit);
		// GrayScale
		Mat gray = new Mat();
		Imgproc.cvtColor(edit, gray, Imgproc.COLOR_BGR2GRAY);
		// ShowImage("gray", gray);

		// GaussianBlur
		Mat blur = new Mat();
		Imgproc.GaussianBlur(gray, blur, new Size(5, 5), 0);
		// ShowImage("GaussianBlur", blur);

		// Thresholding
		Mat binary = new Mat();
		Imgproc.threshold(blur, binary, 127, 255, Imgproc.THRESH_BINARY_INV);
		// ShowImage("Thresholding", binary);

		// Canny Edge
		Mat CannyEdge = new Mat();
		Imgproc.Canny(binary, CannyEdge, 10, 100, 3, true);
		// ShowImage("Canny Edge", CannyEdge);

		// MORPH_CLOSE
		Mat kernel = new Mat();
		Mat close = new Mat();
		kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1, 1));
		Imgproc.morphologyEx(CannyEdge, close, Imgproc.MORPH_CLOSE, kernel);
		// ShowImage("MORPH_CLOSE", close);

		// get Roi
		List<MatOfPoint> contours = new ArrayList<>();
		Mat hierarchy = new Mat();
		// Mat resultMat = new Mat();
		Imgproc.findContours(close, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		List<Integer> contours_x = new ArrayList<>();
		List<Integer> contours_y = new ArrayList<>();
		List<Integer> contours_width = new ArrayList<>();
		List<Integer> contours_height = new ArrayList<>();
		List<String> carNum = new ArrayList<>();
		int x1 = 1000;
		int y1 = 1000;
		int x2 = 0;
		int y2 = 0;
		for (int idx = 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) {
			MatOfPoint matOfPoint = contours.get(idx);
			Rect rect = Imgproc.boundingRect(matOfPoint);
			// Imgproc.drawContours(inputMat, contours, idx, new Scalar(0, 255, 0));
			Imgproc.rectangle(inputMat, rect, new Scalar(0, 255, 0));

			if (rect.width < 5 || rect.height < 35 || rect.width > 25 || rect.x < 108 || rect.x > 318)
				continue;
			System.out.println(rect.toString());
			// point 1
			if (x1 > rect.x || y1 > rect.y) {
				x1 = rect.x;
				y1 = rect.y;
			}
			// point 2
			if (x2 < rect.x || y2 < rect.y) {
				x2 = rect.x + rect.width;
				y2 = rect.y + rect.height;
			}
		}
		System.out.println("x1:" + x1 + " y1:" + y1);
		System.out.println("x2:" + x2 + " y2:" + y2);
		int padding = 10;
		Point Top_l = new Point(x1-padding, y1-padding);
		Point Bottom_r = new Point(x2+padding, y2+padding);
		Rect roi2 = new Rect(Top_l, Bottom_r);
		Mat resultMat = new Mat(edit, roi2);
		ShowImage("resultMat", resultMat);
		Mat rotate = new Mat();
		int width = resultMat.width();
		int height = resultMat.height();
		int newW = (x2+padding)-(x1-padding);
		int newh = (y1-padding)-(y2-padding);
		rotate = Imgproc.getRotationMatrix2D(new Point(width/2,height/2), 15, 1);
		Imgproc.warpAffine(resultMat, resultMat, rotate, new Size(newW,newh), Imgproc.INTER_AREA);
		Imgproc.GaussianBlur(resultMat, resultMat, new Size(5, 5), 0);
		Imgproc.threshold(resultMat, resultMat, 127, 255, Imgproc.THRESH_BINARY);
		Mat kernel2 = new Mat();
		kernel2 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3, 3));
		Imgproc.morphologyEx(resultMat, resultMat, Imgproc.MORPH_CLOSE, kernel2);
		ShowImage("resultImg",resultMat);
		/*
		 * ITesseract instance = new Tesseract();
		 * instance.setDatapath("C:\\finalproject\\OpenCV&TesseractOCR");
		 * instance.setLanguage("kor"); String outPath = "resultImg/total2.jpg";
		 * Imgcodecs.imwrite(outPath,resultMat); File imageFile = new File(outPath); try
		 * { String result = instance.doOCR(imageFile); System.out.println(result); }
		 * catch (TesseractException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Mat origin = Imgcodecs.imread(InPath);
		new TotalTest1().Total(origin);
		System.out.print("Time : ");
		System.out.println(System.currentTimeMillis() - start);
		System.out.println("Done");
	}
}