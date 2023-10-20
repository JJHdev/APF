package business.batch.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class SFTPUtil {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

    private Session session = null;
    private Channel channel = null;
    private ChannelSftp channelSftp = null;    

    /**
     * 서버와 연결에 필요한 값들을 가져와 초기화 시킴
     *
     * @param host 서버 주소
     * @param userName 아이디
     * @param password 패스워드
     * @param port 포트번호
     * @param privateKey 개인키
     */
    public Boolean init(String host, String userName, String password, int port, String privateKey) {

        JSch jSch = new JSch();
        Boolean result = false;

        try {
            if(privateKey != null) {//개인키가 존재한다면
                jSch.addIdentity(privateKey);
            }
            session = jSch.getSession(userName, host, port);

            if(privateKey == null && password != null) {//개인키가 없다면 패스워드로 접속
                session.setPassword(password);
            }

            // 프로퍼티 설정
            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no"); // 접속 시 hostkeychecking 여부
            session.setConfig(config);
            session.connect();
            
            //sftp로 접속
            channel = session.openChannel("sftp");
            channel.connect();
            
            result = channel.isConnected();
        } catch (JSchException e) {
            logger.error("JSch Connect Error : ", e);
        }

        channelSftp = (ChannelSftp) channel;
        
        return result;
    }

    /**
     * 디렉토리 생성
     *
     * @param dir 이동할 주소
     * @param mkdirName 생성할 디렉토리명
     */
    public void mkdir(String dir, String mkdirName) {
        if (!this.exists(dir + "/" + mkdirName)) {
            try {
                channelSftp.cd(dir);
                channelSftp.mkdir(mkdirName);
            } catch (SftpException e) {
            	logger.error("Sftp Exception Error : ", e);
            } catch (Exception e) {
            	logger.error("Sftp mkdir Exception Error : ", e);
            }
        }
    }

    /**
     * 디렉토리( or 파일) 존재 여부
     * @param path 디렉토리 (or 파일)
     * @return
     */
    public boolean exists(String path) {
        Vector res = null;
        try {
            res = channelSftp.ls(path);
        } catch (SftpException e) {
            if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                return false;
            }
        }
        return res != null && !res.isEmpty();
    }

    /**
     * 파일 업로드
     *
     * @param dir 저장할 디렉토리
     * @param file 저장할 파일
     * @return 업로드 여부
     */
    public boolean upload(String dir, File file, int mode) {
        boolean isUpload = false;
        SftpATTRS attrs;
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            channelSftp.cd(dir);
            channelSftp.put(in, file.getName(), mode);

            // 업로드했는지 확인
            if (this.exists(dir +"/"+file.getName())) {
                isUpload = true;
            }
        } catch (SftpException e) {
        	logger.error("Sftp Exception Error : ", e);
        } catch (FileNotFoundException e) {
        	logger.error("Sftp Upload FileNotFoundException : ", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
            	logger.error("Sftp FileInputStream IOException Error : ", e);
            }
        }
        return isUpload;
    }

    /**
     * 파일 다운로드
     *
     * @param dir 다운로드 할 디렉토리
     * @param downloadFileName 다운로드 할 파일
     * @param path 다운로드 후 로컬에 저장될 경로(파일명)
     */
    public boolean download(String dir, String downloadFileName, String path) {
        InputStream in = null;
        FileOutputStream out = null;
        Boolean result = false;
        
        try {
            channelSftp.cd(dir);
            in = channelSftp.get(downloadFileName);
        } catch (SftpException e) {
            result = false;
        }

        try {
        	// 두 번째 인자가 false인 경우 덮어쓰기
            out = new FileOutputStream(new File(path), false);
            int i;

            while ((i = in.read()) != -1) {
                out.write(i);
            }
            
            result = true;
        } catch (IOException e) {
        	logger.error("Sftp download IOException Error : ", e);
        	result = false;
        } finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
            	logger.error("Sftp download IOException Error : ", e);
            	result = false;
            } catch (Exception e) {
            	logger.error("Sftp download Exception Error : ", e);
            	result = false;
            }
        }
        
        return result;
    }

    /**
     * 연결 종료
     */
    public void disconnection() {
        channelSftp.quit();
        session.disconnect();
    }
    
    
    
    /**
     * JAR 압축 해제
     *
     * @param jarFilePath			jar 파일 경로
     * @param destinationDirPath	압축 해제될 폴더 경로
     */
    public static boolean extractJar(String jarFilePath, String destinationDirPath) throws Exception {
    	File f = null;
    	JarFile jarFile = null;
    	Boolean result = false;

    	try {
    		
			f = new File(jarFilePath);
			jarFile = new JarFile(f);
			File destDir = new File(destinationDirPath);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}

			Enumeration entries = jarFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				if (entry.isDirectory()) {
					File newDir = new File(destDir, entry.getName());
					if (entry.getName().toUpperCase().indexOf("META-INF") > -1) {
						continue;
					}

					newDir.mkdirs();
				} else {
					if (entry.getName().toUpperCase().indexOf("META-INF") > -1) {
						continue;
					}

					File targetFileObj = new File(destDir, entry.getName());

					InputStream input = null;
					FileOutputStream fileOutput = null;
					BufferedOutputStream buffOutput = null;

					try {
						input = jarFile.getInputStream(entry);
						fileOutput = new FileOutputStream(targetFileObj);
						buffOutput = new BufferedOutputStream(fileOutput);
						byte[] buf = new byte[4096];
						int read = -1;
						while ((read = input.read(buf)) != -1) {
							buffOutput.write(buf, 0, read);
							}
						buffOutput.flush();
						buffOutput.close();

						fileOutput.close();
						input.close();
					} catch (IOException e) {
						throw e;
					} catch (Exception e) {
						throw e;
					} finally {
						try {
							if (buffOutput != null) {
								buffOutput.close();
							}
							} catch (IOException e) {
						} catch (Exception e) {
						} finally {
							buffOutput = null;
						}

						try {
							if (fileOutput != null) {
								fileOutput.close();
							}
						} catch (IOException e) {
						} catch (Exception e) {
						} finally {
							fileOutput = null;
						}

						try {
							if (input != null) {
								input.close();
							}
						} catch (IOException e) {
						} catch (Exception e) {
						} finally {
							input = null;
						}
					}
				}
			}

			result = true;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (jarFile != null) {
					jarFile.close();
				}
			} catch (IOException e) {
			} catch (Exception e) {
			} finally {
				jarFile = null;
			}
		}

    	return result;
    }
}