/**
 * File Name        : AssemblyListCtrl.cs
 * Author           : kang chang goog
 * Date             : 2005-12-01
 * Version          : 1.0
 * Description      : �ش� Object���� �����ϴ� Assembly List�� ����Ѵ�.
 */
using System;
using System.Reflection;
using System.Collections;
using System.ComponentModel;
using System.Drawing;
using System.Data;
using System.Windows.Forms;

namespace D2net.Common.UI
{
    // [2005-12-01] -> ���α׷� ���� ���̺귯��(����) ����Ʈ �߰�
	/// <summary>
	/// AssemblyListCtrl�� ���� ��� �����Դϴ�.
	/// </summary>
	public class AssemblyListCtrl : System.Windows.Forms.ListView
	{
		private Hashtable _AssemblyTable = new Hashtable();
        private System.ComponentModel.IContainer components;
        private System.Windows.Forms.ImageList imgListItems;
        private ArrayList _ObjectList = new ArrayList();
        // [2006-01-25] -> ���ε� Assembly���� �źεǾ����� Assembly List�� ������
        private string[] _DenyAssembly = null;

        public object CurrnetObject
        {
            set
            {
                _ObjectList.Clear();
                _ObjectList.Add(value);
                AddAssembly(value.GetType());
                UpdateListCtrl();
            }
        }

        public object[] CurrnetObjects
        {
            get { return _ObjectList.ToArray(); }

            set
            {
                _ObjectList.Clear();
                foreach (object o in value)
                {
                    _ObjectList.Add(o);
                    AddAssembly(o.GetType());
                }
                UpdateListCtrl();
            }
        }

        // [2005-12-12] -> ǥ���ϰ��� �ϴ� Assembly�� ����Ʈ�� ������ �ָ� AllowedAssembly���� ������
        //                 Assembly�� ������ Assembly�� ����Ʈ���� �����ȴ�
        // [2006-01-25] -> ���ε� Assembly���� �źεǾ����� Assembly List�� ������
        public string[] DenyAssembly
        {
            get { return _DenyAssembly; }
            set { _DenyAssembly = value; }
        }

		public AssemblyListCtrl()
		{
			InitializeComponent();

            View = View.Details;
            FullRowSelect = true;
            HideSelection = false;
            LargeImageList = imgListItems;
            SmallImageList = imgListItems;
            HeaderStyle = ColumnHeaderStyle.Nonclickable;
            GridLines = true;

            Columns.Clear();

            ColumnHeader header = new ColumnHeader();
            // header.Width = (Width - 20) / 2;
            header.Text = "���";
            Columns.Add(header);

            header = new ColumnHeader();
            // header.Width = (Width - 20) / 2;
            header.Text = "����";
            header.TextAlign = HorizontalAlignment.Center;
            Columns.Add(header);

            header = new ColumnHeader();
            // header.Width = (Width - 20) / 2;
            header.Text = "������";
            header.TextAlign = HorizontalAlignment.Center;
            Columns.Add(header);
		}

		private void AddAssembly(Type t)
		{
            string key = "";
            bool fFind = false;

			Assembly assems = Assembly.GetAssembly(t);
            // Assembly child = null;
			AssemblyName assemName = assems.GetName();
			AssemblyName[] rassemNames = assems.GetReferencedAssemblies();

            key = assemName.Name;

            // [2005-12-12] -> _AllowedAssembly�� ���� ���͸� ����
            // [2006-01-25] -> ���ε� Assembly���� �źεǾ����� Assembly List�� ������
            if (_DenyAssembly != null)
            {
                foreach (string name in _DenyAssembly)
                {
                    if (name.ToLower() == key.ToLower())
                    {
                        fFind = true;
                        break;
                    }
                }
            }
            else
                fFind = false;

            if (!fFind)
            {
                if (_AssemblyTable[key] == null)
                    _AssemblyTable.Add(key, assemName);
                else
                    _AssemblyTable[key] = assemName;
            }

            System.Diagnostics.Debug.WriteLine(key);

			foreach (AssemblyName item in rassemNames)
			{
                fFind = false;
                // child = Assembly.LoadWithPartialName(item.FullName);

                // [2006-01-25] -> ���ε� Assembly���� �źεǾ����� Assembly List�� ������
                if (_DenyAssembly != null)
                {
                    foreach (string name in _DenyAssembly)
                    {
                        if (name.ToLower() == item.Name.ToLower())
                        {
                            fFind = true;
                            break;
                        }
                    }
                }
                else
                    fFind = false;

                if (!fFind)
                {
                    if (_AssemblyTable[item.Name] == null)
                        _AssemblyTable.Add(item.Name, item);
                    else
                        _AssemblyTable[item.Name] = item;
                }

                System.Diagnostics.Debug.WriteLine(item.Name);
			}
		}

		private void UpdateListCtrl()
		{
			try
			{
				ICollection coll = null;
                string[] itemList = new string[3];
                AssemblyName assemName = null;
                string CodeBase = null;
                ListViewItem lvi = null;
                ListViewItem lviExe = null;
                string fileName = "";

				Items.Clear();

                // [2005-12-12] -> Sorting
				coll = _AssemblyTable.Keys;
                foreach (object item in coll)
				{
                    itemList[0] = (string)item;
                    assemName = (AssemblyName)_AssemblyTable[itemList[0]];
                    itemList[1] = assemName.Version.ToString();
                    CodeBase = assemName.CodeBase;
                    // [2006-09-01] -> ���̳ʸ��� ���� �������� ǥ����
                    if (CodeBase != null &&
                        CodeBase.Length > 8)
                    {
                        fileName = CodeBase;
                        fileName = fileName.Substring(8, fileName.Length - 8).Replace("/", "\\");
                        itemList[2] = System.IO.File.GetLastWriteTime(fileName).ToShortDateString();
                    }
                    else
                        itemList[2] = "";

                    lvi = new ListViewItem(itemList);
                    
                    if (CodeBase != null &&
                        CodeBase.ToLower().EndsWith(".exe"))
                    {
                        lvi.ImageIndex = 0;
                        lviExe = lvi;
                    }
                    else
                    {
                        lvi.ImageIndex = 1;
                        Items.Add(lvi);
                    }
				}

                Sorting = SortOrder.Ascending;
                Sort();

                if (lviExe != null)
                    Items.Insert(0, lviExe);
			}
			catch (Exception ex)
			{
				throw ex;
			}
		}

		private void InitializeComponent()
		{
            this.components = new System.ComponentModel.Container();
            System.Resources.ResourceManager resources = new System.Resources.ResourceManager(typeof(AssemblyListCtrl));
            this.imgListItems = new System.Windows.Forms.ImageList(this.components);
            // 
            // imgListItems
            // 
            this.imgListItems.ColorDepth = System.Windows.Forms.ColorDepth.Depth32Bit;
            this.imgListItems.ImageSize = new System.Drawing.Size(16, 16);
            this.imgListItems.ImageStream = ((System.Windows.Forms.ImageListStreamer)(resources.GetObject("imgListItems.ImageStream")));
            this.imgListItems.TransparentColor = System.Drawing.Color.Transparent;
        }
	}
}
