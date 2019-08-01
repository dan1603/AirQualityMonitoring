package com.kalashnyk.denys.airqualitymonitoring.utils.file_controller;

import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.ListFilesFragment;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQuality;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by User on 21.02.2017.
 */

public interface IFileController {
    boolean isExternalStorageReadable();
    boolean createDirectory();
    void writeCSVFile(String fileName, ArrayList<AirQuality> list);
    void writeXMLFile(String fileName, ArrayList<AirQuality> list);
    void writeKMLFile(String fileName, ArrayList<AirQuality> list);
    ArrayList<File> getContentDirectory();
    void openFile(String fileName, ListFilesFragment fragment);
    void shareFile(String fileName, ListFilesFragment fragment);
    boolean deleteSingleFile(String fileName);
    void deleteAllFiles();
    String getPath();
}
