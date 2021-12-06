package openCV;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

//
public class TotalTest1 {
	static String InPath = "img/img3.PNG";
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	void MaximizeContours(Mat inputMat) {
		String OutPath = "resultImg/ThresholdingCanny.jpg";
		
		// GrayScale
		Mat gray = new Mat();
		Imgproc.cvtColor(inputMat, gray, Imgproc.COLOR_BGR2GRAY);
		
		// Thresholding
		Mat binary = new Mat();
		Imgproc.threshold(gray, binary, 127, 255, Imgproc.THRESH_BINARY_INV);
		
		// Canny Edge
		Mat CannyEdge = new Mat();
		Imgproc.Canny(gray, CannyEdge, 10, 100, 3, true);
		
		// List<MatOfPoint> 생성
		// List<MatOfPoint> contours = new ArrayList<>();
		
		// 관심 영역 추출 
		/*
		 * Mat hierarchy = new Mat(); Imgproc.findContours(CannyEdge, contours,
		 * hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
		 * Imgproc.drawContours(CannyEdge, contours=contours, contourIdx=-1,
		 * color=(255,255,255));
		 * 
		 * temp_result = np.zeros((height, width, channel), dtype=np.uint8) for contour
		 * in contours: x, y, w, h = cv2.boundingRect(contour)
		 * cv2.rectangle(temp_result, pt1=(x,y), pt2=(x+w, y+h), color=(255,255,255),
		 * thickness=2)
		 * 
		 * contours_dict.append({ 'contour': contour, 'x': x, 'y': y, 'w': w, 'h': h,
		 * 'cx': x + (w / 2), 'cy': y + (h / 2) })
		 */
		/*
		 * Mat hierarchy = new Mat(); Imgproc.findContours(CannyEdge, contours,
		 * hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE); for(int idx =
		 * 0; idx >= 0; idx = (int) hierarchy.get(0, idx)[0]) { MatOfPoint matOfPoint =
		 * contours.get(idx); Rect rect = Imgproc.boundingRect(matOfPoint);
		 * if(rect.width < 30 || rect.height < 30 || rect.width <= rect.height || rect.x
		 * < 20 || rect.y < 20 || rect.width <= rect.height * 3 || rect.width >=
		 * rect.height * 6) continue; // 사각형 크기에 따라 출력 여부 결정 Bitmapfa // ROI 출력 Bitmap
		 * roi = Bitmap.createBitmap(myBitmap, (int)rect.tl().x, (int)rect.tl().y,
		 * rect.width, rect.height); ImageView imageView1 =
		 * (ImageView)findViewById(R.id.image_result_ROI);
		 * imageView1.setImageBitmap(roi); } image1= Bitmap.createBitmap(img1.cols(),
		 * img1.rows(), Bitmap.Config.ARGB_8888); Utils.matToBitmap(img1, image1); //
		 * Mat to Bitmap imageView = (ImageView)findViewById(R.id.image_result);
		 * imageView.setImageBitmap(image1); } Colored by Color Scripter
		 */
		
		// 다른이름으로 저장
		Imgcodecs.imwrite(OutPath, CannyEdge);
	}
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Mat origin = Imgcodecs.imread(InPath);
		new TotalTest1().MaximizeContours(origin);
		System.out.print("Time : ");
		System.out.println(System.currentTimeMillis()-start);
		System.out.println("Done");
	}
}