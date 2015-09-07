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
	/// <para>Data connection type enumerator.</para>
	/// </summary>
	public enum DataConnectionType
	{
		/// <summary>
		/// <para>Opens a socket and listens for a connection from server for data transfer.</para>
		/// </summary>
		Active,
		/// <summary>
		/// <para>Opens a socket and connects to server for data transfer.</para>
		/// </summary>
		Passive
	}

	/// <summary>
	/// <para>FTP file transfer type enumerator.</para>
	/// </summary>
	public enum FileTransferType 
	{
		/// <summary>
		/// <para>Use for transfer of text files.</para>
		/// </summary>
		ASCII,
		/// <summary>
		/// <para>Use for transfer of non-text files.</para>
		/// </summary>
		Binary
	}
	/// <summary>
	/// <para>FTP connection state enumerator.</para>
	/// </summary>
	public enum ConnectionState
	{
		/// <summary>
		/// <para>Connection is not initiated, or closed by succesfully calling <see cref="M:Melih.FTPClient.FTPClient.FTPClose"/>.</para>
		/// </summary>
		Disconnected,
		/// <summary>
		/// <para>Connection is initiated by calling <see cref="M:Melih.FTPClient.FTPClient.FTPOpen"/>.</para>
		/// </summary>
		Connecting,
		/// <summary>
		/// <para><see cref="M:Melih.FTPClient.FTPClient.FTPConnect"/> is called, but has not yet exited, either successfully or unsuccessfully.</para>
		/// </summary>
		Authorizing,
		/// <summary>
		/// <para><see cref="M:Melih.FTPClient.FTPClient.FTPConnect"/> has exited successfully and the connection has been established.</para>
		/// </summary>
		Connected,
		/// <summary>
		/// <para><see cref="M:Melih.FTPClient.FTPClient.FTPClose"/>is called, but has not yet exited.</para>
		/// </summary>
		Disconnecting,
		/// <summary>
		/// <para><see cref="M:Melih.FTPClient.FTPClient.FTPQuit"/>is called, but has not yet exited.</para>
		/// </summary>
		LoggingOff,
		/// <summary>
		/// <para>Session is closed by succesfully calling <see cref="M:Melih.FTPClient.FTPClient.FTPQuit"/>.</para>
		/// </summary>
		LoggedOff
	}
	enum AsyncOperations
	{
		Open,
		Close,
		Connect,
		Quit,
		CreateDirectory,
		DeleteFile,
		FindFirstFile,
		FindNextFile,
		GetCurrentDirectory,
		GetFile,
		PutFile,
		RemoveDirectory,
		RenameFile,
		SendCommand,
		SetCurrentDirectory
	}
	/// <summary>
	/// <para>Resume or overwrite existing transfer enumerator</para>
	/// </summary>
	public enum FileTransferResuming
	{
		/// <summary>
		/// <para>Resume unfinished transfer.</para>
		/// </summary>
		Resume,
		/// <summary>
		/// <para>Overwrite unfinished transfer.</para>
		/// </summary>
		Overwrite
	}
}
