package com.demo.properties;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePaths {

    //  Get project main dir
    public static Path path = Paths.get(new File(System.getProperty("user.dir")).getPath());


    //  * * * *    F I L E   P A T H S
    public final static String report_json_folder          = path + "/report/JSON/";
    public final static String report_folder               = path + "/report/";
    public final static String report_html_file            = path + "/report/TestReport.html";
    public final static String report_config_xml_file      = path + "/src/main/resources/extent-config.xml";
    public final static String xml_files_folder            = path + "/src/main/resources/xml_files/";

    public final static String video_files                 = path + "/report/video/";

    public final static String config_properties_file      = path + "/src/main/resources/config.properties";

    public final static String cookies_file                = path + "/report/cookies/";


}


