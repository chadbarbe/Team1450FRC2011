using System;
using System.Collections.Generic;
using System.Text;
using Emgu.CV;
using Emgu.Util;
using Emgu;
using Emgu.CV.UI;
using Emgu.CV.Structure;
using System.Drawing;
using System.Linq;

namespace Player
{
    public static class ImageProcess
    {

        
        public static System.Drawing.Bitmap Process(Bitmap imgRecv, bool showBestOnly, bool showCrosshairs, out bool locked)
        {
            Image<Bgr, Byte> img = new Image<Bgr, byte>(imgRecv).Resize(352,288,Emgu.CV.CvEnum.INTER.CV_INTER_AREA);
            //Convert the image to grayscale and filter out the noise
            Image<Gray, Byte> gray = img.Convert<Gray, Byte>().PyrDown().PyrUp();

            Gray cannyThreshold = new Gray(250);
            Gray cannyThresholdLinking = new Gray(250);
            Gray circleAccumulatorThreshold = new Gray(120);

            CircleF[] circles = gray.HoughCircles(
                cannyThreshold,
                circleAccumulatorThreshold,
                5.0, //Resolution of the accumulator used to detect centers of the circles
                10.0, //min distance 
                5, //min radius
                0 //max radius
                )[0]; //Get the circles from the first channel

            Image<Gray, Byte> cannyEdges = gray.Canny(100, 80);
            LineSegment2D[] lines = cannyEdges.HoughLinesBinary(
                1, //Distance resolution in pixel-related units
                Math.PI / 45.0, //Angle resolution measured in radians.
                20, //threshold
                30, //min Line width
                10 //gap between lines
                )[0]; //Get the lines from the first channel

            #region Find triangles and rectangles
            List<Triangle2DF> triangleList = new List<Triangle2DF>();
            List<MCvBox2D> boxList = new List<MCvBox2D>();

            using (MemStorage storage = new MemStorage()) //allocate storage for contour approximation
                for (Contour<Point> contours = cannyEdges.FindContours(); contours != null; contours = contours.HNext)
                {
                    Contour<Point> currentContour = contours.ApproxPoly(contours.Perimeter * 0.05, storage);

                    if (contours.Area > 350) //only consider contours with area greater than 250
                    {
                        if (currentContour.Total == 3) //The contour has 3 vertices, it is a triangle
                        {
                            Point[] pts = currentContour.ToArray();
                            triangleList.Add(new Triangle2DF(
                               pts[0],
                               pts[1],
                               pts[2]
                               ));
                        }
                        else if (currentContour.Total == 4) //The contour has 4 vertices.
                        {
                            #region determine if all the angles in the contour are within the range of [80, 100] degree
                            bool isRectangle = true;
                            Point[] pts = currentContour.ToArray();
                            LineSegment2D[] edges = PointCollection.PolyLine(pts, true);
                            double rangle = 0;
                            for (int i = 0; i < edges.Length; i++)
                            {
                                rangle += Math.Abs(
                                   edges[(i + 1) % edges.Length].GetExteriorAngleDegree(edges[i]));
                                
                            }
                            if (rangle < 300 || rangle > 420)
                                isRectangle = false;
                            #endregion

                            if (isRectangle) boxList.Add(currentContour.GetMinAreaRect());
                        }
                    }
                }
            #endregion

            boxList = boxList.OrderByDescending(m=>m.size.Height*m.size.Width).ToList();

            if (showBestOnly && boxList.Count>0)
            {
                //foreach (MCvBox2D box in boxList)
               
                var box = boxList.First();

                Cross2DF c = new Cross2DF(box.center, (float)box.size.Height * .2F, (float)box.size.Width * .2F);
                img.Draw(box, new Bgr(Color.Blue), 4);
                img.Draw(c, new Bgr(Color.Red), 2);
                
                //img.Draw(new MCvBox2D(new PointF((float)img.Width / 2, (float)img.Height / 2), new SizeF((float)img.Width / 4, (float)img.Height / 4), 0F),new Bgr(Color.White),1);
            }
            else
            {
                foreach (MCvBox2D box in boxList)
                {
                    Cross2DF c = new Cross2DF(box.center, (float)box.size.Height * .2F, (float)box.size.Width * .2F);
                    img.Draw(box, new Bgr(Color.Blue), 4);
                    img.Draw(c, new Bgr(Color.Red), 2);
                }

            }
            #region draw lines

            locked = false;
                Bitmap im = img.ToBitmap();
                
                if (showCrosshairs)
                {
                    using (Graphics g = Graphics.FromImage(im))
                    {
                        g.FillRectangle(new SolidBrush(Color.FromArgb(50, 255, 255, 255)), new Rectangle(new Point(im.Width / 2 - im.Width / 8, im.Height / 2 - im.Height / 8), new Size(img.Width / 4, img.Height / 4)));
                        g.DrawRectangle(new Pen(new SolidBrush(Color.FromArgb(140, 255, 255, 255)), 2), new Rectangle(new Point(im.Width / 2 - im.Width / 8, im.Height / 2 - im.Height / 8), new Size(img.Width / 4, img.Height / 4)));
                        g.DrawLine(new Pen(new SolidBrush(Color.FromArgb(140, 255, 255, 255)), 1), new Point(im.Width / 2 - 10, im.Height / 2), new Point(im.Width / 2 + 10, im.Height / 2));
                        g.DrawLine(new Pen(new SolidBrush(Color.FromArgb(140, 255, 255, 255)), 1), new Point(im.Width / 2, im.Height / 2 - 10), new Point(im.Width / 2, im.Height / 2 + 10));
                    }
                }
                foreach (MCvBox2D box in boxList)
                {
                    if (Math.Abs(box.center.X - im.Width / 2) < 20 && Math.Abs(box.center.Y - im.Height / 2) < 20)
                    {
                        using (Graphics g = Graphics.FromImage(im))
                        {
                            g.FillRectangle(new SolidBrush(Color.FromArgb(100, 255, 0, 0)), new Rectangle(0, 0, im.Width, im.Height));
                            g.DrawString("Locked on target", new Font(FontFamily.GenericMonospace, 24f, FontStyle.Bold), new SolidBrush(Color.White), new PointF(13F, 5F));
                            locked = true;
                            break;
                        }
                    }
                }

            return im;
            #endregion
        }
    }
}
