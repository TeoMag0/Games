public class Vector2 {
    private float x, y;

    public Vector2(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the x value of this vector.
     * @return
     */
    public float getX(){
        return x;
    }
    /**
     * Returns the x value of this vector as an int.
     * @return
     */
    public int intX(){
        return (int)x;
    }
    /**
     * Returns the y value of this vector as an int.
     * @return
     */
    public int intY(){
        return (int)y;
    }
    /**
     * Returns the y value of this vector.
     * @return
     */
    public float getY(){
        return y;
    }
    public Vector2 getDrawnCoords(){
        return new Vector2(x+640, y+360);
    }
    /**
     * Sets the velocity using components.
     * @param x
     * @param y
     */
    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }
    /**
     * Adds the inputted vector to this vector.
     * @param vector
     */
    public void add(Vector2 vector){
        x += vector.x;
        y += vector.y;
    }
    /**
     * Subtracts the inputted vector from this vector.
     * @param vector
     */
    public void subtract(Vector2 vector){
        x -= vector.x;
        y -= vector.y;
    }
    /**
     * Returns a new unit vector in the same direction as the vector
     * @return
     */
    public Vector2 normalized(){
        return new Vector2(x/magnitude(), y/magnitude());
    }
    /**
     * Returns the magnitude of this vector
     * @return
     */
    public float magnitude(){
        return (float)Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }
    /*
     * Returns a clone of this vector
     */
    public Vector2 clone(){
        return new Vector2(x, y);
    }

    /**
     * Returns the sum of two vectors.
     * @param vector1
     * @param vector2
     * @return
     */
    public static Vector2 sum(Vector2 vector1, Vector2 vector2){
        return new Vector2(vector1.x + vector2.x, vector1.y + vector2.y);
    }

    /**
     * Returns the difference of two vectors.
     * @param vector1
     * @param vector2
     * @return
     */
    public static Vector2 difference(Vector2 vector1, Vector2 vector2){
        return new Vector2(vector1.x - vector2.x, vector1.y - vector2.y);
    }
    /**
     * returns the distance between the vectors
     * @return
     */
    public static float distance(Vector2 vector1, Vector2 vector2){
        return (float)Math.sqrt(Math.pow(Vector2.difference(vector1, vector2).x, 2) + Math.pow(Vector2.difference(vector1, vector2).y, 2));
    }
    /**
     * Returns new vector product of a vector and a scalar
     * @param vector
     * @param num
     * @return
     */
    public static Vector2 multiply(Vector2 vector, float num){
        return new Vector2(vector.getX() * num, vector.getY() * num);
    }

    /**
     * Returns a new Vector <0,0>
     * @return
     */
    public static Vector2 zero(){
        return new Vector2(0, 0);
    }
    /**
     * Returns whether Vectors are equal
     * @return
     */
    public boolean equals(Vector2 v){
        if(getX() == v.getX() && getY() == v.getY())
            return true;
        return false;
    }

    @Override
    public String toString(){
        return "<"+getX()+", "+getY()+">";
    }
}
