using System;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.IO;
using System.Collections;
using System.ComponentModel;
using System.Threading;


/*
 * FTP Client Class Library 1.1
 * Author: Melih Odabaş
 * Date: 10/June/2003
 * You may distribute this code freely or use it in your applications provided that 
 * you keep the credits of author.
 * You may edit this code, but if you do so please comment your changes clearly and 
 * state that those changes are not affliated with the author.
 * Disclaimer:
 * This code and all of its contents are provided as is. The author is not 
 * responsible for any loss or damage you might encounter due to using this code.
*/

namespace D2net.Common.Ftp
{


	/// <summary>
	/// <para>Component that encapsulates FTP methods,
	///  which are very similar to the methods in the export table of wininet.dll</para>
	/// </summary>
	public class FTPClient : Component
	{
		/// <summary>
		/// <para>Event fired when state of the connection to the server has changed.</para>
		/// </summary>
		public event ConnectionStateChanged OnConnectionStateChanged;

		/// <summary>
		/// <para>Event fired when asyncronous FTPOpen is completed.</para>
		/// </summary>
		public event AsyncConnectionOpCompleted OnAsyncOpenCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPClose is completed.</para>
		/// </summary>
		public event AsyncConnectionOpCompleted OnAsyncCloseCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPConnect is completed.</para>
		/// </summary>
		public event AsyncSessionOpCompleted OnAsyncConnectCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPQuit is completed.</para>
		/// </summary>
		public event AsyncSessionOpCompleted OnAsyncQuitCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPCreateDirectory is completed.</para>
		/// </summary>
		public event AsyncCreateDirectoryCompleted OnAsyncCreateDirectoryCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPDeleteFile is completed.</para>
		/// </summary>
		public event AsyncDeleteFileCompleted OnAsyncDeleteFileCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPFindFirstFile is completed.</para>
		/// </summary>
		public event AsyncFindFileCompleted OnAsyncFindFirstFileCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPFindNextFile is completed.</para>
		/// </summary>
		public event AsyncFindFileCompleted OnAsyncFindNextFileCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPGetCurrentDirectory is completed.</para>
		/// </summary>
		public event AsyncCurrentDirectoryOpCompleted OnAsyncGetCurrentDirectoryCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPGetFile is completed.</para>
		/// </summary>
		public event AsyncFileTransferCompleted OnAsyncGetFileCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPPutFile is completed.</para>
		/// </summary>
		public event AsyncFileTransferCompleted OnAsyncPutFileCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPRemoveDirectory is completed.</para>
		/// </summary>
		public event AsyncRemoveDirectoryCompleted OnAsyncRemoveDirectoryCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPRenameFile is completed.</para>
		/// </summary>
		public event AsyncRenameFileCompleted OnAsyncRenameFileCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPSendCommand is completed.</para>
		/// </summary>
		public event AsyncSendCommandCompleted OnAsyncSendCommandCompleted;
		/// <summary>
		/// <para>Event fired when asyncronous FTPSetCurrentDirectory is completed.</para>
		/// </summary>
		public event AsyncCurrentDirectoryOpCompleted OnAsyncSetCurrentDirectoryCompleted;
		/// <summary>
		/// <para>Event fired when data is sent during file upload.</para>
		/// </summary>
		public event FileTransferDelegate OnFileUploadDataSent;
		/// <summary>
		/// <para>Event fired when data is received during file download.</para>
		/// </summary>
		public event FileTransferDelegate OnFileDownloadDataReceived;

		/// <summary>
		/// <para>Default port number for FTP connections.</para>
		/// </summary>
		public const int DEFAULT_FTP_PORT = 21;

        public int UploadBufferSize = 65536;

        public int DownloadBufferSize = 4096;

        /// <summary>
        /// <para>Korean Encoding Support.</para>
        /// </summary>
        private static Encoding _KSC5601 = Encoding.GetEncoding(949 /* korean */);

		private Socket FTPSocket;
		private NetworkStream FTPStream;
		private StreamWriter FTPWriter;
		private string szHost="";
		private int iPort=DEFAULT_FTP_PORT;
		private string szUser="anonymous";
		private string szPasswd="anonymous@anonymous.net";
		private byte[] abyBuffer = new byte[4096];
		private ConnectionState csState=ConnectionState.Disconnected;
		private ArrayList alFileNames;
		private int iFileIndex = 0;
		private ArrayList alAsyncOpQueue;
		/// <summary>
		/// <para>Gets or sets port number for FTP connection.</para>
		/// </summary>
		public int Port
		{
			get
			{
				return iPort;
			}
			set
			{
				if (State == ConnectionState.Disconnected)
					iPort=value;
				else
					throw(new FTPClientException("Cannot set port while connected to a server. You must close the connection first by calling FTPClose()."));
			}
		}
		/// <summary>
		/// <para>Gets or sets name or IP of FTP server.</para>
		/// </summary>
		public string Host
		{
			get
			{
				return(szHost);
			}
			set
			{
				if (State == ConnectionState.Disconnected)
					szHost=value;
				else
					throw(new FTPClientException("Cannot set host while connected to a server. You must close the connection first by calling FTPClose()."));
			}
		}
		/// <summary>
		/// <para>Gets or sets username used in FTP connection.</para>
		/// </summary>
		public string User
		{
			get
			{
				return(szUser);
			}
			set
			{
				if ((State == ConnectionState.Disconnected)||(State == ConnectionState.Connecting)||(State == ConnectionState.LoggedOff))
					szUser=value;
				else
					throw(new FTPClientException("Cannot set user while logged in to a server. You must close the session first by calling FTPQuit()."));
			}
		}
		/// <summary>
		/// <para>Gets the state of FTP connection.</para>
		/// </summary>
		public ConnectionState State
		{
			get
			{
				return(csState);
			}
		}


		private void SetConnectionState(ConnectionState connectionState)
		{
			csState = connectionState;
			if (OnConnectionStateChanged != null)
				OnConnectionStateChanged(this, new ConnectionStateChangedEventArgs(State));
		}

		/// <summary>
		/// <para>Sets the password used in FTP connection.</para>
		/// <param name="passwd">New password value.</param>
		/// </summary>
		public void SetPassword(string passwd)
		{
			if ((State == ConnectionState.Disconnected)||(State == ConnectionState.Connecting)||(State == ConnectionState.LoggedOff))
				szPasswd=passwd;
			else
				throw(new FTPClientException("Cannot set password while logged in to a server. You must close session first by calling FTPQuit()."));
		}

		private void SendCommand(string command)
		{
			FTPWriter.Write(command+"\r\n");
			FTPWriter.Flush();
		}

		private string GetReply()
		{
			int iRet;
			string szReply="";
			bool bRead=true;
			do
			{
				iRet = FTPSocket.Receive(abyBuffer,0,4096,SocketFlags.None);
				szReply += Encoding.ASCII.GetString(abyBuffer,0,iRet);
				bRead = IsReplyCompleted(szReply);
			}
			while (!bRead);
			return(szReply);
		}

		private bool IsReplyCompleted(string reply)
		{
			StringReader srReply = new StringReader(reply);
			string szLine;
			bool bCompleted = false;
			while (srReply.Peek()>-1)
			{
				szLine = srReply.ReadLine();
				if (char.IsDigit(szLine,0)&&char.IsDigit(szLine,1)&&char.IsDigit(szLine,2)&&szLine.Substring(3,1).Equals(" "))
				{
					bCompleted = true;
					break;
				}
			}
			srReply.Close();
			return(bCompleted);
		}

