package com.tri.felipe.safeback.Controller;

import android.content.Context;
import android.util.Log;

import com.tri.felipe.safeback.Model.Skeleton;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Felipe on 14-11-23.
 */
public class SkeletonController {
    private ArrayList<Skeleton> mSkeletons;
    private static SkeletonController sSkeletonController;
    private Context appContext;
    private static String SAVEFILE = "skeleton_records.txt";

    private SkeletonController(Context appContext) {
        mSkeletons = new ArrayList<Skeleton>();
    }

    public static SkeletonController get(Context c) {
        if (sSkeletonController == null) {
            sSkeletonController = new SkeletonController(c.getApplicationContext());
        }
        sSkeletonController.appContext = c;
        return sSkeletonController;
    }

    public SkeletonController(){
        this.mSkeletons = new ArrayList<Skeleton>();
    }

    public ArrayList<Skeleton> getSkeletons(){
        return mSkeletons;
    }

    public int CmToInch(int value){
        return Math.round(value * 0.393701f);
    }

    public int InchToCm(int value){
        return Math.round(value * 2.54f);
    }

    public int KiloToPound(int value){
        return Math.round(value * 2.20462f);
    }

    public int PoundToKilo(int value) {
        return Math.round(value * 0.453592f);
    }

    /**
     * Deletes previously written storage files, then recreates and writes
     * to them all of the current saved Skeletons
     * @throws IOException
     */
    public void saveAllSkeletons() throws IOException {
        FileOutputStream skeleton_records;
        appContext.deleteFile("visit_records.txt");
        skeleton_records = appContext.openFileOutput(SAVEFILE, appContext.MODE_PRIVATE);
        OutputStreamWriter skeletons = new OutputStreamWriter(skeleton_records);

        for (Skeleton s: mSkeletons){
            //Format UUID, Title, Description, neck*3, Shoulder *4, Trunk *3, Elbow * 2
            String data = String.format("%s,%s,%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d",
                    s.getId().toString(),s.getTitle(), s.getDescription(),
                    s.getJoints().get(0).get(0).getAngle(),
                    s.getJoints().get(0).get(1).getAngle(),
                    s.getJoints().get(0).get(2).getAngle(),
                    s.getJoints().get(1).get(0).getAngle(),
                    s.getJoints().get(1).get(1).getAngle(),
                    s.getJoints().get(1).get(2).getAngle(),
                    s.getJoints().get(1).get(3).getAngle(),
                    s.getJoints().get(2).get(0).getAngle(),
                    s.getJoints().get(2).get(1).getAngle(),
                    s.getJoints().get(2).get(2).getAngle(),
                    s.getJoints().get(3).get(0).getAngle(),
                    s.getJoints().get(3).get(1).getAngle());
            Log.d("SkeletonController", data);
            skeletons.write(data);
        }
        skeletons.close();
    }

    public void loadAllSkeletons() throws IOException{
        String[] reader;
        Scanner scanner;
        try {
            scanner = new Scanner(appContext.openFileInput(SAVEFILE));
            Log.d("SkeletonController", "completed reading from dynamic");
            while (scanner.hasNextLine()){
                reader = scanner.nextLine().split(",");
                Log.d("dynamic", reader[1]);
                this.createSkeleton(reader);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
        }
    }

    private void createSkeleton(String[] line) {
        Skeleton s = new Skeleton();
    }
}
