// Simple Player sample application
// AForge.NET framework
// http://www.aforgenet.com/framework/
//
// Copyright © AForge.NET, 2006-2011
// contacts@aforgenet.com
//

using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Text;
using System.Windows.Forms;
using System.Diagnostics;
using System.Threading;
using System.Net;
using AForge.Video;
using AForge.Video.DirectShow;
using AviFile;
using Renci.SshNet;

namespace Player
{
    public partial class MainForm : Form
    {
        //private AviFile.AviManager avi;
        //private VideoStream str;
        private Stopwatch stopWatch = null;
        private int connectionAttempts = 0;
        private bool failedCon = false;
        private BackgroundWorker connMon = new BackgroundWorker();
        private SshClient ssh;
        // Class constructor
        public MainForm()
        {
            InitializeComponent();
            connMon.DoWork += new DoWorkEventHandler(connMon_DoWork);
            connMon.RunWorkerCompleted += new RunWorkerCompletedEventHandler(connMon_RunWorkerCompleted);
            var g = videoSourcePlayer.CreateGraphics();
            //g.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
            g.DrawString("Waiting for robot...", new Font(FontFamily.GenericMonospace, 10F, FontStyle.Bold), new SolidBrush(Color.White), new PointF(10, 120));
            //g.Dispose();
            //openMJPEGURLToolStripMenuItem_Click(this, new EventArgs());
            
            showCrosshairs.Checked = true;
            showBestOnly.Checked = true;
            connMon.RunWorkerAsync();
        }

        private void connMon_RunWorkerCompleted(object sender, RunWorkerCompletedEventArgs e)
        {
            openMJPEGURLToolStripMenuItem_Click(this, new EventArgs());
            //stream = new SshStream("10.14.50.11","pi","raspberry");
            ssh = new SshClient("10.14.50.11",1180,"pi","raspberry");
            ssh.Connect();
        }

        private void connMon_DoWork(object sender, DoWorkEventArgs e)
        {
            string response = string.Empty;
            while (string.IsNullOrEmpty(response))
            {
                try
                {
                    
                    response = ((HttpWebRequest)WebRequest.Create("http://10.14.50.11/?action=snapshot")).GetResponse().ToString();
                }
                catch (Exception ex){
                    Console.WriteLine(ex.Message.ToString());
                    
                }

            }
        }



        
        private void MainForm_FormClosing( object sender, FormClosingEventArgs e )
        {
            CloseCurrentVideoSource( );
        }

        // "Exit" menu item clicked
        private void exitToolStripMenuItem_Click( object sender, EventArgs e )
        {
            this.Close( );
        }

        // Open local video capture device
        private void localVideoCaptureDeviceToolStripMenuItem_Click( object sender, EventArgs e )
        {
            VideoCaptureDeviceForm form = new VideoCaptureDeviceForm( );

            if ( form.ShowDialog( this ) == DialogResult.OK )
            {
                // create video source
                VideoCaptureDevice videoSource = form.VideoDevice;

                // open it
                OpenVideoSource( videoSource );
            }
        }

        // Open video file using DirectShow
        private void openVideofileusingDirectShowToolStripMenuItem_Click( object sender, EventArgs e )
        {
            if ( openFileDialog.ShowDialog( ) == DialogResult.OK )
            {
                // create video source
                FileVideoSource fileSource = new FileVideoSource( openFileDialog.FileName );

                // open it
                OpenVideoSource( fileSource );
            }
        }

        // Open JPEG URL
        private void openJPEGURLToolStripMenuItem_Click( object sender, EventArgs e )
        {
            URLForm form = new URLForm( );

            form.Description = "Enter URL of an updating JPEG from a web camera:";
            form.URLs = new string[]
				{
					"http://195.243.185.195/axis-cgi/jpg/image.cgi?camera=1",
				};

            if ( form.ShowDialog( this ) == DialogResult.OK )
            {
                // create video source
                JPEGStream jpegSource = new JPEGStream( form.URL );

                // open it
                OpenVideoSource( jpegSource );
            }
        }

        // Open MJPEG URL
        private void openMJPEGURLToolStripMenuItem_Click( object sender, EventArgs e )
        {

            MJPEGStream mjpegSource = new MJPEGStream( "http://10.14.50.11/?action=stream" );

            // open it
            OpenVideoSource( mjpegSource );
            
        }

