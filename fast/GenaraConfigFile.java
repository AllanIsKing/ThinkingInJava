package saaf.common.fmw.report.model.entities.readonly;

import java.io.Serializable;

/**
 * @author CHENRONGXUAN
 *
 */
public class GenaraConfigFile implements Serializable {

	private String systemFileLocate;
	private String fileSaveLocate;
	private int frames;
	private String platforms;
	private boolean defaultGeneraServer;

	public String getSystemFileLocate() {
		return systemFileLocate;
	}

	public void setSystemFileLocate(String systemFileLocate) {
		this.systemFileLocate = systemFileLocate;
	}

	public String getFileSaveLocate() {
		return fileSaveLocate;
	}

	public void setFileSaveLocate(String fileSaveLocate) {
		this.fileSaveLocate = fileSaveLocate;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public String getPlatforms() {
		return platforms;
	}

	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}

	public boolean isDefaultGeneraServer() {
		return defaultGeneraServer;
	}

	public void setDefaultGeneraServer(boolean defaultGeneraServer) {
		this.defaultGeneraServer = defaultGeneraServer;
	}
}
