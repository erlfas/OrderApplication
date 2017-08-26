package no.fasmer.orderapplication.entity;

import java.io.Serializable;

public class PartKey implements Serializable {

    private String partNumber;
    private int revision;

    public PartKey() {
    }

    public PartKey(String partNumber, int revision) {
        this.partNumber = partNumber;
        this.revision = revision;
    }

    @Override
    public int hashCode() {
        return ((this.getPartNumber() == null ? 0 : this.getPartNumber().hashCode())
                ^ ((int) this.getRevision()));
    }

    @Override
    public boolean equals(Object otherOb) {

        if (this == otherOb) {
            return true;
        }
        if (!(otherOb instanceof PartKey)) {
            return false;
        }
        PartKey other = (PartKey) otherOb;
        return ((this.getPartNumber() == null ? other.getPartNumber() == null : this.getPartNumber().equals(other.getPartNumber()))
                && (getRevision() == other.getRevision()));
    }

    @Override
    public String toString() {
        return getPartNumber() + " rev" + this.getRevision();
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }
}
