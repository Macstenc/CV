package pl.nullpointerexception.hibernate.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("REAL")
public class RealProduct extends BaseProduct{
    private float weight;
    private int width;
    private int length;
    private int height;

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "RealProduct{" +
                "weight=" + weight +
                ", width=" + width +
                ", length=" + length +
                ", height=" + height +
                '}';
    }
}
