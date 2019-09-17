import de.hshl.obj.objects.TriangleObject;
import de.hshl.obj.wavefront.Wavefront;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class ObjImport {
    private int[] foxInd;
    private float[][]foxVertices = new float[33][1];
    private float[] torchVert;
    private int[] torchInd;
    private float[] trapVert;
    private int[] trapInd;
    private float[] chestVert;
    private int[] chestInd;

    /**
     * Autor: Furkan Calisir
     *
     * bearbeitet von: Bela Korb
     *
     * Es wurde der ObjLoader 1.2 verwendet (zu finden in dem Library Ordner)
     * Die verschiedenen Objekte werden in Float Arrays eingeladen und die Vertices und Indices zur Verfügung gestellt.
     */
    public ObjImport() {
        try {
            Wavefront.ObjectLoader loader = Wavefront.objects();
            /* //Alter Ansatz für das Importieren der Object Dateien
            loader.ignoreMaterials()
                    .ignoreTextureCoordinates()
                    .ignoreStructures()

                    .loadFromFile(Paths.get("resources\\"));
            List<TriangleObject> objects0 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJWaschbaer.obj"));

            TriangleObject triangleObject0 = objects0.get(0);
            System.out.println(triangleObject0.name);

            TriangleSurface triangle = triangleObject0.surfaces.get(0);

            foxvert = triangle.shape.vertices;
            foxind = triangle.shape.indices;
            */

            //Einladen aller Objekte. Anzahl der Fuchsobjekte nötig zur Animationssimulierung

            List<TriangleObject> objects00 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs01.obj"));

            foxVertices[0] = objects00.get(0).surfaces.get(0).shape.vertices;
            foxInd = objects00.get(0).surfaces.get(0).shape.indices;

            List<TriangleObject> objects01 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs02.obj"));

            foxVertices[1] = objects01.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects02 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs03.obj"));

            foxVertices[2] = objects02.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects03 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs04.obj"));

            foxVertices[3] = objects03.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects04 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs05.obj"));

            foxVertices[4] = objects04.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects05 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs06.obj"));

            foxVertices[5] = objects05.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects06 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs07.obj"));

            foxVertices[6] = objects06.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects07 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs08.obj"));

            foxVertices[7] = objects07.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects08 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs09.obj"));

            foxVertices[8] = objects08.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects09 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs10.obj"));

            foxVertices[9] = objects09.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects10 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs11.obj"));

            foxVertices[10] = objects10.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects11 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs12.obj"));

            foxVertices[11] = objects11.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects12 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs13.obj"));

            foxVertices[12] = objects12.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects13 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs14.obj"));

            foxVertices[13] = objects13.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects14 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs15.obj"));

            foxVertices[14] = objects14.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects15 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs16.obj"));

            foxVertices[15] = objects15.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects16 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs17.obj"));

            foxVertices[16] = objects16.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects17 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs18.obj"));

            foxVertices[17] = objects17.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects18 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs19.obj"));

            foxVertices[18] = objects18.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects19 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs20.obj"));

            foxVertices[19] = objects19.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects20 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs21.obj"));

            foxVertices[20] = objects20.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects21 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs22.obj"));

            foxVertices[21] = objects21.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects22 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs23.obj"));

            foxVertices[22] = objects22.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects23 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs24.obj"));

            foxVertices[23] = objects23.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects24 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs25.obj"));

            foxVertices[24] = objects24.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects25 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs26.obj"));

            foxVertices[25] = objects25.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects26 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs27.obj"));

            foxVertices[26] = objects26.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects27 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs28.obj"));

            foxVertices[27] = objects27.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects28 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs29.obj"));

            foxVertices[28] = objects28.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects29 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs30.obj"));

            foxVertices[29] = objects29.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects30 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs31.obj"));

            foxVertices[30] = objects30.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects31 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs32.obj"));

            foxVertices[31] = objects31.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects32 = loader.ignoreMaterials().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\Fuchs33.obj"));

            foxVertices[32] = objects32.get(0).surfaces.get(0).shape.vertices;

            List<TriangleObject> objects1 = loader.ignoreMaterials().silenceWarnings().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\OBJFackel2.obj"));

            torchVert = objects1.get(0).surfaces.get(0).shape.vertices;
            torchInd = objects1.get(0).surfaces.get(0).shape.indices;


            List<TriangleObject> objects2 = loader.ignoreMaterials().silenceWarnings().ignoreStructures().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\OBJTrap.obj"));

            trapVert = objects2.get(0).surfaces.get(0).shape.vertices;
            trapInd = objects2.get(0).surfaces.get(0).shape.indices;


            List<TriangleObject> objects3 = loader.ignoreMaterials().ignoreStructures().silenceWarnings().ignoreTextureCoordinates().loadFromFile(Paths.get("resources\\OBJs\\OBJChest.obj"));

            chestVert = objects3.get(0).surfaces.get(0).shape.vertices;
            chestInd = objects3.get(0).surfaces.get(0).shape.indices;



        } catch (IOException e) {
            e.printStackTrace();

        }
    }


    public float[][] getFoxVertices() {
        return foxVertices;
    }

    public int[] getFoxIndices() {
        return foxInd;
    }

    public float[] getTorchVert() {
        return torchVert;
    }

    public int[] getTorchInd() {
        return torchInd;
    }

    public float[] getTrapVert() {
        return trapVert;
    }

    public int[] getTrapInd() {
        return trapInd;
    }

    public float[] getChestVert() {
        return chestVert;
    }

    public int[] getChestInd() {
        return chestInd;
    }


}
