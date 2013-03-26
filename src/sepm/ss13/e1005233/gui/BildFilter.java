package sepm.ss13.e1005233.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class BildFilter extends FileFilter {

	public BildFilter() {
		super();
	}

	@Override
	public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
 
        String endung = getEndung(f);
            if (endung != null && (endung.equals("tiff") ||
                endung.equals("gif") ||
                endung.equals("jpeg") ||
                endung.equals("jpg") ||
                endung.equals("png"))) {
                    return true;
            }
            return false;
	}
	
    private String getEndung(File f) {
    	
        String dateiname = f.getName();
        return dateiname.substring(dateiname.lastIndexOf('.')+1).toLowerCase();
    }

    public static String getFileName(String p) {
    	int last = Math.max(p.lastIndexOf("/"), p.lastIndexOf("\\"));
    	return p.substring(last + 1);
    }
    
	@Override
	public String getDescription() {
		return "Bilder";
	}
	

}
