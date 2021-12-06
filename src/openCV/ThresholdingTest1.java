package openCV;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ThresholdingTest1 {
	static String InPath = "resultImg/GrayScaleTest.jpg";
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	void Thresholding(Mat inputMat) {
		String OutPath = "resultImg/ThreshBinary_Inv.jpg";
		Mat binary = new Mat();
		
		// Thresholding
		Imgproc.threshold(inputMat, binary, 127, 255, Imgproc.THRESH_BINARY_INV);
		
		// 다른이름으로 저장
		Imgcodecs.imwrite(OutPath, binary);
	}
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Mat origin = Imgcodecs.imread(InPath);
		new ThresholdingTest1().Thresholding(origin);
		System.out.print("Time : ");
		System.out.println(System.currentTimeMillis()-start);
		System.out.println("Done");
	}
}
