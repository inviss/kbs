using System;
using System.Windows.Forms;
using System.Drawing;
using System.Drawing.Imaging;

namespace D2net.Common.UI
{
	/// <summary>
	/// ū �̹����� n * m���� �̹����� �����Ѵ�(row=n, col=m).
    /// </summary>
    /// <remarks> �̹��� ���� ���. 
    /// row�� n���̰� col�� m�� �̹��� ������ m*n���� �̹����� ���ҵǰ� �ǰ�, ������ ���� ���ҵȴ�.
    /// ���� n���̰� ���� m���� �������� ���ҵ�.
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
    /// <example> �̹��� �迭�� ����
    /// <code>
    /// Image[] imgs = ImageSplitter.SplitImage(new Image("c:\***.jpg"), 3, 4);
    /// </code>
    /// </example>
	public sealed class ImageSplitter
	{
        /// <summary>
	    /// �⺻ ������
        /// </summary>
		private ImageSplitter()
		{
		}

        /// <summary>
	    /// src �̹����� row * col���� �̹��� �迭�� �����Ѵ�.
        /// </summary>
        /// <returns>���ҵ� �̹��� �迭</returns>
        /// <param name="src">������ ��� �̹���</param>
        /// <param name="row">�̹����� row���� ��������Ѵ�.</param>
        /// <param name="col">�̹����� col���� ���������Ѵ�.</param>
        /// <example> �̹��� ����
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
	    /// src ��Ʈ���� row * col���� ��Ʈ�� �迭�� �����Ѵ�.
        /// </summary>
        /// <returns>���ҵ� ��Ʈ�� �迭</returns>
        /// <param name="src">������ ��� ��Ʈ��</param>
        /// <param name="row">�̹����� row���� ��������Ѵ�.</param>
        /// <param name="col">�̹����� col���� ���������Ѵ�.</param>
        /// <example> ��Ʈ�� ����
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
                    throw new Exception("ũ�Ⱑ �����Ϸ��� ������ �۽��ϴ�.");

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
	    /// src �̹����� row * col���� �̹����� ������ <c>ImageList</c>�������� ��ȯ�Ѵ�.
        /// </summary>
        /// <returns><c>ImageList</c>����</returns>
        /// <param name="src">������ ��� �̹���</param>
        /// <param name="row">�̹����� row���� ��������Ѵ�.</param>
        /// <param name="col">�̹����� col���� ���������Ѵ�.</param>
        /// <example> �̹��� ����
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
	    /// src ��Ʈ���� row * col���� ��Ʈ���� ������ <c>ImageList</c>�������� ��ȯ�Ѵ�.
        /// </summary>
        /// <returns><c>ImageList</c>����</returns>
        /// <param name="src">������ ��� ��Ʈ��</param>
        /// <param name="row">�̹����� row���� ��������Ѵ�.</param>
        /// <param name="col">�̹����� col���� ���������Ѵ�.</param>
        /// <example> �̹��� ����
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
                    throw new Exception("ũ�Ⱑ �����Ϸ��� ������ �۽��ϴ�.");

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
	    /// src �̹����� row * col���� �̹����� lstIamge�� �����Ѵ�.
        /// </summary>
        /// <returns><c>ImageList</c>����</returns>
        /// <param name="lstImage">�̹����� ������ �̹��� ����Ʈ</param>
        /// <param name="src">������ ��� �̹���</param>
        /// <param name="row">�̹����� row���� ��������Ѵ�.</param>
        /// <param name="col">�̹����� col���� ���������Ѵ�.</param>
        /// <example> �̹��� ����
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
	    /// src ��Ʈ�ʸ� row * col���� ��Ʈ�ʸ� lstIamge�� �����Ѵ�.
        /// </summary>
        /// <returns><c>ImageList</c>����</returns>
        /// <param name="lstImage">��Ʈ�ʸ� ������ �̹��� ����Ʈ</param>
        /// <param name="src">������ ��� ��Ʈ��</param>
        /// <param name="row">�̹����� row���� ��������Ѵ�.</param>
        /// <param name="col">�̹����� col���� ���������Ѵ�.</param>
        /// <example> ��Ʈ�� ����
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
                    throw new Exception("ũ�Ⱑ �����Ϸ��� ������ �۽��ϴ�.");

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
