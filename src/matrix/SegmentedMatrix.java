/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package matrix;

/**
 *
 * @author crystal
 */
public class SegmentedMatrix implements Matrix {

    private abstract class ItemSearcher {

        protected Matrix matrix;

        protected int relativeI;

        protected int relativeJ;

        protected int segmentI;

        protected int segmentJ;

        protected int absoluteI;

        protected int absoluteJ;

        public ItemSearcher(int absoluteI, int absoluteJ) {
            this.absoluteI = absoluteI;
            this.absoluteJ = absoluteJ;
        }

        public int getRelativeI() {
            return relativeI;
        }

        public int getRelativeJ() {
            return relativeJ;
        }

        public int getAbsoluteI() {
            return absoluteI;
        }

        public int getAbsoluteJ() {
            return absoluteJ;
        }

        public int getSegmentI() {
            return segmentI;
        }

        public int getSegmentJ() {
            return segmentJ;
        }

        public Matrix getMatrix() {
            return matrix;
        }

        abstract void  doSearch();
        
    }

    private class ItemSearcherNext extends ItemSearcher {

        private int iDiff;

        private int jDiff;

        private ItemSearcher prevSearcher;

        public ItemSearcherNext(ItemSearcher prevSearcher, int iDiff, int jDiff, int i, int j) {
            super(i, j);
            this.iDiff = iDiff;
            this.jDiff = jDiff;
            this.prevSearcher = prevSearcher;
        }

        void doSearch() {
            if (iDiff == -1) {
                if (prevSearcher.getRelativeI() == 0) {
                    segmentI = prevSearcher.segmentI - 1;
                    relativeI = segmentsWidth[segmentI] - 1;
                } else {
                    segmentI = prevSearcher.segmentI;
                    relativeI = prevSearcher.relativeI - 1;
                }
            } else if (iDiff == 0) {
                segmentI = prevSearcher.segmentI;
                relativeI = prevSearcher.relativeI;
            } else if (iDiff == 1) {
                if (prevSearcher.getRelativeI() == segmentsWidth[prevSearcher.segmentI] - 1) {
                    segmentI = prevSearcher.segmentI + 1;
                    relativeI = 0;
                } else {
                    segmentI = prevSearcher.segmentI;
                    relativeI = prevSearcher.relativeI + 1;
                }
            }


            if (jDiff == -1) {
                if (prevSearcher.getRelativeJ() == 0) {
                    segmentJ = prevSearcher.segmentJ - 1;
                    relativeJ = segmentsHeight[segmentJ] - 1;
                } else {
                    segmentJ = prevSearcher.segmentJ;
                    relativeJ = prevSearcher.relativeJ - 1;
                }
            } else if (jDiff == 0) {
                segmentJ = prevSearcher.segmentJ;
                relativeJ = prevSearcher.relativeJ;
            } else if (jDiff == 1) {
                if (prevSearcher.getRelativeJ() == segmentsHeight[prevSearcher.segmentJ] - 1) {
                    segmentJ = prevSearcher.segmentJ + 1;
                    relativeJ = 0;
                } else {
                    segmentJ = prevSearcher.segmentJ;
                    relativeJ = prevSearcher.relativeJ + 1;
                }
            }
            matrix = segments[segmentI][segmentJ];
        }

    }

    private class ItemSearcherFull extends ItemSearcher {

        public ItemSearcherFull(int absoluteI, int absoluteJ) {
            super(absoluteI, absoluteJ);
        }

        void doSearch() {
            relativeI = absoluteI;
            relativeJ = absoluteJ;
            for (segmentI = 0; segmentI < segmentsWidth.length; ++segmentI) {
                if (segmentsWidth[segmentI] <= relativeI) {
                    relativeI -= segmentsWidth[segmentI];
                    continue;
                }
                for (segmentJ = 0; segmentJ < segmentsHeight.length; ++segmentJ) {
                    if (segmentsHeight[segmentJ] <= relativeJ) {
                        relativeJ -= segmentsHeight[segmentJ];
                        continue;
                    }
                    matrix = segments[segmentI][segmentJ];
                    return;
                }
                break;
            }

            throw new MatrixAccessException("the size of the matrix is ("
                    + width + ", " + height
                    + ") so item (" + absoluteI + ", " + absoluteJ + ") can not be accessed;");
        }

    }

    private class ItemSearchFactory {

        private ItemSearcher lastSearcher;

        ItemSearcher getSearcher(int i, int j) {
            ItemSearcher rval;
            if (lastSearcher == null) {
                rval = new ItemSearcherFull(i, j);
            } else {
                int iDiff = i - lastSearcher.getAbsoluteI();
                int jDiff = j - lastSearcher.getAbsoluteJ();

                if ((-1 <= iDiff && iDiff <= 1) && (-1 <= jDiff && jDiff <= 1)) {
                    rval = new ItemSearcherNext(lastSearcher, iDiff, jDiff, i, j);
                } else {
                    rval = new ItemSearcherFull(i, j);
                }
            }
            rval.doSearch();
            lastSearcher = rval;
            return rval;
        }
        
    }

    private ItemSearchFactory searchFactory = new ItemSearchFactory();

    private Matrix[][] segments;

    /**
     * stores the width of the segments per-column
     */
    private int[] segmentsWidth;

    /**
     * stores the height of the segments per-row
     */
    private int[] segmentsHeight;

    private int width;

    private int height;

    public SegmentedMatrix() {
        this((short)4, (short)4);
    }

    public SegmentedMatrix(short width, short height) {
        segments = new Matrix[width][height];
        segmentsWidth = new int[width];
        segmentsHeight = new int[height];
        for (short i = 0; i < width; ++i) {
            segmentsWidth[i] = -1;
        }
        for (short j = 0; j < height; ++j) {
            segmentsHeight[j] = -1;
        }
    }

    public Matrix getSegment(int i, int j) {
        return segments[i][j];
    }

    public synchronized void setSegment(int i, int j, Matrix m) {
        if (segmentsWidth[i] == -1) {
            segmentsWidth[i] = m.getWidth();
            width += m.getWidth();
        } else if (m.getWidth() != segmentsWidth[i])
            throw new MatrixSegmentException("all segments in the same column "
                    + "should have the same width");
        if (segmentsHeight[j] == -1) {
            segmentsHeight[j] = m.getHeight();
            height += m.getHeight();
        } else if (m.getHeight() != segmentsHeight[j]) 
            throw new MatrixSegmentException("all segments in the same row "
                    + "should have the same height");
        
        segments[i][j] = m;
    }


    public double get(int i, int j) {
        ItemSearcher search = searchFactory.getSearcher(i, j);
        return search.getMatrix().get(search.getRelativeI(), search.getRelativeJ());
    }

    public void set(int i, int j, double val) {
        ItemSearcher search = searchFactory.getSearcher(i, j);
        search.getMatrix().set(search.getRelativeI(), search.getRelativeJ(), val);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public boolean isQuadratic() {
        return width == height;
    }

    public Matrix clone() {
        return MatrixOperations.clone(this);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\n");
        for (int j = 0; j < getHeight(); ++j) {
            str.append("[");
            for (int i = 0; i < getWidth(); ++i) {
                str.append(get(i, j)).append("\t");
            }
            str.append("]\n");
        }
        return str.toString();
    }

}
