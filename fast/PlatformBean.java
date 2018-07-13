package saaf.common.fmw.report.model.entities.readonly;

import java.io.Serializable;

/**
 * @author CHENRONGXUAN
 *
 */
public class PlatformBean implements Serializable {
	private String id;
	private String name;
	private String version;
	private int frames;
	private String template;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
}
