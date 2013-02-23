////
////  main.cpp
////  FindRectangle
////
////  Created by Charles Barbe on 1/10/12.
////  Copyright (c) 2012 Xerox. All rights reserved.
////
//
//#include "opencv2/opencv.hpp"
//
//#include <iostream>
//
//using namespace cv;
//using namespace std;
//
//int main (int argc, const char * argv[])
//{
//
////    // insert code here...
////    std::cout << "Hello, World!\n";
////    
////    IplImage* img = 0; 
////    int height,width,step,channels;
////    uchar *data;
////    int i,j,k;
////    
////    if(argc<2){
////        printf("Usage: main <image-file-name>\n\7");
////        exit(0);
////    }
////    
////    // load an image  
////    img=cvLoadImage(argv[1]);
////    if(!img){
////        printf("Could not load image file: %s\n",argv[1]);
////        exit(0);
////    }
////    
////    // get the image data
////    height    = img->height;
////    width     = img->width;
////    step      = img->widthStep;
////    channels  = img->nChannels;
////    data      = (uchar *)img->imageData;
////    printf("Processing a %dx%d image with %d channels\n",height,width,channels); 
////    
////    // create a window
////    cvNamedWindow("mainWin", CV_WINDOW_AUTOSIZE); 
////    cvMoveWindow("mainWin", 100, 100);
////    
////    // invert the image
////    for(i=0;i<height;i++) for(j=0;j<width;j++) for(k=0;k<channels;k++)
////        data[i*step+j*channels+k]=255-data[i*step+j*channels+k];
////    
////    // show the image
////    cvShowImage("mainWin", img );
////    
////    // wait for a key
////    cvWaitKey(0);
////    
////    // release the image
////    cvReleaseImage(&img );
////    
////    return 0;
//    
//    
////    VideoCapture cap(0); // open the default camera
////    if(!cap.isOpened())  // check if we succeeded
////        return -1;
////    
////    Mat edges;
////    namedWindow("edges",1);
////    for(;;)
////    {
////        Mat frame;
////        cap >> frame; // get a new frame from camera
////        cvtColor(frame, edges, CV_RGB2HLS);
////        imshow("edges", edges);
////        if(waitKey(30) >= 0) break;
////    }
////    // the camera will be deinitialized automatically in VideoCapture destructor
////    return 0;
//    
//    
//    VideoCapture webCam(0); // video source for webcam
//    webCam.set(CV_CAP_PROP_FRAME_WIDTH,640);
//    webCam.set(CV_CAP_PROP_FRAME_HEIGHT,480);
//    // slices matrcies that hold H,S and V
//    vector<Mat> slices;
//    // Cross Element for Erosion/Dilation
//    Mat cross = getStructuringElement(MORPH_CROSS, Size(5,5));
//    // create matrices to hold image
//    Mat camImage;		// raw image from webcam
//    Mat blurImage;		// blur image
//    Mat hsvImage;		// hsv image 
//    Mat hue;			// hue channel
//    Mat hue1;			// Hue upper bound
//    Mat hue2;			// Hue lower bound
//    Mat hue3;			// hue color filtering
//    Mat sat;			// Sat channel
//    Mat sat1;			// Sat upper bound
//    Mat sat2;			// sat lower bound
//    Mat sat3;			// sat color filtering
//    Mat val;			// Val channel
//    Mat val1;			// Val upper bound
//    Mat val2;			// Val lower bound
//    Mat val3;			// Val color filtering
//    Mat erd;			// Erosion Image
//    Mat dia;			// dialate image
//    Mat HnS;			// sat and hue channel
//    Mat HSV;			// HSV color fiter detected
//    
//    // slide bar values
//    int HuethresL =12,
//    HuethresH =22, 
//    SatthresL =72,
//    SatthresH = 117,
//    ValthresL = 197,
//    ValthresH = 255,
//    erosionCount = 1,
//    blurSize = 3;
//    
//    // new window
//    cvNamedWindow("Color Tune",CV_WINDOW_NORMAL);
//	
//    // make tune bar
//    cvCreateTrackbar( "Hue UpperT","Color Tune", &HuethresH, 255, 0 );
//    cvCreateTrackbar ("Hue LowerT","Color Tune", &HuethresL,255, 0);
//    cvCreateTrackbar( "Sat UpperT","Color Tune", &SatthresH, 255, 0 );
//    cvCreateTrackbar( "Sat LowerT","Color Tune", &SatthresL, 255, 0 );
//    cvCreateTrackbar( "Val UpperT","Color Tune", &ValthresH, 255, 0 );
//    cvCreateTrackbar( "Val LowerT","Color Tune", &ValthresL, 255, 0 );
//    cvCreateTrackbar ("EroTime","Color Tune", &erosionCount,15, 0);
//    cvCreateTrackbar ("BlurSize","Color Tune", &blurSize,15, 0);
//    // check blurSize bound
//    if(blurSize == 0)
//        blurSize = 1; //reset blurSize
//	
//    // get and display webcam image
//    while(1)
//    {
//        // get new image over and over from webcam
//        webCam >> camImage;
//        
//        // check blurSize bound
//        if(blurSize == 0)
//            blurSize = 1; //reset blurSize
//        
//        // blur image
//        //blur(camImage, blurImage, Size(11,11));
//        blur(camImage, blurImage, Size(blurSize,blurSize));
//		
//        // conver raw image to hsv
//        cvtColor (camImage, hsvImage, CV_RGB2HSV);
//        
//        // check blurSize bound
//        if(blurSize == 0)
//            blurSize = 1; //reset blurSize
//        blur(hsvImage, hsvImage, Size(blurSize,blurSize));
//        //blur(hsvImage, hsvImage, Size(5,5));
//        
//        // split image to H,S and V images
//        split(hsvImage,slices);
//        
//        slices[0].copyTo (hue); // get the hue channel
//        slices[1].copyTo(sat); // get the sat channel
//        slices[2].copyTo(val); // get the V channel
//        //apply threshold HUE upper/lower for color range
//        threshold (hue,hue1,HuethresL,255, CV_THRESH_BINARY); // get lower bound
//        threshold (hue, hue2,HuethresH,255, CV_THRESH_BINARY_INV); // get upper bound
//        
//        hue3 = hue1 &hue2; // multiply 2 matrix to get the color range
//        
//        // apply thresshold for Sat channel
//        threshold (sat,sat1,SatthresL,255, CV_THRESH_BINARY); // get lower bound
//        threshold (sat, sat2,SatthresH,255, CV_THRESH_BINARY_INV); // get upper bound
//        sat3 = sat1 & sat2; // multiply 2 matrix to get the color range
//        
//        // apply thresshold for Val channel
//        threshold (val,val1,ValthresL,255, CV_THRESH_BINARY); // get lower bound
//        threshold (val, val2,ValthresH,255, CV_THRESH_BINARY_INV); // get upper bound
//        val3 = val1 & val2; // multiply 2 matrix to get the color range
//        // combine sat and hue filter together
//        HnS = sat3 & hue3;
//        // erode and dialation to reduce noise
//        erode(HnS,erd,cross,Point(-1,-1),erosionCount); // do erode
//        dilate(HnS,dia,cross,Point(-1,-1),erosionCount);// do dialate
//        // combine sat, val and hue filter together
//        HSV = sat3 & hue3 & val3;
//        
//        // erode and dialation to reduce noise
//        erode(HSV,erd,cross,Point(-1,-1),erosionCount); // do erode
//      //  dilate(HSV,dia,cross,Point(-1,-1),erosionCount); // do dialate
//		
//        // display image over and over
//        imshow("Webcam Orignal", camImage);
//        //imshow("Blur", blurImage);
//        imshow("HSV Image", hsvImage);
//        //imshow("Hue channel",hue);
//        //imshow("Lower bound",hue1);
//        //imshow("Upper bound", hue2);
//        //imshow("L channel", Val);
//        //imshow("Color detected", hue3);
//        //imshow("Erosion",erd);
//        imshow("Hue color",hue3);
//        //imshow("Sat channel",sat);
//        imshow("Sat color",sat3);
//        //imshow("Val color",val3);
//        imshow("Sat and Hue",HnS);
//        imshow("HSV",HSV);
//        // Pause for highgui to process image painting
//        cvWaitKey(5);
//        cout << "HuethresL = " << HuethresL << endl
//             << "HuethresH = " << HuethresH << endl
//             << "SatthresL = " << SatthresL << endl
//             << "SatthresH = " << SatthresH << endl
//             << "ValthresL = " << ValthresL << endl
//             << "ValthresH = " << ValthresH << endl;
//
//    }
//    
//    //------CLEAN UP------- 
//    cvDestroyAllWindows();
//
//}
//