        // Open video source
        private void OpenVideoSource( IVideoSource source )
        {
            
            // set busy cursor
            this.Cursor = Cursors.WaitCursor;

            // stop current video source
            CloseCurrentVideoSource( );

            // start new video source
            videoSourcePlayer.VideoSource = source;
            videoSourcePlayer.VideoSource.VideoSourceError += new VideoSourceErrorEventHandler(VideoSource_VideoSourceError);
            videoSourcePlayer.Start( );
            //avi = new AviFile.AviManager("movie_" + DateTime.Now.ToFileTime().ToString() + ".avi",false);
            //str = avi.AddVideoStream(false, 15.0, new Bitmap(388, 252));
            
            // reset stop watch
            stopWatch = null;

            // start timer
            timer.Start( );

            this.Cursor = Cursors.Default;
        }

        private void VideoSource_VideoSourceError(object sender, VideoSourceErrorEventArgs eventArgs)
        {
            connectionAttempts++;
            fpsLabel.Text = "Connection attempt " + connectionAttempts.ToString() + " failed. Trying again.";
            
           // failedConnection.RunWorkerAsync();

        }

        // Close video source if it is running
        private void CloseCurrentVideoSource( )
        {
            if ( videoSourcePlayer.VideoSource != null )
            {
                videoSourcePlayer.SignalToStop( );

                // wait ~ 3 seconds
                for ( int i = 0; i < 30; i++ )
                {
                    if ( !videoSourcePlayer.IsRunning )
                        break;
                    System.Threading.Thread.Sleep( 100 );
                }

                if ( videoSourcePlayer.IsRunning )
                {
                    videoSourcePlayer.Stop( );
                }
                //str.
                //str.Close();

                //avi.Close();
                
                videoSourcePlayer.VideoSource = null;
            }
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            e.Graphics.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
            e.Graphics.InterpolationMode = System.Drawing.Drawing2D.InterpolationMode.HighQualityBicubic;
            e.Graphics.CompositingQuality = System.Drawing.Drawing2D.CompositingQuality.HighQuality;
            base.OnPaint(e);

        }

        // New frame received by the player
        private void videoSourcePlayer_NewFrame( object sender, ref Bitmap image )
        {
            var e = videoSourcePlayer.CreateGraphics();
            e.SmoothingMode = System.Drawing.Drawing2D.SmoothingMode.AntiAlias;
            e.InterpolationMode = System.Drawing.Drawing2D.InterpolationMode.HighQualityBicubic;
            e.CompositingQuality = System.Drawing.Drawing2D.CompositingQuality.HighQuality;
            DateTime now = DateTime.Now;
            
            bool locked;
            image = ImageProcess.Process(image,showBestOnly.Checked,showCrosshairs.Checked,out locked);
            Graphics g = Graphics.FromImage(image);
            if (ssh.IsConnected)
            {
                if (locked)
                {
                    ssh.RunCommand("touch /home/pi/red");
                }
                else
                {
                    ssh.RunCommand("rm /home/pi/red");
                }
            }
            // paint current time
            SolidBrush brush = new SolidBrush( Color.White );
            g.DrawString( now.ToString( ), this.Font, brush, new PointF( 5, image.Height-20 ) );
            brush.Dispose( );
            //str.AddFrame(image);
            g.Dispose( );
            
        }

        // On timer event - gather statistics
        private void timer_Tick( object sender, EventArgs e )
        {
            IVideoSource videoSource = videoSourcePlayer.VideoSource;

            if ( videoSource != null )
            {
                // get number of frames since the last timer tick
                int framesReceived = videoSource.FramesReceived;

                if ( stopWatch == null )
                {
                    stopWatch = new Stopwatch( );
                    stopWatch.Start( );
                }
                else
                {
                    stopWatch.Stop( );

                    float fps = 1000.0f * framesReceived / stopWatch.ElapsedMilliseconds;
                    fpsLabel.Text = fps.ToString( "F2" ) + " fps";

                    stopWatch.Reset( );
                    stopWatch.Start( );
                }
            }
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (videoSourcePlayer.IsRunning)
            {
                videoSourcePlayer.Stop();
                button1.Text = "Connect";
            }
            else
            {
                button1.Text = "Disconnect";
                openMJPEGURLToolStripMenuItem_Click(this, new EventArgs());
            }
        }
    }
}
