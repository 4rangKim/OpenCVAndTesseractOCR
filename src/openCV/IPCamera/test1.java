package openCV.IPCamera;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class test1 {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	void cameraTest01(String camPath) {
		VideoCapture captureImg = null;
		Mat img = null;
		String imgOutPath = "resultImg/capture/Test1.jpg";
		if(!captureImg.open(camPath)) {
			System.out.println("Can not open capture!!");
		}
		Size size = new Size((int)captureImg.get(Videoio.CAP_PROP_FRAME_WIDTH), (int)captureImg.get(Videoio.CAP_PROP_FRAME_HEIGHT));
		while (true) {
            // Read current camera frame into matrix
			captureImg.read(img);
            // Render frame if the camera is still acquiring images
            if (img != null) {
            	Imgcodecs.imwrite(imgOutPath, img);
            } else {
                System.out.println("No captured frame -- camera disconnected");
                break;
            }
        }
	}
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		String camPath = "rtsp://192.168.0.15:81/stream";
		new test1().cameraTest01(camPath);
		System.out.print("Time : ");
		System.out.println(System.currentTimeMillis()-start);
		System.out.println("Done");

	}

}
