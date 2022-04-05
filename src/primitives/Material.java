package primitives;

public class Material {
    public Double3 kD = new Double3(0);
    public Double3 kS = new Double3(0);
    public int nShininess = 0;

    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    public void setKd(Double3 kD) {
        this.kD = kD;
    }

    public void setKs(Double3 kS) {
        this.kS = kS;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
