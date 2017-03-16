/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JLJava;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 *
 * @author jl
 */
public class InstanceLocker {
  private String lockFilePath = null;
  private RandomAccessFile randomFile = null;
  private FileChannel channel = null;
  private FileLock lock = null;
  
  public boolean lock() throws IOException {
    String workingDir = System.getProperty("user.dir");
    lockFilePath = workingDir + "/lock.txt";
    
    randomFile=new RandomAccessFile(lockFilePath,"rw");
    channel=randomFile.getChannel();
    lock = channel.tryLock();
    return (lock!=null);
  }
  
  public boolean isSingleInstance() throws IOException {
    if (randomFile==null) {
      lock();
    }
    return (lock!=null);
  }
  
  public void unlock() throws IOException {
    if (lock!=null) {
      lock.release();
    }
    if (channel!=null) {
      channel.close();
    }
    if (randomFile!=null) {
      randomFile.close();
    }
  }
  
}
