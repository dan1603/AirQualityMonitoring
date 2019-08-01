package com.kalashnyk.denys.airqualitymonitoring.utils.file_controller;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.FileProvider;

import com.kalashnyk.denys.airqualitymonitoring.main_operation.fragments.list_files_fragment.ListFilesFragment;
import com.kalashnyk.denys.airqualitymonitoring.model.AirQuality;
import com.kalashnyk.denys.airqualitymonitoring.model.FileModel;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileControllerImpl implements IFileController {

    @Override
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean createDirectory() {
        File docsFolder;
        if (Build.VERSION.SDK_INT >= 19) {
            docsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            if (!docsFolder.exists()) {
                docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
            }
        } else {
            docsFolder = new File(Environment.getExternalStorageDirectory() + "/Documents");
        }
        if (!docsFolder.exists()) {
            File myDir = new File(docsFolder, "/AirQualityMonitoring");
            myDir.mkdir();
        } else {
            File myDir = new File(docsFolder, "/AirQualityMonitoring");
            myDir.mkdir();
        }
        return true;
    }

    @Override
    public ArrayList<File> getContentDirectory() {
        File dir = new File(getPath());
        return new ArrayList<>(Arrays.asList(dir.listFiles()));
    }

    @Override
    public void openFile(String fileName, ListFilesFragment fragment) {
        File file = new File(getPath(), fileName);
        Uri uri = FileProvider.getUriForFile(fragment.getActivity(), fragment.getActivity().getPackageName() + ".provider", file);

        Intent inten = new Intent(Intent.ACTION_VIEW);
        inten.setDataAndType(uri, "text/plain");
        inten.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//
        fragment.getActivity().startActivity(Intent.createChooser(inten, ""));
    }

    @Override
    public void shareFile(String fileName, ListFilesFragment fragment) {
        File file = new File(getPath(), fileName);
        Uri uri = FileProvider.getUriForFile(fragment.getActivity(), fragment.getActivity().getPackageName() + ".provider", file);

        ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder
                .from(fragment.getActivity())
                .addStream(uri)
                .setType("text/plain")
                .addStream(uri);

        Intent chooserIntent = intentBuilder.createChooserIntent();
        List<ResolveInfo> resolvedIntentActivities = fragment.getActivity().getPackageManager().queryIntentActivities(chooserIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
            String packageName = resolvedIntentInfo.activityInfo.packageName;
            fragment.getActivity().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fragment.getActivity().startActivity(chooserIntent);
        }
    }

    @Override
    public boolean deleteSingleFile(String fileName) {
        File dir = new File(getPath());
        File file = new File(dir, fileName);
        return file.delete();
    }

    @Override
    public void deleteAllFiles() {
        File dir = new File(getPath());
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }

    @Override
    public void writeXMLFile(String fileName, ArrayList<AirQuality> list) {
        Serializer serializer = new Persister();
        FileModel fileModel = new FileModel(list, fileName);
        File file = new File(getPath(), fileName + ".xml");
        try {
            file.createNewFile();
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(fileModel.toString());
            bw.flush();
            bw.close();
            serializer.write(fileModel, file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeKMLFile(String fileName, ArrayList<AirQuality> list) {
        File file = new File(getPath(), fileName + ".kml");
        String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<kml xmlns=\"http://www.opengis.net/kml/2.2\"> " +
                "<Placemark>\n";
        String footer =
                "</Placemark> " +
                        "</kml>";
        String content = "";

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            for (AirQuality airQuality : list) {
                if (airQuality.getLatitude() != 0.0d && airQuality.getLongitude() != 0.0d) {
                    content = content + "<name>" + airQuality.getDate() + " " + airQuality.getTime() + "</name>\n" +
                            "<description>" + " MP10 " + airQuality.getP10() + " MP2.5 " + airQuality.getP2_5() + "</description>\n" +
                            "<Point>\n" +
                            "<coordinates>" + airQuality.getLongitude() + "," + airQuality.getLatitude() + ",0" + "</coordinates>\n" +
                            "</Point>\n";
                }

            }
            bw.write(header + content + footer);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeCSVFile(String fileName, ArrayList<AirQuality> list) {
        FileWriter fileWriter = null;

        File file = new File(getPath(), fileName + ".csv");
        try {
            fileWriter = new FileWriter(file);//, "check", "pm5h", "pm5l", "pm10l", "pm10h", "res1", "res2"
            fileWriter.append("date, time, latitude, longitude,  PM10, PM2.5");//address, distance by start (m), check, pm5h, pm5l, pm10l, pm10h, res1, res2
            fileWriter.append("\n");
            for (AirQuality airQuality : list) {
                fileWriter.append(airQuality.getDate());
                fileWriter.append(",");
                fileWriter.append(airQuality.getTime());
                fileWriter.append(",");
                fileWriter.append(String.valueOf(airQuality.getLatitude()));
                fileWriter.append(",");
                fileWriter.append(String.valueOf(airQuality.getLongitude()));
                fileWriter.append(",");
//                fileWriter.append(String.valueOf((int)airQuality.getDistanceByStart()));
//                fileWriter.append(",");

//                fileWriter.append(airQuality.getAddress());
//                fileWriter.append(",");
                fileWriter.append(String.valueOf(airQuality.getP10()));
                fileWriter.append(",");
                fileWriter.append(String.valueOf(airQuality.getP2_5()));
                fileWriter.append("\n");//,
//                fileWriter.append(String.valueOf(airQuality.getCheck()));
//                fileWriter.append(",");
//                fileWriter.append(String.valueOf(airQuality.getPm5h()));
//                fileWriter.append(",");
//                fileWriter.append(String.valueOf(airQuality.getPm5l()));
//                fileWriter.append(",");
//                fileWriter.append(String.valueOf(airQuality.getPm10l()));
//                fileWriter.append(",");
//                fileWriter.append(String.valueOf(airQuality.getPm10h()));
//                fileWriter.append(",");
//                fileWriter.append(String.valueOf(airQuality.getRes1()));
//                fileWriter.append(",");
//                fileWriter.append(String.valueOf(airQuality.getRes2()));
//                fileWriter.append("\n");

            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getPath() {
        String path;
        if (Build.VERSION.SDK_INT >= 19) {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString()
                    + "/AirQualityMonitoring";
        } else {
            path = Environment.getExternalStorageDirectory().toString() + "/Documents/AirQualityMonitoring";
        }
        return path;
    }
}