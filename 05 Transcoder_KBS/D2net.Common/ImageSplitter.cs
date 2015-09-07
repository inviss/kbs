using System;
using System.Windows.Forms;
using System.Drawing;
using System.Drawing.Imaging;

namespace D2net.Common.UI
{
	/// <summary>
	/// 큰 이미지를 n * m개의 이미지로 분할한다(row=n, col=m).
    /// </summary>
    /// <remarks> 이미지 분할 방법. 
    /// row가 n개이고 col이 m인 이미지 분할은 m*n개의 이미지로 분할되게 되고, 다음과 같이 분할된다.
    /// 열은 n개이고 행은 m개인 영역으로 분할됨.
    /// <list type="table">
    ///  <item><term>0</term><term>1</term><term>2</term><term>3</term><term>...</term>
    ///  <term>m-4</term><term>m-3</term><term>m-2</term><term>m-1</term></item>
    ///  <item><term>m</term><term>m+1</term><term>m+2</term><term>m+3</term><term>...</term>
    ///  <term>2*m-4</term><term>2*m-3</term><term>2*m-2</term><term>2*m-1</term></item>
    ///  <item><term>2*m</term><term>2*m+1</term><term>2*m+2</term><term>2*m+3</term><term>...</term>
    ///  <term>3*m-4</term><term>3*m-3</term><term>3*m-2</term><term>3*m-1</term></item>
    ///  <item><term>3*m</term><term>3*m+1</term><term>3*m+2</term><term>3*m+3</term><term>...</term>
    ///  <term>4*m-4</term><term>4*m-3</term><term>4*m-2</term><term>4*m-1</term></item>
    ///  <item><term>...</term><term>...</term><term>...</term><term>...</term><term>...</term>
    ///  <term>...</term><term>...</term><term>...</term><term>...</term></item>
    ///  <item><term>(n-4)*m</term><term>(n-4)*m+1</term><term>(n-4)*m+2</term><term>(n-4)*m+3</term><term>...</term>
    ///  <term>(n-3)*m-4</term><term>(n-3)*m-3</term><term>(n-3)*m-2</term><term>(n-3)*m-1</term></item>
    ///  <item><term>(n-3)*m</term><term>(n-3)*m+1</term><term>(n-3)*m+2</term><term>(n-3)*m+3</term><term>...</term>
    ///  <term>(n-2)*m-4</term><term>(n-2)*m-3</term><term>(n-2)*m-2</term><term>(n-2)*m-1</term></item>
    ///  <item><term>(n-2)*m</term><term>(n-2)*m+1</term><term>(n-2)*m+2</term><term>(n-2)*m+3</term><term>...</term>
    ///  <term>(n-1)*m-4</term><term>(n-1)*m-3</term><term>(n-1)*m-2</term><term>(n-1)*m-1</term></item>
    ///  <item><term>(n-1)*m</term><term>(n-1)*m+1</term><term>(n-1)*m+2</term><term>(n-1)*m+3</term><term>...</term>
    ///  <term>n*m-4</term><term>n*m-3</term><term>n*m-2</term><term>n*m-1</term></item>
    /// </list>
    /// </remarks>
    /// <example> 이미지 배열로 분할
    /// <code>
    /// Image[] imgs = ImageSplitter.SplitImage(new Image("c:\***.jpg"), 3, 4);
    /// </code>
    /// </example>
	public sealed class ImageSplitter
	{
        /// <summary>
	    /// 기본 생성자
        /// </summary>
		private ImageSplitter()
		{
		}

