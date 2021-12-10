package Test;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import finalProject.OATUtils;

public class Test {
	public static void main(String[] args) {
		OATUtils tool = new OATUtils();
		Mat img = Imgcodecs.imread("img/em");
		tool.ShowImage(img);
	}
}
