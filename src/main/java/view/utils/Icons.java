package view.utils;

/**
 * This enumeration lists the type of icons that the application will recognize.
 */
public enum Icons {

	/**
	 * 32x32 pixels icon.
	 */
	ICON_32("/pillar.png");

	private static final String IMAGE_PATH = "/pictures";
	private final String path;

	Icons(final String path) {
		this.path = path;
	}

	/**
	 * Returns the path for the icon stored on the disk.
	 *
	 * @return the path.
	 */
	public String getPath() {

		return IMAGE_PATH + path;
	}
}
