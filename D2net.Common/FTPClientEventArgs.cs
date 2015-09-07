using System;

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
	/// <para>Event argument for event <see cref="E:Melih.FTPClient.FTPClient.OnConnectionStateChanged"/>.</para>
	/// </summary>
	public class ConnectionStateChangedEventArgs : EventArgs
	{
		private ConnectionState connectionState;

		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="connstate">New state of the ftp connection.</param>
		public ConnectionStateChangedEventArgs(ConnectionState connstate):base()
		{
			connectionState = connstate;
		}

		/// <summary>
		/// <para>New state of the ftp connection.</para>
		/// </summary>
		public ConnectionState NewConnectionState
		{
			get
			{
				return(connectionState);
			}
		}
	}

	/// <summary>
	/// <para>Event argument for events <see cref="E:Melih.FTPClient.FTPClient.OnAsyncOpenCompleted"/>
	///  and <see cref="E:Melih.FTPClient.FTPClient.OnAsyncCloseCompleted"/>.</para>
	/// </summary>
	public class AsyncConnectionOpCompletedEventArgs : EventArgs
	{
		private string szHost;
		private int iPort;
		private Exception ftpException;
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="host">Connected host.</param>
		/// <param name="port">Connection port.</param>
		public AsyncConnectionOpCompletedEventArgs(string host, int port):base()
		{
			szHost = host;
			iPort = port;
		}
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="exception">Exception that has been raised during execution of async method.</param>
		public AsyncConnectionOpCompletedEventArgs(Exception exception):base()
		{
			ftpException = exception;
		}
		/// <summary>
		/// <para>Connected host.</para>
		/// </summary>
		public string Host
		{
			get
			{
				return(szHost);
			}
		}
		/// <summary>
		/// <para>Connection port.</para>
		/// </summary>
		public int Port
		{
			get
			{
				return(iPort);
			}
		}

		/// <summary>
		/// <para>Exception that has been raised during execution of async method.</para>
		/// </summary>
		public Exception FTPException
		{
			get
			{
				return(ftpException);
			}
		}

		/// <summary>
		/// <para>True if an exception was raised during the execution of the async method, false otherwise.</para>
		/// </summary>
		public bool ExceptionOccured
		{
			get
			{
				if (ftpException == null)
					return(false);
				return(true);
			}
		}
	}

	/// <summary>
	/// <para>Event argument for events <see cref="E:Melih.FTPClient.FTPClient.OnAsyncConnectCompleted"/>
	///  and <see cref="E:Melih.FTPClient.FTPClient.OnAsyncQuitCompleted"/>.</para>
	/// </summary>
	public class AsyncSessionOpCompletedEventArgs : AsyncConnectionOpCompletedEventArgs
	{
		private string szUser;
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="host">Connected host.</param>
		/// <param name="port">Connection port.</param>
		/// <param name="user">User for whom session has been opened.</param>
		public AsyncSessionOpCompletedEventArgs(string host, int port, string user):base(host, port)
		{
			szUser = user;
		}
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="exception">Exception that has been raised during execution of async method.</param>
		public AsyncSessionOpCompletedEventArgs(Exception exception):base(exception){}
		/// <summary>
		/// <para>User, for whom session has been opened.</para>
		/// </summary>
		public string User
		{
			get
			{
				return(szUser);
			}
		}
	}

	/// <summary>
	/// <para>Event argument for event <see cref="E:Melih.FTPClient.FTPClient.OnAsyncCreateDirectoryCompleted"/>.</para>
	/// </summary>
	public class AsyncCreateDirectoryCompletedEventArgs : AsyncSessionOpCompletedEventArgs
	{
		private string szDirectory;
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="host">Connected host.</param>
		/// <param name="port">Connection port.</param>
		/// <param name="user">User, for whom session has been opened.</param>
		/// <param name="directoryname">Name of the newly created directory.</param>
		public AsyncCreateDirectoryCompletedEventArgs(string host, int port, string user, string directoryname):base(host, port, user)
		{
			szDirectory = directoryname;
		}
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="exception">Exception that has been raised during execution of async method.</param>
		public AsyncCreateDirectoryCompletedEventArgs(Exception exception):base(exception){}
		/// <summary>
		/// <para>Name of the newly created directory.</para>
		/// </summary>
		public string CreatedDirectory
		{
			get
			{
				return(szDirectory);
			}
		}
	}

	/// <summary>
	/// <para>Event argument for events <see cref="E:Melih.FTPClient.FTPClient.OnAsyncGetCurrentDirectoryCompleted"/>
	///  and <see cref="E:Melih.FTPClient.FTPClient.OnAsyncSetCurrentDirectoryCompleted"/>.</para>
	/// </summary>
	public class AsyncCurrentDirectoryOpCompletedEventArgs : AsyncSessionOpCompletedEventArgs
	{
		private string szPath;
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="host">Connected host.</param>
		/// <param name="port">Connection port.</param>
		/// <param name="user">User, for whom session has been opened.</param>
		/// <param name="path">Current remote path.</param>
		public AsyncCurrentDirectoryOpCompletedEventArgs(string host, int port, string user, string path):base(host, port, user)
		{
			szPath = path;
		}
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="exception">Exception that has been raised during execution of async method.</param>
		public AsyncCurrentDirectoryOpCompletedEventArgs(Exception exception):base(exception){}
		/// <summary>
		/// <para>Current remote path.</para>
		/// </summary>
		public string CurrentPath
		{
			get
			{
				return(szPath);
			}
		}
	}

	/// <summary>
	/// <para>Event argument for event <see cref="E:Melih.FTPClient.FTPClient.OnAsyncDeleteFileCompleted"/>.</para>
	/// </summary>
	public class AsyncDeleteFileCompletedEventArgs : AsyncSessionOpCompletedEventArgs
	{
		private string szFileName;
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="host">Connected host.</param>
		/// <param name="port">Connection port.</param>
		/// <param name="user">User, for whom session has been opened.</param>
		/// <param name="filename">Name of the deleted file.</param>
		public AsyncDeleteFileCompletedEventArgs(string host, int port, string user, string filename):base(host, port, user)
		{
			szFileName = filename;
		}
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="exception">Exception that has been raised during execution of async method.</param>
		public AsyncDeleteFileCompletedEventArgs(Exception exception):base(exception){}
		/// <summary>
		/// <para>Name of the deleted file.</para>
		/// </summary>
		public string DeletedFile
		{
			get
			{
				return(szFileName);
			}
		}
	}

	/// <summary>
	/// <para>Event argument for events <see cref="E:Melih.FTPClient.FTPClient.OnAsyncFindFirstFileCompleted"/>
	///  and <see cref="E:Melih.FTPClient.FTPClient.OnAsyncFindNextFileCompleted"/>.</para>
	/// </summary>
	public class AsyncFindFileCompletedEventArgs : AsyncSessionOpCompletedEventArgs
	{
		private FileData fdFile;
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="host">Connected host.</param>
		/// <param name="port">Connection port.</param>
		/// <param name="user">User, for whom session has been opened.</param>
		/// <param name="filedata">FileData structure of found file.</param>
		public AsyncFindFileCompletedEventArgs(string host, int port, string user, FileData filedata):base(host, port, user)
		{
			fdFile = filedata;
		}
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="exception">Exception that has been raised during execution of async method.</param>
		public AsyncFindFileCompletedEventArgs(Exception exception):base(exception){}
		/// <summary>
		/// <para>FileData structure of found file.</para>
		/// </summary>
		public FileData FileFound
		{
			get
			{
				return(fdFile);
			}
		}
	}

	/// <summary>
	/// <para>Event argument for events <see cref="E:Melih.FTPClient.FTPClient.OnAsyncGetFileCompleted"/>
	///  and <see cref="E:Melih.FTPClient.FTPClient.OnAsyncPutFileCompleted"/>.</para>
	/// </summary>
	public class AsyncFileTransferCompletedEventArgs : AsyncSessionOpCompletedEventArgs
	{
		private DataConnectionType dcConn;
		private string szLocalFile, szRemoteFile;
		private FileTransferType ftTransfer;
		private FileTransferResuming ftResuming;
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="host">Connected host.</param>
		/// <param name="port">Connection port.</param>
		/// <param name="user">User, for whom session has been opened.</param>
		/// <param name="localfile">Name of the local file.</param>
		/// <param name="remotefile">Name of the remote file.</param>
		/// <param name="type">File transfer type.</param>
		/// <param name="conntype">Data connection type.</param>
		/// <param name="resume">Resumed unfinished transfer or overwrote.</param>
		public AsyncFileTransferCompletedEventArgs(string host, int port, string user, string localfile, string remotefile, FileTransferType type, DataConnectionType conntype, FileTransferResuming resume):base(host, port, user)
		{
			dcConn = conntype;
			szLocalFile = localfile;
			szRemoteFile = remotefile;
			ftTransfer = type;
			ftResuming = resume;
		}
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="exception">Exception that has been raised during execution of async method.</param>
		public AsyncFileTransferCompletedEventArgs(Exception exception):base(exception){}
		/// <summary>
		/// <para>Data connection type.</para>
		/// </summary>
		public DataConnectionType ConnectionType
		{
			get
			{
				return(dcConn);
			}
		}
		/// <summary>
		/// <para>Name of the local file.</para>
		/// </summary>
		public string LocalFile
		{
			get
			{
				return(szLocalFile);
			}
		}
		/// <summary>
		/// <para>Name of the remote file.</para>
		/// </summary>
		public string RemoteFile
		{
			get
			{
				return(szRemoteFile);
			}
		}
		/// <summary>
		/// <para>File transfer type.</para>
		/// </summary>
		public FileTransferType TransferType
		{
			get
			{
				return(ftTransfer);
			}
		}
		/// <summary>
		/// <para>Resumed unfinished transfer or overwrote.</para>
		/// </summary>
		public FileTransferResuming TransferResuming
		{
			get
			{
				return(ftResuming);
			}
		}
	}

	/// <summary>
	/// <para>Event argument for event <see cref="E:Melih.FTPClient.FTPClient.OnAsyncRemoveDirectoryCompleted"/>.</para>
	/// </summary>
	public class AsyncRemoveDirectoryCompletedEventArgs : AsyncSessionOpCompletedEventArgs
	{
		private string szDirectory;
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="host">Connected host.</param>
		/// <param name="port">Connection port.</param>
		/// <param name="user">User, for whom session has been opened.</param>
		/// <param name="directoryname">Name of the deleted directory.</param>
		public AsyncRemoveDirectoryCompletedEventArgs(string host, int port, string user, string directoryname):base(host, port, user)
		{
			szDirectory = directoryname;
		}
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="exception">Exception that has been raised during execution of async method.</param>
		public AsyncRemoveDirectoryCompletedEventArgs(Exception exception):base(exception){}
		/// <summary>
		/// <para>Name of the deleted directory.</para>
		/// </summary>
		public string RemovedDirectory
		{
			get
			{
				return(szDirectory);
			}
		}
	}

	/// <summary>
	/// <para>Event argument for event <see cref="E:Melih.FTPClient.FTPClient.OnAsyncRenameFileCompleted"/>.</para>
	/// </summary>
	public class AsyncRenameFileCompletedEventArgs : AsyncSessionOpCompletedEventArgs
	{
		private string szOldName, szNewName;
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="host">Connected host.</param>
		/// <param name="port">Connection port.</param>
		/// <param name="user">User, for whom session has been opened.</param>
		/// <param name="renamefrom">Old name of the file that has been renamed.</param>
		/// <param name="renameto">New name of the file that has been renamed.</param>
		public AsyncRenameFileCompletedEventArgs(string host, int port, string user, string renamefrom, string renameto):base(host, port, user)
		{
			szOldName = renamefrom;
			szNewName = renameto;
		}
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="exception">Exception that has been raised during execution of async method.</param>
		public AsyncRenameFileCompletedEventArgs(Exception exception):base(exception){}
		/// <summary>
		/// <para>Old name of the file that has been renamed.</para>
		/// </summary>
		public string OldFileName
		{
			get
			{
				return(szOldName);
			}
		}
		/// <summary>
		/// <para>New name of the file that has been renamed.</para>
		/// </summary>
		public string NewFileName
		{
			get
			{
				return(szNewName);
			}
		}
	}

	/// <summary>
	/// <para>Event argument for event <see cref="E:Melih.FTPClient.FTPClient.OnAsyncSendCommandCompleted"/>.</para>
	/// </summary>
	public class AsyncSendCommandCompletedEventArgs : AsyncSessionOpCompletedEventArgs
	{
		private string szReply, szCommand;
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="host">Connected host.</param>
		/// <param name="port">Connection port.</param>
		/// <param name="user">User, for whom session has been opened.</param>
		/// <param name="command">Command string sent to host.</param>
		/// <param name="reply">Reply from the host.</param>
		public AsyncSendCommandCompletedEventArgs(string host, int port, string user, string command, string reply):base(host, port, user)
		{
			szCommand = command;
			szReply = reply;
		}
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="exception">Exception that has been raised during execution of async method.</param>
		public AsyncSendCommandCompletedEventArgs(Exception exception):base(exception){}
		/// <summary>
		/// <para>Command string sent to host.</para>
		/// </summary>
		public string Command
		{
			get
			{
				return(szCommand);
			}
		}
		/// <summary>
		/// <para>Reply from the host.</para>
		/// </summary>
		public string Reply
		{
			get
			{
				return(szReply);
			}
		}
	}

	/// <summary>
	/// <para>Event argument for events <see cref="E:Melih.FTPClient.FTPClient.OnFileUploadDataSent"/>
	///  and <see cref="E:Melih.FTPClient.FTPClient.OnFileDownloadDataReceived"/>.</para>
	/// </summary>
	public class FileTransferEventArgs : AsyncSessionOpCompletedEventArgs
	{
		private string szLocalFile, szRemoteFile;
		long lBytesTransferred, lTotalFileLength;
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="host">Connected host.</param>
		/// <param name="port">Connection port.</param>
		/// <param name="user">User, for whom session has been opened.</param>
		/// <param name="localfile">Name of the local file that is being transferred to/from.</param>
		/// <param name="remotefile">Name of the remote file that is being transferred from/to.</param>
		/// <param name="bytestransferred">Total bytes transferred so far.</param>
		/// <param name="totalfilelength">Length of the file that is being transferred.</param>
		public FileTransferEventArgs(string host, int port, string user, string localfile, string remotefile, long bytestransferred, long totalfilelength):base(host, port, user)
		{
			szLocalFile = localfile;
			szRemoteFile = remotefile;
			lBytesTransferred = bytestransferred;
			lTotalFileLength = totalfilelength;
		}
		/// <summary>
		/// <para>Constructor.</para>
		/// </summary>
		/// <param name="exception">Exception that has been raised during execution of method.</param>
		public FileTransferEventArgs(Exception exception):base(exception){}
		/// <summary>
		/// <para>Name of the local file that is being transferred to/from.</para>
		/// </summary>
		public string LocalFile
		{
			get
			{
				return(szLocalFile);
			}
		}
		/// <summary>
		/// <para>Name of the remote file that is being transferred from/to.</para>
		/// </summary>
		public string RemoteFile
		{
			get
			{
				return(szRemoteFile);
			}
		}
		/// <summary>
		/// <para>Total bytes transferred so far.</para>
		/// </summary>
		public long BytesTransferred
		{
			get
			{
				return(lBytesTransferred);
			}
		}
		/// <summary>
		/// <para>Length of the file that is being transferred.</para>
		/// </summary>
		public long TotalFileLength
		{
			get
			{
				return(lTotalFileLength);
			}
		}
	}

}
