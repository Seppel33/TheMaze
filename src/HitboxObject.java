public class HitboxObject {
    private float[] p0;
    private float[] p1;
    private float[] p2;
    private float[] p3;
    private float[] nf;
    private float[] nb;
    private float[] nr;
    private float[] nl;
    private float[] nlf;
    private float[] nlb;
    private float[] nrf;
    private float[] nrb;

    /**
     * Autor: Sebastian Schmidt
     *
     * Erstellung der Punkte für die Hitboxen der Wände in 2D
     *
     * @param p0
     *  -> Eckpunkt 1
     * @param p1
     *  -> Eckpunkt 2
     * @param p2
     *  -> Eckpunkt 3
     * @param p3
     *  -> Eckpunkt 4
     * @param nf
     *  -> Normalenvektor 1
     * @param nb
     *  -> Normalenvektor 2
     * @param nr
     *  -> Normalenvektor 3
     * @param nl
     *  -> Normalenvektor 4
     * @param nlf
     *  -> nicht mehr verwendet
     * @param nlb
     *  -> nicht mehr verwendet
     * @param nrf
     *  -> nicht mehr verwendet
     * @param nrb
     *  -> nicht mehr verwendet
     */
    public HitboxObject(float[] p0, float[] p1, float[] p2, float[] p3, float[] nf,float[] nb,float[] nr,float[] nl, float[] nlf, float[] nlb, float[] nrf, float[] nrb){

        this.p0 = p0;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.nf = nf;
        this.nb = nb;
        this.nr = nr;
        this.nl = nl;
        this.nlf = nlf;
        this.nlb = nlb;
        this.nrf = nrf;
        this.nrb = nrb;
    }



    public float[] getP0() {
        return p0;
    }

    public void setP0(float[] p0) {
        this.p0 = p0;
    }

    public float[] getP1() {
        return p1;
    }

    public void setP1(float[] p1) {
        this.p1 = p1;
    }

    public float[] getP2() {
        return p2;
    }

    public void setP2(float[] p2) {
        this.p2 = p2;
    }

    public float[] getP3() {
        return p3;
    }

    public void setP3(float[] p3) {
        this.p3 = p3;
    }

    public float[] getNf() {
        return nf;
    }

    public float[] getNr() {
        return nr;
    }

    public float[] getNl() {
        return nl;
    }

    public float[] getNlf() {
        return nlf;
    }

    public float[] getNlb() {
        return nlb;
    }

    public float[] getNrf() {
        return nrf;
    }

    public float[] getNrb() {
        return nrb;
    }
}