		private bool CheckReply(string reply, string expectedcode)
		{
			return(reply.StartsWith(expectedcode));
		}

		private string RemoveReturnCode(string message)
		{
			return(message.Substring(4));
		}

		private void OpenPassiveDataConnection(ref Socket clisock)
		{
			try
			{
				string szReply;
				SendCommand("PASV");
				szReply = GetReply();
				if (!CheckReply(szReply,"227"))
					throw(new FTPClientException(RemoveReturnCode(szReply)));
				szReply = szReply.Substring(szReply.LastIndexOf("(")+1, szReply.LastIndexOf(")")-szReply.LastIndexOf("(")-1);
				string[] aszConnData = szReply.Split(new Char[] {','},6);
				IPEndPoint ipeClient = new IPEndPoint(IPAddress.Parse(aszConnData[0]+"."+aszConnData[1]+"."+aszConnData[2]+"."+aszConnData[3]),(256*int.Parse(aszConnData[4]))+int.Parse(aszConnData[5]));
				clisock.Connect((EndPoint)ipeClient);
				return;
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void OpenActiveDataConnection(ref Socket servsock)
		{
			try
			{
				string szReply;
				IPEndPoint ipeClient = (IPEndPoint)FTPSocket.LocalEndPoint;
				servsock.Bind(new IPEndPoint(IPAddress.Parse(ipeClient.Address.ToString()),0));
				IPEndPoint ipeServer = (IPEndPoint)servsock.LocalEndPoint;
				int iTransferPort = ipeServer.Port;
				int iPortPart1 = (iTransferPort&0xff00) >> 8;
				int iPortPart2 = iTransferPort&0x00ff;
				SendCommand("PORT "+ipeClient.Address.ToString().Replace('.',',')+","+iPortPart1.ToString()+","+iPortPart2.ToString());
				szReply = GetReply();
				if (!CheckReply(szReply,"200"))
					throw(new FTPClientException(RemoveReturnCode(szReply)));
				servsock.Listen(5);
				return;
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void DownloadFile(Socket sock, string localfile, string remotefile, long remotefilelength, FileTransferResuming resume)
		{
			try
			{
				FileStream fs;
				if (resume == FileTransferResuming.Overwrite)
					fs = new FileStream(localfile, FileMode.Create,FileAccess.ReadWrite,FileShare.Read);
				else
					fs = new FileStream(localfile, FileMode.OpenOrCreate,FileAccess.ReadWrite,FileShare.Read);
				try
				{
					int iRet = 1;
                    byte[] abyBuf = new byte[DownloadBufferSize];
					if (resume == FileTransferResuming.Resume)
						fs.Seek(0,SeekOrigin.End);
					if (OnFileDownloadDataReceived != null)
						OnFileDownloadDataReceived(this, new FileTransferEventArgs(Host, Port, User, localfile, remotefile, fs.Position, remotefilelength));
					while (iRet>0)
					{
                        iRet = sock.Receive(abyBuf, 0, DownloadBufferSize, SocketFlags.None);
						fs.Write(abyBuf,0,iRet);
						fs.Flush();
						if (OnFileDownloadDataReceived != null)
							OnFileDownloadDataReceived(this, new FileTransferEventArgs(Host, Port, User, localfile, remotefile, fs.Position, remotefilelength));
					}
				}
				catch (System.Exception ex)
				{
					if (OnFileDownloadDataReceived != null)
						OnFileDownloadDataReceived(this, new FileTransferEventArgs((Exception)ex));
					throw(ex);
				}
				finally
				{
					fs.Close();
				}
			}
			catch (System.Exception ex) {throw(ex);}

		}

		private void UploadFile(Socket sock, string localfile, string remotefile, long offset)
		{
			try
			{
				FileStream fs = new FileStream(localfile, FileMode.Open,FileAccess.Read,FileShare.Read);
				try
				{
					int iRet = 1;
                    byte[] abyBuf = new byte[UploadBufferSize];
					fs.Seek(offset, SeekOrigin.Begin);
					if (OnFileUploadDataSent != null)
						OnFileUploadDataSent(this, new FileTransferEventArgs(Host, Port, User, localfile, remotefile, fs.Position, fs.Length));
					while (fs.Position<fs.Length)
					{
                        iRet = fs.Read(abyBuf, 0, UploadBufferSize);
						sock.Send(abyBuf,0,iRet,SocketFlags.None);
						if (OnFileUploadDataSent != null)
							OnFileUploadDataSent(this, new FileTransferEventArgs(Host, Port, User, localfile, remotefile, fs.Position, fs.Length));
					}
				}
				catch (System.Exception ex)
				{
					if (OnFileUploadDataSent != null)
						OnFileUploadDataSent(this, new FileTransferEventArgs((Exception)ex));
					throw(ex);
				}
				finally
				{
					fs.Close();
				}
			}
			catch (System.Exception ex) {throw(ex);}

		}

		private string ReadIncomingTextData(Socket sock)
		{
			try
			{
				int iRet = 1;
				byte[] abyBuf = new byte[1024];
				string szReply = "";
				while (iRet>0)
				{
					iRet = sock.Receive(abyBuf,0,1024,SocketFlags.None);
					szReply += Encoding.ASCII.GetString(abyBuf,0,iRet);
				}
				return(szReply);
			}
			catch (System.Exception ex) {throw(ex);}
		}

		/// <summary>
		/// <para>Constructor for <see cref="T:Melih.FTPClient.FTPClient"/> class.</para>
		/// </summary>
		public FTPClient()
		{
			FTPSocket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
			alFileNames = new ArrayList();
			alAsyncOpQueue = new ArrayList();
		}

		/// <summary>
		/// <para>Opens FTP connection to specified host through specified port and consumes welcome message.
		/// If successful, <see cref="P:Melih.FTPClient.FTPClient.State"/> property is set to <see cref="F:Melih.FTPClient.ConnectionState.Connecting"/>. Otherwise, <see cref="P:Melih.FTPClient.FTPClient.State"/> property is set to <see cref="F:Melih.FTPClient.ConnectionState.Disconnected"/>. </para>
		/// </summary>
		/// <param name="host">Name or IP of FTP server.</param>
		/// <param name="port">Port number for FTP connection.</param>
		public void FTPOpen(string host, int port)
		{
			try
			{
				if (State != ConnectionState.Disconnected)
					throw(new FTPClientException("You must close your current connection before opening a new one."));
				try
				{
					Host = host;
					Port = port;
					SetConnectionState(ConnectionState.Connecting);
					IPEndPoint ipend = new IPEndPoint(Dns.Resolve(Host).AddressList[0],Port);
//					FTPSocket.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReceiveBuffer, 4096);
//					FTPSocket.SetSocketOption(SocketOptionLevel.Tcp, SocketOptionName.ReceiveTimeout, 60000);
//					FTPSocket.SetSocketOption(SocketOptionLevel.Tcp, SocketOptionName.SendTimeout, 60000);
					FTPSocket.Connect((EndPoint)ipend);
					FTPStream = new NetworkStream(FTPSocket,FileAccess.ReadWrite,true);
					FTPWriter = new StreamWriter(FTPStream, _KSC5601);
					string szReply = GetReply();
					if (!CheckReply(szReply,"220"))
						throw(new FTPClientException(RemoveReturnCode(szReply)));
				}
				catch (System.Exception ex)
				{
					SetConnectionState(ConnectionState.Disconnected);
					throw(ex);
				}
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Opens FTP connection to host specified in property Host through port specified in property Port and consumes welcome message.
		/// Host and Port properties has to be set prior to calling this method. If successful, <see cref="P:Melih.FTPClient.FTPClient.State"/> property is set to <see cref="F:Melih.FTPClient.ConnectionState.Connecting"/>. Otherwise, <see cref="P:Melih.FTPClient.FTPClient.State"/> property is set to <see cref="F:Melih.FTPClient.ConnectionState.Disconnected"/>.</para>
		/// </summary>
		public void FTPOpen()
		{
			try
			{
				FTPOpen(Host,Port);
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Makes authorization for specified user with specified password. As soon as this method is called, <see cref="P:Melih.FTPClient.FTPClient.State"/> property is set to <see cref="F:Melih.FTPClient.ConnectionState.Authorizing"/>. 
		/// If successful, <see cref="P:Melih.FTPClient.FTPClient.State"/> property is set to <see cref="F:Melih.FTPClient.ConnectionState.Connected"/>. Otherwise, <see cref="P:Melih.FTPClient.FTPClient.State"/> property is set to <see cref="F:Melih.FTPClient.ConnectionState.Connecting"/>.</para>
		/// </summary>
		/// <param name="user">Username for FTP connection.</param>
		/// <param name="passwd">Password for the user specified in user parameter.</param>
		public void FTPConnect(string user, string passwd)
		{
			try
			{
				ConnectionState oldState = State;
				try
				{
					string szReply;
					User = user;
					SetPassword(passwd);
					SetConnectionState(ConnectionState.Authorizing);
					SendCommand("USER "+User);
					szReply = GetReply();
					if (!(CheckReply(szReply,"331")||(CheckReply(szReply,"230"))))
						throw(new FTPClientException(RemoveReturnCode(szReply)));
					SendCommand("PASS "+passwd);
					szReply = GetReply();
					if (!CheckReply(szReply,"230"))
						throw(new FTPClientException(RemoveReturnCode(szReply)));
					SetConnectionState(ConnectionState.Connected);

				}
				catch (System.Exception ex)
				{
					SetConnectionState(oldState);
					throw(ex);
				}
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Makes authorization for user specified in User property with password specified by calling <see cref="M:Melih.FTPClient.FTPClient.SetPassword(System.String)"/> method. User property has to be set and <see cref="M:Melih.FTPClient.FTPClient.SetPassword(System.String)"/> method has to be called prior to calling this method. 
		/// As soon as this method is called, <see cref="P:Melih.FTPClient.FTPClient.State"/> property is set to <see cref="F:Melih.FTPClient.ConnectionState.Authorizing"/>. 
		/// If successful, <see cref="P:Melih.FTPClient.FTPClient.State"/> property is set to <see cref="F:Melih.FTPClient.ConnectionState.Connected"/>. Otherwise, <see cref="P:Melih.FTPClient.FTPClient.State"/> property is set to <see cref="F:Melih.FTPClient.ConnectionState.Connecting"/>.</para>
		/// </summary>
		public void FTPConnect()
		{
			try
			{
				FTPConnect(User, szPasswd);
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Changes remote directory.</para>
		/// </summary>
		/// <param name="path">Directory name.</param>
		public void FTPSetCurrentDirectory(string path)
		{
			try
			{
				string szReply;
				SendCommand("CWD "+path);
				szReply = GetReply();
				if (!CheckReply(szReply,"250"))
					throw(new FTPClientException(RemoveReturnCode(szReply)));
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}


		/// <summary>
		/// <para>Determines the path of the remote directory.</para>
		/// </summary>
		/// <returns>Remote directory.</returns>
		public string FTPGetCurrentDirectory()
		{
			try
			{
				string szReply;
				SendCommand("PWD");
				szReply = GetReply();
				if (!CheckReply(szReply,"257"))
					throw(new FTPClientException(RemoveReturnCode(szReply)));
				return(szReply.Substring(szReply.IndexOf('"')+1,szReply.LastIndexOf('"')-szReply.IndexOf('"')-1));
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void FTPSetTransferType(FileTransferType type)
		{
			try
			{
				string szReply;
				switch (type)
				{
					case FileTransferType.ASCII:
						SendCommand("TYPE A");
						break;
					case FileTransferType.Binary:
						SendCommand("TYPE I");
						break;
					default:
						throw(new FTPClientException("Unsupported file transfer type."));
				}
				szReply = GetReply();
				if (!CheckReply(szReply,"200"))
					throw(new FTPClientException(RemoveReturnCode(szReply)));
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Transfers a file on the FTP server to local machine. Uses active data connection by default.</para>
		/// </summary>
		/// <param name="remotefile">Name of the remote file.</param>
		/// <param name="localfile">Full path of the local file that will be created.</param>
		/// <param name="type">Data transfer type. FileTransferType.ASCII for files containing text, and FileTransferType.Binary for others.</param>
		public void FTPGetFile(string remotefile, string localfile, FileTransferType type)
		{
			try
			{
				FTPGetFile(remotefile, localfile, type, DataConnectionType.Active);
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Transfers a file on the FTP server to local machine.</para>
		/// </summary>
		/// <param name="remotefile">Name of the remote file.</param>
		/// <param name="localfile">Full path of the local file that will be created.</param>
		/// <param name="type">Data transfer type. FileTransferType.ASCII for files containing text, and FileTransferType.Binary for others.</param>
		/// <param name="conntype">Data connection type.</param>
		public void FTPGetFile(string remotefile, string localfile, FileTransferType type, DataConnectionType conntype)
		{
			try
			{
				FTPSetTransferType(type);
				FTPGetFile(remotefile, localfile, conntype, FileTransferResuming.Overwrite);
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Transfers a file on the FTP server to local machine.</para>
		/// </summary>
		/// <param name="remotefile">Name of the remote file.</param>
		/// <param name="localfile">Full path of the local file that will be created.</param>
		/// <param name="type">Data transfer type. FileTransferType.ASCII for files containing text, and FileTransferType.Binary for others.</param>
		/// <param name="conntype">Data connection type.</param>
		/// <param name="resume">Resume unfinished transfer or overwrite.</param>
		public void FTPGetFile(string remotefile, string localfile, FileTransferType type, DataConnectionType conntype, FileTransferResuming resume)
		{
			try
			{
				FTPSetTransferType(type);
				FTPGetFile(remotefile, localfile, conntype, resume);
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}
		private void FTPGetFile(string remotefile, string localfile, DataConnectionType conntype, FileTransferResuming resume)
		{
			try
			{
				Socket sserver = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
				Socket sclient = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
				try
				{
					string szReply;
					FileData fileData;
					long lOffset = 0;
					long lFileSize = -1;
					fileData = FTPFindFirstFile(remotefile, conntype);
					if (fileData.ErrorReadingFileSize)
						lFileSize = 0;
					else
						lFileSize = fileData.FileSizeLong;
					if (resume == FileTransferResuming.Resume)
					{
						FileStream fs = new FileStream(localfile, FileMode.OpenOrCreate,FileAccess.Read,FileShare.Read);
						try
						{
							if (fileData.ErrorReadingFileSize)
								throw(new FTPClientException("Cannot read file size from server, cannot resume download."));
							if (fs.Length > fileData.FileSizeLong)
								throw(new FTPClientException("Remote file is smaller than local file, cannot resume dowmload."));
							lOffset = fs.Length;
						}
						catch (System.Exception ex)
						{
							throw(ex);
						}
						finally
						{
							fs.Close();
						}
					}
					else if (resume != FileTransferResuming.Overwrite)
					{
						throw(new FTPClientException("Unsupported transfer resuming type"));
					}
					else
					{
						lOffset = 0;
					}
					if (conntype == DataConnectionType.Active)
					{
						OpenActiveDataConnection(ref sserver);
					}
					else if (conntype == DataConnectionType.Passive)
					{
						OpenPassiveDataConnection(ref sclient);
					}
					else
					{
						throw(new FTPClientException("Unsupported data connection type"));
					}

					sserver.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReceiveTimeout, 30000);
					sserver.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.SendTimeout, 30000);

					if (lOffset > 0)
					{
						SendCommand("REST "+lOffset.ToString());
						szReply = GetReply();
						if (!CheckReply(szReply,"350"))
							throw(new FTPClientException("Cannot resume download. "+RemoveReturnCode(szReply)));
					}
					SendCommand("RETR "+remotefile);
					szReply = GetReply();
					if (!(CheckReply(szReply,"150")||(CheckReply(szReply,"125"))))
						throw(new FTPClientException(RemoveReturnCode(szReply)));
					try
					{
						if (conntype == DataConnectionType.Active)
						{
							sclient = sserver.Accept();
						}
						DownloadFile(sclient, localfile, remotefile, lFileSize, resume);
					}
					catch (System.Exception ex)
					{
						throw(ex);
					}
					finally
					{
						szReply = GetReply();
					}
					if (!CheckReply(szReply,"226"))
						throw(new FTPClientException(RemoveReturnCode(szReply)));
				}
				catch (System.Exception ex)
				{
					throw(ex);
				}
				finally
				{
					sclient.Close();
					sserver.Close();
				}
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Transfers a file on the local machine to FTP server. Uses active data connection by default.</para>
		/// </summary>
		/// <param name="localfile">Full path of the local file that will be uploaded.</param>
		/// <param name="remotefile">Name of the remote file.</param>
		/// <param name="type">Data transfer type. FileTransferType.ASCII for files containing text, and FileTransferType.Binary for others.</param>
		public void FTPPutFile(string localfile, string remotefile, FileTransferType type)
		{
			try
			{
				FTPPutFile(localfile, remotefile, type, DataConnectionType.Active);
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Transfers a file on the local machine to FTP server.</para>
		/// </summary>
		/// <param name="localfile">Full path of the local file that will be uploaded.</param>
		/// <param name="remotefile">Name of the remote file.</param>
		/// <param name="type">Data transfer type. FileTransferType.ASCII for files containing text, and FileTransferType.Binary for others.</param>
		/// <param name="conntype">Data connection type.</param>
		public void FTPPutFile(string localfile, string remotefile, FileTransferType type, DataConnectionType conntype)
		{
			try
			{
				FTPSetTransferType(type);
				FTPPutFile(localfile, remotefile, conntype, FileTransferResuming.Overwrite);
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}


		/// <summary>
		/// <para>Transfers a file on the local machine to FTP server.</para>
		/// </summary>
		/// <param name="localfile">Full path of the local file that will be uploaded.</param>
		/// <param name="remotefile">Name of the remote file.</param>
		/// <param name="type">Data transfer type. FileTransferType.ASCII for files containing text, and FileTransferType.Binary for others.</param>
		/// <param name="conntype">Data connection type.</param>
		/// <param name="resume">Resume unfinished transfer or overwrite.</param>
		public void FTPPutFile(string localfile, string remotefile, FileTransferType type, DataConnectionType conntype, FileTransferResuming resume)
		{
			try
			{
				FTPSetTransferType(type);
				FTPPutFile(localfile, remotefile, conntype, resume);
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void FTPPutFile(string localfile, string remotefile, DataConnectionType conntype, FileTransferResuming resume)
		{
			try
			{
				Socket sserver = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
				Socket sclient = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
				try
				{
					string szReply;
					FileData fileData;
					long lOffset=0;
					if (resume == FileTransferResuming.Resume)
					{
						FileStream fs = new FileStream(localfile, FileMode.Open,FileAccess.Read,FileShare.Read);
						try
						{
							fileData = FTPFindFirstFile(remotefile, conntype);
							if (fileData.ErrorReadingFileSize)
								throw(new FTPClientException("Cannot read file size from server, cannot resume upload."));
							if (fs.Length < fileData.FileSizeLong)
								throw(new FTPClientException("Remote file is larger than local file, cannot resume upload."));
							lOffset = fileData.FileSizeLong;
						}
						catch (System.Exception ex)
						{
							throw(ex);
						}
						finally
						{
							fs.Close();
						}
					}
					else if (resume != FileTransferResuming.Overwrite)
					{
						throw(new FTPClientException("Unsupported transfer resuming type"));
					}
					else
					{
						lOffset = 0;
					}
					if (conntype == DataConnectionType.Active)
					{
						OpenActiveDataConnection(ref sserver);
					}
					else if (conntype == DataConnectionType.Passive)
					{
						OpenPassiveDataConnection(ref sclient);
					}
					else
					{
						throw(new FTPClientException("Unsupported data connection type"));
					}

					sserver.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReceiveTimeout, 30000);
					sserver.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.SendTimeout, 30000);

					if (lOffset > 0)
					{
						SendCommand("APPE "+remotefile);
						szReply = GetReply();
						if (!(CheckReply(szReply,"150")||(CheckReply(szReply,"125"))))
							throw(new FTPClientException("Cannot resume upload. "+RemoveReturnCode(szReply)));
					}
					else
					{
						SendCommand("STOR "+remotefile);
						szReply = GetReply();
						if (!(CheckReply(szReply,"150")||(CheckReply(szReply,"125"))))
							throw(new FTPClientException(RemoveReturnCode(szReply)));
					}
					try
					{
						if (conntype == DataConnectionType.Active)
						{
							sclient = sserver.Accept();
						}
						UploadFile(sclient, localfile, remotefile, lOffset);
					}
					catch(System.Exception ex)
					{
						throw(ex);
					}
					finally
					{
						sclient.Close();
						szReply = GetReply();
					}
					if (!CheckReply(szReply,"226"))
						throw(new FTPClientException(RemoveReturnCode(szReply)));
				}
				catch (System.Exception ex)
				{
					throw(ex);
				}
				finally
				{
					sclient.Close();
					sserver.Close();
				}
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Finds the first file whose name matches the criteria specied in mask parameter. Raises <see cref="T:Melih.FTPClient.FTPClientException"/> if no file matches the criteria. 
		/// Check <see cref="P:Melih.FTPClient.FileData.ErrorReadingFileDate"/> and <see cref="P:Melih.FTPClient.FileData.ErrorReadingFileSize"/> of returned
		/// <see cref="T:Melih.FTPClient.FileData"/> class to see if an error has occured during reading FileSize and FileDate from remote server.</para>
		/// </summary>
		/// <param name="mask">The criteria for the names of the files to be searched for, such as "*.zip"</param>
		/// <param name="conntype">Data connection type</param>
		/// <returns>File data of the first file that matches to the criteria.</returns>
		public FileData FTPFindFirstFile(string mask, DataConnectionType conntype)
		{
			try
			{
				Socket sserver = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
				Socket sclient = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
				try
				{
					string szReply;
					alFileNames.Clear();
					iFileIndex = 0;
					FTPSetTransferType(FileTransferType.Binary);
					if (conntype == DataConnectionType.Active)
					{
						OpenActiveDataConnection(ref sserver);
					}
					else if (conntype == DataConnectionType.Passive)
					{
						OpenPassiveDataConnection(ref sclient);
					}
					else
					{
						throw(new FTPClientException("Unsupported data connection type"));
					}

					sserver.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.ReceiveTimeout, 30000);
					sserver.SetSocketOption(SocketOptionLevel.Socket, SocketOptionName.SendTimeout, 30000);
					sserver.Blocking = false;

					SendCommand("NLST "+mask);
					szReply = GetReply();
					if (!(CheckReply(szReply,"150")||(CheckReply(szReply,"125"))))
						throw(new FTPClientException(RemoveReturnCode(szReply)));
					if (conntype == DataConnectionType.Active)
					{
						for (int i = 0; i < 100; i++)
						{
							try
							{
								sclient = sserver.Accept();
								if (sclient != null)
									break;
							}
							catch (Exception ex)
							{
							}
							finally
							{
								Thread.Sleep(300);
							}
						}

						if (sclient == null)
							throw new Exception("서버 소켓 대기모드 타임아웃");
					}
					string szData = ReadIncomingTextData(sclient);
					szReply = GetReply();
					if (!CheckReply(szReply,"226"))
						throw(new FTPClientException(RemoveReturnCode(szReply)));
					StringReader szrReader = new StringReader(szData);
					while (szrReader.Peek()>-1)
					{
						alFileNames.Add(szrReader.ReadLine());
					}
					szrReader.Close();
					return(FTPFindNextFile());
				}
				catch (System.Exception ex)
				{
					throw(ex);
				}
				finally
				{
					sclient.Close();
					sserver.Close();
				}
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Finds the first file whose name matches the criteria specied in mask parameter. Raises <see cref="T:Melih.FTPClient.FTPClientException"/> if no file matches the criteria. 
		/// Check <see cref="P:Melih.FTPClient.FileData.ErrorReadingFileDate"/> and <see cref="P:Melih.FTPClient.FileData.ErrorReadingFileSize"/> of returned
		/// <see cref="T:Melih.FTPClient.FileData"/> class to see if an error has occured during reading FileSize and FileDate from remote server. Uses active data connection by default.</para>
		/// </summary>
		/// <param name="mask">The criteria for the names of the files to be searched for, such as "*.zip"</param>
		/// <returns>File data of the first file that matches to the criteria.</returns>
		public FileData FTPFindFirstFile(string mask)
		{
			try
			{
				return(FTPFindFirstFile(mask, DataConnectionType.Active));
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Indicates if any more file data exists that can be queried by <see cref="M:Melih.FTPClient.FTPClient.FTPFindNextFile"/>. 
		/// Use in conjunction with <see cref="M:Melih.FTPClient.FTPClient.Peek"/>.</para>
		/// </summary>
		/// <example>Here is a sample on how to use FTPFindFirstFile, Peek and FTPFindNextFile. 
		/// <code>
		/// filedata = ftpcli.FTPFindFirstFile("*.*");
		/// while (ftpcli.Peek())
		/// {
		///		filedata = ftpcli.FTPFindNextFile();
		/// }
		/// </code>
		/// </example>
		/// <returns>True if more file data exists, false otherwise.</returns>
		public bool Peek()
		{
			if (iFileIndex>=alFileNames.Count)
				return false;
			else
				return true;
		}

		/// <summary>
		/// <para>Finds the next file in row whose name matches to the criteria specified in <see cref="M:Melih.FTPClient.FTPClient.FTPFindFirstFile(System.String)"/>. 
		/// Use in conjunction with <see cref="M:Melih.FTPClient.FTPClient.Peek"/>. Check <see cref="P:Melih.FTPClient.FileData.ErrorReadingFileDate"/> and <see cref="P:Melih.FTPClient.FileData.ErrorReadingFileSize"/> of returned
		/// <see cref="T:Melih.FTPClient.FileData"/> class to see if an error has occured during reading FileSize and FileDate from remote server.</para>
		/// </summary>
		/// <example>Here is a sample on how to use FTPFindFirstFile, Peek and FTPFindNextFile.
		/// <code>
		/// filedata = ftpcli.FTPFindFirstFile("*.*");
		/// while (ftpcli.Peek())
		/// {
		///		filedata = ftpcli.FTPFindNextFile();
		/// }
		/// </code>
		/// </example>
		/// <returns>File data of a file that matches to the criteria.</returns>
		public FileData FTPFindNextFile()
		{
			try
			{
				if (!Peek())
					throw(new FTPClientException("No more files."));
				string szBuffer;
				FileData fdItem = new FileData();
				fdItem.FileName = alFileNames[iFileIndex].ToString();
				SendCommand("SIZE "+fdItem.FileName);
				szBuffer = GetReply();
				if (CheckReply(szBuffer,"213"))
				{
					fdItem.FileSizeLong = long.Parse(RemoveReturnCode(szBuffer));
					try
					{
						fdItem.FileSize = (int)fdItem.FileSizeLong;
					}
					catch{}
				}
				else
					fdItem.ErrorReadingFileSize = true;
				SendCommand("MDTM "+fdItem.FileName);
				szBuffer = GetReply();
				if (CheckReply(szBuffer,"213"))
				{
					szBuffer = RemoveReturnCode(szBuffer);				
					fdItem.FileDateTime = new DateTime(int.Parse(szBuffer.Substring(0,4)),
						int.Parse(szBuffer.Substring(4,2)),
						int.Parse(szBuffer.Substring(6,2)),
						int.Parse(szBuffer.Substring(8,2)),
						int.Parse(szBuffer.Substring(10,2)),
						int.Parse(szBuffer.Substring(12,2)));
				}
				else
				{
					fdItem.ErrorReadingFileDate = true;
					fdItem.FileDateTime = new DateTime(0);
				}
				return(fdItem);
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
			finally
			{
				iFileIndex++;
			}
		}

		/// <summary>
		/// <para>Creates a directory on FTP server.</para>
		/// </summary>
		/// <param name="directoryname">Name of the newly created directory.</param>
		public void FTPCreateDirectory(string directoryname)
		{
			try
			{
				string szReply;
				SendCommand("MKD "+directoryname);
				szReply = GetReply();
				if (!CheckReply(szReply,"257"))
					throw(new FTPClientException(RemoveReturnCode(szReply)));
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Deletes a directory from FTP server.</para>
		/// </summary>
		/// <param name="directoryname">Name of the directory to be deleted.</param>
		public void FTPRemoveDirectory(string directoryname)
		{
			try
			{
				string szReply;
				SendCommand("RMD "+directoryname);
				szReply = GetReply();
				if (!CheckReply(szReply,"250"))
					throw(new FTPClientException(RemoveReturnCode(szReply)));
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Deletes a file from FTP server.</para>
		/// </summary>
		/// <param name="filename">Name of the file to be deleted.</param>
		public void FTPDeleteFile(string filename)
		{
			try
			{
				string szReply;
				SendCommand("DELE "+filename);
				szReply = GetReply();
				if (!CheckReply(szReply,"250"))
					throw(new FTPClientException(RemoveReturnCode(szReply)));
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Renames a file that resides on FTP server.</para>
		/// </summary>
		/// <param name="renamefrom">Original name of the file.</param>
		/// <param name="renameto">New name of the file.</param>
		public void FTPRenameFile(string renamefrom, string renameto)
		{
			try
			{
				string szReply;
				SendCommand("RNFR "+renamefrom);
				szReply = GetReply();
				if (!CheckReply(szReply,"350"))
					throw(new FTPClientException(RemoveReturnCode(szReply)));
				SendCommand("RNTO "+renameto);
				szReply = GetReply();
				if (!CheckReply(szReply,"250"))
					throw(new FTPClientException(RemoveReturnCode(szReply)));
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Sends a command string to FTP server</para>
		/// </summary>
		/// <param name="command">Command string</param>
		/// <returns>Reply of the FTP server.</returns>
		public string FTPSendCommand(string command)
		{
			try
			{
				SendCommand(command);
				return(GetReply());
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Closes FTP session. Another session can be established by <see cref="M:Melih.FTPClient.FTPClient.FTPConnect"/>.</para>
		/// </summary>
		public void FTPQuit()
		{
			try
			{
				ConnectionState oldState = State;
				try
				{
					SetConnectionState(ConnectionState.LoggingOff);
					SendCommand("QUIT");
					SetConnectionState(ConnectionState.LoggedOff);
				}
				catch (System.Exception ex)
				{
					SetConnectionState(oldState);
					throw(ex);
				}
			}
			catch (System.Exception ex)
			{
				throw (ex);
			}
		}


		/// <summary>
		/// <para>Grecefully closes the FTP connection and discards all the resources. You should not call <see cref="M:Melih.FTPClient.FTPClient.FTPOpen"/> after this method. 
		/// <see cref="P:Melih.FTPClient.FTPClient.State"/> is set to <see cref="F:Melih.FTPClient.ConnectionState.Disconnecting"/> as soon as this method is called, and to <see cref="F:Melih.FTPClient.ConnectionState.Disconnected"/> after this method exits.</para>
		/// </summary>
		public void FTPClose()
		{
			FTPClose(true);
		}

		/// <summary>
		/// <para>Closes the FTP connection and discards all the resources. You should not call <see cref="M:Melih.FTPClient.FTPClient.FTPOpen"/> after this method. 
		/// <see cref="P:Melih.FTPClient.FTPClient.State"/> is set to <see cref="F:Melih.FTPClient.ConnectionState.Disconnecting"/> as soon as this method is called, and to <see cref="F:Melih.FTPClient.ConnectionState.Disconnected"/> after this method exits.</para>
		/// </summary>
		/// <param name="gracefulshutdown">Closes current session before closing connection if true, directly closes connection if false.</param>
		public void FTPClose(bool gracefulshutdown)
		{
			try
			{
				if ((State == ConnectionState.Connected)&&gracefulshutdown)
					FTPQuit();
				SetConnectionState(ConnectionState.Disconnecting);
				FTPSocket.Shutdown(SocketShutdown.Both);
				FTPSocket.Close();
				FTPStream.Close();
				FTPWriter.Close();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
			finally
			{
				SetConnectionState(ConnectionState.Disconnected);
			}
		}

		private object[] GetAsyncParams(AsyncOperations optype)
		{
			try
			{
				lock (alAsyncOpQueue)
				{
					for (int iLoopCounter=0; iLoopCounter<alAsyncOpQueue.Count; iLoopCounter++)
					{
						object[] aoParams = (object[])alAsyncOpQueue[iLoopCounter];
						if (optype == (AsyncOperations)aoParams.GetValue(0))
						{
							alAsyncOpQueue.RemoveAt(iLoopCounter);
							return(aoParams);
						}
					}
				}
				throw(new FTPClientException("Async operation "+optype.ToString()+" cannot be found in op queue."));
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPOpen"/></para>
		/// </summary>
		/// <param name="host">Name or IP of FTP server.</param>
		/// <param name="port">Port number for FTP connection.</param>
		public void FTPAsyncOpen(string host, int port)
		{
			try
			{
				lock(alAsyncOpQueue)
				{
					object[] aoParams = new object[3];
					aoParams[0] = AsyncOperations.Open;
					aoParams[1] = host;
					aoParams[2] = port;
					alAsyncOpQueue.Add(aoParams);
				}
				new Thread(new ThreadStart(AsyncOpen)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncOpen()
		{
			try
			{
				object[] aoParams = GetAsyncParams(AsyncOperations.Open);
				FTPOpen((string)aoParams[1], (int)aoParams[2]);
				if (OnAsyncOpenCompleted != null)
					OnAsyncOpenCompleted(this, new AsyncConnectionOpCompletedEventArgs(Host, Port));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncOpenCompleted != null)
					OnAsyncOpenCompleted(this, new AsyncConnectionOpCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPClose"/></para>
		/// </summary>
		/// <param name="gracefulshutdown">Closes current session before closing connection if true, directly closes connection if false.</param>
		public void FTPAsyncClose(bool gracefulshutdown)
		{
			try
			{
				lock(alAsyncOpQueue)
				{
					object[] aoParams = new object[2];
					aoParams[0] = AsyncOperations.Close;
					aoParams[1] = gracefulshutdown;
					alAsyncOpQueue.Add(aoParams);
				}
				new Thread(new ThreadStart(AsyncClose)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncClose()
		{
			try
			{
				object[] aoParams = GetAsyncParams(AsyncOperations.Close);
				FTPClose((bool)aoParams[1]);
				if (OnAsyncCloseCompleted != null)
					OnAsyncCloseCompleted(this, new AsyncConnectionOpCompletedEventArgs(Host, Port));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncCloseCompleted != null)
					OnAsyncCloseCompleted(this, new AsyncConnectionOpCompletedEventArgs((Exception)ex));
			}
		}
		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPConnect"/></para>
		/// </summary>
		/// <param name="user">Username for FTP connection.</param>
		/// <param name="passwd">Password for the user specified in user parameter.</param>
		public void FTPAsyncConnect(string user, string passwd)
		{
			try
			{
				lock(alAsyncOpQueue)
				{
					object[] aoParams = new object[3];
					aoParams[0] = AsyncOperations.Connect;
					aoParams[1] = user;
					aoParams[2] = passwd;
					alAsyncOpQueue.Add(aoParams);
				}
				new Thread(new ThreadStart(AsyncConnect)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncConnect()
		{
			try
			{
				object[] aoParams = GetAsyncParams(AsyncOperations.Connect);
				FTPConnect((string)aoParams[1], (string)aoParams[2]);
				if (OnAsyncConnectCompleted != null)
					OnAsyncConnectCompleted(this, new AsyncSessionOpCompletedEventArgs(Host, Port, User));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncConnectCompleted != null)
					OnAsyncConnectCompleted(this, new AsyncSessionOpCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPQuit"/></para>
		/// </summary>
		public void FTPAsyncQuit()
		{
			try
			{
				new Thread(new ThreadStart(AsyncQuit)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncQuit()
		{
			try
			{
				FTPQuit();
				if (OnAsyncCloseCompleted != null)
					OnAsyncQuitCompleted(this, new AsyncSessionOpCompletedEventArgs(Host, Port, User));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncCloseCompleted != null)
					OnAsyncQuitCompleted(this, new AsyncSessionOpCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPCreateDirectory"/></para>
		/// </summary>
		/// <param name="directoryname">Name of the newly created directory.</param>
		public void FTPAsyncCreateDirectory(string directoryname)
		{
			try
			{
				lock(alAsyncOpQueue)
				{
					object[] aoParams = new object[2];
					aoParams[0] = AsyncOperations.CreateDirectory;
					aoParams[1] = directoryname;
					alAsyncOpQueue.Add(aoParams);
				}
				new Thread(new ThreadStart(AsyncCreateDirectory)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncCreateDirectory()
		{
			try
			{
				object[] aoParams = GetAsyncParams(AsyncOperations.CreateDirectory);
				FTPCreateDirectory((string)aoParams[1]);
				if (OnAsyncCreateDirectoryCompleted != null)
					OnAsyncCreateDirectoryCompleted(this, new AsyncCreateDirectoryCompletedEventArgs(Host, Port, User, (string)aoParams[1]));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncCreateDirectoryCompleted != null)
					OnAsyncCreateDirectoryCompleted(this, new AsyncCreateDirectoryCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPDeleteFile"/></para>
		/// </summary>
		/// <param name="filename">Name of the file to be deleted.</param>
		public void FTPAsyncDeleteFile(string filename)
		{
			try
			{
				lock(alAsyncOpQueue)
				{
					object[] aoParams = new object[2];
					aoParams[0] = AsyncOperations.DeleteFile;
					aoParams[1] = filename;
					alAsyncOpQueue.Add(aoParams);
				}
				new Thread(new ThreadStart(AsyncDeleteFile)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncDeleteFile()
		{
			try
			{
				object[] aoParams = GetAsyncParams(AsyncOperations.DeleteFile);
				FTPDeleteFile((string)aoParams[1]);
				if (OnAsyncDeleteFileCompleted != null)
					OnAsyncDeleteFileCompleted(this, new AsyncDeleteFileCompletedEventArgs(Host, Port, User, (string)aoParams[1]));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncDeleteFileCompleted != null)
					OnAsyncDeleteFileCompleted(this, new AsyncDeleteFileCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPFindFirstFile"/>.
		///  Found file data is returned within event arguments.</para>
		/// </summary>
		/// <param name="mask">The criteria for the names of the files to be searched for, such as "*.zip"</param>
		/// <param name="conntype">Data connection type</param>
		public void FTPAsyncFindFirstFile(string mask, DataConnectionType conntype)
		{
			try
			{
				lock(alAsyncOpQueue)
				{
					object[] aoParams = new object[3];
					aoParams[0] = AsyncOperations.FindFirstFile;
					aoParams[1] = mask;
					aoParams[2] = conntype;
					alAsyncOpQueue.Add(aoParams);
				}
				new Thread(new ThreadStart(AsyncFindFirstFile)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncFindFirstFile()
		{
			try
			{
				object[] aoParams = GetAsyncParams(AsyncOperations.FindFirstFile);
				FileData fd = FTPFindFirstFile((string)aoParams[1], (DataConnectionType)aoParams[2]);
				if (OnAsyncFindFirstFileCompleted != null)
					OnAsyncFindFirstFileCompleted(this, new AsyncFindFileCompletedEventArgs(Host, Port, User, fd));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncFindFirstFileCompleted != null)
					OnAsyncFindFirstFileCompleted(this, new AsyncFindFileCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPFindNextFile"/>.
		///  Found file data is returned within event arguments.</para>
		/// </summary>
		public void FTPAsyncFindNextFile()
		{
			try
			{
				new Thread(new ThreadStart(AsyncFindNextFile)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncFindNextFile()
		{
			try
			{
				FileData fd = FTPFindNextFile();
				if (OnAsyncFindNextFileCompleted != null)
					OnAsyncFindNextFileCompleted(this, new AsyncFindFileCompletedEventArgs(Host, Port, User, fd));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncFindNextFileCompleted != null)
					OnAsyncFindNextFileCompleted(this, new AsyncFindFileCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPGetCurrentDirectory"/>.</para>
		/// </summary>
		public void FTPAsyncGetCurrentDirectory()
		{
			try
			{
				new Thread(new ThreadStart(AsyncGetCurrentDirectory)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncGetCurrentDirectory()
		{
			try
			{
				string directory = FTPGetCurrentDirectory();
				if (OnAsyncGetCurrentDirectoryCompleted != null)
					OnAsyncGetCurrentDirectoryCompleted(this, new AsyncCurrentDirectoryOpCompletedEventArgs(Host, Port, User, directory));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncGetCurrentDirectoryCompleted != null)
					OnAsyncGetCurrentDirectoryCompleted(this, new AsyncCurrentDirectoryOpCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPGetFile"/>.</para>
		/// </summary>
		/// <param name="remotefile">Name of the remote file.</param>
		/// <param name="localfile">Full path of the local file that will be created.</param>
		/// <param name="type">Data transfer type. FileTransferType.ASCII for files containing text, and FileTransferType.Binary for others.</param>
		/// <param name="conntype">Data connection type.</param>
		/// <param name="resume">Resume unfinished transfer or overwrite.</param>
		public void FTPAsyncGetFile(string remotefile, string localfile, FileTransferType type, DataConnectionType conntype, FileTransferResuming resume)
		{
			try
			{
				lock(alAsyncOpQueue)
				{
					object[] aoParams = new object[6];
					aoParams[0] = AsyncOperations.GetFile;
					aoParams[1] = remotefile;
					aoParams[2] = localfile;
					aoParams[3] = type;
					aoParams[4] = conntype;
					aoParams[5] = resume;
					alAsyncOpQueue.Add(aoParams);
				}
				new Thread(new ThreadStart(AsyncGetFile)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncGetFile()
		{
			try
			{
				object[] aoParams = GetAsyncParams(AsyncOperations.GetFile);
				FTPGetFile((string)aoParams[1], (string)aoParams[2], (FileTransferType)aoParams[3], (DataConnectionType)aoParams[4], (FileTransferResuming)aoParams[5]);
				if (OnAsyncGetFileCompleted != null)
					OnAsyncGetFileCompleted(this, new AsyncFileTransferCompletedEventArgs(Host, Port, User, (string)aoParams[2], (string)aoParams[1], (FileTransferType)aoParams[3], (DataConnectionType)aoParams[4], (FileTransferResuming)aoParams[5]));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncGetFileCompleted != null)
					OnAsyncGetFileCompleted(this, new AsyncFileTransferCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPPutFile"/>.</para>
		/// </summary>
		/// <param name="localfile">Full path of the local file that will be uploaded.</param>
		/// <param name="remotefile">Name of the remote file.</param>
		/// <param name="type">Data transfer type. FileTransferType.ASCII for files containing text, and FileTransferType.Binary for others.</param>
		/// <param name="conntype">Data connection type.</param>
		/// <param name="resume">Resume unfinished transfer or overwrite.</param>
		public void FTPAsyncPutFile(string localfile, string remotefile, FileTransferType type, DataConnectionType conntype, FileTransferResuming resume)
		{
			try
			{
				lock(alAsyncOpQueue)
				{
					object[] aoParams = new object[6];
					aoParams[0] = AsyncOperations.PutFile;
					aoParams[1] = localfile;
					aoParams[2] = remotefile;
					aoParams[3] = type;
					aoParams[4] = conntype;
					aoParams[5] = resume;
					alAsyncOpQueue.Add(aoParams);
				}
				new Thread(new ThreadStart(AsyncPutFile)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncPutFile()
		{
			try
			{
				object[] aoParams = GetAsyncParams(AsyncOperations.PutFile);
				FTPPutFile((string)aoParams[1], (string)aoParams[2], (FileTransferType)aoParams[3], (DataConnectionType)aoParams[4], (FileTransferResuming)aoParams[5]);
				if (OnAsyncPutFileCompleted != null)
					OnAsyncPutFileCompleted(this, new AsyncFileTransferCompletedEventArgs(Host, Port, User, (string)aoParams[1], (string)aoParams[2], (FileTransferType)aoParams[3], (DataConnectionType)aoParams[4], (FileTransferResuming)aoParams[5]));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncPutFileCompleted != null)
					OnAsyncPutFileCompleted(this, new AsyncFileTransferCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPRemoveDirectory"/>.</para>
		/// </summary>
		/// <param name="directoryname">Name of the directory to be deleted.</param>
		public void FTPAsyncRemoveDirectory(string directoryname)
		{
			try
			{
				lock(alAsyncOpQueue)
				{
					object[] aoParams = new object[2];
					aoParams[0] = AsyncOperations.RemoveDirectory;
					aoParams[1] = directoryname;
					alAsyncOpQueue.Add(aoParams);
				}
				new Thread(new ThreadStart(AsyncRemoveDirectory)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncRemoveDirectory()
		{
			try
			{
				object[] aoParams = GetAsyncParams(AsyncOperations.RemoveDirectory);
				FTPRemoveDirectory((string)aoParams[1]);
				if (OnAsyncRemoveDirectoryCompleted != null)
					OnAsyncRemoveDirectoryCompleted(this, new AsyncRemoveDirectoryCompletedEventArgs(Host, Port, User, (string)aoParams[1]));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncRemoveDirectoryCompleted != null)
					OnAsyncRemoveDirectoryCompleted(this, new AsyncRemoveDirectoryCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPRenameFile"/>.</para>
		/// </summary>
		/// <param name="renamefrom">Original name of the file.</param>
		/// <param name="renameto">New name of the file.</param>
		public void FTPAsyncRenameFile(string renamefrom, string renameto)
		{
			try
			{
				lock(alAsyncOpQueue)
				{
					object[] aoParams = new object[3];
					aoParams[0] = AsyncOperations.RenameFile;
					aoParams[1] = renamefrom;
					aoParams[2] = renameto;
					alAsyncOpQueue.Add(aoParams);
				}
				new Thread(new ThreadStart(AsyncRenameFile)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncRenameFile()
		{
			try
			{
				object[] aoParams = GetAsyncParams(AsyncOperations.RenameFile);
				FTPRenameFile((string)aoParams[1], (string)aoParams[2]);
				if (OnAsyncRenameFileCompleted != null)
					OnAsyncRenameFileCompleted(this, new AsyncRenameFileCompletedEventArgs(Host, Port, User, (string)aoParams[1], (string)aoParams[2]));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncRenameFileCompleted != null)
					OnAsyncRenameFileCompleted(this, new AsyncRenameFileCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPSendCommand"/>.
		///  Reply of the server is returned within event arguments.</para>
		/// </summary>
		/// <param name="command">Command string</param>
		public void FTPAsyncSendCommand(string command)
		{
			try
			{
				lock(alAsyncOpQueue)
				{
					object[] aoParams = new object[2];
					aoParams[0] = AsyncOperations.SendCommand;
					aoParams[1] = command;
					alAsyncOpQueue.Add(aoParams);
				}
				new Thread(new ThreadStart(AsyncSendCommand)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncSendCommand()
		{
			try
			{
				object[] aoParams = GetAsyncParams(AsyncOperations.SendCommand);
				string szReply = FTPSendCommand((string)aoParams[1]);
				if (OnAsyncSendCommandCompleted != null)
					OnAsyncSendCommandCompleted(this, new AsyncSendCommandCompletedEventArgs(Host, Port, User, (string)aoParams[1], szReply));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncSendCommandCompleted != null)
					OnAsyncSendCommandCompleted(this, new AsyncSendCommandCompletedEventArgs((Exception)ex));
			}
		}

		/// <summary>
		/// <para>Asyncronous version of <see cref="M:Melih.FTPClient.FTPClient.FTPSetCurrentDirectory"/>.</para>
		/// </summary>
		/// <param name="path">Directory name.</param>
		public void FTPAsyncSetCurrentDirectory(string path)
		{
			try
			{
				lock(alAsyncOpQueue)
				{
					object[] aoParams = new object[2];
					aoParams[0] = AsyncOperations.SetCurrentDirectory;
					aoParams[1] = path;
					alAsyncOpQueue.Add(aoParams);
				}
				new Thread(new ThreadStart(AsyncSetCurrentDirectory)).Start();
			}
			catch (System.Exception ex)
			{
				throw(ex);
			}
		}

		private void AsyncSetCurrentDirectory()
		{
			try
			{
				object[] aoParams = GetAsyncParams(AsyncOperations.SetCurrentDirectory);
				FTPSetCurrentDirectory((string)aoParams[1]);
				if (OnAsyncSetCurrentDirectoryCompleted != null)
					OnAsyncSetCurrentDirectoryCompleted(this, new AsyncCurrentDirectoryOpCompletedEventArgs(Host, Port, User, FTPGetCurrentDirectory()));
			}
			catch (System.Exception ex)
			{
				if (OnAsyncSetCurrentDirectoryCompleted != null)
					OnAsyncSetCurrentDirectoryCompleted(this, new AsyncCurrentDirectoryOpCompletedEventArgs((Exception)ex));
			}
		}
	}

	/// <summary>
	/// <para>Class that implements the exceptions raised by <see cref="T:Melih.FTPClient.FTPClient"/> class.</para>
	/// </summary>
	public class FTPClientException:System.ApplicationException
	{
		/// <summary>
		/// <para>Constructor for <see cref="T:Melih.FTPClient.FTPClientException"/></para>
		/// </summary>
		/// <param name="message">Message contained in the exception.</param>
		public FTPClientException(string message):base(message)
		{
		}
	}

	/// <summary>
	/// <para>Structure definition for file information returned by
	/// <see cref="M:Melih.FTPClient.FTPClient.FTPFindFirstFile(System.String)"/> and <see cref="M:Melih.FTPClient.FTPClient.FTPFindNextFile"/> methods.</para>
	/// </summary>
	public class FileData
	{
		private string szFileName;
		private DateTime dtFileDateTime;
		private int iFileSize;
		private long lFileSize;
		private bool bErrorReadingFileSize = false;
		private bool bErrorReadingFileDate = false;
		/// <summary>
		/// <para>Name of the file.</para>
		/// </summary>
		public string FileName
		{
			get{return(szFileName);}
			set{szFileName=value;}
		}
		/// <summary>
		/// <para>Size of the file in bytes. Value is Int32 (int).</para>
		/// </summary>
		public int FileSize
		{
			get{return(iFileSize);}
			set{iFileSize=value;}
		}
		/// <summary>
		/// <para>Size of the file in bytes. Value is Int64 (long).</para>
		/// </summary>
		public long FileSizeLong
		{
			get{return(lFileSize);}
			set{lFileSize = value;}
		}
		/// <summary>
		/// <para>Indicates if an error has occured while reading file size from remote server.</para>
		/// </summary>
		public bool ErrorReadingFileSize
		{
			get{return(bErrorReadingFileSize);}
			set{bErrorReadingFileSize=value;}
		}
		/// <summary>
		/// <para>Last write time of the file.</para>
		/// </summary>
		public DateTime FileDateTime
		{
			get{return(dtFileDateTime);}
			set{dtFileDateTime=value;}
		}
		/// <summary>
		/// <para>Indicates if an error has occured while reading date of the last modification on file from remote server.</para>
		/// </summary>
		public bool ErrorReadingFileDate
		{
			get{return(bErrorReadingFileDate);}
			set{bErrorReadingFileDate=value;}
		}
		/// <summary>
		/// <para>Constructor for <see cref="T:Melih.FTPClient.FileData"/>.</para>
		/// </summary>
		public FileData()
		{
		}
	}

}