// The "Square Detector" program.
// It loads several images sequentially and tries to find squares in
// each image

#include "opencv2/core/core.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include "opencv2/highgui/highgui.hpp"

#include <iostream>
#include <math.h>
#include <string.h>

using namespace cv;
using namespace std;

void help()
{
	cout <<
	"\nA program using pyramid scaling, Canny, contours, contour simpification and\n"
	"memory storage (it's got it all folks) to find\n"
	"squares in a list of images pic1-6.png\n"
	"Returns sequence of squares detected on the image.\n"
	"the sequence is stored in the specified memory storage\n"
	"Call:\n"
	"./squares\n"
    "Using OpenCV version %s\n" << CV_VERSION << "\n" << endl;
}


int thresh = 50, N = 11;
const char* wndname = "Square Detection Demo";

// helper function:
// finds a cosine of angle between vectors
// from pt0->pt1 and from pt0->pt2
double angle( Point pt1, Point pt2, Point pt0 )
{
    double dx1 = pt1.x - pt0.x;
    double dy1 = pt1.y - pt0.y;
    double dx2 = pt2.x - pt0.x;
    double dy2 = pt2.y - pt0.y;
    return (dx1*dx2 + dy1*dy2)/sqrt((dx1*dx1 + dy1*dy1)*(dx2*dx2 + dy2*dy2) + 1e-10);
}

