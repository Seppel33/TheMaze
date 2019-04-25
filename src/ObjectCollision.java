import com.jogamp.opengl.math.VectorUtil;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class ObjectCollision {
    private HitboxObject[] hitboxObjects;
    private Mat originalHitboxObjects;
    private Point[] trapPoints;
    private Point[] originalTrapPoints;
    private Point endPoint;
    private Point originalEndPoint;
    private int collidedTrapObjectNumber;

    /**
     * Autor: Sebastian Schmidt
     * <br>
     *     Konstruktor der Klasse. Hier werden alle Objekte, welche eine Kollisionsberechnung benötigen erhalten und so umgesetzt und verrechnet, damit sie für den weiteren Verlauf
     *     der Berechnungen zu nutzen sind.
     * @param wallpoints Wandpunkte aller Wände im Labyrinth in Matrix Format
     * @param trapPoints Mittelpunkte der Fallen
     * @param endPoint Mittelpunkt des Endbereichs
     */
    public ObjectCollision(Mat wallpoints, Point[] trapPoints, Point endPoint) {
        hitboxObjects = new HitboxObject[wallpoints.rows()];
        originalHitboxObjects = new Mat(wallpoints.rows() * 2, 4, CvType.CV_32FC3, Scalar.all(0));
        for (int i = 0; i < wallpoints.rows(); i++) {
            double[] points = wallpoints.get(i, 0);
            float point1X = (float) points[0];
            float point2X = (float) points[2];
            float point1z = (float) points[1];
            float point2z = (float) points[3];
            float[] frontVec1 = {point2X - point1X, 0, point2z - point1z};
            float[] frontVec2 = {0, 2.8f, 0};
            float[] nf = new float[3];
            VectorUtil.crossVec3(nf, frontVec1, frontVec2);
            nf = VectorUtil.normalizeVec3(nf);
            float[] nb = {-nf[0], -nf[1], -nf[2]};
            float[] rightVec1 = {nf[0], 0, nf[2]};
            float[] rightVec2 = {0, 2.8f, 0};
            float[] nr = new float[3];
            VectorUtil.crossVec3(nr, rightVec1, rightVec2);
            nr = VectorUtil.normalizeVec3(nr);
            float[] nl = {-nr[0], -nr[1], -nr[2]};
            // Definition of positions of vertices for a cuboid
            float[] p0 = {point2X + nf[0] / 8, 0f, point2z + nf[2] / 8}; // 2 front   //3074
            float[] p1 = {point1X + nf[0] / 8, 0f, point1z + nf[2] / 8}; // 3 front
            float[] p2 = {point2X - nf[0] / 8, 0f, point2z - nf[2] / 8}; // 6 back
            float[] p3 = {point1X - nf[0] / 8, 0f, point1z - nf[2] / 8}; // 7 back
            float[] nlf = {(p0[0]-p2[0])+(p0[0]-p1[0]),0,(p0[2]-p2[2])+(p0[2]-p1[2])};
            float[] nlb = {(p2[0]-p0[0])+(p2[0]-p3[0]),0,(p2[2]-p0[2])+(p2[2]-p3[2])};
            float[] nrf = {(p1[0]-p3[0])+(p1[0]-p0[0]),0,(p1[2]-p3[2])+(p1[2]-p0[2])};
            float[] nrb = {(p3[0]-p1[0])+(p3[0]-p2[0]),0,(p3[2]-p1[2])+(p3[2]-p2[2])};
            nlf = VectorUtil.normalizeVec3(nlf);
            nlb = VectorUtil.normalizeVec3(nlb);
            nrf = VectorUtil.normalizeVec3(nrf);
            nrb = VectorUtil.normalizeVec3(nrb);
            hitboxObjects[i] = new HitboxObject(p0, p1, p2, p3, nf, nb, nr, nl, nlf, nlb, nrf, nrb);
            originalHitboxObjects.put(i, 0, p0);
            originalHitboxObjects.put(i, 1, p1);
            originalHitboxObjects.put(i, 2, p2);
            originalHitboxObjects.put(i, 3, p3);
        }
        this.trapPoints = new Point[trapPoints.length];
        originalTrapPoints = new Point[trapPoints.length];
        for(int i = 0; i<trapPoints.length; i++){
            this.trapPoints[i]= trapPoints[i];
            originalTrapPoints[i] = trapPoints[i];
        }
        this.endPoint = new Point();
        this.originalEndPoint = new Point();
        this.endPoint.x = endPoint.x;
        this.endPoint.y = endPoint.y;
        originalEndPoint.x = endPoint.x;
        originalEndPoint.y = endPoint.y;
    }

    /**
     * Autor: Sebastian Schmidt
     * <br>
     *     Berechnung ob und wie der Charakter mit den Wänden kollidiert. Dabei wurde sich an dem Clipping-Prinzip von Cohen Sutherland orientiert. Es wird dabei so gerechnet, dass der Charakter
     *     den Clipping-Bereich darstellt und immer am Ursprung bleibt. Die Wandpunkte werden dementsprechend um dem Charakter rotiert und bewegt.
     *     Es wird nach und nach berechnet, ob eine Kollision möglich sein könnte, bis final entschieden wird, ob dem so ist, oder nicht.
     *     Sollte eine Kollision mit einer Seite einer Wand entstehen, wird der Charakter durch Vektorrechnungen in die richtige Richtung geschoben.
     *     Sollten Kollisionen mit zwei Seiten einer Wand entstehen, wird eine ähnliche Rechnung wie bei einer Seite angewandt.
     *     Sollten Kollisionen mit zwei unterschiedlichen Wänden entstehen, wird die Bewegung des Charakters in diese Richtung blockiert und die restliche Berechnung abgebrochen um Rechenleitung
     *     einzusparen.
     *
     * @param camerapos Aktuelle Kameraposition des Charakters (Kameraperspektive)
     * @param oldCamerapos Kameraposition des Charakters (Kameraperspektive) von vor einem Frame
     * @param moveMat Matrix mit allen Bewegungen und Rotierungen für den jeweiligen Frame für die Objekte
     * @return Neu berechnete Kamera- und Charakter-Position
     */
    public float[] testForWallCollision(float[] camerapos, float[] oldCamerapos, float[] moveMat) {
        boolean isCollision = false;
        boolean tempIsCollision = false;
        boolean isDoubleCollision = false;
        boolean tempP0IsInside = false;
        boolean tempP1IsInside = false;
        boolean tempP2IsInside = false;
        boolean tempP3IsInside = false;
        boolean p0IsInside = false;
        boolean p1IsInside = false;
        boolean p2IsInside = false;
        boolean p3IsInside = false;
        boolean intersectL1 = false;
        boolean intersectL2 = false;
        boolean intersectL3 = false;
        boolean intersectL4 = false;
        int objectNumber = 0;
        float leftX = -0.1f;
        float rightX = 0.1f;
        float frontY = 0.3f;
        float backY = -0.3f;
        //calculate wall position
        for (int h = 0; h < hitboxObjects.length; h++) {

            float[] vert = new float[3];
            float[] vert1 = new float[3];
            float[] vert2 = new float[3];
            float[] vert3 = new float[3];
            for (int e = 0; e < 3; e++) {
                vert[e] = (float) (moveMat[e * 4] * originalHitboxObjects.get(h, 0)[0] + moveMat[e * 4 + 1] * originalHitboxObjects.get(h, 0)[1] + moveMat[e * 4 + 2] * originalHitboxObjects.get(h, 0)[2] + moveMat[e * 4 + 3]);
                vert1[e] = (float) (moveMat[e * 4] * originalHitboxObjects.get(h, 1)[0] + moveMat[e * 4 + 1] * originalHitboxObjects.get(h, 1)[1] + moveMat[e * 4 + 2] * originalHitboxObjects.get(h, 1)[2] + moveMat[e * 4 + 3]);
                vert2[e] = (float) (moveMat[e * 4] * originalHitboxObjects.get(h, 2)[0] + moveMat[e * 4 + 1] * originalHitboxObjects.get(h, 2)[1] + moveMat[e * 4 + 2] * originalHitboxObjects.get(h, 2)[2] + moveMat[e * 4 + 3]);
                vert3[e] = (float) (moveMat[e * 4] * originalHitboxObjects.get(h, 3)[0] + moveMat[e * 4 + 1] * originalHitboxObjects.get(h, 3)[1] + moveMat[e * 4 + 2] * originalHitboxObjects.get(h, 3)[2] + moveMat[e * 4 + 3]);
            }
            hitboxObjects[h].setP0(vert);
            hitboxObjects[h].setP1(vert1);
            hitboxObjects[h].setP2(vert2);
            hitboxObjects[h].setP3(vert3);
        }


        for (int i = 0; i < hitboxObjects.length; i++) {
            //Could be a collision for Line 1 (p0/p1)
            if (!(((hitboxObjects[i].getP0()[0] < leftX) && (hitboxObjects[i].getP1()[0] < leftX)) || ((hitboxObjects[i].getP0()[0] > rightX) && (hitboxObjects[i].getP1()[0] > rightX))
                    || ((hitboxObjects[i].getP0()[2] < backY) && (hitboxObjects[i].getP1()[2] < backY)) || ((hitboxObjects[i].getP0()[2] > frontY) && (hitboxObjects[i].getP1()[2] > frontY)))) {

                //if p0 is inside clipping
                if ((((hitboxObjects[i].getP0()[0] > leftX) && (hitboxObjects[i].getP0()[0] < rightX)) && ((hitboxObjects[i].getP0()[2] > backY) && (hitboxObjects[i].getP0()[2] < frontY)))) {
                    tempP0IsInside = true;
                    tempIsCollision = true;
                }
                //if p1 is inside clipping
                if ((((hitboxObjects[i].getP1()[0] > leftX) && (hitboxObjects[i].getP1()[0] < rightX)) && ((hitboxObjects[i].getP1()[2] > backY) && (hitboxObjects[i].getP1()[2] < frontY)))) {
                    tempP1IsInside = true;
                    tempIsCollision = true;
                }
                    if (hitboxObjects[i].getP1()[0] < leftX) {
                        float t = (leftX - hitboxObjects[i].getP0()[0]) / (-hitboxObjects[i].getP0()[0] + hitboxObjects[i].getP1()[0]);
                        if (((1 - t) * hitboxObjects[i].getP0()[2] + t * hitboxObjects[i].getP1()[2]) > frontY) {
                            t = (frontY - hitboxObjects[i].getP0()[2]) / (-hitboxObjects[i].getP0()[2] + hitboxObjects[i].getP1()[2]);
                            if (((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP1()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP1()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL1 = true;
                            }
                        } else if (((1 - t) * hitboxObjects[i].getP0()[2] + t * hitboxObjects[i].getP1()[2]) > backY && ((1 - t) * hitboxObjects[i].getP0()[2] + t * hitboxObjects[i].getP1()[2]) < frontY) {
                            tempIsCollision = true;
                            intersectL1 = true;
                        } else {
                            t = (backY - hitboxObjects[i].getP0()[2]) / (-hitboxObjects[i].getP0()[2] + hitboxObjects[i].getP1()[2]);
                            if (((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP1()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP1()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL1 = true;
                            }
                        }
                    } else if (hitboxObjects[i].getP1()[0] > rightX) {

                        float t = (rightX - hitboxObjects[i].getP0()[0]) / (-hitboxObjects[i].getP0()[0] + hitboxObjects[i].getP1()[0]);
                        if (((1 - t) * hitboxObjects[i].getP0()[2] + t * hitboxObjects[i].getP1()[2]) > frontY) {
                            t = (frontY - hitboxObjects[i].getP0()[2]) / (-hitboxObjects[i].getP0()[2] + hitboxObjects[i].getP1()[2]);
                            if (((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP1()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP1()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL1 = true;
                            }

                        } else if (((1 - t) * hitboxObjects[i].getP0()[2] + t * hitboxObjects[i].getP1()[2]) > backY && ((1 - t) * hitboxObjects[i].getP0()[2] + t * hitboxObjects[i].getP1()[2]) < frontY) {
                            tempIsCollision = true;
                            intersectL1 = true;
                        } else {
                            t = (backY - hitboxObjects[i].getP0()[2]) / (-hitboxObjects[i].getP0()[2] + hitboxObjects[i].getP1()[2]);
                            if (((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP1()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP1()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL1 = true;
                            }
                        }
                    } else if (hitboxObjects[i].getP1()[2] > frontY) {
                        float t = (frontY - hitboxObjects[i].getP0()[2]) / (-hitboxObjects[i].getP0()[2] + hitboxObjects[i].getP1()[2]);
                        if (((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP1()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP1()[0]) < rightX) {
                            tempIsCollision = true;
                            intersectL1 = true;
                        }

                    } else if (hitboxObjects[i].getP1()[2] < backY) {
                        float t = (backY - hitboxObjects[i].getP0()[2]) / (-hitboxObjects[i].getP0()[2] + hitboxObjects[i].getP1()[2]);
                        if ((((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP1()[0]) > leftX) && (((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP1()[0]) < rightX)) {
                            tempIsCollision = true;
                            intersectL1 = true;
                        }
                    }
            }

            //Could be a collision for Line 2 (p2/p3)
            if (!(((hitboxObjects[i].getP2()[0] < leftX) && (hitboxObjects[i].getP3()[0] < leftX)) || ((hitboxObjects[i].getP2()[0] > rightX) && (hitboxObjects[i].getP3()[0] > rightX))
                    || ((hitboxObjects[i].getP2()[2] < backY) && (hitboxObjects[i].getP3()[2] < backY)) || ((hitboxObjects[i].getP2()[2] > frontY) && (hitboxObjects[i].getP3()[2] > frontY)))) {

                //if p2 is inside clipping
                if ((((hitboxObjects[i].getP2()[0] > leftX) && (hitboxObjects[i].getP2()[0] < rightX)) && ((hitboxObjects[i].getP2()[2] > backY) && (hitboxObjects[i].getP2()[2] < frontY)))) {
                    tempP2IsInside = true;
                    tempIsCollision = true;
                }
                //if p3 is inside clipping
                if ((((hitboxObjects[i].getP3()[0] > leftX) && (hitboxObjects[i].getP3()[0] < rightX)) && ((hitboxObjects[i].getP3()[2] > backY) && (hitboxObjects[i].getP3()[2] < frontY)))) {
                    tempP3IsInside = true;
                    tempIsCollision = true;
                }
                    //System.out.println("p2: " + Arrays.toString(hitboxObjects[i].getP2()) + " p3: " + Arrays.toString(hitboxObjects[i].getP3()));

                    if (hitboxObjects[i].getP3()[0] < leftX) {
                        float t = (leftX - hitboxObjects[i].getP2()[0]) / (-hitboxObjects[i].getP2()[0] + hitboxObjects[i].getP3()[0]);
                        if (((1 - t) * hitboxObjects[i].getP2()[2] + t * hitboxObjects[i].getP3()[2]) > frontY) {
                            t = (frontY - hitboxObjects[i].getP2()[2]) / (-hitboxObjects[i].getP2()[2] + hitboxObjects[i].getP3()[2]);
                            if (((1 - t) * hitboxObjects[i].getP2()[0] + t * hitboxObjects[i].getP3()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP2()[0] + t * hitboxObjects[i].getP3()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL2 = true;
                            }
                        } else if (((1 - t) * hitboxObjects[i].getP2()[2] + t * hitboxObjects[i].getP3()[2]) > backY && ((1 - t) * hitboxObjects[i].getP2()[2] + t * hitboxObjects[i].getP3()[2]) < frontY) {
                            tempIsCollision = true;
                            intersectL2 = true;
                        } else {
                            t = (backY - hitboxObjects[i].getP2()[2]) / (-hitboxObjects[i].getP2()[2] + hitboxObjects[i].getP3()[2]);
                            if (((1 - t) * hitboxObjects[i].getP2()[0] + t * hitboxObjects[i].getP3()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP2()[0] + t * hitboxObjects[i].getP3()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL2 = true;
                            }
                        }
                    } else if (hitboxObjects[i].getP3()[0] > rightX) {

                        float t = (rightX - hitboxObjects[i].getP2()[0]) / (-hitboxObjects[i].getP2()[0] + hitboxObjects[i].getP3()[0]);
                        if (((1 - t) * hitboxObjects[i].getP2()[2] + t * hitboxObjects[i].getP3()[2]) > frontY) {
                            t = (frontY - hitboxObjects[i].getP2()[2]) / (-hitboxObjects[i].getP2()[2] + hitboxObjects[i].getP3()[2]);
                            if (((1 - t) * hitboxObjects[i].getP2()[0] + t * hitboxObjects[i].getP3()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP2()[0] + t * hitboxObjects[i].getP3()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL2 = true;
                            }

                        } else if (((1 - t) * hitboxObjects[i].getP2()[2] + t * hitboxObjects[i].getP3()[2]) > backY && ((1 - t) * hitboxObjects[i].getP2()[2] + t * hitboxObjects[i].getP3()[2]) < frontY) {
                            tempIsCollision = true;
                            intersectL2 = true;
                        } else {
                            t = (backY - hitboxObjects[i].getP2()[2]) / (-hitboxObjects[i].getP2()[2] + hitboxObjects[i].getP3()[2]);
                            if (((1 - t) * hitboxObjects[i].getP2()[0] + t * hitboxObjects[i].getP3()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP2()[0] + t * hitboxObjects[i].getP3()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL2 = true;
                            }
                        }
                    } else if (hitboxObjects[i].getP3()[2] > frontY) {
                        float t = (frontY - hitboxObjects[i].getP2()[2]) / (-hitboxObjects[i].getP2()[2] + hitboxObjects[i].getP3()[2]);
                        if (((1 - t) * hitboxObjects[i].getP2()[0] + t * hitboxObjects[i].getP3()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP2()[0] + t * hitboxObjects[i].getP3()[0]) < rightX) {
                            tempIsCollision = true;
                            intersectL2 = true;
                        }

                    } else if (hitboxObjects[i].getP3()[2] < backY) {
                        float t = (backY - hitboxObjects[i].getP2()[2]) / (-hitboxObjects[i].getP2()[2] + hitboxObjects[i].getP3()[2]);
                        if ((((1 - t) * hitboxObjects[i].getP2()[0] + t * hitboxObjects[i].getP3()[0]) > leftX) && (((1 - t) * hitboxObjects[i].getP2()[0] + t * hitboxObjects[i].getP3()[0]) < rightX)) {
                            tempIsCollision = true;
                            intersectL2 = true;
                        }
                    }
            }

            //Could be a collision for Line 3 (p0/p2)
            if (!(((hitboxObjects[i].getP0()[0] < leftX) && (hitboxObjects[i].getP2()[0] < leftX)) || ((hitboxObjects[i].getP0()[0] > rightX) && (hitboxObjects[i].getP2()[0] > rightX))
                    || ((hitboxObjects[i].getP0()[2] < backY) && (hitboxObjects[i].getP2()[2] < backY)) || ((hitboxObjects[i].getP0()[2] > frontY) && (hitboxObjects[i].getP2()[2] > frontY)))) {

                    //System.out.println("p0: " + Arrays.toString(hitboxObjects[i].getP0()) + " p2: " + Arrays.toString(hitboxObjects[i].getP2()));
                    if (hitboxObjects[i].getP2()[0] < leftX) {
                        float t = (leftX - hitboxObjects[i].getP0()[0]) / (-hitboxObjects[i].getP0()[0] + hitboxObjects[i].getP2()[0]);
                        if (((1 - t) * hitboxObjects[i].getP0()[2] + t * hitboxObjects[i].getP2()[2]) > frontY) {
                            t = (frontY - hitboxObjects[i].getP0()[2]) / (-hitboxObjects[i].getP0()[2] + hitboxObjects[i].getP2()[2]);
                            if (((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP2()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP2()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL3 = true;
                            }
                        } else if (((1 - t) * hitboxObjects[i].getP0()[2] + t * hitboxObjects[i].getP2()[2]) > backY && ((1 - t) * hitboxObjects[i].getP0()[2] + t * hitboxObjects[i].getP2()[2]) < frontY) {
                            tempIsCollision = true;
                            intersectL3 = true;
                        } else {
                            t = (backY - hitboxObjects[i].getP0()[2]) / (-hitboxObjects[i].getP0()[2] + hitboxObjects[i].getP2()[2]);
                            if (((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP2()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP2()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL3 = true;
                            }
                        }
                    } else if (hitboxObjects[i].getP2()[0] > rightX) {

                        float t = (rightX - hitboxObjects[i].getP0()[0]) / (-hitboxObjects[i].getP0()[0] + hitboxObjects[i].getP2()[0]);
                        if (((1 - t) * hitboxObjects[i].getP0()[2] + t * hitboxObjects[i].getP2()[2]) > frontY) {
                            t = (frontY - hitboxObjects[i].getP0()[2]) / (-hitboxObjects[i].getP0()[2] + hitboxObjects[i].getP2()[2]);
                            if (((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP2()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP2()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL3 = true;
                            }

                        } else if (((1 - t) * hitboxObjects[i].getP0()[2] + t * hitboxObjects[i].getP2()[2]) > backY && ((1 - t) * hitboxObjects[i].getP0()[2] + t * hitboxObjects[i].getP2()[2]) < frontY) {
                            tempIsCollision = true;
                            intersectL3 = true;
                        } else {
                            t = (backY - hitboxObjects[i].getP0()[2]) / (-hitboxObjects[i].getP0()[2] + hitboxObjects[i].getP2()[2]);
                            if (((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP2()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP2()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL3 = true;
                            }
                        }
                    } else if (hitboxObjects[i].getP2()[2] > frontY) {
                        float t = (frontY - hitboxObjects[i].getP0()[2]) / (-hitboxObjects[i].getP0()[2] + hitboxObjects[i].getP2()[2]);
                        if (((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP2()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP2()[0]) < rightX) {
                            tempIsCollision = true;
                            intersectL3 = true;
                        }

                    } else if (hitboxObjects[i].getP2()[2] < backY) {
                        float t = (backY - hitboxObjects[i].getP0()[2]) / (-hitboxObjects[i].getP0()[2] + hitboxObjects[i].getP2()[2]);
                        if ((((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP2()[0]) > leftX) && (((1 - t) * hitboxObjects[i].getP0()[0] + t * hitboxObjects[i].getP2()[0]) < rightX)) {
                            tempIsCollision = true;
                            intersectL3 = true;
                        }
                    }
            }

            //Could be a collision for Line 4 (p1/p3)
            if (!(((hitboxObjects[i].getP1()[0] < leftX) && (hitboxObjects[i].getP3()[0] < leftX)) || ((hitboxObjects[i].getP1()[0] > rightX) && (hitboxObjects[i].getP3()[0] > rightX))
                    || ((hitboxObjects[i].getP1()[2] < backY) && (hitboxObjects[i].getP3()[2] < backY)) || ((hitboxObjects[i].getP1()[2] > frontY) && (hitboxObjects[i].getP3()[2] > frontY)))) {

                    if (hitboxObjects[i].getP3()[0] < leftX) {
                        float t = (leftX - hitboxObjects[i].getP1()[0]) / (-hitboxObjects[i].getP1()[0] + hitboxObjects[i].getP3()[0]);
                        if (((1 - t) * hitboxObjects[i].getP1()[2] + t * hitboxObjects[i].getP3()[2]) > frontY) {
                            t = (frontY - hitboxObjects[i].getP1()[2]) / (-hitboxObjects[i].getP1()[2] + hitboxObjects[i].getP3()[2]);
                            if (((1 - t) * hitboxObjects[i].getP1()[0] + t * hitboxObjects[i].getP3()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP1()[0] + t * hitboxObjects[i].getP3()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL4 = true;
                            }
                        } else if (((1 - t) * hitboxObjects[i].getP1()[2] + t * hitboxObjects[i].getP3()[2]) > backY && ((1 - t) * hitboxObjects[i].getP1()[2] + t * hitboxObjects[i].getP3()[2]) < frontY) {
                            tempIsCollision = true;
                            intersectL4 = true;
                        } else {
                            t = (backY - hitboxObjects[i].getP1()[2]) / (-hitboxObjects[i].getP1()[2] + hitboxObjects[i].getP3()[2]);
                            if (((1 - t) * hitboxObjects[i].getP1()[0] + t * hitboxObjects[i].getP3()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP1()[0] + t * hitboxObjects[i].getP3()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL4 = true;
                            }
                        }
                    } else if (hitboxObjects[i].getP3()[0] > rightX) {

                        float t = (rightX - hitboxObjects[i].getP1()[0]) / (-hitboxObjects[i].getP1()[0] + hitboxObjects[i].getP3()[0]);
                        if (((1 - t) * hitboxObjects[i].getP1()[2] + t * hitboxObjects[i].getP3()[2]) > frontY) {
                            t = (frontY - hitboxObjects[i].getP1()[2]) / (-hitboxObjects[i].getP1()[2] + hitboxObjects[i].getP3()[2]);
                            if (((1 - t) * hitboxObjects[i].getP1()[0] + t * hitboxObjects[i].getP3()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP1()[0] + t * hitboxObjects[i].getP3()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL4 = true;
                            }

                        } else if (((1 - t) * hitboxObjects[i].getP1()[2] + t * hitboxObjects[i].getP3()[2]) > backY && ((1 - t) * hitboxObjects[i].getP1()[2] + t * hitboxObjects[i].getP3()[2]) < frontY) {
                            tempIsCollision = true;
                            intersectL4 = true;
                        } else {
                            t = (backY - hitboxObjects[i].getP1()[2]) / (-hitboxObjects[i].getP1()[2] + hitboxObjects[i].getP3()[2]);
                            if (((1 - t) * hitboxObjects[i].getP1()[0] + t * hitboxObjects[i].getP3()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP1()[0] + t * hitboxObjects[i].getP3()[0]) < rightX) {
                                tempIsCollision = true;
                                intersectL4 = true;
                            }
                        }
                    } else if (hitboxObjects[i].getP3()[2] > frontY) {
                        float t = (frontY - hitboxObjects[i].getP1()[2]) / (-hitboxObjects[i].getP1()[2] + hitboxObjects[i].getP3()[2]);
                        if (((1 - t) * hitboxObjects[i].getP1()[0] + t * hitboxObjects[i].getP3()[0]) > leftX && ((1 - t) * hitboxObjects[i].getP1()[0] + t * hitboxObjects[i].getP3()[0]) < rightX) {
                            tempIsCollision = true;
                            intersectL4 = true;
                        }

                    } else if (hitboxObjects[i].getP3()[2] < backY) {
                        float t = (backY - hitboxObjects[i].getP1()[2]) / (-hitboxObjects[i].getP1()[2] + hitboxObjects[i].getP3()[2]);
                        if ((((1 - t) * hitboxObjects[i].getP1()[0] + t * hitboxObjects[i].getP3()[0]) > leftX) && (((1 - t) * hitboxObjects[i].getP1()[0] + t * hitboxObjects[i].getP3()[0]) < rightX)) {
                            tempIsCollision = true;
                            intersectL4 = true;
                        }
                    }
            }
            if (isCollision && tempIsCollision) {
                isDoubleCollision = true;
                i = hitboxObjects.length + 1;
            } else if (tempIsCollision) {
                isCollision = true;
                p0IsInside=tempP0IsInside;
                p1IsInside=tempP1IsInside;
                p2IsInside=tempP2IsInside;
                p3IsInside=tempP3IsInside;
                objectNumber = i;
            }
            tempP0IsInside=false;
            tempP1IsInside=false;
            tempP2IsInside=false;
            tempP3IsInside=false;
            tempIsCollision = false;
        }

        float[] newCameraPos = new float[3];
        if (isDoubleCollision) {
            System.err.println("Double Collision!");
            newCameraPos[0] = oldCamerapos[0];
            newCameraPos[1] = oldCamerapos[1];
            newCameraPos[2] = oldCamerapos[2];
        } else if (isCollision) {
            System.err.println("Collision!");
            float[] n = {camerapos[0] - oldCamerapos[0], 0, camerapos[2] - oldCamerapos[2]};
            n = VectorUtil.normalizeVec3(n);
            if (p0IsInside || p1IsInside || p2IsInside || p3IsInside) {
                int inside= 0;
                if(p0IsInside){
                    inside++;
                }if(p1IsInside){
                    inside++;
                }if(p2IsInside){
                    inside++;
                }if(p3IsInside){
                    inside++;
                }
                System.out.println("inside" +inside);
                if(inside == 1){
                    float[] normal;
                    if(p0IsInside){
                        normal = hitboxObjects[objectNumber].getNrf();
                    }else if(p1IsInside){
                        normal = hitboxObjects[objectNumber].getNlf();
                    }else if(p2IsInside){
                        normal = hitboxObjects[objectNumber].getNrb();
                    }else{
                        normal = hitboxObjects[objectNumber].getNlb();
                    }
                    float[] up = {0,1,0};
                    normal= VectorUtil.crossVec3(normal, n, up);
                    normal = VectorUtil.normalizeVec3(normal);
                    float scalar = n[0] * normal[0] + n[2] * normal[2];
                    newCameraPos[0] = oldCamerapos[0] + ((normal[0] * scalar) * 0.1f);
                    newCameraPos[2] = oldCamerapos[2] + (normal[2] * scalar) * 0.1f;

                }else if(inside == 2){
                    float[] normal;
                    if((p0IsInside && p1IsInside) || (p3IsInside && p2IsInside)){
                        normal = hitboxObjects[objectNumber].getNl();
                    }else{
                        normal = hitboxObjects[objectNumber].getNf();
                    }
                    float scalar = n[0] * normal[0] + n[2] * normal[2];
                    newCameraPos[0] = oldCamerapos[0] + ((normal[0] * scalar) * 0.1f);
                    newCameraPos[2] = oldCamerapos[2] + (normal[2] * scalar) * 0.1f;
                }else{
                    newCameraPos = oldCamerapos;
                }
            } else {
                float[] normal = {0, 0, 0};
                if (intersectL1 || intersectL2) {
                    normal = hitboxObjects[objectNumber].getNr();
                } else if (intersectL3 || intersectL4) {
                    normal = hitboxObjects[objectNumber].getNf();
                }
                float scalar = n[0] * normal[0] + n[2] * normal[2];
                newCameraPos[0] = oldCamerapos[0] + ((normal[0] * scalar) * 0.1f);
                newCameraPos[2] = oldCamerapos[2] + (normal[2] * scalar) * 0.1f;
            }
        } else {
            newCameraPos = camerapos;
        }
        return newCameraPos;

    }

    /**
     * Autor: Sebastian Schmidt
     * <br>
     *     Testet, ob eine Kollision mit einer der Fallen existiert. Der Charakter wird durch 6 einzelne Punkte repräsentiert und die Fallen jeweils durch ihren Mittelpunkt.
     *     Ist der Abstand der Fallenpunkte zu denen des Charakters unter dem Radius der Fallen, dann existiert eine Kollision.
     * @param moveMat Matrix mit allen Bewegungen und Rotierungen für den jeweiligen Frame für die Objekte
     * @return Boolean ob eine Kollision entstanden ist
     */
    public boolean testForTrapCollison(float[] moveMat){
        float diameter = 0.6f;
        float leftX = -0.1f;
        float rightX = 0.1f;
        float frontY = 0.3f;
        float backY = -0.3f;
        collidedTrapObjectNumber = -1;
        Point p0 = new Point(leftX, frontY);
        Point p1 = new Point(rightX, frontY);
        Point p2 = new Point(leftX, backY);
        Point p3 = new Point(rightX, backY);
        Point p01 = new Point(leftX*0.5f+rightX*0.5f, frontY);
        Point p23 = new Point(leftX*0.5f+rightX*0.5f, backY);
        boolean isCollision = false;
        //calculate trap position
        for (int h = 0; h < trapPoints.length; h++) {
            float[] vert = new float[3];
            for (int e = 0; e < 3; e++) {
                vert[e] = (float) (moveMat[e * 4] * originalTrapPoints[h].x + moveMat[e * 4 + 1] * 0 + moveMat[e * 4 + 2] * originalTrapPoints[h].y + moveMat[e * 4 + 3]);
            }
            Point newPoint = new Point(vert[0], vert[2]);
            trapPoints[h] = newPoint;
        }
        for (int j = 0; j < trapPoints.length; j++) {
            //System.out.println("Trappoint " + j + ": " + trapPoints[j].x + " " +trapPoints[j].y);
            //System.out.println("OriginalTrappoint " + j + ": " + originalTrapPoints[j].x + " " +originalTrapPoints[j].y);
            if ((Math.sqrt(Math.pow(trapPoints[j].x - p0.x, 2) + Math.pow(trapPoints[j].y - p0.y, 2)) <= diameter) || (Math.sqrt(Math.pow(trapPoints[j].x - p1.x, 2) + Math.pow(trapPoints[j].y - p1.y, 2)) <= diameter) || (Math.sqrt(Math.pow(trapPoints[j].x - p2.x, 2) + Math.pow(trapPoints[j].y - p2.y, 2)) <= diameter)
                    || (Math.sqrt(Math.pow(trapPoints[j].x - p3.x, 2) + Math.pow(trapPoints[j].y - p3.y, 2)) <= diameter) || (Math.sqrt(Math.pow(trapPoints[j].x - p01.x, 2) + Math.pow(trapPoints[j].y - p01.y, 2)) <= diameter) || (Math.sqrt(Math.pow(trapPoints[j].x - p23.x, 2) + Math.pow(trapPoints[j].y - p23.y, 2)) <= diameter)) {
                isCollision = true;
                collidedTrapObjectNumber = j;
            }
        }
        return isCollision;
    }

    /**
     * Autor: Sebastian Schmidt
     * <br>
     * Testet, ob eine Kollision mit dem Endpunkt existiert. Der Charakter wird durch 6 einzelne Punkte repräsentiert und der Endpunkt durch seinen Mittelpunkt.
     * Ist der Abstand des Endpunktes zu denen des Charakters unter dem Radius des Endbereichs, dann existiert eine Kollision.
     * @param moveMat Matrix mit allen Bewegungen und Rotierungen für den jeweiligen Frame für die Objekte
     * @return Boolean ob eine Kollision entstanden ist
     */
    public boolean testForEndCollison(float[] moveMat){
        float diameter = 0.6f;
        float leftX = -0.1f;
        float rightX = 0.1f;
        float frontY = 0.3f;
        float backY = -0.3f;
        Point p0 = new Point(leftX, frontY);
        Point p1 = new Point(rightX, frontY);
        Point p2 = new Point(leftX, backY);
        Point p3 = new Point(rightX, backY);
        Point p01 = new Point(leftX*0.5f+rightX*0.5f, frontY);
        Point p23 = new Point(leftX*0.5f+rightX*0.5f, backY);
        boolean isCollision = false;
        //calculate trap position
            float[] vert = new float[3];
            for (int e = 0; e < 3; e++) {
                vert[e] = (float) (moveMat[e * 4] * originalEndPoint.x + moveMat[e * 4 + 1] * 0 + moveMat[e * 4 + 2] * originalEndPoint.y + moveMat[e * 4 + 3]);
            }
            endPoint.x = vert[0];
            endPoint.y = vert[2];
        //System.out.println(endPoint.x + " " + endPoint.y);
        if ((Math.sqrt(Math.pow(endPoint.x - p0.x, 2) + Math.pow(endPoint.y - p0.y, 2)) <= diameter) || (Math.sqrt(Math.pow(endPoint.x - p1.x, 2) + Math.pow(endPoint.y - p1.y, 2)) <= diameter) || (Math.sqrt(Math.pow(endPoint.x - p2.x, 2) + Math.pow(endPoint.y - p2.y, 2)) <= diameter)
                || (Math.sqrt(Math.pow(endPoint.x - p3.x, 2) + Math.pow(endPoint.y - p3.y, 2)) <= diameter) || (Math.sqrt(Math.pow(endPoint.x - p01.x, 2) + Math.pow(endPoint.y - p01.y, 2)) <= diameter) || (Math.sqrt(Math.pow(endPoint.x - p23.x, 2) + Math.pow(endPoint.y - p23.y, 2)) <= diameter)) {
                isCollision = true; }
        return isCollision;
    }

    public int getCollidedTrapObjectNumber(){
        return collidedTrapObjectNumber;
    }
}