        /// <summary>
	    /// src 이미지를 row * col개의 이미지 배열로 분할한다.
        /// </summary>
        /// <returns>분할된 이미지 배열</returns>
        /// <param name="src">분할할 대상 이미지</param>
        /// <param name="row">이미지를 row개로 수평분할한다.</param>
        /// <param name="col">이미지를 col개로 수직분할한다.</param>
        /// <example> 이미지 분할
        /// <code>
        /// Image[] imgs = ImageSplitter.SplitImage(new Image("c:\***.jpg"), 3, 4);
        /// </code>
        /// </example>
        public static Image[] SplitImage(Image src, int row, int col)
        {
            try
            {
                return SplitImage(new Bitmap(src), row, col);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
	    /// src 비트맵을 row * col개의 비트맵 배열로 분할한다.
        /// </summary>
        /// <returns>분할된 비트맵 배열</returns>
        /// <param name="src">분할할 대상 비트맵</param>
        /// <param name="row">이미지를 row개로 수평분할한다.</param>
        /// <param name="col">이미지를 col개로 수직분할한다.</param>
        /// <example> 비트맵 분할
        /// <code>
        /// Bitmap[] imgs = ImageSplitter.SplitImage(new Image("c:\***.jpg"), 3, 4);
        /// </code>
        /// </example>
        public static Bitmap[] SplitImage(Bitmap src, int row, int col)
        {
            try
            {
                int i, r, c;
                int width, height;
                Graphics g = null;

                PixelFormat pixFormat = src.PixelFormat;
                width = src.Width / col;
                height = src.Height / row;

                if (width <= 0 ||
                    height <= 0)
                    throw new Exception("크기가 분할하려는 수보다 작습니다.");

                Bitmap[] Bitmaps = new Bitmap[row * col];
                for (i = 0; i < Bitmaps.Length; i++)
                {
                    c = i % col;
                    r = i / col;
                    Bitmaps[i] = new Bitmap(width, height);
                    g = Graphics.FromImage(Bitmaps[i]);
                    g.DrawImage(src, 
                                new Rectangle(0, 0, width, height),
                                new Rectangle(c * width, r * height, width, height), 
                                GraphicsUnit.Pixel);
                    g.Dispose();
                }
                return Bitmaps;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
	    /// src 이미지를 row * col개의 이미지를 포함한 <c>ImageList</c>형식으로 반환한다.
        /// </summary>
        /// <returns><c>ImageList</c>형식</returns>
        /// <param name="src">분할할 대상 이미지</param>
        /// <param name="row">이미지를 row개로 수평분할한다.</param>
        /// <param name="col">이미지를 col개로 수직분할한다.</param>
        /// <example> 이미지 분할
        /// <code>
        /// ImageList imgs = ImageSplitter.SplitImageList(new Bitmap("c:\***.jpg"), 3, 4);
        /// </code>
        /// </example>
        public static ImageList SplitImageList(Image src, int row, int col)
        {
            try
            {
                return SplitImageList(new Bitmap(src), row, col);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
	    /// src 비트맵을 row * col개의 비트맵을 포함한 <c>ImageList</c>형식으로 반환한다.
        /// </summary>
        /// <returns><c>ImageList</c>형식</returns>
        /// <param name="src">분할할 대상 비트맵</param>
        /// <param name="row">이미지를 row개로 수평분할한다.</param>
        /// <param name="col">이미지를 col개로 수직분할한다.</param>
        /// <example> 이미지 분할
        /// <code>
        /// ImageList imgs = ImageSplitter.SplitImageList(new Bitmap("c:\***.jpg"), 3, 4);
        /// </code>
        /// </example>
        public static ImageList SplitImageList(Bitmap src, int row, int col)
        {
            try
            {
                int i, r, c, length;
                int width, height;
                Graphics g = null;
                Bitmap bmp = null;

                PixelFormat pixFormat = src.PixelFormat;
                width = src.Width / col;
                height = src.Height / row;

                if (width <= 0 ||
                    height <= 0)
                    throw new Exception("크기가 분할하려는 수보다 작습니다.");

                ImageList lstImage = new ImageList();
                lstImage.ImageSize = new Size(width, height);
                length = row * col;
                for (i = 0; i < length; i++)
                {
                    c = i % col;
                    r = i / col;
                    bmp = new Bitmap(width, height);
                    lstImage.Images.Add(bmp);
                    g = Graphics.FromImage(bmp);
                    g.DrawImage(src, 
                                new Rectangle(0, 0, width, height),
                                new Rectangle(c * width, r * height, width, height), 
                                GraphicsUnit.Pixel);
                    g.Dispose();
                }
                return lstImage;
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
	    /// src 이미지를 row * col개의 이미지를 lstIamge에 저장한다.
        /// </summary>
        /// <returns><c>ImageList</c>형식</returns>
        /// <param name="lstImage">이미지를 저장할 이미지 리스트</param>
        /// <param name="src">분할할 대상 이미지</param>
        /// <param name="row">이미지를 row개로 수평분할한다.</param>
        /// <param name="col">이미지를 col개로 수직분할한다.</param>
        /// <example> 이미지 분할
        /// <code>
        /// ImageList imgs = new ImageList;
        /// ImageSplitter.SplitImageList(imgs, new Image("c:\***.jpg"), 3, 4);
        /// </code>
        /// </example>
        public static void SplitImageListRef(ref ImageList lstImage, Image src, int row, int col)
        {
            try
            {
                SplitImageListRef(ref lstImage, new Bitmap(src), row, col);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        /// <summary>
	    /// src 비트맵를 row * col개의 비트맵를 lstIamge에 저장한다.
        /// </summary>
        /// <returns><c>ImageList</c>형식</returns>
        /// <param name="lstImage">비트맵를 저장할 이미지 리스트</param>
        /// <param name="src">분할할 대상 비트맵</param>
        /// <param name="row">이미지를 row개로 수평분할한다.</param>
        /// <param name="col">이미지를 col개로 수직분할한다.</param>
        /// <example> 비트맵 분할
        /// <code>
        /// ImageList imgs = new ImageList;
        /// ImageSplitter.SplitImageList(imgs, new Bitmap("c:\***.jpg"), 3, 4);
        /// </code>
        /// </example>
        public static void SplitImageListRef(ref ImageList lstImage, Bitmap src, int row, int col)
        {
            try
            {
                int i, r, c, length;
                int width, height;
                Graphics g = null;
                Bitmap bmp = null;

                PixelFormat pixFormat = src.PixelFormat;
                width = src.Width / col;
                height = src.Height / row;

                if (width <= 0 ||
                    height <= 0)
                    throw new Exception("크기가 분할하려는 수보다 작습니다.");

                lstImage.Images.Clear();
                lstImage.ImageSize = new Size(width, height);
                length = row * col;
                for (i = 0; i < length; i++)
                {
                    c = i % col;
                    r = i / col;
                    bmp = new Bitmap(width, height);
                    lstImage.Images.Add(bmp);
                    g = Graphics.FromImage(bmp);
                    g.DrawImage(src, 
                                new Rectangle(0, 0, width, height),
                                new Rectangle(c * width, r * height, width, height), 
                                GraphicsUnit.Pixel);
                    g.Dispose();
                }
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
	}
}