// returns sequence of squares detected on the image.
// the sequence is stored in the specified memory storage
void findSquares( const Mat& image, vector<vector<Point> >& squares )
{
    squares.clear();
    
    Mat pyr, timg, gray0(image.size(), CV_8U), gray;
    
    // down-scale and upscale the image to filter out the noise
    pyrDown(image, pyr, Size(image.cols/2, image.rows/2));
    pyrUp(pyr, timg, image.size());
    vector<vector<Point> > contours;
    
    // find squares in every color plane of the image
    for( int c = 0; c < 3; c++ )
    {
        int ch[] = {c, 0};
        mixChannels(&timg, 1, &gray0, 1, ch, 1);
        
        // try several threshold levels
        for( int l = 0; l < N; l++ )
        {
            // hack: use Canny instead of zero threshold level.
            // Canny helps to catch squares with gradient shading
            if( l == 0 )
            {
                // apply Canny. Take the upper threshold from slider
                // and set the lower to 0 (which forces edges merging)
                Canny(gray0, gray, 0, thresh, 5);
                // dilate canny output to remove potential
                // holes between edge segments
                dilate(gray, gray, Mat(), Point(-1,-1));
            }
            else
            {
                // apply threshold if l!=0:
                //     tgray(x,y) = gray(x,y) < (l+1)*255/N ? 255 : 0
                gray = gray0 >= (l+1)*255/N;
            }
            
            // find contours and store them all as a list
            findContours(gray, contours, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
            
            vector<Point> approx;
            
            // test each contour
            for( size_t i = 0; i < contours.size(); i++ )
            {
                // approximate contour with accuracy proportional
                // to the contour perimeter
                approxPolyDP(Mat(contours[i]), approx, arcLength(Mat(contours[i]), true)*0.02, true);
                
                // square contours should have 4 vertices after approximation
                // relatively large area (to filter out noisy contours)
                // and be convex.
                // Note: absolute value of an area is used because
                // area may be positive or negative - in accordance with the
                // contour orientation
                if( approx.size() == 4 &&
                   fabs(contourArea(Mat(approx))) > 1000 &&
                   isContourConvex(Mat(approx)) )
                {
                    double maxCosine = 0;
                    
                    for( int j = 2; j < 5; j++ )
                    {
                        // find the maximum cosine of the angle between joint edges
                        double cosine = fabs(angle(approx[j%4], approx[j-2], approx[j-1]));
                        maxCosine = MAX(maxCosine, cosine);
                    }
                    
                    // if cosines of all angles are small
                    // (all angles are ~90 degree) then write quandrange
                    // vertices to resultant sequence
                    if( maxCosine < 0.3 )
                        squares.push_back(approx);
                }
            }
        }
    }
}


// the function draws all the squares in the image
void drawSquares( Mat& image, const vector<vector<Point> >& squares )
{
    for( size_t i = 0; i < squares.size(); i++ )
    {
        const Point* p = &squares[i][0];
        int n = (int)squares[i].size();
        polylines(image, &p, &n, 1, true, Scalar(0,255,0), 3, CV_AA);
    }
    
    imshow(wndname, image);
}


int main (int argc, const char * argv[])
{
    vector<vector<Point> > squares;

//    // insert code here...
//    std::cout << "Hello, World!\n";
//    
//    IplImage* img = 0; 
//    int height,width,step,channels;
//    uchar *data;
//    int i,j,k;
//    
//    if(argc<2){
//        printf("Usage: main <image-file-name>\n\7");
//        exit(0);
//    }
//    
//    // load an image  
//    img=cvLoadImage(argv[1]);
//    if(!img){
//        printf("Could not load image file: %s\n",argv[1]);
//        exit(0);
//    }
//    
//    // get the image data
//    height    = img->height;
//    width     = img->width;
//    step      = img->widthStep;
//    channels  = img->nChannels;
//    data      = (uchar *)img->imageData;
//    printf("Processing a %dx%d image with %d channels\n",height,width,channels); 
//    
//    // create a window
//    cvNamedWindow("mainWin", CV_WINDOW_AUTOSIZE); 
//    cvMoveWindow("mainWin", 100, 100);
//    
//    // invert the image
//    for(i=0;i<height;i++) for(j=0;j<width;j++) for(k=0;k<channels;k++)
//        data[i*step+j*channels+k]=255-data[i*step+j*channels+k];
//    
//    // show the image
//    cvShowImage("mainWin", img );
//    
//    // wait for a key
//    cvWaitKey(0);
//    
//    // release the image
//    cvReleaseImage(&img );
//    
//    return 0;
    
    
//    VideoCapture cap(0); // open the default camera
//    if(!cap.isOpened())  // check if we succeeded
//        return -1;
//    
//    Mat edges;
//    namedWindow("edges",1);
//    for(;;)
//    {
//        Mat frame;
//        cap >> frame; // get a new frame from camera
//        cvtColor(frame, edges, CV_RGB2HLS);
//        imshow("edges", edges);
//        if(waitKey(30) >= 0) break;
//    }
//    // the camera will be deinitialized automatically in VideoCapture destructor
//    return 0;
    
    
   // VideoCapture webCam(0); // video source for webcam
    CvCapture *camera=cvCaptureFromFile("http://10.14.50.11/mjpg/video.mjpg");
    if (camera==NULL)
        printf("camera is null\n");
    else
        printf("camera is not null");
  //  webCam.set(CV_CAP_PROP_FRAME_WIDTH,640);
  //  webCam.set(CV_CAP_PROP_FRAME_HEIGHT,480);
    // slices matrcies that hold H,S and V
    vector<Mat> slices;
    // Cross Element for Erosion/Dilation
    Mat cross = getStructuringElement(MORPH_CROSS, Size(5,5));
    // create matrices to hold image
    Mat camImage;		// raw image from webcam
    Mat blurImage;		// blur image
    Mat hsvImage;		// hsv image 
    Mat hue;			// hue channel
    Mat hue1;			// Hue upper bound
    Mat hue2;			// Hue lower bound
    Mat hue3;			// hue color filtering
    Mat sat;			// Sat channel
    Mat sat1;			// Sat upper bound
    Mat sat2;			// sat lower bound
    Mat sat3;			// sat color filtering
    Mat val;			// Val channel
    Mat val1;			// Val upper bound
    Mat val2;			// Val lower bound
    Mat val3;			// Val color filtering
    Mat erd;			// Erosion Image
    Mat dia;			// dialate image
    Mat HnS;			// sat and hue channel
    Mat HSV;			// HSV color fiter detected
    Mat HSV2;
    Mat HSV3;
    
    // slide bar values
    int HuethresL =0,
    HuethresH =255, 
    SatthresL =66,
    SatthresH = 247,
    ValthresL = 199,
    ValthresH = 252,
    erosionCount = 1,
    blurSize = 3;
    
    // new window
    cvNamedWindow("Color Tune",CV_WINDOW_NORMAL);
	
    // make tune bar
    cvCreateTrackbar( "Hue UpperT","Color Tune", &HuethresH, 255, 0 );
    cvCreateTrackbar ("Hue LowerT","Color Tune", &HuethresL,255, 0);
    cvCreateTrackbar( "Sat UpperT","Color Tune", &SatthresH, 255, 0 );
    cvCreateTrackbar( "Sat LowerT","Color Tune", &SatthresL, 255, 0 );
    cvCreateTrackbar( "Val UpperT","Color Tune", &ValthresH, 255, 0 );
    cvCreateTrackbar( "Val LowerT","Color Tune", &ValthresL, 255, 0 );
    cvCreateTrackbar ("EroTime","Color Tune", &erosionCount,15, 0);
    cvCreateTrackbar ("BlurSize","Color Tune", &blurSize,15, 0);
    // check blurSize bound
    if(blurSize == 0)
        blurSize = 1; //reset blurSize
	
    // get and display webcam image
    while(1)
    {
        // get new image over and over from webcam
        camImage = cvQueryFrame(camera);
        
        // check blurSize bound
        if(blurSize == 0)
            blurSize = 1; //reset blurSize
        
        // blur image
        //blur(camImage, blurImage, Size(11,11));
        blur(camImage, blurImage, Size(blurSize,blurSize));
		
        // conver raw image to hsv
        cvtColor (camImage, hsvImage, CV_RGB2HSV);
        
        // check blurSize bound
        if(blurSize == 0)
            blurSize = 1; //reset blurSize
        blur(hsvImage, hsvImage, Size(blurSize,blurSize));
        //blur(hsvImage, hsvImage, Size(5,5));
        
        // split image to H,S and V images
        split(hsvImage,slices);
        
        slices[0].copyTo (hue); // get the hue channel
        slices[1].copyTo(sat); // get the sat channel
        slices[2].copyTo(val); // get the V channel
        //apply threshold HUE upper/lower for color range
        threshold (hue,hue1,HuethresL,255, CV_THRESH_BINARY); // get lower bound
        threshold (hue, hue2,HuethresH,255, CV_THRESH_BINARY_INV); // get upper bound
        
        hue3 = hue1 &hue2; // multiply 2 matrix to get the color range
        
        // apply thresshold for Sat channel
        threshold (sat,sat1,SatthresL,255, CV_THRESH_BINARY); // get lower bound
        threshold (sat, sat2,SatthresH,255, CV_THRESH_BINARY_INV); // get upper bound
        sat3 = sat1 & sat2; // multiply 2 matrix to get the color range
        
        // apply thresshold for Val channel
        threshold (val,val1,ValthresL,255, CV_THRESH_BINARY); // get lower bound
        threshold (val, val2,ValthresH,255, CV_THRESH_BINARY_INV); // get upper bound
        val3 = val1 & val2; // multiply 2 matrix to get the color range
        // combine sat and hue filter together
        HnS = sat3 & hue3;
        // erode and dialation to reduce noise
        erode(HnS,erd,cross,Point(-1,-1),erosionCount); // do erode
        dilate(HnS,dia,cross,Point(-1,-1),erosionCount);// do dialate
        // combine sat, val and hue filter together
        HSV = sat3 & hue3 & val3;
        
        // erode and dialation to reduce noise
        erode(HSV,erd,cross,Point(-1,-1),erosionCount); // do erode
        dilate(HSV,dia,cross,Point(-1,-1),erosionCount); // do dialate
        
        cvtColor(HSV,HSV2, CV_GRAY2BGR);
		
        // display image over and over
      //  imshow("Webcam Orignal", camImage);
        //imshow("Blur", blurImage);
        imshow("HSV Image", hsvImage);
      //  imshow("Hue channel",hue);
        //imshow("Lower bound",hue1);
        //imshow("Upper bound", hue2);
       // imshow("L channel", Val);
        //imshow("Color detected", hue3);
        //imshow("Erosion",erd);
        imshow("Hue color",hue3);
        //imshow("Sat channel",sat);
        imshow("Sat color",sat3);
        imshow("Val color",val3);
      //  imshow("Sat and Hue",HnS);
        findSquares(HSV2, squares);
        drawSquares(HSV2, squares);
      //  imshow("HSV",HSV2);
        // Pause for highgui to process image painting
        cvWaitKey(5);
        
        cout << "HuethresL = " << HuethresL << endl
             << "HuethresH = " <<  HuethresH << endl
             << "SatthresL = "  << SatthresL << endl
             << "SatthresH = "  << SatthresH << endl
             << "ValthresL = "  << ValthresL << endl
             << "ValthresH = "  << ValthresH << endl; 
    }
    
    //------CLEAN UP------- 
    cvDestroyAllWindows();

}

//
//int main(int /*argc*/, char** /*argv*/)
//{
//    static const char* names[] = { "pic1.png", "pic2.png", "pic3.png",
//        "pic4.png", "pic5.png", "pic6.png", 0 };
//    help();
//    namedWindow( wndname, 1 );
//    vector<vector<Point> > squares;
//    
//    for( int i = 0; names[i] != 0; i++ )
//    {
//        Mat image = imread(names[i], 1);
//        if( image.empty() )
//        {
//            cout << "Couldn't load " << names[i] << endl;
//            continue;
//        }
//        
//        findSquares(image, squares);
//        drawSquares(image, squares);
//        
//        int c = waitKey();
//        if( (char)c == 27 )
//            break;
//    }
//    
//    return 0;
//}
//
