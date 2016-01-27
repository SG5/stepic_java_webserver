package templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created on 16.01.16.
 */
public class Html {

    public static final String HTML_DIR = "templates";

    public static String getPage(String fileName, Map<String, Object> data) throws IOException {
        Writer stream = new StringWriter();

        try {
            Template template = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS)
                    .getTemplate(HTML_DIR + File.separator + fileName);
            template.process(data, stream);
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return stream.toString();
    }
}
