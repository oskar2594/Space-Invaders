/**
 * 
 * MIT LICENSE
 * 
 * Copyright 2022 Philip Gilde & Oskar Stanschus
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * @author Philip Gilde & Oskar Stanschus
 * 
 */
package de.pogs.rl.utils;


public class SpecialMath {
    /**
     * Berechnet x mod n. Die Java-Implementierung über den Operator % gibt für negative Werte einen
     * negativen Modulo, diese Implementierung einen positiven zurück.
     * 
     * @param x
     * @param n
     * @return
     */
    public static float modulus(float x, float n) {
        float r = x % n;
        if (r < 0) {
            r += n;
        }
        return r;
    }

    /**
     * Gibt den kleineren Winkel zwischen zwei Winkeln zurück.
     * 
     * @param angle1
     * @param angle2
     * @param angleBase Die Basis für den Winkel, also zum Beispiel 360 oder 2pi.
     * @return
     */
    public static float angleDifferenceSmaller(float angle1, float angle2, float angleBase) {
        angle1 = angle1 + angleBase / 2f;
        angle2 = angle2 + angleBase / 2f;
        float diff1 = angle1 - angle2;
        float diff2 = 0;
        if (diff1 < 0) {
            diff2 = angleBase + diff1;
        } else {
            diff2 = diff1 - angleBase;
        }
        diff1 %= angleBase;
        diff2 %= angleBase;
        if (Math.abs(diff1) > Math.abs(diff2)) {
            return diff2;
        }
        return diff1;
    }

    /**
     * Berechnet einen Richtungsvektor aus einem LibGDX Winkel, von zum Beispiel einem Sprite.
     */
    public static Vector2 gdxAngleToVector(float angle) {
        return new Vector2(((float) Math.cos((angle + 90) * Math.PI / 180)),
                ((float) Math.sin((angle + 90) * (Math.PI / 180))));
    }

    /**
     * Macht aus einem Vektor einen LibGDX-Winkel, von zum Beispiel einem Sprite.
     * 
     * @param vector
     * @return
     */
    public static float VectorToGdxAngle(Vector2 vector) {
        float angle = (float) Math.toDegrees((float) (Math.atan(vector.x / vector.y)));
        if (vector.x >= 0 && vector.y >= 0) {
            angle = -180 + angle;
        }
        if (vector.x < 0 && vector.y >= 0) {
            angle = 180 + angle;
        }
        return angle;
    }

    /**
     * Ein 2-Dimensionaler Vektor. Operationen verändern diesen nicht.
     */
    public static class Vector2 {
        private float x;
        private float y;

        public Vector2(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public Vector2(double x, double y) {
            this.x = (float) x;
            this.y = (float) y;
        }

        /**
         * Nullvektor, damit dieser nicht immer neu initialisiert werden muss.
         */
        public static final Vector2 zero = new Vector2(0, 0);

        /**
         * Multipliziert den Vektor mit einem Skalar.
         * 
         * @param scalar Skalar
         * @return Das Produkt, ein Vektor.
         */
        public Vector2 mul(float scalar) {
            return new Vector2(x * scalar, y * scalar);
        }

        /**
         * Addiert einen anderen Vektor zu diesem.
         * 
         * @param other Der andere Summand.
         * @return Die Summe der beiden Vektoren, ein Vektor.
         */
        public Vector2 add(Vector2 other) {
            return new Vector2(x + other.x, y + other.y);
        }

        /**
         * Subtrahiert einen anderen Vektor von diesem.
         * 
         * @param other Der Subtrahend.
         * @return Die Differenz der beiden Vektoren, ein Vektor.
         */
        public Vector2 sub(Vector2 other) {
            return new Vector2(x - other.x, y - other.y);
        }

        /**
         * Das Quadrat der Entfernung zu einem anderen Vektor.
         * 
         * @param other Der andere Vektor.
         * @return Das Quadrat der Entfernung.
         */
        public float dst2(Vector2 other) {
            return (float) (Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
        }

        /**
         * Gibt die Richtung des Vektors zurück.
         * 
         * @return Ein Vektor mit gleicher Richtung und Länge 1.
         */
        public Vector2 dir() {
            return new Vector2(x / magn(), y / magn());
        }

        /**
         * Berechnet die Länge des Vektors.
         * 
         * @return Die Länge.
         */
        public float magn() {
            return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        }

        /**
         * Berechnet die Entfernung vom anderen Vektor.
         * 
         * @param other Anderer Vektor.
         * @return Entfernung.
         */
        public float dst(Vector2 other) {
            return this.sub(other).magn();
        }

        /**
         * Wandelt den Vektor in den Winkel seiner Richtung um.
         * 
         * @return Der Winkel der Richtung.
         */
        public float toAngle() {
            return (float) Math.acos(dir().x * 180 / Math.PI);
        }


        @Override
        public String toString() {
            return String.format("Vector2<%f, %f>", x, y);
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        @Override
        public int hashCode() {
            return (Float.valueOf(x).hashCode() * 257) ^ Float.valueOf(y).hashCode();
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Vector2) {
                Vector2 v = (Vector2) o;
                return (v.getX() == x) && (v.getY() == y);
            }
            return false;
        }

        /**
         * Berechnet das Skalarprodukt mit einem anderen Vektor.
         * 
         * @param other Anderer Vektor.
         * @return Skalarprodukt.
         */
        public float dot(Vector2 other) {
            return x * other.getX() + y * other.getY();
        }
    }

    /**
     * Eine zufällige natürliche Zahl im Intervall [a; b].
     * 
     * @param a Obere Grenze des Intervalls.
     * @param b Untere Grenze des Intervalls.
     * @return Die zufällige Zahl.
     */
    public static int randint(int a, int b) {
        return (int) (a + Math.random() * (b - a + 1));
    }
}
