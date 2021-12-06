package openCV;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class GrayScaleTest1 {
	static String imgInPath = "img/img3.png";
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	void GrayScale(Mat inputMat) {
		String imgOutPath = "resultImg/GrayScaleTest.jpg";
		String result = "";
		Mat gray = new Mat();
		
		// GrayScale
		Imgproc.cvtColor(inputMat, gray, Imgproc.COLOR_BGR2GRAY);
		
		// 다른이름으로 저장
		Imgcodecs.imwrite(imgOutPath, gray);
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Mat origin = Imgcodecs.imread(imgInPath);
		new GrayScaleTest1().GrayScale(origin);
		System.out.print("Time : ");
		System.out.println(System.currentTimeMillis()-start);
		System.out.println("Done");
	}
}
