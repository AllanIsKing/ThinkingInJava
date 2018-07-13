package saaf.common.fmw.report.model.entities.readonly;

import java.io.Serializable;

/**
 * @author CHENRONGXUAN
 *
 */
public class ElementBean implements Serializable {
	private String id;
	private String name;
	private String element;
	private String version;
	private String initAttrs;
	private int frames;
	private String platforms;
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

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getInitAttrs() {
		return initAttrs;
	}

	public void setInitAttrs(String initAttrs) {
		this.initAttrs = initAttrs;
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

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}
}
